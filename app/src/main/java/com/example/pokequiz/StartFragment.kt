package com.example.pokequiz

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.pokequiz.databinding.FragmentStartBinding

class StartFragment : Fragment() {

    private lateinit var navController: NavController
    private lateinit var binding: FragmentStartBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentStartBinding.inflate(inflater)
        binding.fragment = this
        navController = findNavController()
        val navController = findNavController()
        return binding.root
    }

    fun startGame(){
        val action = StartFragmentDirections.actionStartFragmentToGameFragment()
        navController.navigate(action)
    }
}