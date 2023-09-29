// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    val hiltVersion = "2.47"
    dependencies {
        classpath ("com.google.dagger:hilt-android-gradle-plugin:$hiltVersion")
        classpath("com.android.tools.build:gradle:8.1.0")
        classpath ("com.huawei.agconnect:agcp:1.9.1.300")

    }
    repositories {
        mavenCentral()
        maven ( url = "https://github.com/userxpro/userx/raw/maven/" )
        maven ( url  = "https://jitpack.io" )
        maven(url = "https://developer.huawei.com/repo/")
    }

}

plugins {
    id("com.android.application") version "8.1.0" apply false
    id("org.jetbrains.kotlin.android") version "1.8.10" apply false
}

tasks {
    register("clean", Delete::class) {
        delete(rootProject.buildDir)
    }
}
