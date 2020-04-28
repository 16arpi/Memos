package com.pigeoff.notes

import android.content.Context
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor


class MetroTheme {
    var colors: List<String> = listOf(
         "#FFCD00",
         "#FFCD00",
         "#003CA6",
         "#837902",
         "#CF009E",
         "#FF7E2E",
         "#6ECA97",
         "#FA9ABA",
         "#E19BDF",
         "#B6BD00",
         "#C9910D",
         "#704B1C",
         "#007852",
         "#6EC4E8",
         "#62259D"
    )

    var metroNumeros: List<Int> = listOf(
        1,
        1,
        2,
        3,
        4,
        5,
        6,
        7,
        8,
        9,
        10,
        11,
        12,
        13,
        14
    )

    var colorsRessources: List<Int> = listOf(
        R.color.m1,
        R.color.m1,
        R.color.m2,
        R.color.m3,
        R.color.m4,
        R.color.m5,
        R.color.m6,
        R.color.m7,
        R.color.m8,
        R.color.m9,
        R.color.m10,
        R.color.m11,
        R.color.m12,
        R.color.m13,
        R.color.m14
    )

    var textColorsIndicator: List<Boolean> = listOf(
        false,
        false,
        true,
        true,
        true,
        false,
        false,
        false,
        false,
        false,
        false,
        true,
        true,
        false,
        true
    )

    var listDrawables: List<Int> = listOf(
        R.drawable.ic_m1,
        R.drawable.ic_m1,
        R.drawable.ic_m2,
        R.drawable.ic_m3,
        R.drawable.ic_m4,
        R.drawable.ic_m5,
        R.drawable.ic_m6,
        R.drawable.ic_m7,
        R.drawable.ic_m8,
        R.drawable.ic_m9,
        R.drawable.ic_m10,
        R.drawable.ic_m11,
        R.drawable.ic_m12,
        R.drawable.ic_m13,
        R.drawable.ic_m14
    )


    fun setNextMetro(context: Context, oldId: Int) : MetroBuilder {
        var pref: SharedPreferences = context.getSharedPreferences("theme", 0)

        var newId = 0
        when (oldId) {
            0 -> newId = 2
            14 -> newId = 1
            else -> newId = oldId+1
        }

        val editor: Editor = pref.edit()
        editor.putInt("metroid", newId)
        editor.commit()

        return MetroBuilder(newId)
    }

     class MetroBuilder {
        var id: Int = 0
        var color: Int = 0
         var colorString: String = ""
        var textColor: Int = 0
        var textColorString: String = ""
        var drawable: Int = 0

         constructor(id: Int) {
             this.id = MetroTheme().metroNumeros.get(id)
             this.color = MetroTheme().colorsRessources.get(id)
             this.colorString = MetroTheme().colors.get(id)
             if (MetroTheme().textColorsIndicator.get(id)) {
                 this.textColor = R.color.textColorWhite
                 this.textColorString = "#FFFFFF"
             }
             else {
                 this.textColor = R.color.textColorBlack
                 this.textColorString = "#000000"
             }
             this.drawable = MetroTheme().listDrawables.get(id)
         }
    }
}