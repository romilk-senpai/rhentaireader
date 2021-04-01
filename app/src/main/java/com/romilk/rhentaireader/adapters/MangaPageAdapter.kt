package com.romilk.rhentaireader.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.romilk.rhentaireader.R
import com.romilk.rhentaireader.activities.MangaPageActivity
import com.romilk.rhentaireader.activities.MangaReaderActivity
import com.squareup.picasso.Picasso
import romilk.henparser.core.Manga

class MangaPageAdapter(private val context: Context, private val manga: Manga) :
    RecyclerView.Adapter<MangaPageAdapter.MangaPageViewHolder>() {

    class MangaPageViewHolder(view: View) : ViewHolder(view) {

        val imageView: ImageView

        init {
            imageView = view.findViewById(R.id.manga_page_preview_image)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MangaPageViewHolder {

        val mangaView = LayoutInflater.from(parent.context).inflate(
            R.layout.manga_page_preview,
            parent,
            false
        )

        mangaView.setOnClickListener {
            val intent = Intent(context, MangaReaderActivity::class.java)
            intent.putExtra("MANGA", manga);
            context.startActivity(intent)
        }

        return MangaPageViewHolder(mangaView)
    }

    override fun onBindViewHolder(holder: MangaPageViewHolder, position: Int) {
        val pageUrl = manga.pagePreviewUrls[position]

        Picasso.with(context).load(pageUrl).into(holder.imageView)
    }

    override fun getItemCount(): Int {
        return manga.pagePreviewUrls.size
    }
}