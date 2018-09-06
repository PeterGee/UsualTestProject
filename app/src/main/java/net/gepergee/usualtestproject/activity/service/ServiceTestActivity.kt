package net.gepergee.usualtestproject.activity.service

import android.app.Activity
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.os.RemoteException
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

import net.gepergee.aidlservice.IMyAidlInterface
import net.gepergee.usualtestproject.R

/**
 * @author petergee
 * @date 2018/9/3
 */
class ServiceTestActivity : Activity(), View.OnClickListener {
    private var isBinded: Boolean = false
    private lateinit var textView:TextView



    private val connection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName, service: IBinder) {
            // MyBinder binder = (MyBinder) service;
            // binder.printWord();
            val stub = IMyAidlInterface.Stub.asInterface(service) ?: return
            try {
                val s = stub.setName("PeterGee")
                Log.e("tag", "name set is $s")
            } catch (e: RemoteException) {
                e.printStackTrace()
            }

        }

        override fun onServiceDisconnected(name: ComponentName) {}
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_service_test)
        initView()
    }

    private fun initView() {
        val btnStartService = findViewById<Button>(R.id.btn_start_service)
        btnStartService.setOnClickListener(this)
        val btnStopService = findViewById<Button>(R.id.btn_stop_service)
        btnStopService.setOnClickListener(this)
        val btnBindService = findViewById<Button>(R.id.btn_bind__service)
        btnBindService.setOnClickListener(this)
        textView=findViewById(R.id.tv_textview)


    }

    override fun onClick(v: View) {
        val intent = Intent()
        // intent.setClass(this, MyService.class);
        when (v.id) {
            R.id.btn_start_service -> startService(intent)
            R.id.btn_stop_service -> stopService(intent)
            R.id.btn_bind__service -> {
                intent.action = "net.gepergee.aidlservice.AidlService"
                intent.setPackage("net.gepergee.aidlservice")
                bindService(intent, connection, Context.BIND_AUTO_CREATE)
                isBinded = true
            }
            else -> {
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (isBinded) {
            unbindService(connection)
        }
    }
}
