// composite.kt
package com.redddfoxxyy.pomolin.ui.screens

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.redddfoxxyy.pomolin.handlers.PomoDoroRoutines
import com.redddfoxxyy.pomolin.handlers.RoutineManager
import com.redddfoxxyy.pomolin.ui.Base
import com.redddfoxxyy.pomolin.ui.Crust
import com.redddfoxxyy.pomolin.ui.Green
import com.redddfoxxyy.pomolin.ui.Lavender
import com.redddfoxxyy.pomolin.ui.Mauve
import com.redddfoxxyy.pomolin.ui.Peach
import com.redddfoxxyy.pomolin.ui.Red
import com.redddfoxxyy.pomolin.ui.White
import org.jetbrains.compose.resources.painterResource
import pomolin.composeapp.generated.resources.Res
import pomolin.composeapp.generated.resources.pause
import pomolin.composeapp.generated.resources.play_arrow
import pomolin.composeapp.generated.resources.reset

@Composable
@Preview
fun CompositeScreen() {
	val manager = remember { RoutineManager() }
	val currentRoutine by manager.currentRoutine
	val isRunning by manager.currentTimer.value.isTimerRunning.collectAsState()
	val formattedTime = manager.currentTimer.value.formatedTime.value

	Column(
		Modifier.fillMaxSize().background(Base),
		horizontalAlignment = Alignment.CenterHorizontally
	) {
		Spacer(Modifier.height(24.dp))

		RoutineSelector(
			routines = manager.routines,
			selectedRoutine = currentRoutine,
			onRoutineSelected = { manager.setRoutine(it) }
		)

		Spacer(Modifier.height(80.dp))

		TimerDisplay(time = formattedTime)

		Spacer(Modifier.height(50.dp))

		ControlButtons(
			isRunning = isRunning,
			onPlayPauseClick = {
				if (isRunning) manager.pauseTimer() else manager.startTimer()
			},
			onResetClick = { manager.resetTimer() }
		)
	}
}

@Composable
fun RoutineSelector(
	routines: List<PomoDoroRoutines>,
	selectedRoutine: PomoDoroRoutines,
	onRoutineSelected: (PomoDoroRoutines) -> Unit
) {
	SingleChoiceSegmentedButtonRow() {
		routines.forEach { routine ->
			SegmentedButton(
				shape = SegmentedButtonDefaults.itemShape(
					index = routines.indexOf(routine),
					count = routines.size
				),
				onClick = { onRoutineSelected(routine) },
				selected = routine == selectedRoutine,
				label = { Text(routine.displayName) },
				colors = SegmentedButtonDefaults.colors(
					activeContainerColor = Mauve,
					inactiveContainerColor = Color.Transparent,
					activeContentColor = Crust,
					inactiveContentColor = White
				),
				border = BorderStroke(1.dp, Mauve),
			)
		}
	}
}

@Composable
fun TimerDisplay(time: String) {
	Row(
		horizontalArrangement = Arrangement.Center,
		verticalAlignment = Alignment.CenterVertically
	) {
		time.forEach { char ->
			AnimatedContent(
				targetState = char,
				transitionSpec = {
					slideInVertically { height -> height } togetherWith
							slideOutVertically { height -> -height }
				}
			) { targetChar ->
				Text(
					text = targetChar.toString(),
					fontWeight = FontWeight.ExtraBold,
					fontSize = 100.sp,
					color = Lavender
				)
			}
		}
	}
}

@Composable
fun ControlButtons(
	isRunning: Boolean,
	onPlayPauseClick: () -> Unit,
	onResetClick: () -> Unit
) {
	val resetIconRotation = remember { mutableFloatStateOf(0f) }
	val animatedResetIconRotation by animateFloatAsState(
		targetValue = resetIconRotation.value,
		animationSpec = tween(durationMillis = 400),
		label = "ResetIconRotation"
	)
	val buttonColor by animateColorAsState(
		targetValue = if (isRunning) Peach else Green,
		label = "ButtonColor"
	)

	Row(
		horizontalArrangement = Arrangement.Center,
		verticalAlignment = Alignment.CenterVertically,
		modifier = Modifier.fillMaxWidth()
	) {
		// Play/Pause Button
		Button(
			onClick = onPlayPauseClick,
			colors = ButtonDefaults.buttonColors(containerColor = buttonColor, contentColor = White),
			modifier = Modifier.padding(5.dp)
		) {
			AnimatedContent(
				targetState = isRunning,
				transitionSpec = { scaleIn() togetherWith scaleOut() },
				label = "ButtonIcon"
			) { running ->
				Icon(
					painter = if (running) painterResource(Res.drawable.pause)
					else painterResource(Res.drawable.play_arrow),
					contentDescription = if (running) "Pause" else "Play",
					tint = Crust
				)
			}
		}

		Spacer(Modifier.width(16.dp))

		// Reset Button
		Button(
			onClick = {
				onResetClick()
				resetIconRotation.value -= 360f
			},
			colors = ButtonDefaults.buttonColors(containerColor = Red, contentColor = White),
			modifier = Modifier.padding(5.dp)
		) {
			Icon(
				painter = painterResource(Res.drawable.reset),
				contentDescription = "Reset",
				tint = Crust,
				modifier = Modifier.rotate(animatedResetIconRotation)
			)
		}
	}
}