package com.example.inventoryapp.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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
import com.example.inventoryapp.model.Shoes
import com.example.inventoryapp.presenter.Contract
import com.example.inventoryapp.presenter.ShoesPresenter
import kotlinx.coroutines.*

private const val TAG = "MainFragment"
class MainFragment : Fragment(), Contract.ShoesListView {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private val mAdapter by lazy { RvAdapter() }
    private lateinit var presenter: ShoesPresenter

    private var searchJob: Job? = null
    private val DELAY_TIME = 500L

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initPresenter()
        setupRv()
        setOnFloatingButtonListener()
        getShoes()
        archive()
        search()

    }

    private fun search() {
        binding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {
                text?.toString()?.let { text ->
                    if (searchJob != null) {
                        presenter.search(text)
                    }
                    searchJob?.cancel()
                    searchJob = CoroutineScope(Dispatchers.Main).launch {
                        delay(DELAY_TIME)
                        presenter.search(text)
                    }
                }
            }

            override fun afterTextChanged(p0: Editable?) {}
        })
    }

    private fun initPresenter() {
        presenter = ShoesPresenter(requireContext())
        presenter.attachView(this)
    }

    private fun archive() {
        mAdapter.onClickShowMore = { shoes ->
            shoes?.let {
                val action =
                    MainFragmentDirections.actionMainFragmentToBottomSheetArchiveFragment(shoes)
                findNavController().navigate(action)
            }
        }
    }

    private fun getShoes() {
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
        mAdapter.onItemClick = { shoes ->
            shoes?.let {
                val action = MainFragmentDirections.actionMainFragmentToEditFragment(shoes)
                findNavController().navigate(action)
            }
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
        Log.d(TAG, "onDestroy")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop")
    }

    override fun onResume() {
        super.onResume()

        // needed to get latest search results when Fragment is recreated or resumed because even if
        // EditText text is persisted, TextWatcher gets destroyed which leads to loss of data until
        // text is changed
        val searchResults = presenter.getSearchResults()
        if (searchResults.isNotEmpty()) {
            mAdapter.updateData(searchResults)
        }
    }

}