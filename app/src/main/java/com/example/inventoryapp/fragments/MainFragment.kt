package com.example.inventoryapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.inventoryapp.R
import com.example.inventoryapp.adapters.RvAdapter
import com.example.inventoryapp.databinding.FragmentMainBinding
import com.example.inventoryapp.model.Shoes

class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    val mAdapter by lazy { RvAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRv()
        getData()
    }

    private fun setupRv() {
        binding.rvMain.apply {
            adapter = mAdapter
            layoutManager = GridLayoutManager(requireContext(), 2, RecyclerView.VERTICAL, false)
            isSaveEnabled = true
        }
    }

    private fun getData() {
        val list = listOf(
            Shoes("Air Jordan", "Nike", 1500.0, 105, R.drawable.air_jordan),
            Shoes("Jordan Max Aura", "Nike", 1500.0, 105, R.drawable.jordan_max_aura),
            Shoes("Air Jordan 4 Retro", "Nike", 1500.0, 105, R.drawable.img),
            Shoes("Yeezy 350", "adidas", 1500.0, 105, R.drawable.img_2),
            Shoes("Yeezy 700", "adidas", 1500.0, 105, R.drawable.img_3),
        )
        mAdapter.updateData(list)
    }
}