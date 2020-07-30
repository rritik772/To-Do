package com.ritik.todo

import android.content.Context
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import androidx.core.view.GestureDetectorCompat
import androidx.recyclerview.widget.RecyclerView


private const val TAG="nsdkjf"
class RecycleViewClickListener(context: Context, recycleView: RecyclerView, private var listener: OnRecycleClickListener):RecyclerView.SimpleOnItemTouchListener() {

    interface OnRecycleClickListener {

        fun OnItemClick(view: View, position: Int)
        fun OnLongClick(view: View, posion: Int)
    }

    override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
        var result = gestureDetector.onTouchEvent(e)
        return super.onInterceptTouchEvent(rv, e)
    }

    private var gestureDetector = GestureDetectorCompat(context, object : GestureDetector.SimpleOnGestureListener() {

        override fun onSingleTapUp(e: MotionEvent): Boolean {
            Log.e(TAG, "gesture detected ************************************ ")
            val childView  = recycleView.findChildViewUnder(e.x,e.y)
            Log.e(TAG, "gesture detected ************************************ and child view is ${childView} ")
            if (childView != null){
                listener.OnItemClick(childView!!, recycleView.getChildAdapterPosition(childView))
            }

            return true
        }

        override fun onLongPress(e: MotionEvent) {
            Log.e(TAG, "gesture detected Long Tap ************************************")
            val childView  = recycleView.findChildViewUnder(e.x,e.y)
            Log.e(TAG, "gesture detected  Long Tap ************************************ and child view is ${childView} ")
            if (childView != null) {
                listener.OnLongClick(childView!!, recycleView.getChildAdapterPosition(childView))
            }
        }
    })
}