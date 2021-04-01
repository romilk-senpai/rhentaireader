package com.romilk.rhentaireader.activities

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.romilk.rhentaireader.R
import com.romilk.rhentaireader.fragments.ScreenSlidePageFragment
import romilk.henparser.core.Manga
import kotlin.math.abs

private const val MIN_SCALE = 0.85f
private const val MIN_ALPHA = 0.5f

class MangaReaderActivity : FragmentActivity() {
    private lateinit var viewPager: ViewPager2
    private lateinit var manga: Manga

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manga_reader)

        manga = intent.getSerializableExtra("MANGA") as Manga

        viewPager = findViewById(R.id.pager)
        viewPager.setPageTransformer(ZoomOutPageTransformer())

        val pagerAdapter = ScreenSlidePagerAdapter(this, manga.pageUrls)
        viewPager.adapter = pagerAdapter
    }

    override fun onBackPressed() {
        if (viewPager.currentItem == 0) {
            super.onBackPressed()
        } else {
            viewPager.currentItem = viewPager.currentItem - 1
        }
    }

    private inner class ZoomOutPageTransformer : ViewPager2.PageTransformer {

        override fun transformPage(view: View, position: Float) {
            view.apply {
                val pageWidth = width
                val pageHeight = height
                when {
                    position < -1 -> {
                        alpha = 0f
                    }
                    position <= 1 -> {
                        val scaleFactor = MIN_SCALE.coerceAtLeast(1 - abs(position))
                        val vertMargin = pageHeight * (1 - scaleFactor) / 2
                        val horzMargin = pageWidth * (1 - scaleFactor) / 2
                        translationX = if (position < 0) {
                            horzMargin - vertMargin / 2
                        } else {
                            horzMargin + vertMargin / 2
                        }

                        scaleX = scaleFactor
                        scaleY = scaleFactor

                        alpha = (MIN_ALPHA +
                                (((scaleFactor - MIN_SCALE) / (1 - MIN_SCALE)) * (1 - MIN_ALPHA)))
                    }
                    else -> {
                        alpha = 0f
                    }
                }
            }
        }
    }

    private inner class ScreenSlidePagerAdapter(
        fa: FragmentActivity,
        private val pages: Array<String>
    ) : FragmentStateAdapter(fa) {
        override fun getItemCount(): Int = pages.size

        override fun createFragment(position: Int): Fragment {

            val page = ScreenSlidePageFragment(pages[position])

            return page
        }
    }
}