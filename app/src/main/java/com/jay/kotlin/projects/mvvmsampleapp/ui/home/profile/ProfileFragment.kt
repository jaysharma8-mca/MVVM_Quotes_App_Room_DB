package com.jay.kotlin.projects.mvvmsampleapp.ui.home.profile

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.jay.kotlin.projects.mvvmsampleapp.R
import com.jay.kotlin.projects.mvvmsampleapp.databinding.ProfileFragmentBinding
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein //this import is important
import org.kodein.di.generic.instance

class ProfileFragment : Fragment(), KodeinAware {

    override val kodein by kodein()

    private lateinit var viewModel: ProfileViewModel
    private val factory: ProfileViewModelFactory by instance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding : ProfileFragmentBinding = DataBindingUtil.inflate(
            inflater, R.layout.profile_fragment, container, false
        )

        viewModel = ViewModelProvider(this, factory).get(ProfileViewModel::class.java)
        //return inflater.inflate(R.layout.profile_fragment, container, false)
        binding.viewmodel = viewModel
        binding.lifecycleOwner = this
        return binding.root
    }

}