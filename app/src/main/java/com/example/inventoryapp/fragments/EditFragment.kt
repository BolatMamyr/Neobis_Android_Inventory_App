package com.example.inventoryapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.navArgs
import com.example.inventoryapp.R
import com.example.inventoryapp.databinding.FragmentAddBinding
import com.example.inventoryapp.databinding.FragmentEditBinding

class EditFragment : Fragment() {

    private var _binding: FragmentEditBinding? = null
    private val binding get() = _binding!!

//    private val args by navArgs<EditFragmentArgs>()
//    private val shoes = args.shoes

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEditBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
//            etEditItemName.setText(shoes.name)
//            etEditItemPrice.setText(shoes.price.toString())
//            etEditItemBrand.setText(shoes.brand)
//            etEditItemQuantity.setText(shoes.quantity.toString())
        }
    }

}