import android.graphics.Paint
import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.databinding.ObservableField
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.treplabs.ddm.base.BaseFragment
import com.treplabs.ddm.databinding.FragmentDemoBinding
import kotlinx.android.parcel.Parcelize
import kotlinx.android.synthetic.main.fragment_demo.*
import com.treplabs.ddm.R


class DemoFragment : BaseFragment() {

    private val slideActionTextValue = ObservableField<String>()

    private val dataModels = listOf(
        PagerDataModel(R.string.demo_tab_title_one, R.string.demo_tab_description_one, R.drawable.user_icon_png),
        PagerDataModel(R.string.demo_tab_title_two, R.string.demo_tab_description_two, R.drawable.user_icon_png),
        PagerDataModel(R.string.demo_tab_title_three, R.string.demo_tab_description_three, R.drawable.user_icon_png)
    )

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
        binding.signInTextView.paintFlags = binding.signInTextView.paintFlags or Paint.UNDERLINE_TEXT_FLAG
        binding.signUpButton.setOnClickListener {

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

        override fun createFragment(position: Int): Fragment = DemoPagerFragment.newInstance(dataModels[position])
    }
}

@Parcelize
data class PagerDataModel(
    @StringRes val title: Int, @StringRes val description: Int,
    @DrawableRes val illustrationGraphic: Int
) : Parcelable
