package com.kamran.cats.core

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kamran.cats.R
import com.kamran.cats.ui.adapters.CatsAdapter
import com.kamran.cats.data.api.ApiClientProvider
import com.kamran.cats.data.model.Cat
import com.kamran.cats.ui.interfaces.CatsClickListener
import kotlinx.android.synthetic.main.main_layout.view.*
import kotlinx.android.synthetic.main.toolbar.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/**
 * Created by Kamran Noorinejad on 11/7/2020 AD 16:36.
 * Edited by Kamran Noorinejad on 11/7/2020 AD 16:36.
 */
class MyCustomView : RelativeLayout {

    private val TAG = "CATS MODULE"

    private var inflater: LayoutInflater
    private var apiClient = ApiClientProvider.createApiClient()

    private lateinit var catsAdapter: CatsAdapter
    private var allCats = mutableListOf<Cat>()
    private var listViewManager: RecyclerView.LayoutManager = GridLayoutManager(context, 2)

    private lateinit var parentActivity : AppCompatActivity
    private lateinit var listener : CatsClickListener

    private var actionBarTitle : String = Constants.actionBarDefaultTitle
    private var actionBarColor : String = Constants.actionBarDefaultColor
    private var actionBarTextColor : String = Constants.actionBarDefaultTextColor
    private var actionBarBackImage : Drawable? = null

    private lateinit var moduleTypedArray: TypedArray

    constructor(context: Context) : super(context) {
        inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        setParentActivity(context as AppCompatActivity)

        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        moduleTypedArray = context.obtainStyledAttributes(
            attrs,
            R.styleable.MyCustomView
        )
        inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        //get properties
       if(moduleTypedArray.getString(R.styleable.MyCustomView_action_bar_title) != null )
           actionBarTitle = moduleTypedArray.getString(R.styleable.MyCustomView_action_bar_title).toString()

        if(moduleTypedArray.getString(R.styleable.MyCustomView_action_bar_background_color) != null )
            actionBarColor = moduleTypedArray.getString(R.styleable.MyCustomView_action_bar_background_color)!!

        if(moduleTypedArray.getString(R.styleable.MyCustomView_action_bar_text_color) != null )
            actionBarTextColor = moduleTypedArray.getString(R.styleable.MyCustomView_action_bar_text_color)!!

        if(moduleTypedArray.getDrawable(R.styleable.MyCustomView_action_bar_back_button_icon) != null )
            actionBarBackImage = moduleTypedArray.getDrawable(R.styleable.MyCustomView_action_bar_back_button_icon)!!

        setParentActivity(context as AppCompatActivity)

        init()
    }

    companion object {

    }

    private fun init() {

        this.addView(inflater.inflate(R.layout.main_layout, null))

        //do something
        header_toolbar_title.text = actionBarTitle
        header_toolbar_title.setBackgroundColor(Color.parseColor(actionBarColor))
        header_toolbar_title.setTextColor(Color.parseColor(actionBarTextColor))

        if (actionBarBackImage != null)  back_button.setImageDrawable(actionBarBackImage)
        back_button.setBackgroundColor((Color.parseColor(actionBarColor)))

        moduleTypedArray.recycle()

        back_button.setOnClickListener {
            Log.e(TAG, "back_button.setOnClickListener")
            //parentActivity.finish()
            this.visibility = View.GONE
        }

        GlobalScope.launch(Dispatchers.Main) { callApiTest() }

    }

    private fun setParentActivity(_activity: AppCompatActivity){
        parentActivity = _activity
    }

    fun setListener(_listener: CatsClickListener){
        listener = _listener
    }

    private suspend fun callApiTest(){
        try {
            val response = apiClient.getAllPublicImages(Constants.apiKey, "thumb", 10)
            Log.e("response", response.body().toString())
            if (response.isSuccessful){
                allCats = response.body() as MutableList<Cat>
                feedCatsList(allCats)
            }
        }catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun feedCatsList(_allCats: MutableList<Cat>) {
        catsAdapter =
                CatsAdapter(_allCats, context)
        cats_recyclerview.apply {
            layoutManager = listViewManager
            adapter = catsAdapter
        }
        catsAdapter.onItemClick = { selectedCats ->
            Log.e("response", selectedCats.toString())
            listener.getCatImage(selectedCats.url)
        }
    }


    override fun onLayout(p0: Boolean, l: Int, t: Int, r: Int, b: Int) {
        for (i in 0 until childCount) {
            getChildAt(i).layout(l, t, r, b)
        }
    }
}