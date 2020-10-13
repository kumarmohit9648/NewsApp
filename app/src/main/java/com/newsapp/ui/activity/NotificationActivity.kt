package com.newsapp.ui.activity

import android.os.Bundle
import androidx.activity.viewModels
import com.newsapp.constants.AppConstant
import com.newsapp.databinding.ActivityNotificationBinding
import com.newsapp.model.AuthToken
import com.newsapp.ui.BaseActivity
import com.newsapp.ui.adapter.NewsFeedAdapter
import com.newsapp.ui.adapter.NotificationAdapter
import com.newsapp.ui.vm.NotificationViewModel
import com.pixplicity.easyprefs.library.Prefs
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.toolbar.*

@AndroidEntryPoint
class NotificationActivity : BaseActivity() {

    private lateinit var binding: ActivityNotificationBinding
    private val viewModel: NotificationViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotificationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        titleName.text = "नोटिफिकेशन"

        ivBack.setOnClickListener {
            finish()
        }

        viewModel.getNotificationListResponse.observe(this, {
            if (it.status) {
                if (it.data != null){
                    binding.recyclerNotification.adapter = NotificationAdapter(this@NotificationActivity, it.data)
                }
            }
        })
        viewModel.getNotificationList(AuthToken(Prefs.getString(AppConstant.AUTH_TOKEN, "")))

    }
}