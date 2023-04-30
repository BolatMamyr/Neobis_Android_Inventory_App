package com.example.inventoryapp.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.inventoryapp.R
import com.example.inventoryapp.adapters.RvAdapter
import com.example.inventoryapp.databinding.FragmentMainBinding
import com.example.inventoryapp.db.AppDatabase
import com.example.inventoryapp.model.Shoes
import com.example.inventoryapp.presenter.Contract
import com.example.inventoryapp.presenter.ShoesPresenter

class MainFragment : Fragment(), Contract.ShoesView {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private val mAdapter by lazy { RvAdapter() }
    private lateinit var presenter: ShoesPresenter

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
//        getData()
        setOnFloatingButtonListener()
        initPresenter()
    }

    private fun initPresenter() {
        val db = AppDatabase.getInstance(requireContext())
        presenter = ShoesPresenter(db!!.shoesDao())
        presenter.attachView(this)
        presenter.getAllShoes()
    }
    private fun setOnFloatingButtonListener() {
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
        mAdapter.onItemClick = {
            val action = MainFragmentDirections.actionMainFragmentToEditFragment(it)
            findNavController().navigate(action)
        }
    }

    override fun showShoes(shoes: List<Shoes>) {
        mAdapter.updateData(shoes)
    }

    override fun showError(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }
}