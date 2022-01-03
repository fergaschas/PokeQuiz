package com.example.pokequiz

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.pokequiz.databinding.FragmentStartBinding
import com.google.android.material.chip.Chip

class StartFragment : Fragment() {

    private lateinit var binding: FragmentStartBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStartBinding.inflate(inflater)
        binding.startFragment = this
        return binding.root
    }

    fun startGame(){
        val checkedId = binding.chipGroup.checkedChipId
        val (min:Int, max:Int) = generationRange(checkedId)
        val action = StartFragmentDirections.actionStartFragmentToGameFragment(min, max)
        findNavController().navigate(action)
    }

    private fun generationRange(id:Int): Pair<Int,Int>{
        when(id){
            R.id.gen1 -> return Pair(1, 151)
            R.id.gen2 -> return Pair(152, 251)
            R.id.gen3 -> return Pair(252, 386)
            R.id.gen4 -> return Pair(387, 493)
            R.id.gen5 -> return Pair(494, 649)
            R.id.gen6 -> return Pair(650, 721)
            R.id.gen7 -> return Pair(722, 809)
            R.id.gen8 -> return Pair(810, 898)
        }
        return Pair(1, 898)
    }
}