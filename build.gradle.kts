buildscript {
    repositories {
        // Make sure that you have the following two repositories
        google()  // Google's Maven repository

        mavenCentral()  // Maven Central repository

    }

    dependencies {
        classpath("com.android.tools.build:gradle:3.4.3")
        classpath("com.google.gms:google-services:4.4.1")

        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.9.0")
    }
}
// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.2.0" apply false
    id("org.jetbrains.kotlin.android") version "1.9.0" apply false
    id("com.android.library") version "7.2.1" apply false
}