package com.redddfoxxyy.pomolin.handlers

import androidx.compose.runtime.mutableStateOf

enum class PomoDoroRoutines(duration: Int) {
	Working(25),
	ShortBreak(5),
	LongBreak(20)
}

class RoutineManager {
	private val currentRoutine = mutableStateOf(PomoDoroRoutines.Working)
	private val currentTimer = mutableStateOf(Timer(25))
	private val routineComplete = mutableStateOf(false)

	fun startDefault() {

	}
}