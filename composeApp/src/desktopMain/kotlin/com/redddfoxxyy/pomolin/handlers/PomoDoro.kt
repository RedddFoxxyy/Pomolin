package com.redddfoxxyy.pomolin.handlers

import androidx.compose.runtime.mutableStateOf

enum class PomoDoroRoutines(val displayName: String, val duration: Float) {
	Working("Pomodoro", 25f),
	ShortBreak("Short Break", 5f),
	LongBreak("Long Break", 20f)
}

internal class RoutineManager {
	init {
		// So that this shit gets created here and not when its method is run for the first time.
		Audio
	}

	val routines = PomoDoroRoutines.entries.toList()
	val currentRoutine = mutableStateOf(PomoDoroRoutines.Working)
	internal val currentTimer = mutableStateOf(Timer(25f, ::progressToNextRoutine))

	internal var workSessionDuration = 2
	private var workSessionsCompleted = 0
	internal fun startTimer() = currentTimer.value.startTimer()
	internal fun pauseTimer() = currentTimer.value.pause()

	internal fun resetTimer() {
		currentTimer.value.reset()
	}

	internal fun setRoutine(routine: PomoDoroRoutines) {
		currentTimer.value.updateDuration(routine.duration)
		currentRoutine.value = routine
	}

	private fun progressToNextRoutine() {
		when (currentRoutine.value) {
			PomoDoroRoutines.Working -> {
				workSessionsCompleted++
				if (workSessionsCompleted >= workSessionDuration) {
					setRoutine(PomoDoroRoutines.LongBreak)
					workSessionsCompleted = 0
				} else {
					setRoutine(PomoDoroRoutines.ShortBreak)
				}
			}

			PomoDoroRoutines.ShortBreak, PomoDoroRoutines.LongBreak -> {
				setRoutine(PomoDoroRoutines.Working)
			}
		}
	}
}