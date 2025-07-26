package com.redddfoxxyy.pomolin.ui.screens

import androidx.compose.desktop.ui.tooling.preview.Preview
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

@Composable
@Preview
fun TimerScreen() {
	val timerState = remember { Timer() }

	Column(
		Modifier.fillMaxHeight().background(Base),
		verticalArrangement = Arrangement.Center,
		horizontalAlignment = Alignment.CenterHorizontally
	) {
		Text(
			text = timerState.formatedTime.value,
			fontWeight = FontWeight.Bold,
			fontSize = 30.sp,
			color = Text
		)
		Spacer(Modifier.height(30.dp))
		Row(
			horizontalArrangement = Arrangement.Center,
			verticalAlignment = Alignment.CenterVertically,
			modifier = Modifier.fillMaxWidth()
		) {
			Button(
				onClick = { timerState.startTimer() }, colors = ButtonDefaults.buttonColors(
					containerColor = Green,
					contentColor = Text
				),
				modifier = Modifier.padding(5.dp)
			) {
				Text("Start", color = Text)
			}
			Spacer(Modifier.width(16.dp))
			Button(
				onClick = { timerState.pause() }, colors = ButtonDefaults.buttonColors(
					containerColor = Maroon,
					contentColor = Text
				),
				modifier = Modifier.padding(5.dp)
			) {
				Text("Pause", color = Text)
			}
			Spacer(Modifier.width(16.dp))
			Button(
				onClick = { timerState.reset() }, colors = ButtonDefaults.buttonColors(
					containerColor = Red,
					contentColor = Text
				),
				modifier = Modifier.padding(5.dp)
			) {
				Text("Reset", color = Text)
			}
		}
	}
}