package com.example.studentman3

import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

class AddStudentFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_add_student, container, false)

        val nameEditText: EditText = view.findViewById(R.id.editTextName)
        val idEditText: EditText = view.findViewById(R.id.editTextId)
        val saveButton: Button = view.findViewById(R.id.buttonSave)

        saveButton.setOnClickListener {
            val name = nameEditText.text.toString().trim()
            val id = idEditText.text.toString().trim()

            // Validate input fields
            if (name.isEmpty() || id.isEmpty()) {
                Toast.makeText(requireContext(), "Please fill out all fields.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Pass the new student data back
            val resultBundle = Bundle().apply {
                putString("studentName", name)
                putString("studentId", id)
            }
            parentFragmentManager.setFragmentResult("studentAddKey", resultBundle)

            // Navigate back
            findNavController().navigateUp()
        }

        return view
    }
}
