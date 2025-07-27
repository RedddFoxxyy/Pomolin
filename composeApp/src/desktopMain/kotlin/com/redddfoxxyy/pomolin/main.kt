package com.redddfoxxyy.pomolin

import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import org.jetbrains.compose.resources.painterResource
import pomolin.composeapp.generated.resources.Pomolin
import pomolin.composeapp.generated.resources.Res
import java.awt.Dimension
import javax.swing.SwingUtilities

fun main() = application {
	val icon = painterResource(Res.drawable.Pomolin)
	Window(
		onCloseRequest = ::exitApplication,
		title = "Pomolin",
		resizable = true,
		state = rememberWindowState(width = 400.dp, height = 550.dp),
		icon = icon,
	) {
		SwingUtilities.getWindowAncestor(this.window.rootPane)?.apply {
			minimumSize = Dimension(400, 550)
		}
		App()
	}
}