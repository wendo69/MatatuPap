buildscript {
    dependencies {
        classpath("com.google.gms:google-services:4.4.2")  // Older version for compatibility
    }
}
plugins {
    id("com.android.application") version "8.1.4" apply false
    id("org.jetbrains.kotlin.android") version "1.9.0" apply false
    id("com.google.gms.google-services") version "4.3.15" apply false  // Recommended Firebase plugin version
}
