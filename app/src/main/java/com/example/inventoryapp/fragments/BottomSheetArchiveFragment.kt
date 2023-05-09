package com.example.inventoryapp.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.inventoryapp.databinding.FragmentBottomSheetArchiveBinding
import com.example.inventoryapp.presenter.ShoesPresenter
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.util.Calendar


class BottomSheetArchiveFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentBottomSheetArchiveBinding? = null
    private val binding get() = _binding!!

    private val args by navArgs<BottomSheetArchiveFragmentArgs>()
    private lateinit var presenter: ShoesPresenter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBottomSheetArchiveBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter = ShoesPresenter(requireContext())
        val shoes = args.shoes
        binding.btnArchive.setOnClickListener {
            AlertDialog.Builder(requireContext()).apply {
                setTitle("Архивировать ${shoes.name} из каталога?")
                setPositiveButton("Да") { _, _ ->
                    shoes.archived = 1
                    presenter.archive(shoes)
                }
                setNegativeButton("Нет") { _, _ ->

                }
                this@BottomSheetArchiveFragment.findNavController().navigateUp()
                show()
            }
        }
    }

}