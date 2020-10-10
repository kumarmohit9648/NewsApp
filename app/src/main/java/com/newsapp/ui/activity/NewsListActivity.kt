package com.newsapp.ui.activity

import android.os.Bundle
import com.newsapp.R
import com.newsapp.constants.AppConstant
import com.newsapp.ui.BaseActivity
import kotlinx.android.synthetic.main.toolbar.*

class NewsListActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_list)

        val intentTitle = intent.getStringExtra(AppConstant.TITLE_KEY)
        titleName.text = if (intentTitle.isNullOrEmpty()) "न्यूज़ डिटेल्स" else intentTitle

        ivBack.setOnClickListener {
            supportFinishAfterTransition()
        }

        // setData()
    }

    /*private fun setData() {
        val list = ArrayList<NewsType>()

        list.add(
            NewsType(
                R.drawable.news_feed,
                "हाथरस कांडः परिजनों की दुखभरी दास्तान, डॉक्टर के बयान और पुलिस की कहानी!\n"
            )
        )
        list.add(
            NewsType(
                R.drawable.n1,
                "बाबरी मस्जिद केस पर आए फैसले को लेकर पाकिस्तान ने उगला जहर"
            )
        )
        list.add(
            NewsType(
                R.drawable.n2,
                "श्रीकृष्ण जन्मभूमि केस: मथुरा की कोर्ट ने ईदगाह हटाने की याचिका को किया खारिज"
            )
        )
        list.add(
            NewsType(
                R.drawable.n3,
                "हाथरस गैंगरेप: आरोपी की मां ने की पारिवारिक झगड़े की बात, कहा- बेटे को फंसाया जा रहा"
            )
        )
        list.add(
            NewsType(
                R.drawable.n4,
                "NBA की एडवर्टाइजर्स से अपील- नफरत फैलाने वाले चैनलों से दूरी बनाएं"
            )
        )
        list.add(
            NewsType(
                R.drawable.n5,
                "बाबरी मस्जिद पर फैसले को ओवैसी ने किया खारिज, कहा-भारत के न्यायिक इतिहास का काला दिन"
            )
        )
        list.add(
            NewsType(
                R.drawable.n6,
                "Instagram में जुड़े 10 नए फ़ीचर्स, मैसेंजर से इंस्टा यूज़र्स को कर सकेंगे रिप्लाई\n"
            )
        )
        list.add(
            NewsType(
                R.drawable.n7,
                "भारत ने किया ब्रह्मोस सुपरसोनिक क्रूज मिसाइल का परीक्षण, 400 किमी. तक दुश्मन होगा ढेर"
            )
        )

        recyclerNewsFeed.adapter = NewsFeedAdapter(this@NewsListActivity, list)
    }*/

}