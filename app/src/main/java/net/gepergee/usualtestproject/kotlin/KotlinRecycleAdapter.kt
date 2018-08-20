package net.gepergee.usualtestproject.kotlin

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import net.gepergee.usualtestproject.R

/**
 * adapter
 * @author petergee
 *
 * @date 2018/8/20
 *
 */
class KotlinRecycleAdapter : RecyclerView.Adapter<KotlinRecycleAdapter.MyHolder> {
    private var list: ArrayList<String>? = null
    private var context: Context? = null
    private var itemClickListener: IKotlinItemClickListener? = null

    constructor (mContext: Context, list: ArrayList<String>?) {
        this.context = mContext
        this.list = list
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        var view = LayoutInflater.from(context).inflate(R.layout.item_multi_column_list, parent, false)
        return MyHolder(view)
    }

    override fun getItemCount(): Int = list?.size!!

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        holder?.text?.text = list!![position]

        // 点击事件
        holder.itemView.setOnClickListener {
            itemClickListener!!.onItemClickListener(position)
        }

    }

    class MyHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
        // !! 断言
        var text: TextView = itemView!!.findViewById(R.id.item_text)
    }

    fun setOnKotlinItemClickListener(itemClickListener: IKotlinItemClickListener) {
        this.itemClickListener = itemClickListener
    }

    interface IKotlinItemClickListener {
        fun onItemClickListener(position: Int)
    }

}