package com.romilk.rhentaireader.activities

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.GridLayout
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.romilk.rhentaireader.R
import com.squareup.picasso.Picasso
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import romilk.henparser.core.Manga


class MangaPageActivity : AppCompatActivity() {
    private lateinit var gridLayout: GridLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_manga_page)

        val manga: Manga = intent.getSerializableExtra("MANGA") as Manga

        gridLayout = findViewById(R.id.manga_page_container)

        GlobalScope.launch {
            loadMangaPages(manga)

            runOnUiThread {
                val previewImageView = findViewById<ImageView>(R.id.manga_page_preview);

                Picasso.with(this@MangaPageActivity).load(manga.mangaPreviewUrl).into(previewImageView)
            }
        }

    }

    private fun loadMangaPages(manga: Manga) {
        for (url in manga.mangaData.pageUrls) {

            val mangaView = LayoutInflater.from(this).inflate(
                R.layout.manga_page_preview,
                gridLayout, false
            )

            runOnUiThread {
                val imageView: ImageView = mangaView.findViewById(R.id.manga_page_preview_image)

                mangaView.setOnClickListener {
                    val intent = Intent(this, MangaReaderActivity::class.java)
                    intent.putExtra("MANGA", manga);
                    startActivity(intent)
                }

                Picasso.with(this).load(url).into(imageView)

                gridLayout.addView(mangaView)
            }
        }
    }
}