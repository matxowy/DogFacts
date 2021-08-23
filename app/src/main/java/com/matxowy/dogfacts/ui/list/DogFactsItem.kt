package com.matxowy.dogfacts.ui.list

import com.matxowy.dogfacts.R
import com.matxowy.dogfacts.data.db.entity.DogFactItem
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.item_dog_fact.*

class DogFactsItem(
    val dogFactItem: DogFactItem
): Item() {
    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.apply {
            textView_id.text = "#${dogFactItem.id.toString()}"
            updateIcon()
        }
    }

    override fun getLayout(): Int = R.layout.item_dog_fact

    private fun ViewHolder.updateIcon() {
        if (this.adapterPosition % 2 == 0) { // we set every second position other icon
            imageView_icon.setImageResource(R.drawable.ic_dog_2)
        } else {
            imageView_icon.setImageResource(R.drawable.ic_dog_1)
        }
    }

}