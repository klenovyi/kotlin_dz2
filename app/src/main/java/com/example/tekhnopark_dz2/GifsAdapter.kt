package com.example.tekhnopark_dz2

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class GifsAdapter(val context: Context, val gifs: MutableList<DataObject>) :
    RecyclerView.Adapter<GifsAdapter.ImageViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        Log.d(null,"ON CREATE VIEW HOLDER")
        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_holder_fragment, null)

        return ImageViewHolder(view)
    }

    override fun getItemCount(): Int {
        return gifs.size
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        Log.d(null,"ON BIND VIEW HOLDER")
        val data = gifs.get(position)
        Glide.with(context).load(data.image.originalImage.url).into(holder.imgView)
    }

    fun insert(add_gifs: MutableList<DataObject>){
        Log.d(null,"INSERT DATA IN ADAPTER")
        val index = itemCount
        gifs.addAll(add_gifs)
        notifyItemRangeInserted(index - 1, add_gifs.size)
        Log.d(null,"GIFS SIZE ${itemCount}")
    }

    class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imgView: ImageView = itemView.findViewById(R.id.image)
    }

}


