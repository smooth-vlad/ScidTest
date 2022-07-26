package com.android.scidtest.ui.details.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.android.scidtest.databinding.FragmentDetailsBinding
import com.android.scidtest.di.App
import com.android.scidtest.ui.details.viewmodel.DetailsViewModel
import com.android.scidtest.ui.list.viewmodel.ListViewModel
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*

class DetailsFragment : Fragment() {

    private lateinit var viewModel: DetailsViewModel

    private var _binding: FragmentDetailsBinding? = null

    private val binding get() = _binding!!

    private val args: DetailsFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val bookId = args.bookId

        val appComponent = App.getMainComponent(requireContext())
        val detailsComponent = appComponent.getDetailsComponentFactory().create(bookId)
        val viewModelFactory = detailsComponent.getDetailsViewModelFactory()
        viewModel = ViewModelProvider(
            this,
            viewModelFactory
        )[DetailsViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.item.observe(viewLifecycleOwner) {
            binding.titleTextView.text = it.label
            binding.descriptionTextView.text = it.description
            try {
                val date = LocalDate.parse(it.date)
                binding.dateTextView.text = date.year.toString()
            } catch (exception: Exception) {
                binding.dateTextView.text = "-"
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}