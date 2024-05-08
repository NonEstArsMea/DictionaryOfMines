package raa.example.dictionaryofmines.ui.mainFragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.view.marginTop
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import raa.example.dictionaryofmines.R
import java.util.Calendar
import java.util.Random

class RecycleViewAdapter :
    ListAdapter<MineDataClass, RecycleViewAdapter.RVOnMainFragmentViewHolder>(MineDiffCallBack()) {

    var onClickListener: ((MineDataClass) -> (Unit))? = null

    class RVOnMainFragmentViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val name = view.findViewById<TextView>(R.id.name)
        val number = view.findViewById<TextView>(R.id.number)
        val card = view.findViewById<CardView>(R.id.person_vh)


        fun bind(text: String, position: Int) {
            name.text = text
            number.text = position.toString()
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RVOnMainFragmentViewHolder {

        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.card_of_mine, parent, false)

        return RVOnMainFragmentViewHolder(view)
    }

    override fun onBindViewHolder(holder: RVOnMainFragmentViewHolder, position: Int) {
        currentList[position].apply {
            holder.bind(name, id)
        }

        holder.view.setOnClickListener {
            onClickListener?.invoke(currentList[position])
        }


    }


}