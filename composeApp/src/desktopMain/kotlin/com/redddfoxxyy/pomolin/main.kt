package com.redddfoxxyy.pomolin

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import pomolin.composeapp.generated.resources.Pomolin
import pomolin.composeapp.generated.resources.Res
import org.jetbrains.compose.resources.painterResource
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
	val icon = painterResource(Res.drawable.Pomolin)
	Window(
		onCloseRequest = ::exitApplication,
		title = "Pomolin",
		resizable = true,
		icon = icon,
	) {
		App()
	}
}