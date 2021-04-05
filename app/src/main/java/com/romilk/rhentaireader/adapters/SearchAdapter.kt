package com.romilk.rhentaireader.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.romilk.rhentaireader.activities.MangaPageActivity
import com.romilk.rhentaireader.R
import com.squareup.picasso.Picasso
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import romilk.henparser.core.Manga


class SearchAdapter(private val context: Context, private val mangaList: ArrayList<Manga>) :
    RecyclerView.Adapter<SearchAdapter.SearchViewHolder>() {

    class SearchViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val mangaNameView: TextView = view.findViewById(R.id.search_manga_page_text)
        val imageView: ImageView = view.findViewById(R.id.search_manga_page_image)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.search_manga_page, parent, false)

        return SearchViewHolder(view)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {

        val manga = mangaList[position]

        Picasso.with(context).load(manga.mangaPreviewUrl).into(holder.imageView)

        holder.mangaNameView.text = manga.mangaName

        holder.itemView.setOnClickListener {
            GlobalScope.launch {
                val intent = Intent(context, MangaPageActivity::class.java)
                intent.putExtra("MANGA", manga);
                context.startActivity(intent)
            }
        }
    }

    override fun getItemCount(): Int {
        return mangaList.size
    }

    fun addData(data: Array<Manga>) {
        val oldIndex = mangaList.size - 1

        this.mangaList.addAll(oldIndex, data.toList())

        notifyItemRangeChanged(oldIndex, mangaList.size - 1)
    }
}