package tsisyk.app.kanbanboard.ui

import android.R
import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.fragment_add_new_task.*
import kotlinx.coroutines.launch
import tsisyk.app.kanbanboard.data.Task
import tsisyk.app.kanbanboard.data.TaskDatabase
import tsisyk.app.kanbanboard.utiliies.State.*


class AddNewTaskFragment : BaseFragment() {

    private var task: Task? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(
            tsisyk.app.kanbanboard.R.layout.fragment_add_new_task,
            container,
            false
        )
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        if (task != null) spinner.visibility = View.VISIBLE
//        else spinner.visibility = View.INVISIBLE

        arguments?.let {

            task = AddNewTaskFragmentArgs.fromBundle(it).tsakNote
            editTextTitle.setText(task?.title)
            editTextDescription.setText(task?.description)


            val values = arrayOf(IN_PROGRESS, PEER_REVIREW, IN_TEST, BLOKED, DONE)
            val adapter = ArrayAdapter(this.activity!!, R.layout.simple_spinner_item, values)
            adapter.setDropDownViewResource(R.layout.simple_dropdown_item_1line)
            spinner.adapter = adapter

        }


        button_delete.setOnClickListener {
            AlertDialog.Builder(context).apply {
                setTitle("Are you sure?")
                setMessage("You cannot undo this operation")
                setPositiveButton("Yes") { _, _ ->
                    launch {
                        TaskDatabase(context).getTaskDao().deleteTask(task!!)
                        val action =
                            AddNewTaskFragmentDirections.actionAddNewNoteFragmentToNavigationHome()
                        Navigation.findNavController(view!!).navigate(action)
                    }
                }
                setNegativeButton("No") { _, _ ->

                }
            }.create().show()

        }

        buttonSave.setOnClickListener { view ->
            val descriptionTask = editTextDescription.text.trim().toString()
            val titleTask = editTextTitle.text.trim().toString()
            val state = "IN_PROGRESS"
            //    .toString().setText() task?.state

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
                context?.let {
                    val myTask = Task(titleTask, descriptionTask, state)
                    if (task == null) {
                        TaskDatabase(it).getTaskDao().addTask(myTask)
                    } else {
                        myTask.id = task!!.id
                        TaskDatabase(it).getTaskDao().updateTask(myTask)
                    }
                }
            }

            val action = AddNewTaskFragmentDirections.actionAddNewNoteFragmentToNavigationHome()
            Navigation.findNavController(view).navigate(action)
        }
    }

}
