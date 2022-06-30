package com.example.kotlinroom

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import com.example.kotlinroom.databinding.ActivityNewProductBinding
import com.example.kotlinroom.utils.Constants.PRODUCT_NAME
import com.example.kotlinroom.utils.Constants.PRODUCT_PRICE

class NewProductActivity : AppCompatActivity() {

    private var binding: ActivityNewProductBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityNewProductBinding.inflate(layoutInflater)
        val view = binding?.root

        setContentView(view)

        binding?.btnSave?.setOnClickListener {
            val replyIntent = Intent()
            val productName = binding?.etProductName?.text
            val productPrice = binding?.etProductPrice?.text

            if (!TextUtils.isEmpty(productName) && !TextUtils.isEmpty(productPrice)
            ) {
                val bundle = Bundle()

                bundle.putString(PRODUCT_NAME, productName.toString())
                bundle.putDouble(PRODUCT_PRICE, productPrice.toString().toDouble())

                replyIntent.putExtra(EXTRA_REPLY, bundle)

                setResult(Activity.RESULT_OK, replyIntent)
            } else
                setResult(Activity.RESULT_CANCELED, replyIntent)


            finish()
        }
    }

    companion object {
        const val EXTRA_REPLY = "com.example.kotlinroom.REPLY"
    }
}