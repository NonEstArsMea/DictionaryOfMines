package raa.example.dictionaryofmines.ui.mainFragment

import androidx.recyclerview.widget.DiffUtil

class MineDiffCallBack: DiffUtil.ItemCallback<MineDataClass>() {
    override fun areItemsTheSame(oldItem: MineDataClass, newItem: MineDataClass): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: MineDataClass, newItem: MineDataClass): Boolean {
        return  oldItem == newItem
    }
}