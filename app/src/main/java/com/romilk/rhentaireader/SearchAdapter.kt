package com.romilk.rhentaireader

import android.content.Context
import android.content.Intent
import android.os.Parcel
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import com.squareup.picasso.Picasso
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import romilk.henparser.core.Manga


class SearchAdapter(private val context: Context, private val mangaList: Array<Manga>) :
    BaseAdapter() {

    override fun getCount(): Int {
        return mangaList.size
    }

    override fun getItem(position: Int): Any {
        return mangaList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val manga = mangaList[position]

        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        val mangaView = inflater.inflate(R.layout.search_manga_page, parent, false)

        val imageView = mangaView.findViewById<ImageView>(R.id.search_manga_page_image)
        val textView = mangaView.findViewById<TextView>(R.id.search_manga_page_text)

        Picasso.with(context).load(manga.mangaPreviewUrl).into(imageView)
        textView.text = manga.mangaName

        mangaView.setOnClickListener {
            GlobalScope.launch {
                manga.pagePreviewUrls
                manga.pageUrls
                val intent = Intent(context, MangaPageActivity::class.java)
                intent.putExtra("MANGA", manga);
                context.startActivity(intent)
            }
        }

        return mangaView
    }
}