package com.redddfoxxyy.pomolin

import pomolin.composeapp.generated.resources.Pomolin
import pomolin.composeapp.generated.resources.Res
import org.jetbrains.compose.resources.painterResource
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import java.awt.Dimension
import javax.swing.SwingUtilities

fun main() = application {
	val icon = painterResource(Res.drawable.Pomolin)
	Window(
		onCloseRequest = ::exitApplication,
		title = "Pomolin",
		resizable = true,
		icon = icon,
	) {
		SwingUtilities.getWindowAncestor(this.window.rootPane)?.apply {
			minimumSize = Dimension(350, 550)  // Min width x height
//			maximumSize = Dimension(1920, 1080) // Optional: Max size
		}
		App()
	}
}