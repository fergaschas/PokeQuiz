package com.example.pokequiz

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController

import com.example.pokequiz.databinding.GameFragmentBinding

class GameFragment : Fragment() {

    private val viewModel: GameViewModel by viewModels()
    private lateinit var binding: GameFragmentBinding
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = GameFragmentBinding.inflate(inflater)
        navController = findNavController()
        binding.fragment = this
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        return binding.root
    }

    fun goToScore(){
        val score = viewModel.score.value ?: 0
        val action = GameFragmentDirections.actionGameFragmentToScoreFragment(score)
        navController.navigate(action)
    }
}