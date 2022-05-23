package com.adefruandta.fungame.model

import com.google.firebase.database.DataSnapshot

class OptionQuestion(private val dataSnapshot: DataSnapshot) {

    val option: String by lazy {
        dataSnapshot.child("option").getValue(String::class.java) ?: ""
    }

    val questions: List<String> by lazy {
        val result = mutableListOf<String>()
        dataSnapshot.child("questions").children.forEach {
            result.add(it.getValue(String::class.java) ?: return@forEach)
        }
        result
    }
}