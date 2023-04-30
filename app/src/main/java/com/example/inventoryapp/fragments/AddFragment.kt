package com.example.inventoryapp.fragments

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.inventoryapp.R
import com.example.inventoryapp.databinding.FragmentAddBinding
import com.example.inventoryapp.db.AppDatabase
import com.example.inventoryapp.model.Shoes
import com.example.inventoryapp.presenter.Contract
import com.example.inventoryapp.presenter.ShoesPresenter

class AddFragment : Fragment(), Contract.ShoesView {

    private var _binding: FragmentAddBinding? = null
    private val binding get() = _binding!!

    private lateinit var presenter: ShoesPresenter
    private var imgUri: Uri? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        setNavigationListener()
        getImage()
        addShoes()
    }

    private fun init() {
        val db = AppDatabase.getInstance(requireContext())
        presenter = ShoesPresenter(db!!.shoesDao())
        presenter.attachView(this)
    }

    private fun addShoes() {
        binding.apply {
            btnAddItem.setOnClickListener {
                if (etItemName.text.isNullOrBlank()) {
                    etItemName.error = getString(R.string.error_fill_fields)
                    etItemName.clearFocus()
                } else if (etItemPrice.text.isNullOrBlank()) {
                    etItemPrice.error = getString(R.string.error_fill_fields)
                    etItemPrice.clearFocus()
                }else if (etItemBrand.text.isNullOrBlank()) {
                    etItemBrand.error = getString(R.string.error_fill_fields)
                    etItemBrand.clearFocus()
                }
                else if (etItemQuantity.text.isNullOrBlank()) {
                    etItemQuantity.error = getString(R.string.error_fill_fields)
                    etItemQuantity.clearFocus()
                } else {
                    val name = etItemName.text.toString().trim()
                    val price = try {
                        etItemPrice.text.toString().trim().toDouble()
                    } catch (e: Exception) {
                        .0
                    }
                    val brand = etItemBrand.text.toString().trim()
                    val quantity = try {
                        etItemQuantity.text.toString().trim().toInt()
                    } catch (e: Exception) {
                        0
                    }
                    val shoes = Shoes(
                        name = name,
                        price = price,
                        brand = brand,
                        quantity = quantity,
                        imgUri = imgUri.toString()
                    )
                    presenter.addShoes(shoes)
                    findNavController().navigateUp()
                }

            }
        }
    }

    private fun getImage() {
        val getContent =
            registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
                Glide.with(requireContext())
                    .load(uri)
                    .placeholder(resources.getDrawable(R.drawable.placeholder, null))
                    .into(binding.imgAdd)

                imgUri = uri
            }
        binding.imgAdd.setOnClickListener {
            getContent.launch(IMG_MIME_TYPE)
        }
    }

    private fun setNavigationListener() {
        binding.tbAdd.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }

    override fun showShoes(shoes: List<Shoes>) {

    }

    override fun showError(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }

    companion object {
        const val IMG_MIME_TYPE = "image/*"
    }
}