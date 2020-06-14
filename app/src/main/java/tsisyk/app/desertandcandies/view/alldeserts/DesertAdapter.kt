package tsisyk.app.desertandcandies.view.alldeserts

import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.list_item_desert.view.*
import tsisyk.app.desertandcandies.R
import tsisyk.app.desertandcandies.app.inflate
import tsisyk.app.desertandcandies.model.Desert
import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Color
import androidx.core.content.ContextCompat
import androidx.palette.graphics.Palette
import java.util.*


class DesertAdapter(private val deserts: MutableList<Desert>
) : RecyclerView.Adapter<DesertAdapter.ViewHolder>() {

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


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private lateinit var desert: Desert
        val context = itemView.context

        fun bind(desert: Desert) {
            this.desert = desert
            itemView.avatarListItem.setImageDrawable(itemView.context.getDrawable(desert.drawable))
            itemView.name.text = desert.name
            itemView.hitPoints.text = desert.pointsRanking.toString()
            setBackgroundColors(context, desert.drawable)
        }

        private fun setBackgroundColors(context: Context, imageResource: Int) {
            val image = BitmapFactory.decodeResource(context.resources, imageResource)
            Palette.from(image).generate { palette ->
                val backgroundColor = palette!!.getDominantColor(ContextCompat.getColor(context, R.color.colorPrimaryDark))
                itemView.desert_card.setBackgroundColor(backgroundColor)
                val textColor = if (isColorDark(backgroundColor)) Color.WHITE else Color.BLACK
                itemView.name.setTextColor(textColor)
                itemView.hitPoints.setTextColor(textColor)
            }
        }

        private fun isColorDark(color: Int): Boolean {
            val darkness = 1 - (0.299 * Color.red(color) + 0.587 * Color.green(color) + 0.114 * Color.blue(color)) / 255
            return darkness >= 0.5
        }
    }
}