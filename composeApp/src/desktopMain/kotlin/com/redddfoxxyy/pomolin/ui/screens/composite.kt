package com.redddfoxxyy.pomolin.ui.screens

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.redddfoxxyy.pomolin.handlers.PomoDoroRoutines
import com.redddfoxxyy.pomolin.handlers.RoutineManager
import com.redddfoxxyy.pomolin.ui.Base
import com.redddfoxxyy.pomolin.ui.*
import org.jetbrains.compose.resources.painterResource
import pomolin.composeapp.generated.resources.Res
import pomolin.composeapp.generated.resources.play_arrow
import pomolin.composeapp.generated.resources.pause
import pomolin.composeapp.generated.resources.reset
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.runtime.getValue


@Composable
@Preview
// NOTE: Composite meaning one screen showing all the stuff required. Later in future will add more screens.
// TODO: Separate out each component into different composable(s).
fun compositeScreen() {
	val manager = remember { RoutineManager() }
	val playIconPainter = painterResource(Res.drawable.play_arrow)
	val pauseIconPainter = painterResource(Res.drawable.pause)
	val resetIconPainter = painterResource(Res.drawable.reset)
	val selectedIndex = remember { mutableIntStateOf(0) }
	val options = listOf("Pomodoro", "Short Break", "Long Break")

	fun onSegmentSelected(index: Int) {
		when (index) {
			0 -> {
				manager.setRoutine(PomoDoroRoutines.Working)
			}

			1 -> {
				manager.setRoutine(PomoDoroRoutines.ShortBreak)
			}

			2 -> {
				manager.setRoutine(PomoDoroRoutines.LongBreak)
			}
		}
	}

	Column(
		Modifier.fillMaxHeight().background(Base),
		verticalArrangement = Arrangement.Top,
		horizontalAlignment = Alignment.CenterHorizontally
	) {
		SingleChoiceSegmentedButtonRow {
			options.forEachIndexed { index, label ->
				SegmentedButton(
					shape = SegmentedButtonDefaults.itemShape(
						index = index,
						count = options.size
					),
					onClick = {
						selectedIndex.value = index
						onSegmentSelected(index)
					},
					selected = index == selectedIndex.value,
					label = { Text(label) },
					colors = SegmentedButtonDefaults.colors(
						activeContainerColor = Mauve,
						inactiveContainerColor = Color.Transparent,
						activeContentColor = Crust,
						inactiveContentColor = White
					),
					border = BorderStroke(1.dp, Mauve),
					modifier = Modifier.fillMaxWidth().padding(top = 24.dp, bottom = 80.dp)
				)
			}
		}
		// NOTE: Kept for future EnableAnimation or Disable support.
//		Text(
//			text = manager.currentTimer.value.formatedTime.value,
//			fontWeight = FontWeight.ExtraBold,
//			fontSize = 100.sp,
//			color = Lavender
//		)
		Row(
			horizontalArrangement = Arrangement.Center,
			verticalAlignment = Alignment.CenterVertically
		) {
			val timerText = manager.currentTimer.value.formatedTime.value

			// Iterate over each character of the timer string
			timerText.forEach { char ->
				AnimatedContent(
					targetState = char,
					transitionSpec = {
						// Animation for each character
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

		Spacer(Modifier.height(50.dp))
		Row(
			horizontalArrangement = Arrangement.Center,
			verticalAlignment = Alignment.CenterVertically,
			modifier = Modifier.fillMaxWidth()
		) {
			val isRunning = manager.currentTimer.value.isTimerRunning.collectAsState().value

			// 1. Animate the button color
			val buttonColor by animateColorAsState(
				targetValue = if (isRunning) Peach else Green,
				label = "ButtonColor"
			)

			// TODO: This timer state can be handled better instead of this shit ( wtf is even this? )
			// NOTE: Kept for future EnableAnimation or Disable support.
//			if (manager.currentTimer.value.isTimerRunning.collectAsState().value) {
//				Button(
//					onClick = { manager.pauseTimer() }, colors = ButtonDefaults.buttonColors(
//						containerColor = Peach,
//						contentColor = White
//					),
//					modifier = Modifier.padding(5.dp)
//				) {
//					Icon(painter = pauseIconPainter, contentDescription = "Pause", tint = Crust)
//				}
//			} else {
//				Button(
//					onClick = { manager.startTimer() }, colors = ButtonDefaults.buttonColors(
//						containerColor = Green,
//						contentColor = White
//					),
//					modifier = Modifier.padding(5.dp)
//				) {
//					Icon(painter = playIconPainter, contentDescription = "Play", tint = Crust)
//				}
//			}
			Button(
				onClick = {
					if (isRunning) manager.pauseTimer() else manager.startTimer()
				},
				colors = ButtonDefaults.buttonColors(
					containerColor = buttonColor,
					contentColor = White
				),
				modifier = Modifier.padding(5.dp)
			) {
				// 3. Animate the icon change
				AnimatedContent(
					targetState = isRunning,
					transitionSpec = {
						scaleIn() togetherWith scaleOut()
					},
					label = "ButtonIcon"
				) { running ->
					if (running) {
						Icon(painter = pauseIconPainter, contentDescription = "Pause", tint = Crust)
					} else {
						Icon(painter = playIconPainter, contentDescription = "Play", tint = Crust)
					}
				}
			}
			Spacer(Modifier.width(16.dp))
			Button(
				onClick = { manager.resetTimer() }, colors = ButtonDefaults.buttonColors(
					containerColor = Red,
					contentColor = White
				),
				modifier = Modifier.padding(5.dp)
			) {
				Icon(painter = resetIconPainter, contentDescription = "Reset", tint = Crust)
			}
		}

	}
}