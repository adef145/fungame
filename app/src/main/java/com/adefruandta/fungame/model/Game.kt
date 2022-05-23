package com.adefruandta.fungame.model

import com.adefruandta.fungame.ImageRendering
import com.adefruandta.fungame.RemoteImageRendering
import com.adefruandta.fungame.getNumberOfQuestion
import com.google.firebase.database.DataSnapshot

class Game(private val dataSnapshot: DataSnapshot) {

    val instruction: String by lazy {
        dataSnapshot.child("instruction").getValue(String::class.java) ?: ""
    }

    val optionQuestionMap: Map<ImageRendering, List<ImageRendering>> by lazy {
        val result = mutableMapOf<ImageRendering, List<ImageRendering>>()
        dataSnapshot.child("option_question").children.forEach {
            val optionQuestion = OptionQuestion(it)
            RemoteImageRendering(optionQuestion.option).apply {
                val questionsImageRendering = mutableListOf<ImageRendering>()
                optionQuestion.questions.forEach {
                    questionsImageRendering.add(RemoteImageRendering(it))
                }
                result[this] = questionsImageRendering
            }
        }
        result
    }

    private val questions: List<ImageRendering> by lazy {
        optionQuestionMap.values.flatten()
    }

    val options: Set<ImageRendering>
        get() = optionQuestionMap.keys

    fun generateQuestions(size: Int = getNumberOfQuestion().toInt()): List<ImageRendering> {
        val result = mutableListOf<ImageRendering>()
        for (i in 0 until size) {
            result.add(questions[(questions.indices).random()])
        }
        return result
    }

    fun isCorrect(option: ImageRendering, question: ImageRendering): Boolean {
        return optionQuestionMap[option]?.contains(question) == true
    }
}