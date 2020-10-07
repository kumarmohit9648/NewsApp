package com.newsapp.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.newsapp.R
import com.newsapp.model.State
import com.newsapp.ui.adapter.StateAdapter
import kotlinx.android.synthetic.main.fragment_state_list.*

class StateListFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_state_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setData()
    }

    private fun setData() {
        val list = ArrayList<State>()

        list.add(State(R.drawable.state, "1", "आंध्र प्रदेश"))
        list.add(State(R.drawable.arunachal_pradesh, "1", "अरुणाचल प्रदेश"))
        list.add(State(R.drawable.assam, "1", "असम"))
        list.add(State(R.drawable.bihar, "1", "बिहार"))
        list.add(State(R.drawable.chhattisgarh, "1", "छत्तीसगढ़"))
        list.add(State(R.drawable.goa, "1", "गोवा"))
        list.add(State(R.drawable.state, "1", "गुजरात"))
        list.add(State(R.drawable.haryana, "1", "हरियाणा"))
        list.add(State(R.drawable.himachal_pradesh, "1", "हिमाचल प्रदेश"))
        list.add(State(R.drawable.chhattisgarh, "1", "झारखंड"))
        list.add(State(R.drawable.state, "1", "कर्नाटक"))
        list.add(State(R.drawable.bihar, "1", "केरल"))
        list.add(State(R.drawable.goa, "1", "मध्य प्रदेश"))
        list.add(State(R.drawable.meghalaya, "1", "महाराष्ट्र"))
        list.add(State(R.drawable.nagaland, "1", "मणिपुर"))
        list.add(State(R.drawable.meghalaya, "1", "मेघालय"))
        list.add(State(R.drawable.state, "1", "मिजोरम"))
        list.add(State(R.drawable.himachal_pradesh, "1", "नगालैंड"))
        list.add(State(R.drawable.arunachal_pradesh, "1", "ओडिशा"))
        list.add(State(R.drawable.chhattisgarh, "1", "पंजाब"))
        list.add(State(R.drawable.rajasthan, "1", "राजस्थान"))
        list.add(State(R.drawable.goa, "1", "सिक्किम"))
        list.add(State(R.drawable.bihar, "1", "तमिलनाडु"))
        list.add(State(R.drawable.assam, "1", "तेलंगाना"))
        list.add(State(R.drawable.state, "1", "त्रिपुरा"))
        list.add(State(R.drawable.meghalaya, "1", "उत्तर प्रदेश"))
        list.add(State(R.drawable.nagaland, "1", "उत्तराखंड"))
        list.add(State(R.drawable.goa, "1", "पश्चिम बंगाल"))

        recyclerState.adapter = StateAdapter(requireContext(), list)
    }

    companion object {
        @JvmStatic
        fun newInstance() = StateListFragment()
    }
}