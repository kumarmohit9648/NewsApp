package com.knovatik.navadesh.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.knovatik.navadesh.OnSingleItemClickListener
import com.knovatik.navadesh.constants.AppConstant
import com.knovatik.navadesh.databinding.ActivityNotificationBinding
import com.knovatik.navadesh.model.AuthToken
import com.knovatik.navadesh.model.notification.NotificationList
import com.knovatik.navadesh.model.notification.NotificationStatus
import com.knovatik.navadesh.ui.BaseActivity
import com.knovatik.navadesh.ui.adapter.NotificationAdapter
import com.knovatik.navadesh.ui.vm.NotificationViewModel
import com.knovatik.navadesh.util.toast
import com.pixplicity.easyprefs.library.Prefs
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NotificationActivity : BaseActivity(), OnSingleItemClickListener {

    private lateinit var binding: ActivityNotificationBinding
    private val viewModel: NotificationViewModel by viewModels()

    private var list = mutableListOf<NotificationList>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotificationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbar.titleName.text = "नोटिफिकेशन"

        binding.toolbar.ivBack.setOnClickListener {
            finish()
        }

        viewModel.updateNotificationStatusResponse.observe(this, {
            if (!it.status) {
                toast(it.message)
            }
        })

        viewModel.getNotificationListResponse.observe(this, {
            binding.progressBar.visibility = View.GONE
            if (it.status) {
                if (it.data != null) {
                    list = it.data as MutableList<NotificationList>
                    binding.recyclerNotification.adapter =
                        NotificationAdapter(this, it.data)
                }
            }
        })
        viewModel.getNotificationList(AuthToken(Prefs.getString(AppConstant.AUTH_TOKEN, "")))
        binding.progressBar.visibility = View.VISIBLE

    }

    override fun onClickPosition(position: Int) {
        viewModel.updateNotificationStatus(
            NotificationStatus(
                Prefs.getString(AppConstant.AUTH_TOKEN, ""),
                list[position].id
            )
        )
        startActivity(
            Intent(this@NotificationActivity, NewsDetailActivity::class.java)
                .putExtra(AppConstant.VIDEO_ID, list[position].id)
        )
    }
}