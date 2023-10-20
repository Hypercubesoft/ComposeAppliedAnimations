package com.example.composeappliedanimations

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.appliedanimations.animationvisibilitygroup.AnimatedVisibilityGroup
import com.appliedanimations.multistepanimation.MultistepAnimation
import com.appliedanimations.multistepanimation.MultistepAnimationState
import com.example.composeappliedanimations.ui.theme.ComposeAppliedAnimationsTheme
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val triggerState = mutableStateOf(0)
        val multistepAnimation = MultistepAnimationState(
            triggerState = triggerState,
            MultistepAnimationState.AnimationSteps(
                key = 0,
                animatedAlpha = 0F
            ),
            MultistepAnimationState.AnimationSteps(
                key = 1,
                animatedAlpha = 1F,
                animationSpec = tween(800)
            )
        )

        val multistepInnerAnimation = MultistepAnimationState(
            triggerState,
            MultistepAnimationState.AnimationSteps(
                key = 1,
                animatedRoundedCornersShape = 100.dp,
                animatedWidth = 10.dp,
                animatedHeight = 10.dp,
                animatedBackgroundColor = Color.Red
            ),
            MultistepAnimationState.AnimationSteps(
                key = 2,
                animatedRoundedCornersShape = 0.dp,
                animatedBackgroundColor = Color.Red,
                animatedWidth = 100.dp,
                animatedHeight = 100.dp,
                animationSpec = tween(800)
            )
        )

        setContent {
            ComposeAppliedAnimationsTheme {
                // A surface container using the 'background' color from the theme
                val stateOne = remember {
                    mutableStateOf(false)
                }
                val stateTwo = remember {
                    mutableStateOf(false)
                }
                val stateThree = remember {
                    mutableStateOf(false)
                }
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceEvenly
                ) {

                    LaunchedEffect(Unit) {
                        delay(5000)
                        triggerState.value = 0
                        delay(2000)
                        triggerState.value = 1
                        delay(2000)
                        triggerState.value = 2
                        delay(3000)
                        triggerState.value = 1
                    }

                    // We now have two different scopes triggered by the same triggerState but using two different multistep animations
                    MultistepAnimation(triggerState = triggerState, animationState = multistepInnerAnimation) {
                        MultistepAnimation(
                            triggerState = triggerState,
                            animationState = multistepAnimation
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(200.dp)
                                    .applyAnimation() // We apply the first animation through the scope
                                    .background(Color.Cyan),
                                contentAlignment = Alignment.Center
                            )
                            {
                                Box(
                                    modifier = Modifier
                                        .applyAnimation(multistepInnerAnimation) // To apply an animation that's outside the current scope, we need to pass the obejct directly to the modifier
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
