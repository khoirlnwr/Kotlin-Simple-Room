package com.example.kotlinroom

import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.activity.viewModels
import com.example.kotlinroom.databinding.ActivityUpdateProductBinding
import com.example.kotlinroom.repository.ProductApplication
import com.example.kotlinroom.utils.Constants.PRODUCT_ID
import com.example.kotlinroom.utils.Constants.PRODUCT_NAME
import com.example.kotlinroom.utils.Constants.PRODUCT_PRICE
import com.example.kotlinroom.view_models.ProductViewModel
import com.example.kotlinroom.view_models.ProductViewModelFactory

class UpdateProductActivity : AppCompatActivity() {

    private var binding: ActivityUpdateProductBinding? = null
    private var productId: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateProductBinding.inflate(layoutInflater)
        val view = binding?.root

        setContentView(view)

        val nameFromPrevActivity = intent.getStringExtra(PRODUCT_NAME)
        val priceFromPrevActivity = intent.getDoubleExtra(PRODUCT_PRICE, 0.0).toString()

        productId = intent.getIntExtra(PRODUCT_ID, 1)
        binding?.etUpdateProductName?.setText(nameFromPrevActivity)
        binding?.etUpdatePrice?.setText(priceFromPrevActivity)

        binding?.btnUpdate?.setOnClickListener {
            val replyIntent = Intent()
            val name = binding?.etUpdateProductName?.text
            val price = binding?.etUpdatePrice?.text

            if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(price)) {
                val bundle = Bundle()

                bundle.putInt(PRODUCT_ID, productId!!)
                bundle.putString(PRODUCT_NAME, name.toString())
                bundle.putDouble(PRODUCT_PRICE, price.toString().toDouble())

                replyIntent.putExtra(EXTRA_UPDATE_REPLY, bundle)
                setResult(Activity.RESULT_OK, replyIntent)

            } else
                setResult(Activity.RESULT_CANCELED, replyIntent)

            finish()
        }
    }


    companion object {
        const val EXTRA_UPDATE_REPLY = "REPLY_UPDATE"
    }
}