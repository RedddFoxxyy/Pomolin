package com.redddfoxxyy.pomolin.handlers

import androidx.compose.runtime.mutableStateOf

enum class PomoDoroRoutines(val duration: Float) {
	Working(25f),
	ShortBreak(5f),
	LongBreak(20f)
}

internal class RoutineManager {
	init {
		// SO that this shit gets created here and now when its method is run for the firs time.
		Audio
	}

	private var currentRoutine = PomoDoroRoutines.Working
	internal val currentTimer = mutableStateOf(Timer(25f))

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
		currentRoutine = routine
	}
}