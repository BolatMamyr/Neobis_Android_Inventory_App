package com.example.inventoryapp.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.inventoryapp.R
import com.example.inventoryapp.adapters.RvAdapter
import com.example.inventoryapp.databinding.FragmentAddBinding
import com.example.inventoryapp.databinding.FragmentArchiveBinding
import com.example.inventoryapp.model.Shoes
import com.example.inventoryapp.presenter.Contract
import com.example.inventoryapp.presenter.ShoesPresenter
import com.example.inventoryapp.util.MyUtils
import kotlinx.coroutines.*

class ArchiveFragment : Fragment(), Contract.ArchivedShoesListView {

    private var _binding: FragmentArchiveBinding? = null
    private val binding get() = _binding!!

    private lateinit var presenter: ShoesPresenter
    private val mAdapter by lazy { RvAdapter() }

    private var searchJob: Job? = null
    private val DELAY_TIME = 500L
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentArchiveBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initPresenter()
        setupRv()
        getShoes()
        setMoreActions()
        setFloatingButtonListener()
        search()
    }

    private fun search() {
        binding.etArchiveSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {
                text?.toString()?.let { text ->
                    if (searchJob != null) {
                        presenter.searchArchived(text)
                    }
                    searchJob?.cancel()
                    searchJob = CoroutineScope(Dispatchers.Main).launch {
                        delay(DELAY_TIME)
                        presenter.searchArchived(text)
                    }
                }
            }

            override fun afterTextChanged(p0: Editable?) {}
        })
    }

    private fun setFloatingButtonListener() {
        binding.btnAdd.setOnClickListener {
            findNavController().navigate(R.id.action_archiveFragment_to_addFragment)
        }
    }

    private fun setMoreActions() {
        mAdapter.onClickShowMore = { shoes ->
            shoes?.let {
                val action = ArchiveFragmentDirections
                    .actionArchiveFragmentToBottomSheetUnarchiveFragment(shoes)
                findNavController().navigate(action)
            }
        }
    }

    private fun getShoes() {
        presenter.getAllShoes()
    }

    private fun initPresenter() {
        presenter = ShoesPresenter(requireContext())
        presenter.attachView(this)
    }

    private fun setupRv() {
        binding.rvArchive.apply {
            adapter = mAdapter
            layoutManager = GridLayoutManager(requireContext(), 2, RecyclerView.VERTICAL, false)
            isSaveEnabled = true
        }
        mAdapter.onItemClick = {shoes ->
            shoes?.let {
                val action = ArchiveFragmentDirections.actionArchiveFragmentToEditFragment(shoes)
                findNavController().navigate(action)
            }
        }
    }

    override fun showError(message: String) {
        MyUtils.toast(requireContext(), message)
    }

    override fun showShoes(shoes: List<Shoes>) {
        mAdapter.updateData(shoes)
    }

    override fun onDestroy() {
        super.onDestroy()
        // sometimes throws UninitializedPropertyAccessException when changing bottomNav menu items
        if (::presenter.isInitialized) {
            presenter.detachView()
        }
    }

    override fun onResume() {
        super.onResume()
        // needed to get latest search results when Fragment is recreated or resumed because even if
        // EditText text is persisted, TextWatcher gets destroyed which leads to loss of data until
        // text is changed
        val searchResults = presenter.getArchivedSearchResults()
        if (searchResults.isNotEmpty()) {
            mAdapter.updateData(searchResults)
        }
    }
}