import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
	alias(libs.plugins.kotlinMultiplatform)
	alias(libs.plugins.composeMultiplatform)
	alias(libs.plugins.composeCompiler)
	alias(libs.plugins.composeHotReload)
}

kotlin {
	jvm("desktop")

	sourceSets {
		val desktopMain by getting

		commonMain.dependencies {
			implementation(compose.runtime)
			implementation(compose.foundation)
			implementation(compose.material3)
			implementation(compose.ui)
			implementation(compose.components.resources)
			implementation(compose.components.uiToolingPreview)
			implementation(libs.androidx.lifecycle.viewmodel)
		}
		commonTest.dependencies {
			implementation(libs.kotlin.test)
		}
		desktopMain.dependencies {
			implementation(compose.desktop.currentOs)
			implementation(libs.kotlinx.coroutinesSwing)
			implementation(compose.components.resources)
		}
	}
}


compose.desktop {
	application {
		mainClass = "com.redddfoxxyy.pomolin.MainKt"
		nativeDistributions {
			targetFormats(
				TargetFormat.Dmg,
				TargetFormat.Msi,
				TargetFormat.Deb,
				TargetFormat.AppImage,
				TargetFormat.Rpm
			)
			packageName = "Pomolin"
			packageVersion = "1.0.0"
			linux {
				iconFile.set(project.file("composeResources/drawable/icons/Gicon.png"))
			}
		}
		jvmArgs += listOf(
			"-XX:+UnlockExperimentalVMOptions",
			"-XX:+UseZGC",
			"-Xms100m",
			"-Xmx512m",
			"-Dsun.java2d.vulkan=true"
//            "-XX:+AlwaysPreTouch"
		)
	}
}
