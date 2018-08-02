package net.gepergee.usualtestproject.blueTooth;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothProfile;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import net.gepergee.usualtestproject.R;
import net.gepergee.usualtestproject.blueTooth.Utils.AesUtils;
import net.gepergee.usualtestproject.blueTooth.Utils.BtLockTalk;
import net.gepergee.usualtestproject.blueTooth.Utils.ByteStringHexUtil;
import net.gepergee.usualtestproject.blueTooth.Utils.ByteUtil;
import net.gepergee.usualtestproject.blueTooth.Utils.LogUtils;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author geqipeng
 * @date 2017/6/21
 * @time 14:52
 */

public class BlueToothActivity extends Activity implements AdapterView.OnItemClickListener {

    private BluetoothAdapter mBlueToothAdapter;
    private TextView tvBlueToothAddress;
    private String mBluetoothDeviceAddress;
    private BluetoothDevice bleDevice;
    public static BluetoothGatt mBluetoothGatt;
    private static final int STATE_DISCONNECTED = 0;
    private static final int STATE_CONNECTED = 2;
    private int mConnectionState = STATE_DISCONNECTED;
    //用于保存蓝牙设备.
    private ListView lvDevices;
    private List<String> bluetoothDevices = new ArrayList<>();
    private ArrayAdapter<String> arrayAdapter;
    //发送蓝牙命令 ，子线程
    private ExecutorService fixedThreadPoolBle = Executors.newSingleThreadExecutor();
    private BleService bleService;
    /**
     * 蓝牙连接成功
     */
    public final static String ACTION_GATT_CONNECTED =
            "net.edaibu.adminapp.ACTION_GATT_CONNECTED";
    /**
     * 断开连接
     */
    public final static String ACTION_GATT_DISCONNECTED =
            "net.edaibu.adminapp.ACTION_GATT_DISCONNECTED";
    /**
     * 发现GATT_SERVICES
     */
    public final static String ACTION_GATT_SERVICES_DISCOVERED =
            "net.edaibu.adminapp.ACTION_GATT_SERVICES_DISCOVERED";
    /**
     * 扫描蓝牙超时
     */
    public final static String ACTION_SCAN_TIME_OUT =
            "net.edaibu.adminapp.action_scan_time_out";
    /**
     * 接收到了数据
     */
    public final static String ACTION_DATA_AVAILABLE =
            "net.edaibu.adminapp.ACTION_DATA_AVAILABLE";
    /**
     * 发送接到的数据的KEY
     */
    public final static String EXTRA_DATA =
            "net.edaibu.adminapp.EXTRA_DATA";
    /**
     * 初始化通道错误或者传输数据错误，通道不通
     */
    public final static String DEVICE_DOES_NOT_SUPPORT_UART =
            "net.edaibu.adminapp.DEVICE_DOES_NOT_SUPPORT_UART";
    /**
     * 通道建立成功
     */
    public final static String ENABLE_NOTIFICATION_SUCCES =
            "net.edaibu.adminapp.enablenotificationsucces";
    public static final UUID RX_SERVICE_UUID = UUID.fromString("0000fff0-0000-1000-8000-00805f9b34fb");
    public static final UUID RX_CHAR_UUID = UUID.fromString("0000fff1-0000-1000-8000-00805f9b34fb");
    public static final UUID TX_CHAR_UUID = UUID.fromString("0000fff4-0000-1000-8000-00805f9b34fb");
    public static final UUID CCCD = UUID.fromString("00002902-0000-1000-8000-00805f9b34fb");
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_ble);
        initView();
        initBleService();
        initBlueTooth();
    }

    private void initBlueTooth() {
        checkAppPermission();
        mBlueToothAdapter = BluetoothAdapter.getDefaultAdapter();

        //注册广播监听返回的数据
        IntentFilter myIntentFilter = new IntentFilter();
        myIntentFilter.addAction(ACTION_GATT_CONNECTED);//蓝牙连接成功
        myIntentFilter.addAction(ACTION_GATT_DISCONNECTED);//蓝牙断开
        myIntentFilter.addAction(ACTION_DATA_AVAILABLE);//接收到了数据
        myIntentFilter.addAction(DEVICE_DOES_NOT_SUPPORT_UART);
        myIntentFilter.addAction(ENABLE_NOTIFICATION_SUCCES);
        myIntentFilter.addAction(ACTION_SCAN_TIME_OUT);
        // 注册广播监听
        registerReceiver(mBroadcastReceiver, myIntentFilter);
    }

    private void initView() {
        Button btnCheckBlueTooth = (Button) findViewById(R.id.btn_checkBlueTooth);
        Button btnOpenBlueTooth = (Button) findViewById(R.id.btn_openBlueTooth);
        Button btnCloseBlueTooth = (Button) findViewById(R.id.btn_CloseBlueTooth);
        Button btnScanBlueTooth = (Button) findViewById(R.id.btn_ScanBlueTooth);
        Button btnOpenLock= (Button) findViewById(R.id.tv_open_lock);
        Button btnCloseLock= (Button) findViewById(R.id.tv_close_lock);
        Button btnFlashingLight= (Button) findViewById(R.id.tv_flashing_light);
        Button btnBuzzing= (Button) findViewById(R.id.tv_buzzing);
        tvBlueToothAddress = (TextView) findViewById(R.id.tv_bluetooth_address);
        btnCheckBlueTooth.setOnClickListener(new myListener());
        btnOpenBlueTooth.setOnClickListener(new myListener());
        btnCloseBlueTooth.setOnClickListener(new myListener());
        btnScanBlueTooth.setOnClickListener(new myListener());
        btnOpenLock.setOnClickListener(new myListener());
        btnCloseLock.setOnClickListener(new myListener());
        btnFlashingLight.setOnClickListener(new myListener());
        btnBuzzing.setOnClickListener(new myListener());
        //listview相关
        lvDevices = (ListView) findViewById(R.id.lvDevices);
        arrayAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1,
                bluetoothDevices);
        lvDevices.setAdapter(arrayAdapter);
        lvDevices.setOnItemClickListener(this);

        findViewById(R.id.btn_ScanBlueTooth).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scanBlueTooth(v);
            }
        });

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        mBlueToothAdapter.stopLeScan(mLeScanCallback);
        String s = arrayAdapter.getItem(position);
        //蓝牙客户端发数据第1步,得到设备的地址.
        String address = s.substring(s.indexOf(":") + 1).trim();
        //如果正在搜索,要先取消.
        if (mBlueToothAdapter.isDiscovering()) {
            mBlueToothAdapter.cancelDiscovery();
        }
        //蓝牙客户端发数据第2步,得到设备
        if (bleDevice == null) {
            bleDevice = mBlueToothAdapter.getRemoteDevice(address);
        }
        //连接设备
        connectBlueTooth(address);
        Log.e("tag", "条目点击事件");

    }

    /**
     * 点击事件
     */
    class myListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
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
                    scanBlueTooth(v);
                    break;
                case R.id.tv_open_lock:
                    sigleSendMessage(BtLockTalk.openLock("864287034717437"), true, true);
                    //开锁
                    break;
                case R.id.tv_close_lock:
                    sigleSendMessage(BtLockTalk.closeLock("864287034717437"), true, true);
                    //关锁
                    break;
                case R.id.tv_flashing_light:
                    sigleSendMessage(BtLockTalk.findLock("864287034717437",0x01), true, true);
                    //闪灯
                    break;
                case R.id.tv_buzzing:
                    sigleSendMessage(BtLockTalk.findLock("864287034717437",0x00), true, true);
                    //响铃
                    break;
                default:
                    break;
            }

        }
    }

    /***
     * 初始化蓝牙服务
     */
    private void initBleService() {
        Intent bindIntent = new Intent(this, BleService.class);
        bindService(bindIntent, mServiceConnection, Context.BIND_AUTO_CREATE);
    }

    /***
     * 和bleService建立连接
     */
    private ServiceConnection mServiceConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName className, IBinder rawBinder) {
            bleService = ((BleService.LocalBinder) rawBinder).getService();
            mBlueToothAdapter = bleService.createBluetoothAdapter();
            if (mBlueToothAdapter == null) {
                new AlertDialog.Builder(BlueToothActivity.this).setMessage("未找到蓝牙设备").setPositiveButton("确定", null).show();
            }
        }

        public void onServiceDisconnected(ComponentName classname) {
            bleService = null;
            LogUtils.e("服务断开");
        }
    };

    private void checkBlueTooth() {
        //检测蓝牙
        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
            Toast.makeText(this, "设备不支持蓝牙4.0", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "可以打开蓝牙设备", Toast.LENGTH_SHORT).show();
        }
    }

    private void openBlueTooth() {
        //打开蓝牙
        mBlueToothAdapter.enable();
    }

    private void closeBlueTooth() {
        //关闭蓝牙
        mBlueToothAdapter.disable();
    }

    private void scanBlueTooth(View v) {
        if (mBlueToothAdapter.isDiscovering()) {
            //如果正在扫描就先取消扫描
            mBlueToothAdapter.stopLeScan(mLeScanCallback);
        }
        //开始扫描
        mBlueToothAdapter.startLeScan(mLeScanCallback);
    }

    /**
     * 蓝牙扫描回调
     */
    private BluetoothAdapter.LeScanCallback mLeScanCallback = new BluetoothAdapter.LeScanCallback() {
        public void onLeScan(BluetoothDevice device, int rssi, byte[] scanRecord) {
            Log.e("tag", "搜索到蓝牙：" + device.getName() + "___" + device.getAddress());
            bluetoothDevices.add(device.getName() + ":" + device.getAddress() + "\n");
            arrayAdapter.notifyDataSetChanged();

        }
    };

    /**
     * 连接设备
     *
     * @param address
     */
    private boolean connectBlueTooth(String address) {
        Log.e("tag", "连接蓝牙");
        if (mBlueToothAdapter == null || TextUtils.isEmpty(address)) {
            return false;
        }
        if (mBluetoothDeviceAddress != null && address.equals(mBluetoothDeviceAddress)
                && mBluetoothGatt != null) {
            if (mBluetoothGatt.connect()) {
                return true;
            } else {
                return false;
            }
        }
        bleDevice = mBlueToothAdapter.getRemoteDevice(address);
        if (bleDevice == null) {
            return false;
        }
        mBluetoothGatt = bleDevice.connectGatt(this, false, mGattCallback);
        mBluetoothDeviceAddress = address;
        return true;

    }

    private BluetoothGattCallback mGattCallback = new BluetoothGattCallback() {
        @Override
        //连接状态改变
        public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
            Log.e("tag", "连接状态改变");
            if (newState == BluetoothProfile.STATE_CONNECTED) {
                Log.e("tag", "连接状态");
                mConnectionState = STATE_CONNECTED;
                broadcastUpdate(ACTION_GATT_CONNECTED);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (mBluetoothGatt != null) {
                            mBluetoothGatt.discoverServices();
                        }
                    }
                }, 1000);
            } else if (newState == BluetoothProfile.STATE_DISCONNECTED) {
                Log.e("tag", "断开连接状态");
                disconnect();
                mConnectionState = STATE_DISCONNECTED;
                broadcastUpdate(ACTION_GATT_DISCONNECTED);
                unPairDevice();
            }
        }

        @Override
        //发现服务
        public void onServicesDiscovered(BluetoothGatt gatt, int status) {
            if (status == BluetoothGatt.GATT_SUCCESS) {
                Log.e("tag", "发现服务");
                //初始化通道
                enableTXNotification();
            }
        }

        @Override
        //读数据成功
        public void onCharacteristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
            if (status == BluetoothGatt.GATT_SUCCESS) {
                LogUtils.e("读取数据成功：" + characteristic);
                broadcastUpdate(ACTION_DATA_AVAILABLE, characteristic);
            }
        }

        @Override
        //数据变化
        public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
            LogUtils.e("数据变化：" + characteristic);
            broadcastUpdate(ACTION_DATA_AVAILABLE, characteristic);
        }
    };

    /**
     * 初始化通道
     *
     * @return
     */
    public void enableTXNotification() {
        Log.e("tag", "初始化通道");
        if (mBluetoothGatt == null) {
            Log.e("tag", "初始化通道错误:mBluetoothGatt=====null");
            broadcastUpdate(DEVICE_DOES_NOT_SUPPORT_UART);
            disconnect();
            return;
        }
        BluetoothGattService RxService = mBluetoothGatt.getService(RX_SERVICE_UUID);
        if (RxService == null) {
            Log.e("tag", "初始化通道错误:BluetoothGattService=====null");
            broadcastUpdate(DEVICE_DOES_NOT_SUPPORT_UART);
            disconnect();
            return;
        }
        BluetoothGattCharacteristic TxChar = RxService.getCharacteristic(TX_CHAR_UUID);
        if (TxChar == null) {
            Log.e("tag", "初始化通道错误:BluetoothGattCharacteristic=====null");
            broadcastUpdate(DEVICE_DOES_NOT_SUPPORT_UART);
            disconnect();
            return;
        }
        mBluetoothGatt.setCharacteristicNotification(TxChar, true);
        BluetoothGattDescriptor descriptor = TxChar.getDescriptor(CCCD);
        descriptor.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
        mBluetoothGatt.writeDescriptor(descriptor);
        broadcastUpdate(ENABLE_NOTIFICATION_SUCCES);
        Log.e("tag", "初始化通道成功");
    }

    //广播
    BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @SuppressLint("NewApi")
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            switch (action) {
                case ACTION_GATT_CONNECTED:
                    //蓝牙连接成功
                    LogUtils.e("蓝牙连接成功");
                    break;
                case ACTION_GATT_DISCONNECTED:
                    //蓝牙断开
                    LogUtils.e("蓝牙断开");
                    break;
                case ACTION_DATA_AVAILABLE:
                    //接收到了数据
                    byte[] txValue = intent.getByteArrayExtra(BleService.EXTRA_DATA);
                    LogUtils.e("接收到了数据：" + txValue);
                    LogUtils.e("接收蓝牙数据：" + ByteStringHexUtil.bytesToHexString(txValue));
                    if (txValue != null && txValue.length >= 5) {
                        int wat = ByteUtil.bytesToInt2(new byte[]{0, 0, 0, txValue[2]}, 0);
                        if (wat != 128) {
                            txValue = AesUtils.decrypt(txValue);
                        }
                    }
                    if (txValue == null) {
                        return;
                    }
                    parseReceiveData(txValue);
                    break;
                case ACTION_SCAN_TIME_OUT:
                    //扫描超时
                    LogUtils.e("扫描超时");
                    break;
                case ENABLE_NOTIFICATION_SUCCES:
                    //初始化通道成功
                    LogUtils.e("开始认证");
                    sigleSendMessage(BtLockTalk.checkLock("864287034717437"), false, true);
                    break;
            }
        }
    };

    /**
     * 发送蓝牙数据
     *
     * @param cmd            发送的数据
     * @param enable         是否加密
     * @param isShowProgress 是否显示等待框
     */

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    public void sigleSendMessage(final byte[] cmd, final boolean enable, boolean isShowProgress) {
        // 判断蓝牙是否开启，没开启则申请打开蓝牙
        if (!BtLockTalk.isEnabled(this, mBlueToothAdapter)) {
            return;
        }
        if (bleService.getBluetoothGatt() == null) {
            bleService.connectScan("00000000002");
            LogUtils.e("BlueToothGatt为空");
            return;
        }
        fixedThreadPoolBle.execute(new Runnable() {
            public void run() {
                BtLockTalk.bleSendMessage(cmd, bleService, enable);
            }
        });
    }

    /**
     * 解析接收到的数据
     *
     * @param receiveValue
     */

    private void parseReceiveData(byte[] receiveValue) {
        if (receiveValue.length > 4) {
            byte[] len = new byte[4];
            len[0] = 0;
            len[1] = 0;
            len[2] = 0;
            len[3] = receiveValue[3];
            int length = ByteUtil.bytesToInt2(len, 0);
            if (length <= receiveValue.length) {
                reciveBleDo(receiveValue);
            }
        }
    }

    /**
     * 蓝牙接收数据处理
     *
     * @param reciveStr
     */
    protected void reciveBleDo(byte[] reciveStr) {
        int wat = ByteUtil.bytesToInt2(new byte[]{0, 0, 0, reciveStr[2]}, 0);
        LogUtils.e("接收到蓝牙返回的数据："+wat);
        int flg = 0;
        if (reciveStr.length >= 5) {
            flg = ByteUtil.bytesToInt2(new byte[]{0, 0, 0, reciveStr[4]}, 0);
        }
        switch (wat) {
            case 128://验证
                InputStream inputStream = getApplicationContext().getResources().openRawResource(R.raw.key_table);
                byte[] key_table = AesUtils.decryptFile(inputStream);
                byte[] keys = AesUtils.getKey(reciveStr[4], reciveStr[5], key_table);
                AesUtils.cKey = keys;
                break;
            case 129://开锁
                if (flg == 83) {
                    showShortToast(getString(R.string.open_lock_success));
                } else if (flg == 70) {
                    showShortToast(getString(R.string.open_lock_failure));
                }
                break;
            case 130://关锁
                if (flg == 83) {
                    showShortToast(getString(R.string.close_lock_success));
                } else if (flg == 70) {
                    showShortToast(getString(R.string.close_lock_failure));
                }
                break;
            case 132://音频
                if (flg == 83) {
                    showShortToast(getString(R.string.play_success));
                } else if (flg == 70) {
                    showShortToast(getString(R.string.play_failure));
                }
                break;

            case 66://开电池仓
                if (flg == 83) {
                    showShortToast(getString(R.string.open_battery_success));
                } else if (flg == 70) {
                    showShortToast(getString(R.string.open_battery_failure));
                }
                break;
            case 76://关电池仓
                if (flg == 83) {
                    showShortToast(getString(R.string.close_battery_success));
                } else if (flg == 70) {
                    showShortToast(getString(R.string.close_battery_failure));
                }
                break;
            default:
                break;
        }
    }
    /**
     * 清除蓝牙缓存（反射）
     **/
    private void unPairDevice() {
        Set<BluetoothDevice> devices = mBlueToothAdapter.getBondedDevices();
        if (devices.size() > 0) {
            for (BluetoothDevice bluetoothDevice : devices) {
                try {
                    Method m = BluetoothDevice.class
                            .getMethod("removeBond", (Class[]) null);
                    m.invoke(bluetoothDevice, (Object[]) null);
                } catch (Exception e) {
                    Log.e("tag", "清除蓝牙缓存异常：" + e.getMessage());
                }
            }

        }
    }

    /**
     * 断开BluetoothGatt连接
     */
    public void disconnect() {
        if (mBlueToothAdapter == null || mBluetoothGatt == null) {
            Log.e("tag", "BluetoothAdapter not initialized");
            return;
        }
        mBluetoothGatt.disconnect();
        mBluetoothDeviceAddress = null;
        mBluetoothGatt.close();
        mBluetoothGatt = null;
    }

