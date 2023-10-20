package com.appliedanimations.animationvisibilitygroup

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable


@Composable
fun AnimatedVisibilityGroup(
    enter: EnterTransition,
    exit: ExitTransition,
    content: @Composable AnimatedVisibilityGroupScope.() -> Unit
) {
    content.invoke(AnimatedVisibilityGroupScope(enter, exit))
}