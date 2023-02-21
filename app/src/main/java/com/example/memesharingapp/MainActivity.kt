package com.example.memesharingapp

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.engine.Resource
import com.bumptech.glide.request.RequestListener
import javax.sql.CommonDataSource
import javax.sql.DataSource

class MainActivity : AppCompatActivity() {
    var currentImageUrl :String?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()
        loadMeme()
    }
 @SuppressLint("SuspiciousIndentation")
 private fun loadMeme(){
     val progressBarVariable = findViewById<ProgressBar>(R.id.progressBar)
     progressBarVariable.visibility = View.VISIBLE
     val queue = Volley.newRequestQueue(this)
     val url = "https://meme-api.com/gimme"
val memeImageView = findViewById<ImageView>(R.id.memeImageView)
// Request a string response from the provided URL.
     val jsonObjectRequest = JsonObjectRequest(
         Request.Method.GET, url,null,
         Response.Listener { response ->
      currentImageUrl = response.getString("url")
             Glide.with(this).load(currentImageUrl).listener(object :RequestListener<Drawable>{
                 override fun onLoadFailed(
                     e:GlideException?,
                     model:Any?,
                     target: com.bumptech.glide.request.target.Target<Drawable>?,
                     isFirstResource: Boolean
                 ):Boolean {
             progressBarVariable.visibility = View.GONE
                     return false

                 }

                 override fun onResourceReady(
                     resource: Drawable?,
                     model: Any?,
                     target: com.bumptech.glide.request.target.Target<Drawable>?,
                     dataSource: com.bumptech.glide.load.DataSource?,
                     isFirstResource: Boolean
                 ): Boolean {
                     progressBarVariable.visibility = View.GONE
               return false
                 }

             }).into(memeImageView)
         },
         Response.ErrorListener {
             Toast.makeText(this , "something went wrong.." ,Toast.LENGTH_LONG).show()
         })

// Add the request to the RequestQueue.
     queue.add(jsonObjectRequest)
 }
    @SuppressLint("SuspiciousIndentation")
    fun sharememe(view: View) {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_TEXT , "Hey Checkout this meme I got from reddit 😂😂... $currentImageUrl")
     val chooser = Intent.createChooser(intent , "Share this meme using... ")
        startActivity(chooser)
    }
    fun nextmeme(view: View) {
        loadMeme()
    }
}