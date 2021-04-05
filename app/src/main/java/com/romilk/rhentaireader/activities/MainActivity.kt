package com.romilk.rhentaireader.activities

import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.romilk.rhentaireader.R
import com.romilk.rhentaireader.adapters.EndlessRecyclerViewScrollListener
import com.romilk.rhentaireader.adapters.SearchAdapter
import kotlinx.coroutines.*
import romilk.henparser.core.Manga
import romilk.henparser.parsers.RHentaiParser

class MainActivity : AppCompatActivity() {
    private lateinit var searchAdapter: SearchAdapter;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        val searchEditTextView: EditText = findViewById(R.id.edit_text_search_tags)
        val searchButton: Button = findViewById(R.id.search_button);

        searchEditTextView.setOnKeyListener(object : View.OnKeyListener {
            override fun onKey(v: View?, keyCode: Int, event: KeyEvent): Boolean {
                // if the event is a key down event on the enter button
                if (event.action == KeyEvent.ACTION_DOWN &&
                    keyCode == KeyEvent.KEYCODE_ENTER
                ) {
                    // perform action on key press
                    findMangaByTags(searchEditTextView.text.split(' ', ',').toTypedArray())

                    return true
                }
                return false
            }
        })

        searchButton.setOnClickListener {
            findMangaByTags(searchEditTextView.text.split(' ', ',').toTypedArray())
        }

        val recyclerView: RecyclerView = findViewById(R.id.search_container)
        val gridLayoutManager = recyclerView.layoutManager as GridLayoutManager;

        val scrollListener: EndlessRecyclerViewScrollListener =
            object : EndlessRecyclerViewScrollListener(gridLayoutManager) {
                override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView) {
                    getNextMangaChunk()
                }
            }

        recyclerView.addOnScrollListener(scrollListener)
    }

    private lateinit var currentParser: RHentaiParser;

    private fun findMangaByTags(tags: Array<String>) {
        GlobalScope.launch {
            currentParser = RHentaiParser(tags)

            val manga: ArrayList<Manga> = ArrayList()
            manga.addAll(currentParser.mangaByTags)

            runOnUiThread {
                val recyclerView: RecyclerView = findViewById(R.id.search_container)

                searchAdapter = SearchAdapter(this@MainActivity, manga)
                recyclerView.adapter = searchAdapter
            }
        }
    }

    private fun getNextMangaChunk() {
        GlobalScope.launch {
            val manga = currentParser.mangaByTags

            runOnUiThread {
                searchAdapter.addData(manga)
            }
        }
    }
}