package com.pigeoff.notes

import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.pigeoff.notes.database.RmDatabase
import com.pigeoff.notes.database.RmNotes


class MainActivity : AppCompatActivity() {

    var db: RmDatabase? = null
    var recyclerView: RecyclerView? = null
    var globalAdapter: MainAdapter? = null
    var notesList: List<RmNotes>? = null
    var metroId: Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var pref: SharedPreferences = this.getSharedPreferences("theme", 0)
        metroId = pref.getInt("metroid", 1)
        var metro = MetroTheme.MetroBuilder(metroId)

        db = Room.databaseBuilder(
            applicationContext,
            RmDatabase::class.java, "notes"
        ).allowMainThreadQueries().build()

        notesList = db!!.notesDAO().getAllNotes()
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView?.layoutManager = LinearLayoutManager(this)
        globalAdapter = MainAdapter(this, notesList, metro.drawable)
        recyclerView?.adapter = globalAdapter

        var addDot: ImageView = findViewById(R.id.imageViewDot2)
        var textAdd: TextView = findViewById(R.id.textAddNote)
        var btnHelp: Button = findViewById(R.id.btnHelp)

        textAdd.setOnClickListener {
            var intent = Intent(this, EditActivity::class.java)
            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, addDot, "topBar")
            startActivity(intent)
        }

        btnHelp.setOnClickListener {
            var intent = Intent(this, HelpActivity::class.java)
            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, addDot, "topBar")
            startActivity(intent)
        }

        configTheme(metro, recyclerView, notesList)

    }

    override fun onResume() {
        var notesList = db!!.notesDAO().getAllNotes()
        globalAdapter?.updateData(notesList, MetroTheme.MetroBuilder(metroId).drawable)
        super.onResume()
    }

    fun configTheme(metro: MetroTheme.MetroBuilder, recyclerView: RecyclerView?, notesList: List<RmNotes>?) {
        var leftLine = findViewById<View>(R.id.leftLine)
        var metroIndic = findViewById<Button>(R.id.metroIndic)
        var ligneIndic = findViewById<Button>(R.id.ligneIndic)

        leftLine.setBackgroundColor(Color.parseColor(metro.colorString))
        ligneIndic.setTextColor(Color.parseColor(metro.textColorString))
        ligneIndic.setBackgroundResource(metro.drawable)
        ligneIndic.setText(metro.id.toString())

        var themeClickListener = View.OnClickListener {
            var newMetro = MetroTheme().setNextMetro(this, metroId)
            metroId = newMetro.id

            var notesList = db!!.notesDAO().getAllNotes()
            globalAdapter?.updateData(notesList, MetroTheme.MetroBuilder(metroId).drawable)
            configTheme(newMetro, recyclerView, notesList)
        }

        ligneIndic.setOnClickListener(themeClickListener)
        metroIndic.setOnClickListener(themeClickListener)
    }
}
