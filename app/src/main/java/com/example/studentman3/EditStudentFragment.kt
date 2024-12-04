package com.example.studentman3

import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
class EditStudentFragment : Fragment() {

    private var studentIndex: Int? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_edit_student, container, false)

        val nameEditText: EditText = view.findViewById(R.id.editTextName)
        val idEditText: EditText = view.findViewById(R.id.editTextId)
        val saveButton: Button = view.findViewById(R.id.buttonSave)
        val deleteButton: Button = view.findViewById(R.id.buttonDelete)

        // Retrieve arguments passed from the StudentListFragment
        val studentName = arguments?.getString("studentName")
        val studentId = arguments?.getString("studentId")
        studentIndex = arguments?.getInt("studentIndex")

        if (studentName != null && studentId != null) {
            nameEditText.setText(studentName)
            idEditText.setText(studentId)
        }

        saveButton.setOnClickListener {
            val name = nameEditText.text.toString().trim()
            val id = idEditText.text.toString().trim()

            // Validate input fields
            if (name.isEmpty() || id.isEmpty()) {
                Toast.makeText(requireContext(), "Please fill out all fields.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Send result back to StudentListFragment
            val resultBundle = Bundle().apply {
                putString("studentName", name)
                putString("studentId", id)
                putInt("studentIndex", studentIndex ?: -1)
            }
            parentFragmentManager.setFragmentResult("studentEditKey", resultBundle)

            // Navigate back
            findNavController().navigateUp()
        }

        deleteButton.setOnClickListener {
            // Notify StudentListFragment to remove the student
            studentIndex?.let {
                val resultBundle = Bundle().apply {
                    putInt("studentIndexToRemove", it)
                }
                parentFragmentManager.setFragmentResult("studentDeleteKey", resultBundle)

                // Navigate back
                findNavController().navigateUp()
            }
        }

        return view
    }
}
