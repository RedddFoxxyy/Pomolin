package com.redddfoxxyy.pomolin.handlers

import androidx.compose.runtime.mutableStateOf
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import pomolin.composeapp.generated.resources.Res
import javax.sound.sampled.AudioSystem

class Timer(durationMinutes: Int) {
	private var coroutineScope = CoroutineScope(Dispatchers.Main)
	private val initialTimeMillis = durationMinutes * 60 * 1000L
	private val timeMillis = MutableStateFlow(initialTimeMillis)
	internal val formatedTime = mutableStateOf(formatTime(initialTimeMillis))
	private val lastUpdateTime = MutableStateFlow(0L)
	internal val isTimerRunning = MutableStateFlow(false)

	// TODO: Fix audio, it sometimes does not play
	private suspend fun playSound() {
		withContext(Dispatchers.IO) {
			try {
				val clip = AudioSystem.getClip()
				val resource = Res.readBytes("files/timerComplete.wav")
				val byteArrayInputStream = resource.inputStream()
				val audioInputStream = AudioSystem.getAudioInputStream(byteArrayInputStream)

				clip.open(audioInputStream)
				clip.start()

				Thread.sleep(clip.microsecondLength / 1000)

			} catch (e: Exception) {
				println("Error playing sound: ${e.message}")
			}
		}
	}

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
				playSound()
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
