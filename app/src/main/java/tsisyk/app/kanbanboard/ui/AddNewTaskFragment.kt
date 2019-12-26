package tsisyk.app.kanbanboard.ui

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.fragment_add_new_task.*
import kotlinx.coroutines.launch
import tsisyk.app.kanbanboard.R
import tsisyk.app.kanbanboard.data.Task
import tsisyk.app.kanbanboard.data.TaskDatabase

class AddNewTaskFragment : BaseFragment() {

    private var task: Task? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_new_task, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        buttonSave.setOnClickListener { view ->
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

                context?.let {
                    val myTask = Task(titleTask, descriptionTask)

                    if (task == null) {
                        TaskDatabase(it).getTaskDao().addTask(myTask)
                        it.toast("New Task Saved")
                    } else {
                        myTask.id = task!!.id
                        TaskDatabase(it).getTaskDao().updateTask(myTask)
                        it.toast("Current Task Updated")
                    }


                    val action = AddNewTaskFragmentDirections.actionAddNewNoteFragmentToNavigationHome()
                    Navigation.findNavController(view).navigate(action)
                }
            }

        }
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.delete -> if (task != null) deleteTask() else context?.toast("Cannot Delete")
        }
        return super.onOptionsItemSelected(item)
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu, menu)
    }


    private fun deleteTask() {
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
}
