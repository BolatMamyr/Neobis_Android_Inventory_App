package com.example.inventoryapp.fragments

import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.drawToBitmap
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.inventoryapp.R
import com.example.inventoryapp.databinding.FragmentAddBinding
import com.example.inventoryapp.db.Converters
import com.example.inventoryapp.model.Shoes
import com.example.inventoryapp.presenter.Contract
import com.example.inventoryapp.presenter.ShoesPresenter
import com.example.inventoryapp.util.MyUtils
import com.example.inventoryapp.util.MyUtils.Companion.toast
import com.example.inventoryapp.util.showError

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
        presenter = ShoesPresenter(requireContext())
        presenter.attachView(this)
    }

    private fun addShoes() {
        binding.apply {
            btnAddItem.setOnClickListener {
                if (etItemName.text.isNullOrBlank()) {
                    etItemName.showError()
                } else if (etItemPrice.text.isNullOrBlank()) {
                    etItemPrice.showError()
                } else if (etItemBrand.text.isNullOrBlank()) {
                    etItemBrand.showError()
                } else if (etItemQuantity.text.isNullOrBlank()) {
                    etItemQuantity.showError()
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
                        image = imgAdd.drawToBitmap()
                    )
                    presenter.addShoes(shoes)
                    MyUtils.toast(requireContext(), getString(R.string.item_added))
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