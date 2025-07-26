package com.redddfoxxyy.pomolin.handlers

import androidx.compose.runtime.mutableStateOf

enum class PomoDoroRoutines(val duration: Int) {
	Working(25),
	ShortBreak(5),
	LongBreak(20)
}

class RoutineManager {
	private val currentRoutine = mutableStateOf(PomoDoroRoutines.Working)
	internal var currentTimer = mutableStateOf(Timer(25))
		private set

	// TODO: Change to next routine after current routine completion.
	private val routineComplete = mutableStateOf(false)

	internal fun startTimer() {
		currentTimer.value.startTimer()
	}

	internal fun pauseTimer() {
		currentTimer.value.pause()
	}

	internal fun resetTimer() {
		currentTimer.value.reset()
	}

	internal fun setRoutine(routine: PomoDoroRoutines) {
		currentTimer.value.reset()
		currentTimer.value = Timer(routine.duration)
		currentRoutine.value = routine
	}
}