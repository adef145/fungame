package com.adefruandta.fungame.module.game

import androidx.lifecycle.*
import com.adefruandta.fungame.getNumberOfGame
import com.adefruandta.fungame.model.Game
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GameViewModel : ViewModel() {

    val gamesLiveData: MutableLiveData<List<Game>> = MutableLiveData()

    val onGameDoneLiveData: MutableLiveData<Game> = MutableLiveData()

    fun generateGame() {
        viewModelScope.launch(Dispatchers.IO) {
            // Write a message to the database
            // Write a message to the database
            val database: FirebaseDatabase = FirebaseDatabase.getInstance()
            val gamesReference: DatabaseReference = database.getReference("games")
            gamesReference.get().addOnSuccessListener {
                val games = mutableListOf<Game>()
                it.children.shuffled().subList(0, Math.min(it.childrenCount, getNumberOfGame()).toInt()).forEach {
                    games.add(Game(it))
                }

                gamesLiveData.postValue(games)
            }
        }
    }

    fun getGame(position: Int): LiveData<Game> {
        return Transformations.map(gamesLiveData) {
            it[position]
        }
    }

    fun riseGameDone(game: Game) {
        onGameDoneLiveData.postValue(game)
    }
}