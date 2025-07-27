-dontwarn javax.servlet.**
-dontwarn javax.mail.**
-dontwarn org.codehaus.janino.**
-dontwarn org.codehaus.commons.compiler.**
-dontwarn sun.reflect.**

-keep class javazoom.jlgui.basicplayer.** { *; }
-keep class com.googlecode.soundlibs.** { *; }

-keep class * implements javax.sound.sampled.spi.AudioFileReader
-keep class * implements javax.sound.sampled.spi.FormatConversionProvider

-dontwarn javazoom.**
-dontwarn tritonus.**

-keep class org.slf4j.impl.StaticMDCBinder { *; }
-keep class org.slf4j.impl.StaticMarkerBinder { *; }

-dontwarn org.slf4j.**
-dontwarn jakarta.**
-dontwarn org.tukaani.xz.**
-dontwarn ch.qos.logback.classic.servlet.**
-dontwarn ch.qos.logback.core.net.**