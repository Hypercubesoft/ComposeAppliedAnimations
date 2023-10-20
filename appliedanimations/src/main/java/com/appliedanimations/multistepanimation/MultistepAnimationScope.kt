package com.appliedanimations.multistepanimation

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

class MultistepAnimationScope<T>(
    val triggerState: MutableState<T>,
    val animationState: MultistepAnimationState<T>
) {

    fun Modifier.applyAnimation(animation: MultistepAnimationState<T> = animationState): Modifier =
        animation.run {
            then(
                Modifier
                    .width(animatedWidth?.value ?: 0.dp)
                    .height(animatedHeight?.value ?: 0.dp)
                    .clip(RoundedCornerShape(animatedRoundedCornersShape?.value ?: 0.dp))
                    .alpha(animatedAlpha.value)
                    .background(animatedBackgroundColor.value)
                    .padding(animatedPadding?.value ?: 0.dp)
                    .border(animateBorderStroke?.value ?: 0.dp, animatedBorderColor.value)
            )
        }

    @Composable
    @Suppress("UNCHECKED_CAST")
    fun SetUpdates() {
        animationState.apply {
            animatedWidth = animateDpAsState(
                when (triggerState.value) {
                    else ->
                        if (animationStepsMap.containsKey(triggerState.value))
                            animationStepsMap[triggerState.value]?.animatedWidth ?: 0.dp
                        else 0.dp

                }, animationSpecState.value as AnimationSpec<Dp>, label = "animatedWidthState"
            )

            animatedHeight = animateDpAsState(
                when (triggerState.value) {
                    else ->
                        if (animationStepsMap.containsKey(triggerState.value))
                            animationStepsMap[triggerState.value]?.animatedHeight ?: 0.dp
                        else 0.dp

                }, tween(800), label = "animatedHeightState"
            )

            animatedPadding = animateDpAsState(
                when (triggerState.value) {
                    else ->
                        if (animationStepsMap.containsKey(triggerState.value))
                            animationStepsMap[triggerState.value]?.animatedPadding ?: 0.dp
                        else 0.dp

                }, animationSpecState.value as AnimationSpec<Dp>, label = "animatedPaddingState"
            )

//            animateBorderStroke =
//                animateDpAsState(
//                    when (triggerState.value) {
//                        else -> {
//                            if (animationStepsMap.containsKey(triggerState.value))
//                                animationStepsMap[triggerState.value]?.animatedBorderStroke ?: 0.dp
//                            else 0.dp
//                        }
//                    }, animationSpecState.value as AnimationSpec<Dp>, label = "animatedBorderState"
//                )

            animatedRoundedCornersShape =
                if (animationStepsMap[triggerState.value]?.animatedRoundedCornersShape != null) animateDpAsState(
                    when (triggerState.value) {
                        else -> {
                            if (animationStepsMap.containsKey(triggerState.value))
                                animationStepsMap[triggerState.value]?.animatedRoundedCornersShape
                                    ?: 0.dp
                            else 0.dp
                        }
                    }, label = "animatedRoundedCornersShape"
                ) else null

            //Force trigger an update whenever the target state changes
            when (triggerState.value) {
                else -> {
                    val step = animationStepsMap.get(triggerState.value)
                    step?.let {
//                        LaunchedEffect(triggerState.value) {
//                            if (it.animatedBorderColor != null) animatedBorderColor.animateTo(
//                                it.animatedBorderColor,
//                                animationSpecState.value as AnimationSpec<Color>
//                            )
//                        }

                        LaunchedEffect(triggerState.value) {
                            if (it.animatedBackgroundColor != null
                                && it.animatedBackgroundColor != MultistepAnimationState.IGNORED_COLOR
                            )
                                animatedBackgroundColor.animateTo(
                                    it.animatedBackgroundColor,
                                    animationSpecState.value as AnimationSpec<Color>
                                )
                        }

                        LaunchedEffect(triggerState.value) {
                            if (it.animatedAlpha != null)
                                animatedAlpha.animateTo(
                                    it.animatedAlpha,
                                    animationSpecState.value as AnimationSpec<Float>
                                )
                        }

                        animationStepsMap[triggerState.value]?.animationSpec?.let { specs ->
                            animationSpecState.value = specs
                        }
                    }
                }
            }

        }

    }
}