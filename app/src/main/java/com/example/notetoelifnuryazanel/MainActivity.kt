package com.example.notetoelifnuryazanel

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {
    // Temporary code
    //private var tempNote = Note()

    private var noteList : ArrayList<Note>? = null
    private var jsonsSerializer : JSONSerializer? = null

    private var recyclerView: RecyclerView? = null
    private var adapter: NoteAdapter? = null
    private var showDividers: Boolean = false



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fabNewNote = findViewById<FloatingActionButton>(R.id.fab)
        fabNewNote.setOnClickListener {
            val dialogNewNote = DialogNewNote()
            dialogNewNote.show(supportFragmentManager, "")
        }

        var mSerializer = JSONSerializer("NoteToElifnurYazanel", applicationContext)

        try{
            noteList = jsonsSerializer!!.load()
        } catch (e: Exception) {
            noteList = ArrayList()
         Log.e(TAG,"Error loading notes...")

        }


        recyclerView = findViewById<View>(R.id.recyclerView) as RecyclerView
        adapter = NoteAdapter(this, noteList!!)
        val layoutManager = LinearLayoutManager(applicationContext)
        recyclerView!!.layoutManager = layoutManager
        recyclerView!!.itemAnimator = DefaultItemAnimator()
        recyclerView!!.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))
        recyclerView!!.adapter = adapter

    }

    override fun onResume() {
        super.onResume()
        val prefs = getSharedPreferences("Note to self", Context.MODE_PRIVATE)
        showDividers = prefs.getBoolean("dividers", true)
        if (showDividers){
            recyclerView!!.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))
        } else {
            if (recyclerView!!.itemDecorationCount > 0)
                recyclerView!!.removeItemDecorationAt(0)
        }
    }


    fun createNewNote(n: Note) {
        noteList!!.add(n)
        adapter!!.notifyDataSetChanged()
    }
    fun showNote(noteToShow: Int) {
        val dialog = DialogShowNote()
        noteList?.get(noteToShow)?.let { dialog.sendNoteSelected(it) }
        dialog.show(supportFragmentManager, "")
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val b = when (item.itemId) {
            R.id.action_settings -> {
                val intentToSettings = Intent(this, SettingsActivity::class.java)
                startActivity(intentToSettings)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
        return b
    }

    private fun saveNotes(){
        try {
            jsonsSerializer!!.save(this.noteList!!)
        } catch (e: Exception){
            Log.e(TAG, "Error loading notes...")
        }
    }

    override fun onPause(){
        super.onPause()
        saveNotes()
    }

}