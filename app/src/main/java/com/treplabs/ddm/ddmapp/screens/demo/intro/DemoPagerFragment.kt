
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.treplabs.ddm.base.BaseFragment
import com.treplabs.ddm.databinding.FragmentDemoPagerBinding

class DemoPagerFragment : BaseFragment() {

    companion object {
        private const val PAGER_MODEL_KEY = "pager_model"
        fun newInstance(pagerDataModel: PagerDataModel): DemoPagerFragment = DemoPagerFragment().also {
            it.arguments = Bundle().apply { putParcelable(PAGER_MODEL_KEY, pagerDataModel) }
        }
    }

    private lateinit var binding: FragmentDemoPagerBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDemoPagerBinding.inflate(inflater)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val pagerDataModel = arguments!!.get(PAGER_MODEL_KEY) as PagerDataModel
        binding.illustrationImage.setImageResource(pagerDataModel.illustrationGraphic)
        binding.titleTextView.text = getString(pagerDataModel.title)
        binding.descriptionTextView.text = getString(pagerDataModel.description)
    }
}
