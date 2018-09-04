package net.gepergee.aidlservice

import android.app.Service
import android.content.Intent
import android.os.IBinder

/**
 * @author petergee
 * @date 2018/9/4
 */
class AidlService : Service() {
    private var stub: IMyAidlInterface.Stub = object : IMyAidlInterface.Stub() {
        override fun setName(name: String): String {
            return name
        }

        override fun setAge(age: Int): Int {
            return age
        }
    }


    override fun onBind(intent: Intent): IBinder? {
        return stub
    }
}
