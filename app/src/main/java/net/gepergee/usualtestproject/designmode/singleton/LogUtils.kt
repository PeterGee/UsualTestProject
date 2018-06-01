package net.gepergee.usualtestproject.designmode.singleton

import android.util.Log

/**
 * @author geqipeng
 * @date 2018/3/6
 */

class LogUtils private constructor() {

    fun I(msg: String) {
        Log.e("TAG", msg)
    }

    fun D(msg: String) {
        Log.e("TAG", msg)
    }

    fun W(msg: String) {
        Log.e("TAG", msg)
    }

    fun E(msg: String) {
        Log.e("TAG", msg)
    }

    companion object {
        private var mLogUtils: LogUtils? = null

        fun getmLogUtils(): LogUtils {
                    if (mLogUtils == null) {
                        mLogUtils = LogUtils()
                    }
            return mLogUtils as LogUtils
        }
    }

}
