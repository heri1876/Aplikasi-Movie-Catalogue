package com.shadow.moviecatalogue.ui.main.fragment.favorite

import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import com.shadow.moviecatalogue.R
import com.shadow.moviecatalogue.widget.adapter.FavoriteTabAdapter
import kotlinx.android.synthetic.main.fragment_favorite.view.*

@Suppress("DEPRECATION")
class FavoriteFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_favorite, container, false)

        with(root) {
            val tabAdapter = FavoriteTabAdapter(container!!.context, getChildFragmentManager())
            viewPager.adapter = tabAdapter
            tabLayout.setupWithViewPager(root.viewPager)

            tabLayout.getTabAt(0)!!.setIcon(R.drawable.ic_movie)
            tabLayout.getTabAt(1)!!.setIcon(R.drawable.ic_tv)

            tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabReselected(p0: TabLayout.Tab) {
                }

                override fun onTabUnselected(p0: TabLayout.Tab) {
                    p0.getIcon()
                        ?.setColorFilter(Color.parseColor("#a8a8a8"), PorterDuff.Mode.SRC_IN)
                }

                override fun onTabSelected(p0: TabLayout.Tab) {
                    p0.getIcon()
                        ?.setColorFilter(Color.parseColor("#E64525"), PorterDuff.Mode.SRC_IN)
                }
            })
        }

        return root
    }
}