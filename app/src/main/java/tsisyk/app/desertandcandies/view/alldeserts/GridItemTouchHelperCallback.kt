package tsisyk.app.desertandcandies.view.alldeserts

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

class GridItemTouchHelperCallback(private val listener: ItemTouchHelperListener) : ItemTouchHelper.Callback() {

    override fun isLongPressDragEnabled() = true
    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
    }


    override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int {
        return makeMovementFlags(ItemTouchHelper.UP or ItemTouchHelper.DOWN or ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT, 0)
    }

    override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
        return listener.onItemMove(recyclerView, viewHolder.adapterPosition, target.adapterPosition)
    }

}