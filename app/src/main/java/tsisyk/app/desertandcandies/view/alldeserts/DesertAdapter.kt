package tsisyk.app.desertandcandies.view.alldeserts

import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import tsisyk.app.desertandcandies.R
import tsisyk.app.desertandcandies.app.inflate
import tsisyk.app.desertandcandies.model.Desert

class DesertAdapter(private val deserts: MutableList<Desert>)
    : androidx.recyclerview.widget.RecyclerView.Adapter<DesertAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent.inflate(R.layout.list_item_desert))
    }

    override fun onBindViewHolder(holder: DesertAdapter.ViewHolder, position: Int) {
        holder.bind(deserts[position])
    }

    override fun getItemCount() = deserts.size

    fun updateDeserts(deserts: List<Desert>) {
        this.deserts.clear()
        this.deserts.addAll(deserts)
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {

        private lateinit var desert: Desert

        fun bind(desert: Desert) {
            this.desert = desert
            // TODO: populate views
        }
    }
}