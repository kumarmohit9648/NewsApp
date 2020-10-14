package com.newsapp.ui.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.newsapp.R
import kotlinx.android.synthetic.main.recycler_jokes.view.*

class JokesAdapter(
    private var context: Context,
    private var list: List<String>?
) : RecyclerView.Adapter<JokesHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JokesHolder {
        return JokesHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.recycler_jokes,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = 10

    override fun onBindViewHolder(holder: JokesHolder, position: Int) {
        // val data = list!![position]
        // Glide.with(context).load(data.profile_image).into(holder.itemView.circleImageView)
        holder.itemView.tvJokes.text =
            "जिस तरह अच्छी हवा, अच्छा खानपान किसी भी इंसान के सेहतमंद रहने के लिए जरूरी होता है, उसी प्रकार आपकी हंसी भी आपको स्वस्थ रखने में अहम भूमिका निभाती है। अगर आप सुबह-शाम हंसने की आदत डाल लें तो कोई भी बीमारी, चाहे मानसिक हो या शारीरिक आपके पास भी नहीं आएगी। इसीलिए हम आपके लिए कुछ ऐसे मजेदार चुटकुले लेकर आए हैं, जिन्हें पढ़ने के बाद आप हंसते-हंसते लोटपोट हो जाएंगे। तो चलिए शुरू करते हैं हंसने-हंसाने का ये सिलसिला...\n" +
                    "\n" +
                    "------------------------------------------\n" +
                    "\n" +
                    "दो बातें आज तक समझ नहीं आई...\n" +
                    "पहली - जिसने पहली बार घड़ी बनाया,\n" +
                    "उसने समय कैसे मिलाया होगा...!\n" +
                    ".\n" +
                    "और...\n" +
                    ".\n" +
                    "दूसरी - जिसने पहली बार दही जमाया,\n" +
                    "वो जावण (जोड़न) कहां से लाया होगा...?"
        holder.itemView.ivShare.setOnClickListener {
            val shareIntent = Intent()
            shareIntent.action = Intent.ACTION_SEND
            shareIntent.type = "text/plain"
            shareIntent.putExtra(Intent.EXTRA_TEXT, "data")
            context.startActivity(Intent.createChooser(shareIntent, "Share via"))
        }
    }

}

class JokesHolder(itemView: View) : RecyclerView.ViewHolder(itemView)