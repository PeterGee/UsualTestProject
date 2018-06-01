package net.gepergee.usualtestproject.activity

import android.content.ComponentName
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Button

import net.gepergee.usualtestproject.R
import net.gepergee.usualtestproject.designmode.singleton.LogUtils


class MainActivity : AppCompatActivity(), View.OnClickListener {

    private var defaultComponent: ComponentName? = null
    private var componentEleven: ComponentName? = null
    private var componentTwelve: ComponentName? = null
    private var mPackageManager: PackageManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()

    }

    private fun init() {
        LogUtils.getmLogUtils().E("init")
        defaultComponent = componentName
        componentEleven = ComponentName(baseContext, "net.edaibu.testapplication.activity.changeIcon.ElevenActivity")
        componentTwelve = ComponentName(baseContext, "net.edaibu.testapplication.activity.changeIcon.TwelveActivity")
        mPackageManager = applicationContext.packageManager


        val btnJump11 = findViewById<Button>(R.id.btn_jump11)
        val btnJump12 = findViewById<Button>(R.id.btn_jump12)
        btnJump11.setOnClickListener(this)
        btnJump12.setOnClickListener(this)
    }


    override fun onClick(view: View) {
        // startActivity(new Intent(this,JsCallJavaActivity.class));

        when (view.id) {
            R.id.btn_jump11 -> {
                disableComponent(defaultComponent)
                disableComponent(componentTwelve)
                enableComponent(componentEleven)
                Log.e("tag", "双11")
            }
            R.id.btn_jump12 -> {
                disableComponent(defaultComponent)
                disableComponent(componentEleven)
                enableComponent(componentTwelve)
                Log.e("tag", "双12")
            }
            else -> {
            }
        }

    }

    private fun enableComponent(component: ComponentName?) {
        mPackageManager!!.setComponentEnabledSetting(component,
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP)
    }

    private fun disableComponent(Component: ComponentName?) {
        mPackageManager!!.setComponentEnabledSetting(Component,
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                PackageManager.DONT_KILL_APP)
    }

    override fun onPointerCaptureChanged(hasCapture: Boolean) {
        LogUtils.getmLogUtils().D("onPointerCaptureChanged")
    }
}