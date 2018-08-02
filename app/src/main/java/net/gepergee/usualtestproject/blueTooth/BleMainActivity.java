package net.gepergee.usualtestproject.blueTooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothProfile;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import net.gepergee.usualtestproject.R;

import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;

import static android.bluetooth.BluetoothAdapter.STATE_CONNECTED;
import static android.bluetooth.BluetoothAdapter.STATE_DISCONNECTED;

/**
 *
 */
public class BleMainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String EXTRA_DATA ="EXTRA_DATA";
    private static final UUID UUID_HEART_RATE_MEASUREMENT =UUID.fromString("0000fff4-0000-1000-8000-00805f9b34fb");
    private BluetoothAdapter mBlueToothAdapter;
    //存储蓝牙列表
    ArrayList<String> mArrayAdapter=new ArrayList<>();
    private BroadcastReceiver mReceiver;
    public static final String TAG = "TAG";
    BluetoothGatt mBluetoothGatt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_ble);
        initView();
        BluetoothManager bluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        mBlueToothAdapter = bluetoothManager.getAdapter();

        Set<BluetoothDevice> pairedDevices = mBlueToothAdapter.getBondedDevices();
        if (pairedDevices.size() > 0) {
            for (BluetoothDevice device : pairedDevices) {
                mArrayAdapter.add(device.getName() + "\n" + device.getAddress());
                Log.e("tag","获取到的蓝牙设备列表："+mArrayAdapter.size());
            }
        }
        //接收扫描到蓝牙的广播
        mReceiver = new BroadcastReceiver() {
             public void onReceive(Context context, Intent intent) {
                 String action = intent.getAction();
                 if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                     BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                     Log.e("tag","222222222222222222");
                     mArrayAdapter.add(device.getName() + "\n" + device.getAddress());
                 }
             }
         };
        /**
         * 注册广播
         */
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(mReceiver, filter);
       //发现蓝牙设备
        Intent discoverableIntent = new
                Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
        startActivity(discoverableIntent);


    }

    private void initView() {
        Button btnCheck= (Button) findViewById(R.id.btn_checkBlueTooth);
        Button btnOpen= (Button) findViewById(R.id.btn_openBlueTooth);
        Button btnClose= (Button) findViewById(R.id.btn_CloseBlueTooth);
        Button btnScan= (Button) findViewById(R.id.btn_ScanBlueTooth);
        btnCheck.setOnClickListener(this);
        btnOpen.setOnClickListener(this);
        btnClose.setOnClickListener(this);
        btnScan.setOnClickListener(this);

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_checkBlueTooth:
                checkBlueTooth();
                break;
            case R.id.btn_openBlueTooth:
                openBlueTooth();
                break;
            case R.id.btn_CloseBlueTooth:
                closeBlueTooth();
                break;
            case R.id.btn_ScanBlueTooth:
                scanBlueTooth();
                break;
        }

    }
    //关闭蓝牙
    private void closeBlueTooth() {
        mBlueToothAdapter.disable();

    }
    //开启蓝牙
    private void openBlueTooth() {
        /**
         * 请求开启蓝牙
         */
        if (mBlueToothAdapter == null || !mBlueToothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, 1);
        }
    }

    /**
     * 检查是否支持蓝牙设备
     */
    private void checkBlueTooth() {
        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
            Toast.makeText(this, "设备不支持蓝牙4.0", Toast.LENGTH_SHORT).show();
            finish();
        }else{
            Toast.makeText(this,"可以打开蓝牙设备",Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 扫描蓝牙设备
     */
    private void scanBlueTooth() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mBlueToothAdapter.startLeScan(new BluetoothAdapter.LeScanCallback() {
                    @Override
                    public void onLeScan(BluetoothDevice device, int rssi, byte[] scanRecord) {
                        Log.e("tag","扫描到的蓝牙设备名称："+device.getName());
                    }
                });
            }
        },200);

    }

