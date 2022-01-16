package com.fgascong.pokequiz

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

//base url + {number}.png -> image url
private const val BASE_URL = "https://github.com/PokeAPI/sprites/blob/master/sprites/pokemon/"
private const val MIN_POKEMON = 1
private const val MAX_POKEMON = 898
private const val CORRECT_POINTS = 10
private const val ERROR_POINTS = 5
private const val INITIAL_TIME_IN_MILLIS = 30_000L
private const val ONE_SECOND_IN_MILLIS = 1_000L


class GameViewModel : ViewModel() {

    private val _timer = MutableLiveData<Long>(INITIAL_TIME_IN_MILLIS)
    val timer
        get() = Transformations.map(_timer) { time ->
            time / 1000L
        }

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

    val minPokemon = MutableLiveData(MIN_POKEMON)

    val maxPokemon = MutableLiveData(MAX_POKEMON)

    private fun getRandomNumber(min: Int, max: Int): Int {
        return Random.nextInt(min, max + 1)
    }

    init {
        viewModelScope.launch {
            decreaseTimer()
        }
    }

    fun startGame() {
        _pokemonId.value = getRandomNumber(
            minPokemon.value ?: MIN_POKEMON,
            maxPokemon.value ?: MAX_POKEMON
        )

        _pokemonImage.value = "$BASE_URL${_pokemonId.value.toString()}.png?raw=true"

        _options.value = setOptions()
        _optionNames.value = setOptionNames()
    }

    private fun setOptions(): MutableList<Int> {
        val answers: MutableList<Int> = mutableListOf()
        answers.add(_pokemonId.value ?: 0)
        var number: Int

        while (answers.size < 4) {
            number = getRandomNumber(
                minPokemon.value ?: MIN_POKEMON,
                maxPokemon.value ?: MAX_POKEMON
            )
            if (number !in answers) answers.add(number)
        }
        answers.shuffle()
        return answers
    }

    private fun setOptionNames(): MutableList<String> {
        val answers: MutableList<String> = mutableListOf()
        var index = 0
        while (answers.size < 4) {
            answers.add(
                pokemonList[(_options.value?.get(index)) ?: 0]
            )
            index++
        }
        return answers
    }

    private fun resetValues() {
        _options.value?.clear()
        _optionNames.value?.clear()
    }

    fun playButton(option: Int) {

        if (option == _pokemonId.value) {
            _score.value = _score.value?.plus(CORRECT_POINTS)
        } else {
            if (_score.value!! >= ERROR_POINTS) {
                _score.value = _score.value?.minus(ERROR_POINTS)
            }
        }
        resetValues()
        startGame()
    }

    private suspend fun decreaseTimer() {
        while (_timer.value?.compareTo(0L) ?: 0 >= 0) {
            _timer.value = _timer.value?.minus(ONE_SECOND_IN_MILLIS)
            delay(ONE_SECOND_IN_MILLIS)
        }
    }
}