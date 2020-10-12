package com.newsapp.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.newsapp.constants.AppConstant
import com.newsapp.databinding.FragmentTabBinding
import com.newsapp.model.posts.PostsRequest
import com.newsapp.ui.adapter.NewsFeedAdapter
import com.newsapp.ui.vm.DashboardViewModel
import com.pixplicity.easyprefs.library.Prefs
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TabFragment : Fragment() {

    companion object {
        private const val TAG = "TabFragment"

        @JvmStatic
        fun newInstance() = TabFragment()
    }

    private var _id = ""
    private var _binding: FragmentTabBinding? = null
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
        _binding = FragmentTabBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setData()
    }

    private fun setData() {
        viewModel.getPostsResponse.observe(viewLifecycleOwner, {
            try {
                if (it.status) {
                    if (it.data!!.isNotEmpty()) {
                        binding.recyclerNewsFeed.adapter =
                            NewsFeedAdapter(requireContext(), it.data)
                    }
                }
            } catch (e: Exception) {
            }
        })
        viewModel.getPosts(PostsRequest(_id, "", Prefs.getString(AppConstant.AUTH_TOKEN, "")))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}