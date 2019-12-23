package tsisyk.app.kanbanboard.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.launch
import tsisyk.app.kanbanboard.R
import tsisyk.app.kanbanboard.data.TaskDatabase
import tsisyk.app.kanbanboard.ui.Basefragment
import tsisyk.app.kanbanboard.ui.TaskAdapter

class HomeFragment : Basefragment() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        addNote.setOnClickListener {
            val action = HomeFragmentDirections.actionNavigationHomeToAddNewNoteFragment()
            Navigation.findNavController(it).navigate(action)
        }
        TaskDatabase(activity!!).getTaskDao()

        launch {
            context?.apply { val task = TaskDatabase(this).getTaskDao().getAll()
            recycleView.adapter = TaskAdapter(task) }
        }

        recycleView.setHasFixedSize(true)
        recycleView.layoutManager =
            StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
    }
}