/**
 * __________________________________________________________________________工具类——————————————————————————————————————————————————————
 */
    /**
     * @功能描述：显示String类型的Toast
     * @@param text
     */
    protected void showShortToast(String text) {
        if (TextUtils.isEmpty(text)) {
            return;
        }
        Toast toast = Toast.makeText(this, text, Toast.LENGTH_SHORT);
        toast.show();
    }

    /**
     * 显示进度条
     *
     * @param msg
     */
    public void showProgress(String msg) {
        if (progressDialog != null && progressDialog.isShowing()) {
            return;
        }
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(msg);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(true);
        progressDialog.show();
    }
    /**
     * 发送广播
     **/
    private void broadcastUpdate(final String action) {
        Intent intent = new Intent();
        intent.setAction(action);
        getApplicationContext().sendBroadcast(intent);
        LogUtils.e("发送广播成功");
    }

    /**
     * 发送广播（携带接受到的值）
     **/
    private void broadcastUpdate(final String action,
                                 final BluetoothGattCharacteristic characteristic) {
        final Intent intent = new Intent(action);
        if (TX_CHAR_UUID.equals(characteristic.getUuid())) {
            intent.putExtra(EXTRA_DATA, characteristic.getValue());
        }
        getApplication().sendBroadcast(intent);
    }

    android.os.Handler handler = new android.os.Handler();

    /**
     * 检查权限
     */
    private void checkAppPermission() {
        boolean isAllGranted = checkPermissionAllGranted(
                new String[]{
                        Manifest.permission.BLUETOOTH,
                        Manifest.permission.BLUETOOTH_ADMIN
                }
        );
        Log.e("tag", "" + isAllGranted);
        if (isAllGranted) {
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.BLUETOOTH, Manifest.permission.BLUETOOTH_ADMIN}, 1);
        }
    }

    private boolean checkPermissionAllGranted(String[] permissions) {
        for (String permission : permissions) {
            if (ActivityCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mBroadcastReceiver);
        unbindService(mServiceConnection);
    }
}
