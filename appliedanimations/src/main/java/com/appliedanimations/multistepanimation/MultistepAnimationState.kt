package com.appliedanimations.multistepanimation


import androidx.compose.animation.Animatable
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.tween
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp

class MultistepAnimationState<T>(val triggerState: MutableState<T>, vararg steps: AnimationSteps<T>) {
    val animatedAlpha = androidx.compose.animation.core.Animatable(1F)
    var animatedWidth: State<Dp>? = null
    var animatedHeight: State<Dp>? = null
    val animatedBackgroundColor = Animatable(IGNORED_COLOR)
    val animatedBorderColor = Animatable(Color.White)
    var animateBorderStroke: State<Dp>? = null
    var animatedPadding: State<Dp>? = null
    var animatedRoundedCornersShape: State<Dp>? = null

    val animationStepsMap = mutableMapOf<T, AnimationSteps<T>>().apply {
        steps.forEach {
            this[it.key] = it
        }
    }

    val animationSpecState = mutableStateOf<AnimationSpec<T>>(tween(800))

    fun addStep(key: T, step: AnimationSteps<T>) {
        animationStepsMap[key] = step
    }

    fun removeStep(key: T) {
        animationStepsMap.remove(key)
    }

    data class AnimationSteps<T>(
        val key: T,
        val animatedAlpha: Float? = null,
        val animatedWidth: Dp? = null,
        val animatedHeight: Dp? = null,
        val animatedPadding: Dp? = null,
        val animatedBackgroundColor: Color? = IGNORED_COLOR,
        val animatedElevation: Float? = null,
        val animatedRoundedCornersShape: Dp? = null,
        val animationSpec: AnimationSpec<T> = tween(300)
    )

    companion object {
        // Looking for a better solution
        val IGNORED_COLOR = Color(1, 2, 3, 0)
    }
}