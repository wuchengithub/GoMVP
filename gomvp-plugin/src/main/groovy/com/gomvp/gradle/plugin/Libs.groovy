package com.gomvp.gradle.plugin

class Libs {
    Version version = new Version()

    String support_constraint = "com.android.support.constraint:constraint-layout:1.0.2"
    String support_v4 = "com.android.support:support-v4:$version.supportLibraryVersion"
    String support_design = "com.android.support:design:$version.supportLibraryVersion"
    String support_card_view = "com.android.support:cardview-v7:$version.supportLibraryVersion"
    String support_v7 = "com.android.support:appcompat-v7:$version.supportLibraryVersion"
    String support_annotation = "com.android.support:support-annotations:$version.supportLibraryVersion"
    String support_recyclerview = "com.android.support:recyclerview-v7:$version.supportLibraryVersion"
    String android_anotation_processor = "org.androidannotations:androidannotations:$version.AAVersion"
    String android_anotation = "org.androidannotations:androidannotations-api:$version.AAVersion"

    String butternife = "com.jakewharton:butterknife:8.8.1"
    String butternife_processor = "com.jakewharton:butterknife-compiler:8.8.1"

    String multdex =  "com.android.support:multidex:$version.multidexLibraryVersion"
    String glide = "com.github.bumptech.glide:glide:$version.glideLibraryVersion"

    String dagger2 = "com.google.dagger:dagger:$version.dagger2Version"
    String dagger2_processor =  "com.google.dagger:dagger-compiler:$version.dagger2Version"

    String okhttp = "com.squareup.okhttp3:okhttp:$version.okhttpVersion"
    String okhttp_logging = "com.squareup.okhttp3:logging-interceptor:$version.okhttpVersion"

    String gson =  "com.google.code.gson:gson:2.6.2"
    String rxjava2 = "io.reactivex.rxjava2:rxjava:2.0.1"
    String rxandroid = "io.reactivex.rxjava2:rxandroid:2.0.1"
    String retrofit = "com.squareup.retrofit2:retrofit:2.3.0"
    String retrofit_gson = "com.squareup.retrofit2:converter-gson:2.3.0"
    String retrofit_rxjava = "com.squareup.retrofit2:adapter-rxjava2:2.3.0"
    String gson_converter = "com.squareup.retrofit2:converter-gson:2.3.0"

    String kotlin_stdlib = "org.jetbrains.kotlin:kotlin-stdlib:$version.kotlin_version"
    String aspectJ = "org.aspectj:aspectjrt:1.9.1"
}