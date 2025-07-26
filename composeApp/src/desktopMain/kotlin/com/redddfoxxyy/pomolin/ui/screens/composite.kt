package com.redddfoxxyy.pomolin.ui.screens

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.Image
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.redddfoxxyy.pomolin.handlers.Timer
import com.redddfoxxyy.pomolin.ui.Base
import com.redddfoxxyy.pomolin.ui.*
import org.jetbrains.compose.resources.painterResource
import pomolin.composeapp.generated.resources.Res
import pomolin.composeapp.generated.resources.play_arrow
import pomolin.composeapp.generated.resources.pause
import pomolin.composeapp.generated.resources.reset
import kotlin.concurrent.timer


@Composable

@Preview

// NOTE: Composite meaning one screen showing all the stuff required. Later in future will add more screens.
fun compositeScreen() {
	val timerState = remember { Timer(25) }
	val playIconPainter = painterResource(Res.drawable.play_arrow)
	val pauseIconPainter = painterResource(Res.drawable.pause)
	val resetIconPainter = painterResource(Res.drawable.reset)

	Column(
		Modifier.fillMaxHeight().background(Base),
		verticalArrangement = Arrangement.Center,
		horizontalAlignment = Alignment.CenterHorizontally
	) {
		Text(
			text = timerState.formatedTime.value,
			fontWeight = FontWeight.ExtraBold,
			fontSize = 50.sp,
			color = Text
		)

		Spacer(Modifier.height(30.dp))
		Row(
			horizontalArrangement = Arrangement.Center,
			verticalAlignment = Alignment.CenterVertically,
			modifier = Modifier.fillMaxWidth()
		) {

			if (timerState.isTimerRunning.value) {
				Button(
					onClick = { timerState.pause() }, colors = ButtonDefaults.buttonColors(
						containerColor = Maroon,
						contentColor = Text
					),
					modifier = Modifier.padding(5.dp)
				) {
					Icon(painter = pauseIconPainter, contentDescription = "Pause", tint = Crust)
				}
			} else {
				Button(
					onClick = { timerState.startTimer() }, colors = ButtonDefaults.buttonColors(
						containerColor = Green,
						contentColor = Text
					),
					modifier = Modifier.padding(5.dp)
				) {
					Icon(painter = playIconPainter, contentDescription = "Play", tint = Crust)
				}
			}
			Spacer(Modifier.width(16.dp))
			Button(
				onClick = { timerState.reset() }, colors = ButtonDefaults.buttonColors(
					containerColor = Red,
					contentColor = Text
				),
				modifier = Modifier.padding(5.dp)
			) {
				Icon(painter = resetIconPainter, contentDescription = "Reset", tint = Crust)
			}
		}
	}
}