package com.example.inventoryapp.fragments

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.content.FileProvider
import androidx.core.net.toUri
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.inventoryapp.BuildConfig
import com.example.inventoryapp.R
import com.example.inventoryapp.databinding.FragmentEditBinding
import com.example.inventoryapp.model.Shoes
import com.example.inventoryapp.presenter.Contract
import com.example.inventoryapp.presenter.ShoesPresenter
import com.example.inventoryapp.util.*
import java.io.File

class EditFragment : Fragment(), Contract.ShoesView {

    private var _binding: FragmentEditBinding? = null
    private val binding get() = _binding!!

    private val args by navArgs<EditFragmentArgs>()
    private lateinit var shoes: Shoes

    private lateinit var presenter: ShoesPresenter
    private var imgUri: Uri? = null
    private var tempImgUri: Uri? = null

    private val takeImageResult =
        registerForActivityResult(ActivityResultContracts.TakePicture()) { isSuccess ->
            if (isSuccess) {
                imgUri = tempImgUri
                binding.imgEdit.setImageURI(imgUri)
            } else {
                Log.d("MyLog", "takeImageResult: ERROR")
            }
        }

    private val selectImageResult =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            uri?.let {
                imgUri = uri
                binding.imgEdit.setImageURI(imgUri)
            }
        }

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
        showDialogForImage()
        editShoes()
        cancelChanges()
    }

    private fun cancelChanges() {
        binding.btnEditCancel.setOnClickListener { findNavController().navigateUp() }
    }

    private fun showDialogForImage() {
        val dialog = AlertDialog.Builder(requireContext()).apply {
            setTitle("Выберите источник картины")
            setMessage("Вы хотите сделать новое фото или выбрать из галереи?")
            setPositiveButton("Камера") { dialog, which ->
                takeImage()
            }
            setNegativeButton("Галерея") { dialog, which ->
                selectImageResult.launch(AddFragment.IMG_MIME_TYPE)
            }
            create()
        }
        binding.imgEdit.setOnClickListener {
            dialog.show()
        }
    }

    private fun takeImage() {
        lifecycleScope.launchWhenStarted {
            getImgUri().let { uri ->
                tempImgUri = uri
                takeImageResult.launch(uri)
            }
        }
    }

    private fun init() {
        shoes = args.shoes
        imgUri = shoes.image?.toUri()
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
                    val newName = etEditItemName.text.toString().trim()
                    val newPrice = try {
                        etEditItemPrice.text.toString().trim().toDouble()
                    } catch (e: Exception) {
                        .0
                    }
                    val newBrand = etEditItemBrand.text.toString().trim()
                    val newQuantity = try {
                        etEditItemQuantity.text.toString().trim().toInt()
                    } catch (e: Exception) {
                        0
                    }
                    shoes.apply {
                        name = newName
                        price = newPrice
                        brand = newBrand
                        quantity = newQuantity
                        image = imgUri.toString()
                    }
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
                .load(imgUri)
                .placeholder(R.drawable.placeholder)
                .into(imgEdit)
        }
    }

    override fun showError(message: String) {
        MyUtils.toast(requireContext(), message)
    }

    private fun getImgUri(): Uri {
        val tmpFile =
            File.createTempFile("tmp_image_file", ".png", requireActivity().cacheDir).apply {
                createNewFile()
                deleteOnExit()
            }

        return FileProvider.getUriForFile(
            requireContext().applicationContext,
            "${BuildConfig.APPLICATION_ID}.provider",
            tmpFile
        )
    }
}