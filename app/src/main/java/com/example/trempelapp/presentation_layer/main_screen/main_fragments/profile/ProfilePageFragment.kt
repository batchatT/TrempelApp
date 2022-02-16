package com.example.trempelapp.presentation_layer.main_screen.main_fragments.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.trempelapp.BaseFragment
import com.example.trempelapp.TrempelApplication
import com.example.trempelapp.databinding.FragmentProfilePageBinding
import com.example.trempelapp.presentation_layer.logIn_screen.TrempelLogInActivity

class ProfilePageFragment : BaseFragment() {

    companion object {
        fun newInstance(): ProfilePageFragment {

            val args = Bundle()

            val profileLoginFragment = ProfilePageFragment()
            profileLoginFragment.arguments = args
            return profileLoginFragment
        }
    }

    val viewModel by lazy {
        ViewModelProvider(this, viewModelProviderFactory)[ProfilePageViewModel::class.java]
    }

    private val binding by lazy {
        FragmentProfilePageBinding.inflate(layoutInflater)
    }

    override fun injectDagger() {
        (requireActivity().application as TrempelApplication).trempelApp.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        setUpBindings()
        setUpObservers()
        return binding.root
    }

    private fun setUpBindings() {
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
    }

    private fun setUpObservers() {
        viewModel.onLogOutButtonClicked.observe(viewLifecycleOwner) {
            val intent = Intent(context, TrempelLogInActivity::class.java)
            startActivity(intent)
            activity?.finish()
        }
    }
}
