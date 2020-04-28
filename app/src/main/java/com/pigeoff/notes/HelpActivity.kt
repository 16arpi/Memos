package com.pigeoff.notes

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.core.app.ActivityCompat

class HelpActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_help)

        var pref: SharedPreferences = this.getSharedPreferences("theme", Context.MODE_PRIVATE)
        var metroId: Int = pref.getInt("metroid", 0)
        var metro = MetroTheme.MetroBuilder(metroId)


        var closeBtn: Button = findViewById(R.id.btnClose)
        var leftLine: View = findViewById(R.id.leftLine)
        var imgDot: ImageView = findViewById(R.id.imgDot)
        var imgDot2: ImageView = findViewById(R.id.imgDot2)
        leftLine.setBackgroundColor(Color.parseColor(metro.colorString))
        imgDot.setImageDrawable(getDrawable(metro.drawable))
        imgDot2.setImageDrawable(getDrawable(metro.drawable))


        closeBtn.setOnClickListener {
            supportFinishAfterTransition()
            ActivityCompat.finishAfterTransition(this)

        }

    }

    override fun onBackPressed() {
        supportFinishAfterTransition()
        ActivityCompat.finishAfterTransition(this)
        super.onBackPressed()
    }
}
