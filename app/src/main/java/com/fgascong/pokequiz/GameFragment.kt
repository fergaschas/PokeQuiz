package com.fgascong.pokequiz

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs

import com.fgascong.pokequiz.databinding.GameFragmentBinding

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
        viewModel.startGame()

        viewModel.timer.observe(viewLifecycleOwner){ time ->
            if(time <= 0L){
                goToScore()
            }
        }

        return binding.root
    }

    public fun goToScore() {
        val score = viewModel.score.value ?: 0
        val action = GameFragmentDirections.actionGameFragmentToScoreFragment(score)
        findNavController().navigate(action)
    }
}