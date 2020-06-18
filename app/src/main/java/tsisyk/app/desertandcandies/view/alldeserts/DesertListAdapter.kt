package tsisyk.app.desertandcandies.view.alldeserts

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.list_item_desert_list.view.*
import kotlinx.android.synthetic.main.list_item_desert_many_calories.view.*
import kotlinx.android.synthetic.main.list_item_desert_many_calories.view.avatarListItem
import kotlinx.android.synthetic.main.list_item_desert_many_calories.view.hitPoints
import kotlinx.android.synthetic.main.list_item_desert_many_calories.view.name
import tsisyk.app.desertandcandies.R
import tsisyk.app.desertandcandies.R.anim.*
import tsisyk.app.desertandcandies.app.inflate
import tsisyk.app.desertandcandies.model.Desert
import java.util.*


class DesertListAdapter(private val deserts: MutableList<Desert>,
                        private val itemDragListener: ItemDragListener) :
        RecyclerView.Adapter<DesertListAdapter.ViewHolder>(), ItemTouchHelperListener {

    var scrollDirection = ScrollDirection.DOWN

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent.inflate(R.layout.list_item_desert_list))
    }


    override fun onBindViewHolder(holder: DesertListAdapter.ViewHolder, position: Int) {
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
        this.deserts.remove(deserts[position])
        deserts[position]
        notifyItemRemoved(position)
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) , ItemSelectedListener{

        private lateinit var desert: Desert
        val context = itemView.context

        @SuppressLint("ClickableViewAccessibility")
        fun bind(desert: Desert) {
            this.desert = desert
            itemView.avatarListItem.setImageDrawable(itemView.context.getDrawable(desert.drawable))
            itemView.name.text = desert.name
            itemView.hitPoints.text = desert.pointsRanking.toString()
            animateView(itemView)
            itemView.handle.setOnTouchListener { _, motionEvent ->
                if (motionEvent.action == MotionEvent.ACTION_DOWN) {
                    itemDragListener.onItemDrag(this)
                }
                false
            }
        }


        fun animateView(viewAnimated: View) {
            if (viewAnimated.animation == null) {
                val animId = if (scrollDirection == ScrollDirection.DOWN) slide_from_bottom else slide_from_top
                val animation = AnimationUtils.loadAnimation(viewAnimated.context, animId)
                viewAnimated.animation = animation
            }
        }

        override fun onItemSelected() {
            itemView.desert_list_card.setCardBackgroundColor(ContextCompat.getColor(itemView.context, R.color.selectedItem))
        }

        override fun onItemCleared() {
            itemView.desert_list_card.setCardBackgroundColor(Color.WHITE)
        }
    }


}



