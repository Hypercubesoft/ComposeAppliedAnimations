package com.appliedanimations.multistepanimation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState


@Composable
fun <T> MultistepAnimation(
    triggerState: MutableState<T>,
    animationState: MultistepAnimationState<T>,
    content: @Composable MultistepAnimationScope<T>.() -> Unit
) {
    val scope = MultistepAnimationScope(triggerState, animationState)
    scope.SetUpdates()
    content.invoke(scope)
}