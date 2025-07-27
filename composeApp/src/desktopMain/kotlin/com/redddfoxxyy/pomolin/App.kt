package com.redddfoxxyy.pomolin

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.redddfoxxyy.pomolin.ui.screens.CompositeScreen
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
	MaterialTheme {
		Scaffold(modifier = Modifier.safeContentPadding().fillMaxSize()) {
			CompositeScreen()
		}
	}
}
