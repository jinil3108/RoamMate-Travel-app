@file:Suppress("DEPRECATION")

package com.techholding.android.roammate.data

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.techholding.android.roammate.ui.tabs.DescriptionFragment
import com.techholding.android.roammate.ui.tabs.gallery.GalleryFragment
import com.techholding.android.roammate.ui.tabs.ReviewsFragment

class PlaceDetailTabsAdapter(
    fragmentManager: FragmentManager,
    private var totalTabs: Int,
    private var description: String,
    private var placeId: String
) : FragmentPagerAdapter(fragmentManager) {
    override fun getCount(): Int {
        return totalTabs
    }

    override fun getItem(position: Int): Fragment {

        return when (position) {
            0 -> {
                DescriptionFragment.getInstance(description)
            }
            1 -> {
                ReviewsFragment.getInstance(placeId)
            }
            2 -> {
                GalleryFragment.getInstance(placeId)
            }
            else -> {
                getItem(position)
            }
        }
    }
}