Handler mHandler=new Handler();
    private final BluetoothGattCallback mGattCallback = new BluetoothGattCallback() {
        public static final String ACTION_DATA_AVAILABLE ="ACTION_DATA_AVAILABLE";
        public static final String ACTION_GATT_SERVICES_DISCOVERED ="ACTION_GATT_SERVICES_DISCOVERED";
        public static final String ACTION_GATT_DISCONNECTED ="ACTION_GATT_DISCONNECTED";
        public static final String ACTION_GATT_CONNECTED = "ACTION_GATT_CONNECTED";

        @Override
        public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
            String intentAction;
            int mConnectionState;
            //收到设备notify值 （设备上报值）
            if (newState == BluetoothProfile.STATE_CONNECTED) {
                intentAction = ACTION_GATT_CONNECTED;
                mConnectionState = STATE_CONNECTED;
                broadcastUpdate(intentAction);
                Log.i(TAG, "Connected to GATT server.");
                Log.i(TAG, "Attempting to start service discovery:" +
                        mBluetoothGatt.discoverServices());

            } else if (newState == BluetoothProfile.STATE_DISCONNECTED) {
                intentAction = ACTION_GATT_DISCONNECTED;
                mConnectionState = STATE_DISCONNECTED;
                Log.i(TAG, "Disconnected from GATT server.");
                broadcastUpdate(intentAction);
            }
        }

        @Override
        public void onServicesDiscovered(BluetoothGatt gatt, int status) {
            if (status == BluetoothGatt.GATT_SUCCESS) {
                broadcastUpdate(ACTION_GATT_SERVICES_DISCOVERED);
            } else {
                Log.w(TAG, "onServicesDiscovered received: " + status);
                System.out.println("onServicesDiscovered received: " + status);
            }
        }

        @Override
        public void onCharacteristicRead(BluetoothGatt gatt,
                                         BluetoothGattCharacteristic characteristic,
                                         int status) {
            //读取到值，在这里读数据
            if (status == BluetoothGatt.GATT_SUCCESS) {
                broadcastUpdate(ACTION_DATA_AVAILABLE, characteristic);
            }
        }

        @Override
        public void onCharacteristicChanged(BluetoothGatt gatt,
                                            BluetoothGattCharacteristic characteristic) {
            broadcastUpdate(ACTION_DATA_AVAILABLE, characteristic);
        }
    };

    private void broadcastUpdate(final String action) {
        final Intent intent = new Intent(action);
        sendBroadcast(intent);
    }

    private void broadcastUpdate(final String action,
                                 final BluetoothGattCharacteristic characteristic) {
        final Intent intent = new Intent(action);

        if (UUID_HEART_RATE_MEASUREMENT.equals(characteristic.getUuid())) {
            int flag = characteristic.getProperties();
            int format = -1;
            if ((flag & 0x01) != 0) {
                format = BluetoothGattCharacteristic.FORMAT_UINT16;
                Log.d(TAG, "Heart rate format UINT16.");
            } else {
                format = BluetoothGattCharacteristic.FORMAT_UINT8;
                Log.d(TAG, "Heart rate format UINT8.");
            }
            final int heartRate = characteristic.getIntValue(format, 1);
            Log.d(TAG, String.format("Received heart rate: %d", heartRate));
            intent.putExtra(EXTRA_DATA, String.valueOf(heartRate));
        } else {
            //这里读取到数据
            final byte[] data = characteristic.getValue();
            for (int i = 0; i < data.length; i++) {
                System.out.println("data......" + data[i]);
            }
            if (data != null && data.length > 0) {
                final StringBuilder stringBuilder = new StringBuilder(data.length);
                for(byte byteChar : data)
                    //以十六进制的形式输出
                    stringBuilder.append(String.format("%02X ", byteChar));
                intent.putExtra(EXTRA_DATA, new String(data));
            }
        }
        sendBroadcast(intent);
    }
    //读数据
    public void readCharacteristic(BluetoothGattCharacteristic characteristic) {

        if (mBlueToothAdapter == null || mBluetoothGatt == null) {
            Log.w(TAG, "BluetoothAdapter not initialized");
            return;
        }
        mBluetoothGatt.readCharacteristic(characteristic);
    }
    //写数据
    public void writeCharacteristic(BluetoothGattCharacteristic characteristic) {
        if (mBlueToothAdapter == null || mBluetoothGatt == null) {
            Log.w(TAG, "BluetoothAdapter not initialized");
            return;
        }
        mBluetoothGatt.writeCharacteristic(characteristic);
    }
    @Override
    protected void onDestroy() {
        unregisterReceiver(mReceiver);
        super.onDestroy();
    }

}
