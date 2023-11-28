package com.example.tekhnopark_dz1

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.tekhnopark_dz2.DataObject
import com.example.tekhnopark_dz2.GifsAdapter
import com.example.tekhnopark_dz2.R
import com.example.tekhnopark_dz2.RecyclerViewFragment
import com.example.tekhnopark_dz2.databinding.MainActivityBinding
import com.example.tekhnopark_dz2package.RetrofitController
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


/*
Giphy.configure(this, giphyKey)
val settings = GPHSettings(GPHTheme.Dark)
val gifsDialog = GiphyDialogFragment.newInstance(settings)
gifsDialog.show(supportFragmentManager, "giphy_dialog")
 */

class MainActivity : AppCompatActivity() {
    private lateinit var binding: MainActivityBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: GifsAdapter
    private val retrofitController by lazy {
        RetrofitController()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(null, "ON CREATE MAIN ACTIVITY")
        super.onCreate(savedInstanceState)
        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setProgressBar()
        val context: Context = this
        CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            Log.d(null, "ON COROUTINE SCOPE")
            val gifs = retrofitController.requestGifs()
            withContext(Dispatchers.Main) {
                Log.d(null, "SET RECYCLER FRAGMENT")
                setRecyclerView(context,gifs,retrofitController)
            }
        }


    }

    fun setProgressBar(){
        supportFragmentManager.beginTransaction().run {
            add(R.id.container, Fragment(R.layout.progress_bar_fragment),"PROGRESS BAR")
            commit()
        }
    }
    fun setRecyclerView(context: Context, gifs: MutableList<DataObject>, retrofitController: RetrofitController){
        supportFragmentManager.beginTransaction().run {
            add(R.id.container,RecyclerViewFragment(context,gifs,retrofitController),"PROGRESS BAR")
            commit()
        }
    }



    fun setRecyclerViewFragment(gifs: MutableList<DataObject>) {
        recyclerView = binding.recyclerView
        if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            recyclerView.layoutManager =
                StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        } else {
            recyclerView.layoutManager =
                StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        }
        adapter = GifsAdapter(this, gifs)
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
                    CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
                        val gifs = retrofitController.requestGifs()
                        withContext(Dispatchers.Main) {
                            adapter.insert(gifs)
                        }
                    }
                }
            }

        })

    }


    private val exceptionHandler = CoroutineExceptionHandler { _, e ->
        MainScope().launch {
            setError(e.message)
        }
    }


    private fun setError(message: String?) {
        Log.e(null, "retrofit controller error")
    }
}