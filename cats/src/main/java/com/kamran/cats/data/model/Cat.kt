package com.kamran.cats.data.model

import com.google.gson.annotations.SerializedName

/**
 * Created by Kamran Noorinejad on 11/9/2020 AD 14:55.
 * Edited by Kamran Noorinejad on 11/9/2020 AD 14:55.
 */
data class Cat(
    @SerializedName("id")
    var id: String,
    @SerializedName("url")
    var url: String,
    @SerializedName("width")
    var width: Int,
    @SerializedName("height")
    var height: Int,
  //  @SerializedName("breeds")
   // var breeds: Array
)