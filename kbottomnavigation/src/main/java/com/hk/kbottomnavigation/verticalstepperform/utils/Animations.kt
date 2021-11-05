package com.hk.kbottomnavigation.verticalstepperform.utils

import android.view.View
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.Transformation

object Animations {
    @JvmStatic
    fun slideDown(v: View) {
        if (v.visibility != View.VISIBLE) {
            v.measure(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT
            )
            val targetHeight = v.measuredHeight
            setHeight(v, 1)
            v.visibility = View.VISIBLE
            val a: Animation = object : Animation() {
                override fun applyTransformation(
                    interpolatedTime: Float,
                    t: Transformation
                ) {
                    val newHeight =
                        if (interpolatedTime == 1f) WindowManager.LayoutParams.WRAP_CONTENT else (targetHeight * interpolatedTime).toInt()
                    setHeight(v, newHeight)
                    v.requestLayout()
                }

                override fun willChangeBounds(): Boolean {
                    return true
                }
            }
            a.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationStart(animation: Animation) {}
                override fun onAnimationEnd(animation: Animation) {
                    v.visibility = View.VISIBLE
                }

                override fun onAnimationRepeat(animation: Animation) {}
            })

            a.duration = (targetHeight / v.context.resources.displayMetrics.density).toInt() * 2.toLong()
            v.startAnimation(a)
        }
    }

    @JvmStatic
    fun slideUp(v: View) {
        if (v.visibility == View.VISIBLE) {
            val initialHeight = v.measuredHeight
            val a: Animation = object : Animation() {
                override fun applyTransformation(
                    interpolatedTime: Float,
                    t: Transformation
                ) {
                    if (interpolatedTime != 1f) {
                        val newHeight = initialHeight - (initialHeight * interpolatedTime).toInt()
                        setHeight(v, newHeight)
                        v.requestLayout()
                    }
                }

                override fun willChangeBounds(): Boolean {
                    return true
                }
            }
            a.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationStart(animation: Animation) {}
                override fun onAnimationEnd(animation: Animation) {
                    v.visibility = View.GONE
                }

                override fun onAnimationRepeat(animation: Animation) {}
            })

            a.duration = (initialHeight / v.context.resources.displayMetrics.density).toInt() * 2.toLong()
            v.startAnimation(a)
        }
    }

    internal fun setHeight(v: View, newHeight: Int) {
        v.layoutParams.apply {
            width = v.layoutParams.width
            height = newHeight
        }
    }
}