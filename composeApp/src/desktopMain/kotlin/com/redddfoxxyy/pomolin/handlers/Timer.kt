package com.redddfoxxyy.pomolin.handlers

import androidx.compose.runtime.mutableStateOf
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class Timer {
	private var coroutineScope = CoroutineScope(Dispatchers.Main)
	var formatedTime = mutableStateOf("00:00")
	private val lastUpdateTime = MutableStateFlow(0L)
	private val timeMillis = MutableStateFlow(0L)
	private val isTimerRunning = MutableStateFlow(false)

	fun startTimer() {
		if (isTimerRunning.value) return
		coroutineScope.launch {
			lastUpdateTime.value = System.currentTimeMillis()
			isTimerRunning.value = true

			while (isTimerRunning.value) {
				delay(10L)
				timeMillis.value += System.currentTimeMillis() - lastUpdateTime.value
				lastUpdateTime.value = System.currentTimeMillis()
				formatedTime.value = formatTime(timeMillis.value)
			}
		}
	}

	fun pause() {
		isTimerRunning.value = false
	}

	fun reset() {
		isTimerRunning.value = false
		timeMillis.value = 0L
		lastUpdateTime.value = 0L
		formatedTime.value = "00:00"
		coroutineScope.cancel()
		coroutineScope = CoroutineScope(Dispatchers.Main)
	}

	private fun formatTime(timeMillis: Long): String {
		val totalSeconds = timeMillis / 1000
		val minutes = (totalSeconds / 60) % 60
		val seconds = totalSeconds % 60
		return String.format("%02d:%02d", minutes, seconds)
	}
}