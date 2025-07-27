package com.redddfoxxyy.pomolin.handlers

import androidx.compose.runtime.mutableStateOf
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class Timer(durationMinutes: Float) {
	// Routine Handlers
	private var coroutineScope = CoroutineScope(Dispatchers.Main)

	// Timer Handlers
	private val initialTimeMillis = (durationMinutes * 60 * 1000L).toLong()
	private var timeMillis = initialTimeMillis
	internal val formatedTime = mutableStateOf(formatTime(initialTimeMillis))
	private var lastUpdateTime = 0L
	internal val isTimerRunning = MutableStateFlow(false)
	private var audioPlayed = false

	fun startTimer() {
		if (isTimerRunning.value || timeMillis <= 0L) return

		coroutineScope.launch {
			lastUpdateTime = System.currentTimeMillis()
			isTimerRunning.value = true

			while (isTimerRunning.value && timeMillis > 0) {
				delay(10L)
				val elapsed = System.currentTimeMillis() - lastUpdateTime
				timeMillis -= elapsed
				lastUpdateTime = System.currentTimeMillis()

				if (timeMillis < 0L) {
					timeMillis = 0L
				}

				if (timeMillis <= 2000L && !audioPlayed) {
					Audio.playCompletionSound()
					audioPlayed = true
				}

				formatedTime.value = formatTime(timeMillis)
			}

			if (timeMillis <= 0L) {
				isTimerRunning.value = false
				audioPlayed = false
			}
		}
	}

	fun pause() {
		isTimerRunning.value = false
	}

	fun reset() {
		isTimerRunning.value = false
		timeMillis = initialTimeMillis
		lastUpdateTime = 0L
		formatedTime.value = formatTime(initialTimeMillis)
		audioPlayed = false
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
