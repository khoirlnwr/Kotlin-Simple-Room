package com.example.kotlinroom.utils

import android.content.Context
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class RecyclerItemClickListener(
    context: Context,
    recyclerView: RecyclerView,
    private val mListener: OnItemClickListener?
) : RecyclerView.OnItemTouchListener {

    private val mGestureDetector: GestureDetector =
        GestureDetector(context, object : GestureDetector.SimpleOnGestureListener() {
            override fun onSingleTapUp(e: MotionEvent?): Boolean {
                return true
            }

            override fun onLongPress(e: MotionEvent?) {
                e?.let {
                    val childView = recyclerView.findChildViewUnder(e.x, e.y)

                    if (childView != null && mListener != null) {
                        mListener.onItemLongClick(
                            childView,
                            recyclerView.getChildAdapterPosition(childView)
                        )
                    }
                }

            }
        })

    interface OnItemClickListener {
        fun onItemClick(view: View, position: Int)

        fun onItemLongClick(view: View?, position: Int)
    }

    override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
        val childView = rv.findChildViewUnder(e.x, e.y)

        if (childView != null && mListener != null && mGestureDetector.onTouchEvent(e)) {
            mListener.onItemClick(childView, rv.getChildAdapterPosition(childView))
        }

        return false;
    }

    override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {}

    override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {}

}