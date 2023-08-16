package com.example.makeupinfo.ui.makeup

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.makeupinfo.R
import com.example.makeupinfo.model.Makeups.MakeUpResposneItem

class MakeupProductAdapter(
    var makeupItem: ArrayList<MakeUpResposneItem>
):RecyclerView.Adapter<MakeupProductAdapter.MakeupViewHolder>() {


    inner class MakeupViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
       var  tvproductDesc : TextView =  itemView.findViewById(R.id.tvproductDesc)
       var  tvCategory : TextView =  itemView.findViewById(R.id.tvCategory)
       var  tvproductType : TextView =  itemView.findViewById(R.id.tvproductType)

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MakeupViewHolder {
       var view = LayoutInflater.from(parent.context).inflate(R.layout.item_makeup_layout,parent,false)
        return MakeupViewHolder(view)
    }

    override fun onBindViewHolder(holder: MakeupViewHolder, position: Int) {
         var productList = makeupItem[position]
         holder.tvproductDesc.text = productList.description
         holder.tvproductType.text = productList.product_type
    }

    override fun getItemCount(): Int {
        return  makeupItem.size
    }
}