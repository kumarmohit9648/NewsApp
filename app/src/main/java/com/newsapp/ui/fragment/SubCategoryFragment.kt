package com.newsapp.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.newsapp.constants.AppConstant
import com.newsapp.databinding.FragmentSubCategoryBinding
import com.newsapp.model.submenu.SubMenuRequest
import com.newsapp.ui.adapter.SubCategoryAdapter
import com.newsapp.ui.vm.DashboardViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SubCategoryFragment : Fragment() {

    companion object {
        private const val TAG = "StateListFragment"

        @JvmStatic
        fun newInstance() = SubCategoryFragment()
    }

    private var _id = ""
    private var _binding: FragmentSubCategoryBinding? = null
    private val viewModel: DashboardViewModel by viewModels()

    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            _id = it.getString(AppConstant.CATEGORY_ID)!!
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSubCategoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setData()
    }

    private fun setData() {
        viewModel.getSubcategoryListResponse.observe(viewLifecycleOwner, {
            try {
                if (it.status) {
                    if (it.data!!.isNotEmpty()) {
                        binding.recyclerState.adapter = SubCategoryAdapter(requireContext(), it.data)
                    }
                }
            } catch (e: Exception) {
            }
        })
        viewModel.getSubcategoryList(SubMenuRequest(_id))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}