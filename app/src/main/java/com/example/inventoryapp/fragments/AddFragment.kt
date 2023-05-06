package com.example.inventoryapp.fragments

import android.app.AlertDialog
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.core.view.drawToBitmap
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.inventoryapp.BuildConfig
import com.example.inventoryapp.R
import com.example.inventoryapp.databinding.FragmentAddBinding
import com.example.inventoryapp.model.Shoes
import com.example.inventoryapp.presenter.Contract
import com.example.inventoryapp.presenter.ShoesPresenter
import com.example.inventoryapp.util.MyUtils
import com.example.inventoryapp.util.showError
import java.io.File

class AddFragment : Fragment(), Contract.ShoesView {

    private var _binding: FragmentAddBinding? = null
    private val binding get() = _binding!!

    private lateinit var presenter: ShoesPresenter
    private var imgUri: Uri? = null

    private val takeImageResult =
        registerForActivityResult(ActivityResultContracts.TakePicture()) { isSuccess ->
            if (isSuccess) {
                imgUri?.let { uri ->
                    binding.imgAdd.setImageURI(uri)
                }
            }
        }

    private val selectImageResult =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            uri?.let {
                binding.imgAdd.setImageURI(uri)
                imgUri = uri
            }
        }

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
                        image = imgUri.toString()
                    )
                    presenter.addShoes(shoes)
                    MyUtils.toast(requireContext(), getString(R.string.item_added))
                    findNavController().navigateUp()
                }

            }
        }
    }

    // Chooses Camera or Gallery in AlertDialog and launches appropriate one
    private fun getImage() {
        val dialog = AlertDialog.Builder(requireContext()).apply {
            setTitle("Выберите источник картины")
            setMessage("Вы хотите сделать новое фото или выбрать из галереи?")
            setPositiveButton("Камера") { dialog, which ->
                takeImage()
            }
            setNegativeButton("Галерея") { dialog, which ->
                selectImageResult.launch(IMG_MIME_TYPE)
            }
            create()
        }

        binding.imgAdd.setOnClickListener {
            dialog.show()
        }
    }

    private fun takeImage() {
        lifecycleScope.launchWhenStarted {
            getImgUri().let { uri ->
                imgUri = uri
                takeImageResult.launch(uri)
            }
        }
    }

    private fun getImgUri(): Uri {
        val tmpFile =
            File.createTempFile(
                "tmp_image_file",
                ".png",
                requireActivity().cacheDir
            ).apply {
                createNewFile()
                deleteOnExit()
            }

        return FileProvider.getUriForFile(
            requireContext().applicationContext,
            "${BuildConfig.APPLICATION_ID}.provider",
            tmpFile
            )
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