package com.redddfoxxyy.pomolin.handlers

import javazoom.jlgui.basicplayer.BasicPlayer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import pomolin.composeapp.generated.resources.Res
import java.net.URI
import java.net.URL

object Audio {
	private val player = BasicPlayer()
	private var audioFile: URL? = null
	private fun loadAudioResources() {
		audioFile = URI(Res.getUri("files/timerComplete.mp3")).toURL()
	}

	init {
		loadAudioResources()
		player.open(audioFile)
	}

	internal suspend fun playCompletionSound() {
		withContext(Dispatchers.IO) {
			try {
				player.play()
			} catch (e: Exception) {
				println("Audio playback failed: ${e.message}")
				java.awt.Toolkit.getDefaultToolkit().beep()
			}
		}
	}
}