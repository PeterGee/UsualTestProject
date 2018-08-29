package net.gepergee.usualtestproject.kotlin

import android.app.Fragment
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import net.gepergee.usualtestproject.R


/**
 * @author petergee
 *
 * @date 2018/8/24
 *
 *
 */
class LazyFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        Log.e("tag","onCreateView")
        return inflater!!.inflate(R.layout.activity_kotlin_test,null)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        Log.e("tag","onViewCreated")
        var isPrepared=true
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Log.e("tag","onActivityCreated")
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        Log.e("tag","setUserVisibleHint")
    }
}