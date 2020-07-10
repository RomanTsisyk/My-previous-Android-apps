package tsisyk.app.rssfeed.adapter

import android.content.Intent
import android.content.Context
import android.view.ViewGroup
import android.view.LayoutInflater
import androidx.recyclerview.widget.RecyclerView
import android.net.Uri
import android.view.View
import android.widget.TextView
import tsisyk.app.rssfeed.R
import tsisyk.app.rssfeed.`interface`.ItemClickListener
import tsisyk.app.rssfeed.medium_model.Medium


class FeedAdapter(private val rssObject: Medium, private val mContext: Context) :
    RecyclerView.Adapter<FeedViewHolder>() {

    override fun getItemCount(): Int {
        return rssObject.items.size
    }

    private val inflater: LayoutInflater

    init {
        inflater = LayoutInflater.from(mContext)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedViewHolder {
        val itemView = inflater.inflate(R.layout.item, parent, false)
        return FeedViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: FeedViewHolder, position: Int) {

        holder.txtTitle.text = rssObject.items[position].title
        holder.txtPubDate.text = rssObject.items[position].pubDate
        holder.txtContent.text = rssObject.items[position].content

        holder.setItemClickListener(object : ItemClickListener {
            override fun onClick(view: View, position: Int, isLongClick: Boolean) {
                if (!isLongClick) {
                    val browserIntent = Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse(rssObject.items[position].link)
                    )
                    mContext.startActivity(browserIntent)
                }
            }
        })
    }
}