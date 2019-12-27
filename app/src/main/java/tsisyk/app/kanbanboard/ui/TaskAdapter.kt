package tsisyk.app.kanbanboard.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.card.view.*
import tsisyk.app.kanbanboard.R
import tsisyk.app.kanbanboard.data.Task
import tsisyk.app.kanbanboard.utiliies.State.*


class TaskAdapter(private val tasks: List<Task>) :
    RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {

        return TaskViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.card, parent, false)
        )
    }

    override fun getItemCount() = tasks.size

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.view.cardTastTitle.text = tasks[position].title
        holder.view.cardTastDescription.text = tasks[position].description
        when (tasks[position].state) {
            DONE.toString() -> holder.view.setBackgroundResource(R.drawable.card1)
            IN_PROGRESS.toString() -> holder.view.setBackgroundResource(R.drawable.card2)
            PEER_REVIREW.toString() -> holder.view.setBackgroundResource(R.drawable.card3)
            IN_TEST.toString() -> holder.view.setBackgroundResource(R.drawable.card4)
            BLOKED.toString() -> holder.view.setBackgroundResource(R.drawable.card5)
        }


        holder.view.setOnClickListener {
            val cationNumber = HomeFragmentDirections.actionHomeToNew(tsakNote = tasks[position])
            Navigation.findNavController(it).navigate(cationNumber)
        }
    }

    class TaskViewHolder(val view: View) : RecyclerView.ViewHolder(view)

}
