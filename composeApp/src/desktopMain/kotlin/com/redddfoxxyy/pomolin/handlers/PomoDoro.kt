package com.redddfoxxyy.pomolin.handlers

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

enum class PomoDoroRoutines {
	Working,
	ShortBreak,
	LongBreak
}

class RoutineManager {
	private val currentRoutine = mutableStateOf(PomoDoroRoutines.Working)
	var currentTimer = mutableStateOf(Timer())

	fun startDefault() {

	}
}