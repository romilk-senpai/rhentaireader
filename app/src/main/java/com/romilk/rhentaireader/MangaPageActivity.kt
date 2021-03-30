package com.romilk.rhentaireader

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import romilk.henparser.core.Manga


class MangaPageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val manga : Manga = intent.getSerializableExtra("MANGA") as Manga

        println(manga.mangaName);
    }
}