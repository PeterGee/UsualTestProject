package net.gepergee.usualtestproject.activity.multiColumnList

import android.app.Activity
import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.Toast
import net.gepergee.usualtestproject.R
import net.gepergee.usualtestproject.activity.multiColumnList.adapter.FirstLineAdapter
import net.gepergee.usualtestproject.activity.multiColumnList.adapter.RecyclerItemClickListener
import java.util.*

/**
 * 多列list
 *
 * @author petergee
 * @date 2018/7/26
 */
class MultiColumnListActivity : Activity() {

    private var list: MutableList<String>? = null
    private var list2: MutableList<String>? = null
    private var list3: MutableList<String>? = null
    private var list4: MutableList<String>? = null
    private var firstLineAdapter: FirstLineAdapter? = null
    private var secondLineAdapter: FirstLineAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_multi_column)
        initData()
        initView()
    }

    private fun initData() {
        list = ArrayList()
        list!!.add("于谦")
        list!!.add("郭德纲")
        list!!.add("小岳岳")


        list2 = ArrayList()
        list2!!.add("抽烟")
        list2!!.add("喝酒")
        list2!!.add("烫头")


        list3 = ArrayList()
        list3!!.add("主持")
        list3!!.add("说相声")
        list3!!.add("拍电影")


        list4 = ArrayList()
        list4!!.add("拍电影")
        list4!!.add("唱歌")
        list4!!.add("说相声")
        list4!!.add("做采访")


    }

    private fun initView() {
        val recyclerView = findViewById<RecyclerView>(R.id.rv_recyclerview_one)
        val recyclerViewTwo = findViewById<RecyclerView>(R.id.rv_recyclerview_two)
        // layoutManager
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        // set layoutManager
        recyclerView.layoutManager = layoutManager
        recyclerViewTwo.layoutManager = LinearLayoutManager(this)
        // item Decoration
        val dividerItemDecoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        dividerItemDecoration.setDrawable(resources.getDrawable(R.drawable.divider_line))
        recyclerView.addItemDecoration(dividerItemDecoration)
        recyclerViewTwo.addItemDecoration(dividerItemDecoration)
        // item Animator
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerViewTwo.itemAnimator = DefaultItemAnimator()
        // adapter
        firstLineAdapter = FirstLineAdapter(this, list)
        secondLineAdapter = FirstLineAdapter(this, list2)
        recyclerView.adapter = firstLineAdapter
        // childAdapter
        recyclerViewTwo.adapter = secondLineAdapter
        // add listener
        firstLineAdapter!!.setRecyclerItemClickListener(object : RecyclerItemClickListener {
            override fun onItemClickListener(position: Int) {
                if (position == 0) {
                    secondLineAdapter = FirstLineAdapter(applicationContext, list2)
                    recyclerViewTwo.adapter = secondLineAdapter
                }

                if (position == 1) {
                    secondLineAdapter = FirstLineAdapter(applicationContext, list3)
                    recyclerViewTwo.adapter = secondLineAdapter
                }

                if (position == 2) {
                    secondLineAdapter = FirstLineAdapter(applicationContext, list4)
                    recyclerViewTwo.adapter = secondLineAdapter
                }
            }

            override fun onItemLongClickListener(position: Int) {
                Toast.makeText(applicationContext, "LongClicked " + list!![position], Toast.LENGTH_SHORT).show()
            }
        })

    }


}
