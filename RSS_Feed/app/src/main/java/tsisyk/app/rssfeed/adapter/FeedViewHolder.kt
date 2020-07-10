package tsisyk.app.rssfeed.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import tsisyk.app.rssfeed.R
import tsisyk.app.rssfeed.`interface`.ItemClickListener

class FeedViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
    View.OnClickListener, View.OnLongClickListener {

    var txtTitle: TextView
    var txtPubDate: TextView
    var txtContent: TextView
    private var itemClickListener: ItemClickListener? = null

    init {

        txtTitle = itemView.findViewById(R.id.textTitle) as TextView
        txtPubDate = itemView.findViewById(R.id.textDate) as TextView
        txtContent = itemView.findViewById(R.id.textContent) as TextView

        //Set Event
        itemView.setOnClickListener(this)
        itemView.setOnLongClickListener(this)
    }

    fun setItemClickListener(itemClickListener: ItemClickListener) {
        this.itemClickListener = itemClickListener
    }

    override fun onClick(v: View) {

        itemClickListener!!.onClick(v, adapterPosition, false)

    }

    override fun onLongClick(v: View): Boolean {
        itemClickListener!!.onClick(v, adapterPosition, true)
        return true
    }
}


