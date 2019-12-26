package tsisyk.app.kanbanboard.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.card.view.*
import tsisyk.app.kanbanboard.R
import tsisyk.app.kanbanboard.data.Task


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

        holder.view.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeToNew()
       //     action.arguments = tasks[position]
            Navigation.findNavController(it).navigate(action)
        }
    }



    class TaskViewHolder(val view: View) : RecyclerView.ViewHolder(view)

}
