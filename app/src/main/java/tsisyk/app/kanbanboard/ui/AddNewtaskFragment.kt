package tsisyk.app.kanbanboard.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_add_new_task.*
import kotlinx.coroutines.launch
import tsisyk.app.kanbanboard.R
import tsisyk.app.kanbanboard.data.Task
import tsisyk.app.kanbanboard.data.TaskDatabase

class AddNewtaskFragment : Basefragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_new_task, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        buttonSave.setOnClickListener {
            val descriptionTask = editTextDescription.text.trim().toString()
            val titleTask = editTextTitle.text.trim().toString()


            if (descriptionTask.isEmpty()) {
                editTextDescription.error = "description is missing"
                editTextDescription.requestFocus()
                return@setOnClickListener
            }
            if (titleTask.isEmpty()) {
                editTextTitle.error = "title is missing"
                editTextTitle.requestFocus()
                return@setOnClickListener
            }


            launch {
                val task = Task(titleTask, descriptionTask)
                context?.run {
                    TaskDatabase(this).getTaskDao().addTask(task)
                }

            }
        }
//            TaskDatabase(activity!!).getNoteDao().addMote(note)
    }


}
