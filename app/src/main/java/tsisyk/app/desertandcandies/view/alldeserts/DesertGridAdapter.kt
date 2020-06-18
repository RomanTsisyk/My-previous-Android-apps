package tsisyk.app.desertandcandies.view.alldeserts

import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.core.content.ContextCompat
import androidx.palette.graphics.Palette
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.list_item_desert_many_calories.view.*
import tsisyk.app.desertandcandies.R
import tsisyk.app.desertandcandies.R.anim.scale_xy
import tsisyk.app.desertandcandies.R.anim.slide_from_top
import tsisyk.app.desertandcandies.app.inflate
import tsisyk.app.desertandcandies.model.Desert
import java.util.*


class DesertGridAdapter(private val deserts: MutableList<Desert>
) : RecyclerView.Adapter<DesertGridAdapter.ViewHolder>(), ItemTouchHelperListener {

    var scrollDirection = ScrollDirection.DOWN
    var toManyCaloriesSpanSize = 0


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return when (viewType) {
            ViewType.MANY_CALORIES.ordinal  -> ViewHolder(parent.inflate(R.layout.list_item_desert_many_calories))
            ViewType.OTHER.ordinal -> ViewHolder(parent.inflate(R.layout.list_item_desert_grid))
            else -> throw IllegalArgumentException()
        }
    }

    override fun onBindViewHolder(holder: DesertGridAdapter.ViewHolder, position: Int) {
        holder.bind(deserts[position])
    }

    override fun getItemViewType(position: Int): Int {
        val desert = deserts[position]
        return if (desert.attributes.calories == 500) ViewType.MANY_CALORIES.ordinal else ViewType.OTHER.ordinal
    }

    override fun getItemCount() = deserts.size

    fun updateDeserts(deserts: List<Desert>) {
        this.deserts.clear()
        this.deserts.addAll(deserts)
        notifyDataSetChanged()
    }

    override fun onItemMove(recyclerView: RecyclerView, fromPosition: Int, toPosition: Int): Boolean {
        if (fromPosition < toPosition) {
            for (i in fromPosition until toPosition) {
                Collections.swap(deserts, i, i + 1)
            }
        } else {
            for (i in fromPosition downTo toPosition) {
                Collections.swap(deserts, i, i - 1)
            }
        }
        notifyItemMoved(fromPosition, toPosition)
        return true
    }

    override fun onItemDismiss(viewHolder: RecyclerView.ViewHolder, position: Int) {
        deserts.removeAt(position)
        notifyItemRemoved(position)
    }

    // not shows deserts with max pointRanking

    fun spanSizePosition(position: Int): Int {
        return if (deserts[position].pointsRanking == 5250) {
            toManyCaloriesSpanSize
        } else 1
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private lateinit var desert: Desert
        val context = itemView.context

        fun bind(desert: Desert) {
            this.desert = desert
            itemView.avatarListItem.setImageDrawable(itemView.context.getDrawable(desert.drawable))
            itemView.name.text = desert.name
            itemView.hitPoints.text = desert.pointsRanking.toString()
            setBackgroundColors(context, desert.drawable)
            animateView(itemView)
        }

        private fun setBackgroundColors(context: Context, imageResource: Int) {

            val image = BitmapFactory.decodeResource(context.resources, imageResource)
            Palette.from(image).generate { palette ->
                val backgroundColor = palette!!.getDominantColor(ContextCompat.getColor(context, R.color.colorPrimaryDark))
                itemView.desert_card.setBackgroundColor(backgroundColor)
                val textColor = if (isColorDark(backgroundColor)) Color.WHITE else Color.BLACK
                itemView.name.setTextColor(textColor)
                itemView.hitPoints.setTextColor(textColor)

                if (itemView.textCalories != null) itemView.textCalories.setTextColor(textColor)
                if (itemView.imageHandle != null) itemView.imageHandle.setBackgroundColor(textColor)
            }
        }

        private fun isColorDark(color: Int): Boolean {
            val darkness = 1 - (0.299 * Color.red(color) + 0.587 * Color.green(color) + 0.114 * Color.blue(color)) / 255
            return darkness >= 0.5
        }

        fun animateView(viewAnimated: View) {
            if (viewAnimated.animation == null) {
                val animId = if (scrollDirection == ScrollDirection.DOWN) scale_xy else slide_from_top
                val animation = AnimationUtils.loadAnimation(viewAnimated.context, animId)
                viewAnimated.animation = animation
            }
        }
    }


}



