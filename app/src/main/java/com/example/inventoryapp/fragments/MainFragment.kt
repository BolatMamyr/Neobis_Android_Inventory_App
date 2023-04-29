package com.example.inventoryapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
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
        addItem()
    }

    private fun addItem() {
        binding.btnAdd.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_addFragment)
        }
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
            Shoes(
                name = "Air Jordan",
                brand = "Nike",
                price = 1500.0,
                quantity = 105,
                imgId = R.drawable.air_jordan
            ),
            Shoes(
                name = "Jordan Max Aura",
                brand = "Nike",
                price = 1500.0,
                quantity = 105,
                imgId = R.drawable.jordan_max_aura
            ),
            Shoes(
                name = "Air Jordan 4 Retro",
                brand = "Nike",
                price = 1500.0,
                quantity = 105,
                imgId = R.drawable.img
            ),
            Shoes(
                name = "Yeezy 350",
                brand = "adidas",
                price = 1500.0,
                quantity = 105,
                imgId = R.drawable.img_2
            ),
            Shoes(
                name = "Yeezy 700",
                brand = "adidas",
                price = 1500.0,
                quantity = 105,
                imgId = R.drawable.img_3
            ),
        )
        mAdapter.updateData(list)
    }
}