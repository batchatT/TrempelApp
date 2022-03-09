package com.example.trempelapp.presentation_layer.main_screen.main_fragments.home

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.trempelapp.BaseFragment
import com.example.trempelapp.R
import com.example.trempelapp.TrempelApplication
import com.example.trempelapp.databinding.FragmentHomePageBinding
import com.example.trempelapp.utils.EMPTY_STRING
import com.example.trempelapp.utils.HOME_TO_SEARCH_KEY

class HomePageFragment : BaseFragment() {

    companion object {
        fun newInstance(): HomePageFragment {

            val args = Bundle()

            val homeLoginFragment = HomePageFragment()
            homeLoginFragment.arguments = args
            return homeLoginFragment
        }
    }

    override fun configureAppBar() {
        hideAppBar()
    }

    private val binding by lazy {
        FragmentHomePageBinding.inflate(layoutInflater)
    }

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelProviderFactory)[HomePageViewModel::class.java]
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
        setUpBinding()
        setUpNavigation()
        return binding.root
    }

    private fun setUpBinding() {
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
    }

    private fun setUpNavigation() {

        viewModel.moveToCategoriesLiveData.observe(viewLifecycleOwner) {
            findNavController().navigate(R.id.action_homePage_to_categoriesPage)
        }

        binding.search.setOnEditorActionListener(
            TextView.OnEditorActionListener { _, actionId, keyEvent ->
                if (actionId == EditorInfo.IME_ACTION_DONE ||
                    keyEvent?.keyCode == KeyEvent.KEYCODE_ENTER
                ) {
                    if (!binding.search.text.isNullOrEmpty()) {
                        val bundle = Bundle()
                        bundle.putString(HOME_TO_SEARCH_KEY, binding.search.text.toString())
                        findNavController().navigate(
                            R.id.action_homePageFragment_to_searchPageFragment,
                            bundle
                        )
                        binding.search.setText(EMPTY_STRING)
                        return@OnEditorActionListener true
                    }
                }
                false
            }
        )
    }
}
