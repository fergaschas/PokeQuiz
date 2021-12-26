package com.example.pokequiz

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kotlin.random.Random

//base url + {number}.png -> image url
private const val BASE_URL = "https://github.com/PokeAPI/sprites/blob/master/sprites/pokemon/"
private const val MIN_POKEMON = 1
private const val MAX_POKEMON = 898


class GameViewModel : ViewModel() {

    private val _pokemonImage = MutableLiveData<String>("${BASE_URL}1.png?raw=true")
    val pokemonImage get() = _pokemonImage

    private val _pokemonId = MutableLiveData<Int>(1)
    val pokemonId get() = _pokemonId

    private val _options = MutableLiveData<MutableList<Int>>()
    val options get() = _options

    private val _optionNames = MutableLiveData<MutableList<String>>()
    val optionNames get() = _optionNames

    private val _score = MutableLiveData<Int>(0)
    val score get() = _score

    init {
        startGame()
    }

    private fun getRandomNumber(): Int {
        val random = Random.nextInt(MIN_POKEMON, MAX_POKEMON + 1)

        return if(isValidId(random)) random else getRandomNumber()
    }

    private fun isValidId(random: Int): Boolean {
        //todo: comprobar si forma parte del rango
        //for more generations 1-8
        return true
    }

    private fun startGame() {
        _pokemonId.value = getRandomNumber()
        _pokemonImage.value = "${BASE_URL}${_pokemonId.value.toString()}.png?raw=true"

        _options.value = setOptions()
        _optionNames.value = setOptionNames()
    }

    private fun setOptions(): MutableList<Int> {
        val answers: MutableList<Int> = mutableListOf()
        answers.add(_pokemonId.value?:0)

        while (answers.size < 4) {
            answers.add(getRandomNumber())
        }
        answers.shuffle()
        return answers
    }

    private fun setOptionNames(): MutableList<String>{
        val answers: MutableList<String> = mutableListOf()
        var index = 0
        while (answers.size < 4) {
            answers.add(
                pokemonList[(_options.value?.get(index)) ?:0])
            index++
        }
        return answers
    }

    private fun resetValues() {
        _options.value?.clear()
        _optionNames.value?.clear()
    }

    fun playButton(option:Int) {

        if (option == _pokemonId.value){
            _score.value = _score.value?.plus(10)
            resetValues()
            startGame()
        }else{
            _score.value = _score.value?.minus(2)
            resetValues()
            startGame()
        }

    }

}