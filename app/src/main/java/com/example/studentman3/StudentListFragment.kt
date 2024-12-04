package com.example.studentman3

import android.os.Bundle
import android.view.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.NavHostFragment

class StudentListFragment : Fragment() {

    private val students = mutableListOf(
        Student("Nguyen Van A", "20190001"),
        Student("Tran Thi B", "20190002"),
        Student("Le Van C", "20190003")
    )

    private lateinit var adapter: ArrayAdapter<Student>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        val view = inflater.inflate(R.layout.fragment_student_list, container, false)

        val listView: ListView = view.findViewById(R.id.studentListView)
        adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, students)
        listView.adapter = adapter

        // Register for context menu
        registerForContextMenu(listView)


        listView.setOnItemClickListener { _, _, position, _ ->
            val action = StudentListFragmentDirections
                .actionStudentListFragmentToEditStudentFragment(
                    students[position].name,
                    students[position].id,
                    position
                )
            findNavController().navigate(action)
        }

        // Listen for the Add result
        parentFragmentManager.setFragmentResultListener("studentAddKey", this) { _, bundle ->
            val name = bundle.getString("studentName")
            val id = bundle.getString("studentId")

            if (!name.isNullOrEmpty() && !id.isNullOrEmpty()) {
                // Add the new student to the list
                students.add(Student(name, id))
                adapter.notifyDataSetChanged() // Refresh the ListView
            }
        }

        // Listen for the Edit result
        parentFragmentManager.setFragmentResultListener("studentEditKey", this) { _, bundle ->
            val name = bundle.getString("studentName")
            val id = bundle.getString("studentId")
            val index = bundle.getInt("studentIndex")

            if (!name.isNullOrEmpty() && !id.isNullOrEmpty() && index != -1) {
                // Update the student information in the list
                students[index] = Student(name, id)
                adapter.notifyDataSetChanged() // Refresh the ListView
            }
        }
        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.options_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.add_student) {

            findNavController().navigate(R.id.action_studentListFragment_to_addStudentFragment)
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateContextMenu(
        menu: ContextMenu,
        v: View,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        requireActivity().menuInflater.inflate(R.menu.context_menu, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val info = item.menuInfo as AdapterView.AdapterContextMenuInfo
        when (item.itemId) {
            R.id.edit_student -> {
                val action = StudentListFragmentDirections
                    .actionStudentListFragmentToEditStudentFragment(
                        students[info.position].name,
                        students[info.position].id,
                        info.position
                    )
                findNavController().navigate(action)
                return true
            }
            R.id.remove_student -> {
                students.removeAt(info.position)
                adapter.notifyDataSetChanged()
                Toast.makeText(requireContext(), "Student removed", Toast.LENGTH_SHORT).show()
                return true
            }
        }
        return super.onContextItemSelected(item)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Handle button click to add new student
        val addStudentButton: Button = view.findViewById(R.id.addStudentButton)
        addStudentButton.setOnClickListener {
            findNavController().navigate(R.id.action_studentListFragment_to_addStudentFragment)
        }


    }

}

