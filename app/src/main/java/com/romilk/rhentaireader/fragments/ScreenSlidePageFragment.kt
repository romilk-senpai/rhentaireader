package com.romilk.rhentaireader.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.romilk.rhentaireader.R
import com.squareup.picasso.Picasso

class ScreenSlidePageFragment(private val mangaUrl: String) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_screen_slide_page, container, false)

        val imageView = view.findViewById<ImageView>(R.id.manga_page)

        Picasso.with(context).load(mangaUrl).into(imageView)

        return view
    }
}