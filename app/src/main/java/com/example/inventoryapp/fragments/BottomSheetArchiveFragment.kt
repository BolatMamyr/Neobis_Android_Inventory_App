package com.example.inventoryapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.inventoryapp.R
import com.example.inventoryapp.databinding.FragmentBottomSheetArchiveBinding
import com.example.inventoryapp.databinding.FragmentMainBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class BottomSheetArchiveFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentBottomSheetArchiveBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBottomSheetArchiveBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnArchive.setOnClickListener {

        }
    }

}