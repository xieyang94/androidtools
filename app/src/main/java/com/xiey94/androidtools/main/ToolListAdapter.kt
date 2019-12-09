package com.xiey94.androidtools.main

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.xiey94.androidtools.R
import kotlinx.android.synthetic.main.tool_item.view.*

/**
 * Create by xiey on 2019/12/9.
 */
class ToolListAdapter constructor(datas: MutableList<String>, context: Context) :
    RecyclerView.Adapter<ToolListAdapter.StaticField.ToolListVH>() {

    private var datas: MutableList<String> = datas
    private var context: Context = context
    private var listener: ((view: View, position: Int) -> Unit)? = null

    fun setListener(listener: (view: View, position: Int) -> Unit) {
        this.listener = listener
    }

    override fun getItemCount(): Int {
        return datas.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToolListVH {
        return ToolListVH(
            LayoutInflater.from(parent.context).inflate(
                R.layout.tool_item,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ToolListVH, position: Int) {
        holder.tvTitle.text = datas[position]
        holder.tvTitle.setOnClickListener {
            listener?.let { it -> it(holder.tvTitle, position) }
        }
    }

    companion object StaticField {
        class ToolListVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
            var tvTitle: TextView = itemView.tv_title
        }
    }

}


