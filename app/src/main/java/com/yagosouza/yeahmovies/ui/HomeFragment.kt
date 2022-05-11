package com.yagosouza.yeahmovies.ui

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.yagosouza.yeahmovies.MainActivity
import com.yagosouza.yeahmovies.R
import com.yagosouza.yeahmovies.databinding.HomeFragmentBinding
import com.yagosouza.yeahmovies.viewmodel.HomeViewModel
import javax.inject.Inject

class HomeFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel by viewModels<HomeViewModel> {viewModelFactory}

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity() as MainActivity).mainComponent.inject(this)
    }

    private lateinit var binding: HomeFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.home_fragment, container, false
        )
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        attachObserver()

        return binding.root
    }

    private fun attachObserver() {
        listsOfMoviesObserver()
    }

    private fun listsOfMoviesObserver() {
        viewModel.listsOfMovies?.observe(viewLifecycleOwner, Observer { lists ->
          lists?.let {
              binding.rvListsOfMovies.layoutManager =
                  LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
              binding.rvListsOfMovies.adapter =
                  ListsOfMoviesAdapter(requireContext(), lists)
          }

        })
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

}