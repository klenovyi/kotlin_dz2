package com.example.tekhnopark_dz2

import com.google.gson.annotations.SerializedName;

data class GifsModel(
    @SerializedName("data")
    val gifs: MutableList<DataObject>
)

data class DataObject(
    @SerializedName("images")
    val image: DataImage
)

data class DataImage(
    @SerializedName("original")
    val originalImage: OriginalImage
)

data class OriginalImage(
    val height: Int,
    val width: Int,
    val url: String
)
