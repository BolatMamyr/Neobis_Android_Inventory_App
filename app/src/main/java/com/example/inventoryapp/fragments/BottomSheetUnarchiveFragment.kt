package com.example.inventoryapp.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.inventoryapp.databinding.FragmentBottomSheetUnarchiveBinding
import com.example.inventoryapp.model.Shoes
import com.example.inventoryapp.presenter.ShoesPresenter
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSheetUnarchiveFragment : BottomSheetDialogFragment() {
    private var _binding: FragmentBottomSheetUnarchiveBinding? = null
    private val binding get() = _binding!!

    private val args by navArgs<BottomSheetArchiveFragmentArgs>()
    private lateinit var presenter: ShoesPresenter
    private lateinit var shoes: Shoes
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBottomSheetUnarchiveBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter = ShoesPresenter(requireContext())
        shoes = args.shoes
        onRestoreClick()
        onDeleteClick()
    }

    private fun onRestoreClick() {
        binding.btnRestore.setOnClickListener {
            AlertDialog.Builder(requireContext()).apply {
                setTitle("Восcтановить ${shoes.name} из архива?")
                setPositiveButton("Да") { _, _ ->
                    shoes.archived = 0
                    presenter.unarchive(shoes)
                }
                setNegativeButton("Нет") { _, _ -> }
                this@BottomSheetUnarchiveFragment.findNavController().navigateUp()
                show()
            }
        }
    }

    private fun onDeleteClick() {
        binding.btnDelete.setOnClickListener {
            AlertDialog.Builder(requireContext()).apply {
                setTitle("Удалить ${shoes.name} из архива?")
                setPositiveButton("Да") { _, _ ->
                    presenter.delete(shoes)
                }
                setNegativeButton("Нет") { _, _ -> }
                this@BottomSheetUnarchiveFragment.findNavController().navigateUp()
                show()
            }
        }
    }
}