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
		}
		commonTest.dependencies {
			implementation(libs.kotlin.test)
		}
		desktopMain.dependencies {
			implementation(compose.desktop.currentOs)
			implementation(libs.kotlinx.coroutinesSwing)
			implementation(compose.components.resources)
			implementation(compose.animation)
			implementation("com.googlecode.soundlibs:mp3spi:1.9.5.4")
			implementation("com.googlecode.soundlibs:basicplayer:3.0.0.0")
			implementation("ch.qos.logback:logback-classic:1.5.18")
		}
	}
}


compose.desktop {
	application {
		mainClass = "com.redddfoxxyy.pomolin.MainKt"
		nativeDistributions {
			targetFormats(
				TargetFormat.Dmg,
				TargetFormat.Pkg,
				TargetFormat.Exe,
				TargetFormat.Msi,
				TargetFormat.Deb,
//				TargetFormat.AppImage,
				TargetFormat.Rpm
			)
			packageName = "pomolin"
			packageVersion = "1.0.0"
			linux {
				iconFile.set(project.file("src/desktopMain/composeResources/drawable/Pomolin.png"))
				packageName = "pomolin"
				debMaintainer = "RedddFoxxyy"
				appCategory = "Utility"
				description = "A simple Pomodoro App written in Kotlin. Focus on what matters! "
				licenseFile.set(project.file("../LICENSE"))
				vendor = "RedddFoxxyy"
			}
			macOS {
				packageName = "pomolin"
			}
			windows {
				packageName = "pomolin"
			}
		}
		jvmArgs += listOf(
//			"-XX:+UseZGC",
			"-Xms256m",
			"-Xmx512m",
//			"-Dskiko.renderer=VULKAN"
//            "-XX:+AlwaysPreTouch"
		)
		buildTypes.release.proguard {
			configurationFiles.from("proguard-rules.pro")
		}
	}
}
