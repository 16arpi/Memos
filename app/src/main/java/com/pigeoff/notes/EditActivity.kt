package com.pigeoff.notes

import android.app.AlertDialog
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.room.Room
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.pigeoff.notes.database.RmDatabase
import com.pigeoff.notes.database.RmNotes
import kotlinx.android.synthetic.main.activity_edit.*


class EditActivity : AppCompatActivity() {

    var DB: RmDatabase? = null
    var NOTE = RmNotes()
    var TITLE: String = ""
    var CONTENT: String = ""
    var NO_ID: Int = 999999
    var EDIT_STATUT = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)


        DB = Room.databaseBuilder(
            applicationContext,
            RmDatabase::class.java, "notes"
        ).allowMainThreadQueries().build()

        //Config Theme
        var pref: SharedPreferences = this.getSharedPreferences("theme", Context.MODE_PRIVATE)
        var metroId: Int = pref.getInt("metroid", 0)
        var metro = MetroTheme.MetroBuilder(metroId)
        configTheme(metro)

        var topTitle = findViewById<TextView>(R.id.textViewTopTitle)
        var EDIT_TITLE: EditText = findViewById(R.id.editTextTitle)
        var EDIT_CONTENT: EditText = findViewById(R.id.editTextContent)
        var CLOSE: Button = findViewById(R.id.btnClose)
        var DELETE: Button = findViewById(R.id.btnDelete)
        var SAVE: Button = findViewById(R.id.btnSave)

        var id = intent.getIntExtra("id", NO_ID)

        if (id != NO_ID) {
            EDIT_STATUT = 1
            topTitle.text = getString(R.string.edit_memo)
            NOTE = DB!!.notesDAO().getNote(id)
            EDIT_TITLE?.setText(NOTE?.titre)
            EDIT_CONTENT?.setText(NOTE?.content)
        }
        else {
            //EDIT_TITLE?.requestFocus()
            /*EDIT_TITLE.setOnFocusChangeListener { v, hasFocus ->
                if (hasFocus) {
                    getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE)
                }
            }*/
        }

        var watcher = object : TextWatcher {
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                NOTE?.titre = EDIT_TITLE.text.toString()
                NOTE?.content = EDIT_CONTENT.text.toString()
            }

            override fun afterTextChanged(s: Editable?) {
                //NADA
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                //NADA
            }
        }
        editTextTitle.addTextChangedListener(watcher)
        editTextContent.addTextChangedListener(watcher)

        DELETE.setOnClickListener {
            if (EDIT_STATUT == 0) {
                delete(NO_ID)
            }
            if (EDIT_STATUT == 1) {
                delete(NOTE?.id)
            }
        }

        SAVE.setOnClickListener {
            saveNote()
            Snackbar
                .make(it, getString(R.string.indicator_saved_note), Snackbar.LENGTH_SHORT)
                .setTextColor(Color.parseColor(metro.textColorString))
                .setBackgroundTint(Color.parseColor(metro.colorString))
                .show()
        }

        CLOSE.setOnClickListener {
            saveNote()
            supportFinishAfterTransition()
            ActivityCompat.finishAfterTransition(this)
        }



    }

    override fun onBackPressed() {
        saveNote()
        supportFinishAfterTransition()
        ActivityCompat.finishAfterTransition(this)
        super.onBackPressed()
    }

    fun delete(id: Int?){
        if (id == NO_ID) {
            supportFinishAfterTransition()
            ActivityCompat.finishAfterTransition(this)
        }
        else {
            AlertDialog.Builder(this)
                .setMessage(resources.getString(R.string.confirm_message))
                .setNegativeButton(resources.getString(R.string.confirm_cancel)) { dialog, which ->
                    // Respond to negative button press
                }
                .setPositiveButton(resources.getString(R.string.confirme_ok)) { dialog, which ->
                    DB!!.notesDAO().deleteNote(NOTE)
                    supportFinishAfterTransition()
                    ActivityCompat.finishAfterTransition(this)
                }
                .show()
        }
    }

    override fun onStart() {
        super.onStart()
        var newId = intent.getIntExtra("id", NO_ID)
        var EDIT_TITLE: EditText = findViewById(R.id.editTextTitle)
        var EDIT_CONTENT: EditText = findViewById(R.id.editTextContent)

        Handler().postDelayed({
            if (newId != NO_ID) {

            }
            else {
                showKeyboard(true, EDIT_TITLE)
            }
        }, 400)

    }

    fun configTheme(metro: MetroTheme.MetroBuilder) {
        var leftLine = findViewById<View>(R.id.leftLine)
        var imageDot = findViewById<ImageView>(R.id.imageViewDotEdit)

        leftLine.setBackgroundColor(Color.parseColor(metro.colorString))
        imageDot.setImageDrawable(this.getDrawable(metro.drawable))

    }

    fun saveNote() {
        if (EDIT_STATUT == 0) {
            if (!NOTE?.titre.isNullOrEmpty() || !NOTE?.content.isNullOrEmpty())
                DB!!.notesDAO().newNote(NOTE)
            var allMemos = DB!!.notesDAO().getAllNotes()
            var createdMemo = allMemos.first()
            NOTE.id = createdMemo.id
            EDIT_STATUT = 1
        }
        else if (EDIT_STATUT == 1) {
            DB!!.notesDAO().updateNote(NOTE)
        }
    }

    fun showKeyboard(condition: Boolean, edit: EditText) {
        if (condition) {
            edit.requestFocus()
            edit.setSelection(edit.text.length)
            val inputMethodManager: InputMethodManager =
                this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
        }
        else {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)
        }
    }


}
