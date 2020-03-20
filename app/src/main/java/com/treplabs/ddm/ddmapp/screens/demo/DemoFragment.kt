package com.treplabs.ddm.ddmapp.screens.demo

import android.graphics.Paint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.treplabs.ddm.R
import com.treplabs.ddm.base.BaseFragment
import com.treplabs.ddm.databinding.FragmentDemoBinding
import com.treplabs.ddm.ddmapp.datasources.repositories.ConditionsRepository
import com.treplabs.ddm.ddmapp.screens.condition.ChooseConditionViewModel
import com.treplabs.ddm.extensions.underline
import kotlinx.android.synthetic.main.fragment_demo.*
import javax.inject.Inject

class DemoFragment : BaseFragment() {

    private val dataModels = listOf(
        PagerDataModel(
            R.string.demo_tab_title_one,
            R.string.demo_tab_description_one,
            R.drawable.user_icon_png
        ),
        PagerDataModel(
            R.string.demo_tab_title_two,
            R.string.demo_tab_description_two,
            R.drawable.user_icon_png
        ),
        PagerDataModel(
            R.string.demo_tab_title_three,
            R.string.demo_tab_description_three,
            R.drawable.user_icon_png
        )
    )

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: DemoViewModel

    private lateinit var binding: FragmentDemoBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDemoBinding.inflate(inflater)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpToolbar()
        binding.viewPager.adapter = DemoPagerAdapter(this)
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { _, _ -> }.attach()

        binding.signInTextView.underline()

        daggerAppComponent.inject(this)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(DemoViewModel::class.java)
        viewModel.markDemoShown()

        binding.signUpButton.setOnClickListener {
            findNavController().navigate(DemoFragmentDirections.actionDemoFragmentToSignUpFragment())
        }

        binding.signInTextView.setOnClickListener {
            findNavController().navigate(DemoFragmentDirections.actionDemoFragmentToLoginFragment())
        }
    }

    override fun onBackPressed(): Boolean {
        return if (viewPager.currentItem == 0) {
            super.onBackPressed()
        } else {
            binding.viewPager.currentItem = viewPager.currentItem - 1
            true
        }
    }

    private fun setUpToolbar() = mainActivity.run {
        setUpToolBar("", true)
        invalidateToolbarElevation(0)
    }

    private inner class DemoPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

        override fun getItemCount() = dataModels.size

        override fun createFragment(position: Int): Fragment =
            DemoPagerFragment.newInstance(
                dataModels[position]
            )
    }
}

