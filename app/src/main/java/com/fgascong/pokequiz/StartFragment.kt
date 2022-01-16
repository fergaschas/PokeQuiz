package com.fgascong.pokequiz

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.preference.PreferenceManager
import com.fgascong.pokequiz.databinding.FragmentStartBinding
import com.fgascong.pokequiz.utils.*

class StartFragment : Fragment() {

    private lateinit var binding: FragmentStartBinding
    private lateinit var generation: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        generation = PreferenceManager.getDefaultSharedPreferences(activity)
            .getString(GENERATIONS, ALL_GENS) ?: ALL_GENS

        binding = FragmentStartBinding.inflate(inflater)
        binding.startFragment = this
        binding.generation.text = generation

        return binding.root
    }

    fun startGame() {
        val gen = generation
        val (min: Int, max: Int) = generationRange(gen)
        val action = StartFragmentDirections.actionStartFragmentToGameFragment(min, max)
        findNavController().navigate(action)
    }

    fun showSettings() {
        val action = StartFragmentDirections.actionStartFragmentToSettingsFragment()
        findNavController().navigate(action)
    }

    private fun generationRange(gen: String): Pair<Int, Int> {
        when (gen) {
            GEN_1 -> return Pair(1, 151)
            GEN_2 -> return Pair(152, 251)
            GEN_3 -> return Pair(252, 386)
            GEN_4 -> return Pair(387, 493)
            GEN_5 -> return Pair(494, 649)
            GEN_6 -> return Pair(650, 721)
            GEN_7 -> return Pair(722, 809)
            GEN_8 -> return Pair(810, 898)
        }
        return Pair(1, 898)
    }
}