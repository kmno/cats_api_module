/*
 * Creator: Kamran Noorinejad on 10/20/20 10:00 AM
 * Last modified: 10/20/20 9:59 AM
 * Copyright: All rights reserved Ⓒ 2020
 */

package com.kamran.cats.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.kamran.cats.Cat
import com.kamran.cats.R
import kotlinx.android.synthetic.main.cats_recyclerview_item.view.*

/**
 * Created by Kamran Noorinejad on 10/20/2020 AD 07:50.
 * Edited by Kamran Noorinejad on 10/20/2020 AD 07:50.
 */
class CatsAdapter(
    private val catsList: MutableList<Cat>,
    private val context: Context
) :
    RecyclerView.Adapter<CatsAdapter.CatViewHolder>() {

    var onItemClick: ((Cat) -> Unit)? = null

    inner class CatViewHolder(val layout: View) : RecyclerView.ViewHolder(layout) {
        init {
            layout.setOnClickListener {
                onItemClick?.invoke(catsList[adapterPosition])
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CatViewHolder {
        return CatViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.cats_recyclerview_item, parent, false)
        )
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: CatViewHolder, position: Int) {
        val currentItem = catsList[position]

        holder.layout.cat_image.load(currentItem.url){
            crossfade(true)
            placeholder(R.drawable.cat_image_placeholder)
        }
    }

    override fun getItemCount() = catsList.size
}