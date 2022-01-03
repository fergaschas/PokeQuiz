package com.example.pokequiz

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlin.random.Random

//base url + {number}.png -> image url
private const val BASE_URL = "https://github.com/PokeAPI/sprites/blob/master/sprites/pokemon/"
private const val MIN_POKEMON = 1
private const val MAX_POKEMON = 898
private const val CORRECT_POINTS = 10
private const val ERROR_POINTS = 5


class GameViewModel : ViewModel() {

    private val _pokemonImage = MutableLiveData<String>("${BASE_URL}1.png?raw=true")
    val pokemonImage get() = _pokemonImage

    private val _pokemonId = MutableLiveData<Int>(1)
    val pokemonId get() = _pokemonId

    val minPokemon = MutableLiveData(MIN_POKEMON)

    val maxPokemon = MutableLiveData(MAX_POKEMON)

    private val _options = MutableLiveData<MutableList<Int>>()
    val options get() = _options

    private val _optionNames = MutableLiveData<MutableList<String>>()
    val optionNames get() = _optionNames

    private val _score = MutableLiveData<Int>(0)
    val score get() = _score

    init {
        startGame()
    }

    private fun getRandomNumber(min: Int, max: Int): Int {
        return Random.nextInt(min, max + 1)
    }

    private fun startGame() {
        _pokemonId.value = getRandomNumber(
            minPokemon.value?: MIN_POKEMON,
            maxPokemon.value?: MAX_POKEMON)

        _pokemonImage.value = "${BASE_URL}${_pokemonId.value.toString()}.png?raw=true"

        _options.value = setOptions()
        _optionNames.value = setOptionNames()
    }

    private fun setOptions(): MutableList<Int> {
        val answers: MutableList<Int> = mutableListOf()
        answers.add(_pokemonId.value ?: 0)

        while (answers.size < 4) {
            answers.add(getRandomNumber(
                minPokemon.value?: MIN_POKEMON,
                maxPokemon.value?: MAX_POKEMON))
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

}