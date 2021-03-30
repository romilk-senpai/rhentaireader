package com.romilk.rhentaireader

import android.os.Bundle
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.GridView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.*
import romilk.henparser.core.AppConfiguration
import romilk.henparser.core.Manga
import romilk.henparser.parsers.RHentaiParser


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        AppConfiguration("/app-configuration.xml")

        val searchEditTextView: EditText = findViewById(R.id.edit_text_search_tags)

        searchEditTextView.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH ||
                actionId == EditorInfo.IME_ACTION_DONE ||
                event != null &&
                event.action == KeyEvent.ACTION_DOWN &&
                event.keyCode == KeyEvent.KEYCODE_ENTER
            ) {
                if (event == null || !event.isShiftPressed) {
                    // the user is done typing.
                    findMangaByTags(searchEditTextView.text.split(' ', ',').toTypedArray())

                    return@setOnEditorActionListener true // consume.
                }
            }
            return@setOnEditorActionListener false // pass on to other listeners.
        }

    }

    private fun findMangaByTags(tags: Array<String>) {
        GlobalScope.launch {

            val manga: Array<Manga> = RHentaiParser.getInstance().getMangaByTags(tags)

            val adapter = SearchAdapter(this@MainActivity, manga)

            runOnUiThread {
                val g: GridView = findViewById(R.id.search_container)

                g.adapter = adapter
            }
        }
    }
}