package net.gepergee.usualtestproject.kotlin

import android.app.Activity
import android.app.FragmentTransaction
import android.os.Bundle
import android.util.Log
import net.gepergee.usualtestproject.R
import net.gepergee.usualtestproject.blueTooth.Utils.LogUtils


/**
 * @author petergee
 * @date 2018/8/9
 */
class KotlinTestActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlin_test)
        initView()
        initFragment()

    }

    private fun initFragment() {
        var fragmentManager=fragmentManager
        val transaction = fragmentManager.beginTransaction()
        var fragment=LazyFragment()
        // transaction.add(R.id.fragment_content,fragment)
        transaction.commitAllowingStateLoss()
    }

    private fun initView() {
        var s=SetName()
        s?.setName("张三")


    }

    interface IgetName{
        fun getName(name: String)
    }
    class SetName{
          fun setName(name: String){
              var i=GetName()
              i?.getName(name)
          }

    }
    class  GetName:IgetName{
        override fun getName(name: String) {
            LogUtils.e("The Name is $name")
        }

    }



    // 两个等号比较的是值是否相等，三个等号比较的是地址值是否相等
    private fun compare(a: Int, b: Int) {
        if (a > b) {
            Log.e("tag", "a is bigger")
            print(a)
        } else {
            Log.e("tag", "b is bigger")
            print(b)
        }
    }

    // 判断指定值是否在范围内
    private fun getIsContainsValue() {
        var a = 5
        if (a in (1..6)) {
            print("a in list")
        }
        // when 表达式
        when (a) {
            in 1..10 -> print("in 1-10")
            else -> print("other")
        }

        for (i in 1..10) {
            if (i == 3) continue
            if (i > 8) break
            print(i)
        }
    }

    open class InnerClass {
        constructor(name: String, age: Int)
    }

    // 构造方法重写
    class InnerClass2 : InnerClass{
        constructor(name: String, age: Int, sex: String) : super(name, age)
    }

    // 数据复制
    class User(name: String,age: Int){
        fun main(agrs:ArrayList<String>){
            val jack=User(name="张三",age=11)
        }
    }

}
