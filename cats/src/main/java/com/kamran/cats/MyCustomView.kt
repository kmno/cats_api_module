package com.kamran.cats

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import androidx.recyclerview.widget.RecyclerView


/**
 * Created by Kamran Noorinejad on 11/7/2020 AD 16:36.
 * Edited by Kamran Noorinejad on 11/7/2020 AD 16:36.
 */
class MyCustomView(context: Context, attrs: AttributeSet?) : RecyclerView(context, attrs) {

    init {
        val a: TypedArray = context.obtainStyledAttributes(attrs, R.styleable.MyCustomView)

        val str = a.getString(R.styleable.MyCustomView_attr_one)

        //do something

        a.recycle()
    }

    companion object {

        fun getSample(param:String){
        }

        fun getSample2(param2:String){
        }
    }
}