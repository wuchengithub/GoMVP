import Versions.AAVersion
import Versions.dagger2Version
import Versions.glideLibraryVersion
import Versions.kotlin_version
import Versions.multidexLibraryVersion
import Versions.okhttpVersion
import Versions.supportLibraryVersion

object Versions {
    val multidexLibraryVersion = "1.0.2"
    val supportLibraryVersion = "27.0.2"
    val AAVersion = "4.4.0"
    val glideLibraryVersion = "3.7.0"
    val retrofitLibraryVersion = "2.3.0"
    val dagger2Version = "2.11-rc2"
    val okhttpVersion = "3.8.1"
    val kotlin_version = "1.2.51"

}
object Config {
    val compileSdkVersion = 27
    val buildToolsVersion = "27.0.3"
    val minSdkVersion = 15
    val targetSdkVersion = 19
    val applicationId = "com.xstore.assistant"
    val versionName = "1.3.0"
    val versionCode = 7
}

object Libs {
    val support_constraint = "com.android.support.constraint:constraint-layout:1.0.2"
    val support_v4 = "com.android.support:support-v4:$supportLibraryVersion"
    val support_design = "com.android.support:design:$supportLibraryVersion"
    val support_card_view = "com.android.support:cardview-v7:$supportLibraryVersion"
    val support_v7 = "com.android.support:appcompat-v7:$supportLibraryVersion"
    val support_annotation = "com.android.support:support-annotations:$supportLibraryVersion"
    val support_recyclerview = "com.android.support:recyclerview-v7:$supportLibraryVersion"
    val android_anotation_processor = "org.androidannotations:androidannotations:$AAVersion"
    val android_anotation = "org.androidannotations:androidannotations-api:$AAVersion"

    val butternife = "com.jakewharton:butterknife:8.8.1"
    val butternife_processor = "com.jakewharton:butterknife-compiler:8.8.1"

    val multdex =  "com.android.support:multidex:$multidexLibraryVersion"
    val glide = "com.github.bumptech.glide:glide:$glideLibraryVersion"

    val dagger2 = "com.google.dagger:dagger:$dagger2Version"
    val dagger2_processor =  "com.google.dagger:dagger-compiler:$dagger2Version"

    val okhttp = "com.squareup.okhttp3:okhttp:$okhttpVersion"
    val okhttp_logging = "com.squareup.okhttp3:logging-interceptor:$okhttpVersion"

    val gson =  "com.google.code.gson:gson:2.6.2"
    val rxjava2 = "io.reactivex.rxjava2:rxjava:2.0.1"
    val rxandroid = "io.reactivex.rxjava2:rxandroid:2.0.1"
    val retrofit = "com.squareup.retrofit2:retrofit:2.3.0"
    val retrofit_gson = "com.squareup.retrofit2:converter-gson:2.3.0"
    val retrofit_rxjava = "com.squareup.retrofit2:adapter-rxjava2:2.3.0"
    val gson_converter = "com.squareup.retrofit2:converter-gson:2.3.0"

    val kotlin_stdlib = "org.jetbrains.kotlin:kotlin-stdlib:$kotlin_version"
    val aspectJ = "org.aspectj:aspectjrt:1.9.1"
}