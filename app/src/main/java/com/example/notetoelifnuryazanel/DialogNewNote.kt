package com.example.notetoelifnuryazanel

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import androidx.fragment.app.DialogFragment

class DialogNewNote : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val builder = AlertDialog.Builder(requireActivity())

        val inflater = requireActivity().layoutInflater
        val dialogLayout = inflater.inflate(R.layout.dialog_new_note, null)

        val editTitle = dialogLayout.findViewById<EditText>(R.id.editTitle)
        val editDescription = dialogLayout.findViewById<EditText>(R.id.editDescription)
        val checkBoxIdea = dialogLayout.findViewById<CheckBox>(R.id.checkBoxIdea)
        val checkBoxToDo = dialogLayout.findViewById<CheckBox>(R.id.checkBoxTodo)
        val checkBoxImportant = dialogLayout.findViewById<CheckBox>(R.id.checkBoxImportant)
        val btnOK = dialogLayout.findViewById<Button>(R.id.btnOK)
        val btnCansel = dialogLayout.findViewById<Button>(R.id.btnCancel)




        builder.setView(dialogLayout).setMessage("Ad d a new note")
        btnCansel.setOnClickListener {
            dismiss()
        }


        btnOK.setOnClickListener {
            val newNote = Note()
            newNote.title = editTitle.text.toString()
            newNote.description = editDescription.text.toString()
            newNote.idea = checkBoxIdea.isChecked
            newNote.todo = checkBoxToDo.isChecked
            newNote.important = checkBoxImportant.isChecked

            val callingActivity = activity as MainActivity?
            callingActivity!!.createNewNote(newNote)

            dismiss()
        }
        return builder.create()
    }
}
