package com.redddfoxxyy.pomolin.handlers

import androidx.compose.runtime.mutableStateOf
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class Timer(durationMinutes: Int) {
	private var coroutineScope = CoroutineScope(Dispatchers.Main)
	private val initialTimeMillis = durationMinutes * 60 * 1000L
	private val timeMillis = MutableStateFlow(initialTimeMillis)
	var formatedTime = mutableStateOf(formatTime(initialTimeMillis))
		private set

	private val lastUpdateTime = MutableStateFlow(0L)
	internal var isTimerRunning = MutableStateFlow(false)
		private set

	fun startTimer() {
		if (isTimerRunning.value || timeMillis.value <= 0L) return

		coroutineScope.launch {
			lastUpdateTime.value = System.currentTimeMillis()
			isTimerRunning.value = true

			while (isTimerRunning.value && timeMillis.value > 0) {
				delay(10L)
				val elapsed = System.currentTimeMillis() - lastUpdateTime.value
				timeMillis.value -= elapsed
				lastUpdateTime.value = System.currentTimeMillis()

				if (timeMillis.value < 0L) {
					timeMillis.value = 0L
				}

				formatedTime.value = formatTime(timeMillis.value)
			}

			if (timeMillis.value <= 0L) {
				isTimerRunning.value = false
			}
		}
	}

	fun pause() {
		isTimerRunning.value = false
	}

	fun reset() {
		isTimerRunning.value = false
		timeMillis.value = initialTimeMillis
		lastUpdateTime.value = 0L
		formatedTime.value = formatTime(initialTimeMillis)
		coroutineScope.cancel()
		coroutineScope = CoroutineScope(Dispatchers.Main)
	}

	private fun formatTime(millis: Long): String {
		val totalSeconds = millis / 1000
		val minutes = (totalSeconds / 60)
		val seconds = totalSeconds % 60
		return String.format("%02d:%02d", minutes, seconds)
	}
}
