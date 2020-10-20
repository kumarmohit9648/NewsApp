package com.knovatik.navadesh.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.knovatik.navadesh.R
import com.knovatik.navadesh.constants.AppConstant
import com.knovatik.navadesh.ui.activity.JokesActivity
import com.knovatik.navadesh.util.loadUrl
import kotlinx.android.synthetic.main.fragment_time_pass.*

class TimePassFragment : Fragment() {

    companion object {
        @JvmStatic
        fun newInstance() = TimePassFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_time_pass, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        cvJokes.setOnClickListener {
            requireContext().startActivity(
                Intent(requireContext(), JokesActivity::class.java)
                    .putExtra(AppConstant.SECTION_NAME, "चुटकुले")
                    .putExtra(AppConstant.SECTION_ID, "1")
            )
        }
        cvMusic.setOnClickListener {
            requireContext().startActivity(
                Intent(requireContext(), JokesActivity::class.java)
                    .putExtra(AppConstant.SECTION_NAME, "संगीत")
                    .putExtra(AppConstant.SECTION_ID, "2")
            )
        }
        cvVideo.setOnClickListener {
            requireContext().startActivity(
                Intent(requireContext(), JokesActivity::class.java)
                    .putExtra(AppConstant.SECTION_NAME, "वीडियो")
                    .putExtra(AppConstant.SECTION_ID, "3")
            )
        }
        cvPopular.setOnClickListener {
            requireContext().startActivity(
                Intent(requireContext(), JokesActivity::class.java)
                    .putExtra(AppConstant.SECTION_NAME, "फिल्म")
                    .putExtra(AppConstant.SECTION_ID, "4")
            )
        }
        cvBhakti.setOnClickListener {
            requireContext().startActivity(
                Intent(requireContext(), JokesActivity::class.java)
                    .putExtra(AppConstant.SECTION_NAME, "भक्ति")
                    .putExtra(AppConstant.SECTION_ID, "5")
            )
        }
        cvFilmy.setOnClickListener {
            requireContext().startActivity(
                Intent(requireContext(), JokesActivity::class.java)
                    .putExtra(AppConstant.SECTION_NAME, "वायरल")
                    .putExtra(AppConstant.SECTION_ID, "7")
            )
        }
        cvAstrology.setOnClickListener {
            requireContext().startActivity(
                Intent(requireContext(), JokesActivity::class.java)
                    .putExtra(AppConstant.SECTION_NAME, "ज्योतिष")
                    .putExtra(AppConstant.SECTION_ID, "8")
            )
        }
        cvRadio.setOnClickListener {
            requireContext().loadUrl("http://radio.garden/")
        }
        cvLifestyle.setOnClickListener {
            requireContext().startActivity(
                Intent(requireContext(), JokesActivity::class.java)
                    .putExtra(AppConstant.SECTION_NAME, "लाइफस्टाइल")
                    .putExtra(AppConstant.SECTION_ID, "2")
            )
        }
    }

}