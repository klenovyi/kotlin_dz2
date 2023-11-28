package com.example.tekhnopark_dz2

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.tekhnopark_dz2package.RetrofitController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RecyclerViewFragment(val _context : Context, val gifs: MutableList<DataObject>, val retrofitController: RetrofitController) :
    Fragment(R.layout.recycler_view_fragment) {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: GifsAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view = inflater.inflate(R.layout.recycler_view_fragment, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            recyclerView.layoutManager =
                StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        } else {
            recyclerView.layoutManager =
                StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        }
        adapter = GifsAdapter(_context, gifs)
        recyclerView.adapter = adapter

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val layoutManager = recyclerView.layoutManager as StaggeredGridLayoutManager
                val lastVisibleItemPositions = layoutManager.findLastVisibleItemPositions(null)
                val maxLastVisiblePosition = lastVisibleItemPositions.maxOrNull()
                val totalItemCount: Int = layoutManager.itemCount
                if (maxLastVisiblePosition != null && maxLastVisiblePosition >= totalItemCount - 1) {
                    Log.d(null, "END RECYCLER")
                    CoroutineScope(Dispatchers.IO ).launch {
                        val gifs = retrofitController.requestGifs()
                        withContext(Dispatchers.Main) {
                            adapter.insert(gifs)
                        }
                    }
                }
            }

        })

        return view;
    }


}