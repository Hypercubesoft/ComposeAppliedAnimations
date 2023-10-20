package com.appliedanimations.animationvisibilitygroup



import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import kotlinx.coroutines.delay

class AnimatedVisibilityGroupScope(
    val enter: EnterTransition,
    val exit: ExitTransition
) {
    @Composable
    fun Item(
        visiblityState: MutableState<Boolean>,
        extendedEnterTransition: EnterTransition? = null,
        extendedExitTransition: ExitTransition? = null,
        initialDelay: Long = 0,
        modifier: Modifier = Modifier,
        itemState: AnimatedVisibilityItemState = AnimatedVisibilityItemState(visiblityState.value),
        content: @Composable () -> Unit
    ) {

        itemState.apply {

            LaunchedEffect(visiblityState) {
                //Handle animation delay
                delay(initialDelay)
                internalTriggerState.value = visiblityState.value
            }

            val enterAnimation =
                if (extendedEnterTransition != null) enter + extendedEnterTransition else enter
            val exitAnimation =
                if (extendedExitTransition != null) exit + extendedExitTransition else exit

            AnimatedVisibility(
                visible = internalTriggerState.value,
                enter = enterAnimation, exit = exitAnimation,
                modifier = modifier
            ) {
                content.invoke()
            }
        }
    }

    @Composable
    fun Item(
        visiblityState: Boolean,
        extendedEnterTransition: EnterTransition? = null,
        extendedExitTransition: ExitTransition? = null,
        initialDelay: Long = 0,
        modifier: Modifier = Modifier,
        itemState: AnimatedVisibilityItemState = AnimatedVisibilityItemState(visiblityState),
        content: @Composable () -> Unit
    ) {
        itemState.apply {

            LaunchedEffect(visiblityState) {
                //Handle animation delay
                delay(initialDelay)
                internalTriggerState.value = visiblityState
            }

            val enterAnimation =
                if (extendedEnterTransition != null) enter + extendedEnterTransition else enter
            val exitAnimation =
                if (extendedExitTransition != null) exit + extendedExitTransition else exit

            AnimatedVisibility(
                visible = internalTriggerState.value,
                enter = enterAnimation,
                exit = exitAnimation,
                modifier = modifier
            ) {
                content.invoke()
            }
        }
    }

    class AnimatedVisibilityItemState(intitiallyVisibile: Boolean) {
        val internalTriggerState = mutableStateOf(intitiallyVisibile)
    }
}