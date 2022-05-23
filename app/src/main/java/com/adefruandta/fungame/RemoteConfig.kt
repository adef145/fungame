package com.adefruandta.fungame

import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfig.DEFAULT_VALUE_FOR_LONG
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings


val firebaseRemoteConfig by lazy {
    FirebaseRemoteConfig.getInstance().also {
        if (BuildConfig.DEBUG) {
            it.setConfigSettingsAsync(
                FirebaseRemoteConfigSettings.Builder()
                    .setMinimumFetchIntervalInSeconds(3600)
                    .build()
            )
        }
        it.fetchAndActivate()
    }
}

fun getNumberOfGame(): Long {
    return firebaseRemoteConfig.getLong("NUMBER_OF_GAME").let {
        if (it == DEFAULT_VALUE_FOR_LONG) {
            3
        } else {
            it
        }
    }
}

fun getNumberOfQuestion(): Long {
    return firebaseRemoteConfig.getLong("NUMBER_OF_QUESTION").let {
        if (it == DEFAULT_VALUE_FOR_LONG) {
            8
        } else {
            it
        }
    }
}