package net.gepergee.usualtestproject.kotlin

import android.app.Activity
import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.Toast
import net.gepergee.usualtestproject.R

/**
 * KotlinRecyclerView
 * @author petergee
 *
 * @date 2018/8/20
 *
 *
 */
class KotlinRecyclerViewActivity : Activity() {

    private var list: ArrayList<String>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlin_listview)
        initData()
        initView()
    }

    private fun initData() {
        list = ArrayList()
        for (i in 1..20) {
            list!!?.add("我是条目" + i.toString())
        }
    }

    private fun initView() {
        val mRecyclerView = findViewById<RecyclerView>(R.id.rv_list)
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        // layoutManager
        mRecyclerView.layoutManager = layoutManager
        // itemDecoration
        val itemDecoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        itemDecoration.setDrawable(resources.getDrawable(R.drawable.divider_line))
        mRecyclerView.addItemDecoration(itemDecoration)
        // animation
        mRecyclerView.itemAnimator = DefaultItemAnimator()
        // setAdapter
        val adapter = KotlinRecycleAdapter(this, list)
        mRecyclerView.adapter = adapter
        // itemClick
        adapter!!.setOnKotlinItemClickListener(object : KotlinRecycleAdapter.IKotlinItemClickListener {
            override fun onItemClickListener(position: Int) {
                Toast.makeText(applicationContext, list!![position], Toast.LENGTH_SHORT).show()
            }
        })

    }
}