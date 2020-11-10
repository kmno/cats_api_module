package com.kamran.cats.core

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.annotation.CallSuper
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kamran.cats.R
import com.kamran.cats.ui.adapters.CatsAdapter
import com.kamran.cats.data.api.ApiClientProvider
import com.kamran.cats.ui.decorators.MarginItemDecoration
import com.kamran.cats.data.model.Cat
import com.kamran.cats.ui.interfaces.CatsClickListener
import kotlinx.android.synthetic.main.main_layout.view.*
import kotlinx.android.synthetic.main.toolbar.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


/**
 * Created by Kamran Noorinejad on 11/10/2020 AD 09:53.
 * Edited by Kamran Noorinejad on 11/10/2020 AD 09:53.
 */
class CatsListDialog(_context: Context) : AppCompatDialogFragment() {

    private val TAG = "CatsDialog."

    private var inflater: LayoutInflater
    private var builder: AlertDialog.Builder
    private var dialogView: View
    private var ctx: Context = _context

    private var apiClient = ApiClientProvider.createApiClient()

    private lateinit var catsAdapter: CatsAdapter
    private var allCats = mutableListOf<Cat>()
    private var listViewManager: RecyclerView.LayoutManager = GridLayoutManager(ctx, 2)
    private var listener : CatsClickListener

    private val CANCELABLE = false

    init {
        inflater = (ctx as AppCompatActivity).layoutInflater
        builder = AlertDialog.Builder(ctx as AppCompatActivity)
        dialogView = inflater.inflate(R.layout.main_layout, null)
        listener = ctx as CatsClickListener
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        builder
            .setView(dialogView)

        dialogView.back_button.setOnClickListener { dismiss() }

        isCancelable = CANCELABLE

        GlobalScope.launch(Dispatchers.Main) { callApiTest() }

        return builder.create()
    }

    private suspend fun callApiTest(){
        try {
            val response = apiClient.getAllPublicImages(Constants.apiKey, "thumb", 10)
            Log.e("response", response.body().toString())
            if (response.isSuccessful){
                allCats = response.body() as MutableList<Cat>
                feedCatsList(allCats)
            } else {
                showErrorMsg()
            }
        } catch (e: Exception) {
            showErrorMsg()
        }
    }

    private fun feedCatsList(_allCats: MutableList<Cat>) {
        catsAdapter =
            CatsAdapter(_allCats, ctx)
        dialogView.cats_recyclerview.apply {
            layoutManager = listViewManager
            adapter = catsAdapter
            addItemDecoration(
                MarginItemDecoration(resources.getDimension(R.dimen.cats_container_margin)
                .toInt())
            )
            visibility = View.VISIBLE
            dialogView.loading_text.visibility = View.GONE
        }
        catsAdapter.onItemClick = { selectedCats ->
            listener.getCatImage(selectedCats.url)
            dismiss()
        }
    }

    private fun showErrorMsg(){
        dialogView.loading_text.text = getString(R.string.network_error)
    }

    /**
     *** public methods
     */

    fun title(title: String?): CatsListDialog {
        return setToolbarTitle(title)
    }

    fun title(@StringRes titleResourceId: Int): CatsListDialog {
        return setToolbarTitle(titleResourceId)
    }

    fun toolbarColor(@ColorRes colorResourceId: Int): CatsListDialog {
        dialogView.back_button.setBackgroundColor(ContextCompat.getColor(ctx, colorResourceId))
        dialogView.header_toolbar_title.setBackgroundColor(ContextCompat.getColor(ctx, colorResourceId))
        return this
    }

    fun toolbarTextColor(@ColorRes colorResourceId: Int): CatsListDialog {
        dialogView.header_toolbar_title.setTextColor(ContextCompat.getColor(ctx, colorResourceId))
        return this
    }

    fun backIcon(@DrawableRes iconResourceId: Int): CatsListDialog {
        dialogView.back_button.setImageDrawable(ContextCompat.getDrawable(ctx, iconResourceId))
        return this
    }

    private fun setToolbarTitle(titleRes: Int): CatsListDialog {
        dialogView.header_toolbar_title.text = getString(titleRes)
        return this
    }

    private fun setToolbarTitle(title: String?): CatsListDialog {
        dialogView.header_toolbar_title.text = title
        return this
    }

    fun showNow() {
        super.show((ctx as AppCompatActivity).supportFragmentManager, "TAG")
    }

    @CallSuper
    override fun onDestroyView() {
        if (dialog != null && retainInstance) {
            dialog!!.setDismissMessage(null)
        }
        super.onDestroyView()
    }
}