package com.promobileapp.chiasenhac.widget

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class GridSpacesItemDecoration(private val space: Int) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect, view: View,
        parent: RecyclerView, state: RecyclerView.State
    ) {
        outRect.left = space
        outRect.right = space
        outRect.bottom = space

        if (parent.getChildLayoutPosition(view) == 0) {
            outRect.top = space
            outRect.right = space / 2
        } else if (parent.getChildLayoutPosition(view) == 1) {
            outRect.top = space
            outRect.left = space / 2
        } else if (parent.getChildLayoutPosition(view) % 2 == 0) {
            outRect.right = space / 2
        } else if (parent.getChildLayoutPosition(view) % 2 != 0) {
            outRect.left = space / 2
        } else {
            outRect.top = 0
        }
    }
}