package com.example.inventoryapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.drawToBitmap
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.inventoryapp.R
import com.example.inventoryapp.databinding.FragmentEditBinding
import com.example.inventoryapp.model.Shoes
import com.example.inventoryapp.presenter.Contract
import com.example.inventoryapp.presenter.ShoesPresenter
import com.example.inventoryapp.util.*

class EditFragment : Fragment(), Contract.ShoesView {

    private var _binding: FragmentEditBinding? = null
    private val binding get() = _binding!!

    private val args by navArgs<EditFragmentArgs>()
    private lateinit var shoes: Shoes

    private lateinit var presenter: ShoesPresenter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEditBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        getAndSetInfo()
        editShoes()
    }

    private fun init() {
        shoes = args.shoes
        presenter = ShoesPresenter(requireContext())
    }

    private fun editShoes() {
        binding.apply {
            btnEditSave.setOnClickListener {
                if (etEditItemName.text.isNullOrBlank()) {
                    etEditItemName.showError()
                } else if (etEditItemPrice.text.isNullOrBlank()) {
                    etEditItemPrice.showError()
                } else if (etEditItemBrand.text.isNullOrBlank()) {
                    etEditItemBrand.showError()
                } else if (etEditItemQuantity.text.isNullOrBlank()) {
                    etEditItemQuantity.showError()
                } else {
                    val name = etEditItemName.text.toString().trim()
                    val price = try {
                        etEditItemPrice.text.toString().trim().toDouble()
                    } catch (e: Exception) {
                        .0
                    }
                    val brand = etEditItemBrand.text.toString().trim()
                    val quantity = try {
                        etEditItemQuantity.text.toString().trim().toInt()
                    } catch (e: Exception) {
                        0
                    }
                    val shoes = Shoes(
                        name = name,
                        price = price,
                        brand = brand,
                        quantity = quantity,
                        image = imgEdit.drawToBitmap()
                    )
                    presenter.updateShoes(shoes)
                    MyUtils.toast(requireContext(), getString(R.string.item_updated))
                    findNavController().navigateUp()
                }
            }
        }
    }

    private fun getAndSetInfo() {
        binding.apply {
            tbEdit.setNavigationOnClickListener { findNavController().navigateUp() }
            etEditItemName.setText(shoes.name)
            etEditItemPrice.setText(shoes.price.toString())
            etEditItemBrand.setText(shoes.brand)
            etEditItemQuantity.setText(shoes.quantity.toString())
            Glide.with(requireContext())
                .load(shoes.image)
                .placeholder(R.drawable.placeholder)
                .into(imgEdit)
        }
    }

    override fun showShoes(shoes: List<Shoes>) {

    }

    override fun showError(message: String) {
        MyUtils.toast(requireContext(), message)
    }
}