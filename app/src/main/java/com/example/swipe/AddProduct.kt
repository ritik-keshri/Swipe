package com.example.swipe

import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.swipe.api.ApiInterface
import com.example.swipe.api.RetrofitInstance
import com.example.swipe.model.AddProductResponse
import okhttp3.MultipartBody
import retrofit2.Call
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.FileOutputStream

class AddProduct : AppCompatActivity() {

    lateinit var img: ImageView
    lateinit var name: TextView
    lateinit var type: TextView
    lateinit var price: TextView
    lateinit var tax: TextView
    lateinit var button: Button
    lateinit var imageUri: Uri

    private val contract = registerForActivityResult(ActivityResultContracts.GetContent()) {
        imageUri = it!!
        img.setImageURI(it)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_product)

        img = findViewById(R.id.addProductImage)
        name = findViewById(R.id.addProductName)
        type = findViewById(R.id.addProductType)
        price = findViewById(R.id.addProductPrice)
        tax = findViewById(R.id.addProductTax)
        button = findViewById(R.id.submit)

        button.setOnClickListener(View.OnClickListener { postData() })

        img.setOnClickListener { contract.launch("image/*") }
    }

    fun postData() {
        if (name.text.isEmpty())
            name.error = "Can't be empty"
        else if (type.text.isEmpty())
            type.error = "Can't be empty"
        else if (price.text.isEmpty())
            price.error = "Can't be empty"
        else if (tax.text.isEmpty())
            tax.error = "Can't be empty"
        else {
            sendData(
                name.text.toString(),
                type.text.toString(),
                price.text.toString().toFloat(),
                tax.text.toString().toFloat()
            )
        }
    }

    private fun sendData(name: String, type: String, price: Float, tax: Float) {
        val filesDir = applicationContext.filesDir
        val file = File(filesDir, "image.png")
        val inputStream = contentResolver.openInputStream(imageUri)
        val outputStream = FileOutputStream(file)
        inputStream!!.copyTo(outputStream)

        val requestBody = file.asRequestBody("image/*".toMediaTypeOrNull())
        val image = MultipartBody.Part.createFormData("file", file.name, requestBody)

        val apiInterface =
            RetrofitInstance.getInstance().create(ApiInterface::class.java)

        apiInterface.addProduct(name, type, price, tax, image)
            .enqueue(object : Callback<AddProductResponse?> {
                override fun onResponse(
                    call: Call<AddProductResponse?>,
                    response: Response<AddProductResponse?>
                ) {
                    Toast.makeText(applicationContext, "Data Added", Toast.LENGTH_SHORT).show()
                }

                override fun onFailure(call: Call<AddProductResponse?>, t: Throwable) {
                }
            })
    }
}