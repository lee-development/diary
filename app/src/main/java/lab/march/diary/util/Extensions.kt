package lab.march.diary.util

import android.transition.ChangeBounds
import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.Interpolator
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import com.bumptech.glide.Glide

fun View.visible() { visibility = View.VISIBLE }

fun View.invisible() { visibility = View.INVISIBLE }

fun View.gone() { visibility = View.GONE }

val ViewGroup.layoutInflater: LayoutInflater get() = LayoutInflater.from(context)

val View.layoutInflater: LayoutInflater get() = LayoutInflater.from(context)

fun ConstraintLayout.update(constraintSet: ConstraintSet = ConstraintSet(), update: ConstraintSet.() -> Unit) {
    constraintSet.clone(this)
    constraintSet.update()
    constraintSet.applyTo(this)
}

fun ConstraintLayout.delayedTransition(
    duration: Long = 250,
    interpolator: Interpolator = AccelerateDecelerateInterpolator()
) = TransitionManager.beginDelayedTransition(this, ChangeBounds().also {
    it.duration = duration
    it.interpolator = interpolator
})

fun ImageView.loadImage(url: String) = Glide.with(this.context).load(url).into(this)