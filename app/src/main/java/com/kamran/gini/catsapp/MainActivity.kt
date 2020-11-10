package com.kamran.gini.catsapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import coil.api.load
import com.kamran.cats.ui.interfaces.CatsClickListener
import com.kamran.cats.core.CatsListDialog
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.selected_cat_image

class MainActivity : AppCompatActivity(), CatsClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        go_to_cats_list.setOnClickListener {
            CatsListDialog(this)
                /*.title("test title")
                .title(R.string.app_name)
                .backIcon(R.drawable.baseline_keyboard_backspace_white_24dp)
                .toolbarColor(R.color.design_default_color_primary_dark)
                .toolbarTextColor(R.color.design_default_color_secondary)*/
                .showNow()
        }
    }

    override fun getCatImage(url: String) {
        selected_cat_image.load(url)
    }

}