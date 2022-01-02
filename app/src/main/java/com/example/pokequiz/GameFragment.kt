package com.example.pokequiz

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs

import com.example.pokequiz.databinding.GameFragmentBinding

class GameFragment : Fragment() {

    private val viewModel: GameViewModel by viewModels()
    private lateinit var binding: GameFragmentBinding
    private val args: GameFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = GameFragmentBinding.inflate(inflater)
        binding.gameFragment = this
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        viewModel.minPokemon.value = args.firstPokemon
        viewModel.maxPokemon.value = args.lastPokemon

        return binding.root
    }

    fun goToScore() {
        val score = viewModel.score.value ?: 0
        val action = GameFragmentDirections.actionGameFragmentToScoreFragment(score)
        findNavController().navigate(action)
    }
}