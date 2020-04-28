package com.pigeoff.notes

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.recyclerview.widget.RecyclerView
import com.pigeoff.notes.database.RmNotes
import kotlinx.android.synthetic.main.recycler_list.view.*


class MainAdapter(private val context: MainActivity, private var notesList: List<RmNotes>?, private var color: Int) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    /* CE QUI SE LANCE EN PREMIER : initilisise la classe ViewHolder qui sert de classe principal */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.recycler_list, parent, false))
    }

    override fun getItemCount(): Int {
        return notesList!!.count()
    }


    /* Action quand un holder est cliqué */
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder as ViewHolder
        val note = notesList?.get(position)
        holder.imageDot.setImageDrawable(context.getDrawable(color))
        if (!note?.titre.isNullOrEmpty()) holder.textTitle.text = note?.titre else holder.textTitle.text = context.getString(R.string.no_title)
        holder.linearLayout.setOnClickListener {
            var intent = Intent(context, EditActivity::class.java)
            intent.putExtra("id", note?.id)
            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(context, holder.imageDot, "transitionTitre")
            context.startActivity(intent)
        }

    }

    fun updateData(data: List<RmNotes>, clr: Int) {
        notesList = data
        color = clr
        notifyDataSetChanged()
        notifyItemRangeChanged(0, itemCount)
    }


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textTitle = view.textViewTitle
        val imageDot = view.imageViewDotAdapter
        val linearLayout = view.linearList
    }

}