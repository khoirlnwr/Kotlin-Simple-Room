package com.example.kotlinroom

import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Update
import com.example.kotlinroom.adapters.ProductListAdapter
import com.example.kotlinroom.databinding.ActivityMainBinding
import com.example.kotlinroom.repository.Product
import com.example.kotlinroom.repository.ProductApplication
import com.example.kotlinroom.utils.Constants.PRODUCT_ID
import com.example.kotlinroom.utils.Constants.PRODUCT_NAME
import com.example.kotlinroom.utils.Constants.PRODUCT_PRICE
import com.example.kotlinroom.utils.RecyclerItemClickListener
import com.example.kotlinroom.view_models.ProductViewModel
import com.example.kotlinroom.view_models.ProductViewModelFactory

class MainActivity : AppCompatActivity() {

    private var binding: ActivityMainBinding? = null
    private val productViewModel: ProductViewModel by viewModels {
        ProductViewModelFactory((application as ProductApplication).repository)
    }

    private val newProductActivityRequestCode = 1
    private val updateProductActivityRequestCode = 2
    private var productList: List<Product>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding?.root

        setContentView(view)

        // setup for recycler view
        binding?.rvProductsList?.layoutManager = LinearLayoutManager(this)

        // create an observer
        productViewModel.allProducts.observe(this, Observer {
            productList = it
            val adapter = ProductListAdapter(it)

            binding?.rvProductsList?.adapter = adapter
        })

        val recyclerView = binding?.rvProductsList
        binding?.rvProductsList?.addOnItemTouchListener(
            RecyclerItemClickListener(
                this,
                recyclerView!!,
                object : RecyclerItemClickListener.OnItemClickListener {
                    override fun onItemClick(view: View, position: Int) {

                        productList?.let {
                            val id: Int? = it[position].id
                            val name: String = it[position].productName
                            val price: Double = it[position].productPrice

                            val intent =
                                Intent(this@MainActivity, UpdateProductActivity::class.java)

                            intent.putExtra(PRODUCT_ID, id)
                            intent.putExtra(PRODUCT_NAME, name)
                            intent.putExtra(PRODUCT_PRICE, price)

                            startActivityForResult(intent, updateProductActivityRequestCode)
                        }

                    }

                    override fun onItemLongClick(view: View?, position: Int) {
                        // Log.e(TAG, "onItemLongClick: ")
                    }
                },
            )
        )

        productViewModel.countProducts.observe(this, Observer {
            Log.e(TAG, "onCreate: count: $it")
        })

        binding?.fabAdd?.setOnClickListener {
            val intent = Intent(this@MainActivity, NewProductActivity::class.java)
            startActivityForResult(intent, newProductActivityRequestCode)
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {

            var bundle: Bundle? = null

            if (requestCode == newProductActivityRequestCode) {
                bundle = data?.getBundleExtra(NewProductActivity.EXTRA_REPLY)

                bundle?.let {
                    val name = bundle!!.getString(PRODUCT_NAME)
                    val price = bundle!!.getDouble(PRODUCT_PRICE)

                    val product = Product(productName = name.toString(), productPrice = price)
                    productViewModel.insert(product)
                }

            } else if (requestCode == updateProductActivityRequestCode) {
                bundle = data?.getBundleExtra(UpdateProductActivity.EXTRA_UPDATE_REPLY)

                bundle?.let {
                    val id = bundle.getInt(PRODUCT_ID)
                    val name = bundle.getString(PRODUCT_NAME)
                    val price = bundle.getDouble(PRODUCT_PRICE)

                    productViewModel.update(name!!, price, id)
                }
            }


        } else
            Toast.makeText(this, R.string.empty_not_saved, Toast.LENGTH_SHORT).show()

    }
}