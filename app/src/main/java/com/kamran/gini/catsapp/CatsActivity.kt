package com.kamran.gini.catsapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import coil.api.load
import com.kamran.cats.CatsClickListener
import com.kamran.cats.MyCustomView
import kotlinx.android.synthetic.main.activity_cats.*

class CatsActivity : AppCompatActivity(), CatsClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cats)

        my_custom_view.apply {
            this.setListener(this@CatsActivity)
        }
    }

    override fun getCatImage(url: String) {
        Log.e("CATS ACTIITY", url)
    }
}