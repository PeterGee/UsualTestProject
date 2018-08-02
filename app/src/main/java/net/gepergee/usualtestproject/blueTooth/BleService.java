package net.gepergee.usualtestproject.blueTooth;

import android.annotation.SuppressLint;
import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothProfile;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.text.TextUtils;
import net.gepergee.usualtestproject.blueTooth.Utils.LogUtils;
import net.gepergee.usualtestproject.blueTooth.Utils.TimerUtil;
import java.lang.reflect.Method;
import java.util.Set;
import java.util.UUID;

/**
 * 蓝牙Service
 */
@SuppressLint("NewApi")
public class BleService extends Service {
    private final static String TAG = BleService.class.getSimpleName();
    public static final UUID TX_POWER_UUID = UUID.fromString("00001804-0000-1000-8000-00805f9b34fb");
    public static final UUID TX_POWER_LEVEL_UUID = UUID.fromString("00002a07-0000-1000-8000-00805f9b34fb");
    public static final UUID CCCD = UUID.fromString("00002902-0000-1000-8000-00805f9b34fb");
    public static final UUID FIRMWARE_REVISON_UUID = UUID.fromString("00002a26-0000-1000-8000-00805f9b34fb");
    public static final UUID DIS_UUID = UUID.fromString("0000180a-0000-1000-8000-00805f9b34fb");
    public static final UUID RX_SERVICE_UUID = UUID.fromString("0000fff0-0000-1000-8000-00805f9b34fb");
    public static final UUID RX_CHAR_UUID = UUID.fromString("0000fff1-0000-1000-8000-00805f9b34fb");
    public static final UUID TX_CHAR_UUID = UUID.fromString("0000fff4-0000-1000-8000-00805f9b34fb");
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
    private final IBinder mBinder = new LocalBinder();
    private BluetoothAdapter mBluetoothAdapter;
    private String mBluetoothDeviceAddress;
    private BluetoothGatt mBluetoothGatt=BlueToothActivity.mBluetoothGatt;
    private BluetoothDevice bleDevice;
    private int mConnectionState = STATE_DISCONNECTED;
    private static final int STATE_DISCONNECTED = 0;
    private static final int STATE_CONNECTED = 2;
    private String bleName;
    private TimerUtil scanTimerUtil;

    public class LocalBinder extends Binder {
        public BleService getService() {
            return BleService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        registerReceiver();
        return mBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
//        registerReceiver();
        disconnect();
        return super.onUnbind(intent);
    }

    /**
     * 暂停计时器
     */
    private void stopScanTimeOut() {
        if (scanTimerUtil != null) {
            scanTimerUtil.stop();
        }
    }

    private void startScanTimeOut() {
        scanTimerUtil = new TimerUtil(20 * 1000, new TimerUtil.TimerCallBack() {
            @Override
            public void onFulfill() {
                mBluetoothAdapter.stopLeScan(mLeScanCallback);
                broadcastUpdate(ACTION_SCAN_TIME_OUT);
            }
        });
        scanTimerUtil.start();
    }


    /**
     * 创建蓝牙适配器
     *
     * @return true  successful.
     */
    public BluetoothAdapter createBluetoothAdapter() {
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        return mBluetoothAdapter;
    }

    /**
     * 扫描并且连接
     *
     * @param bleName 蓝牙名
     */
    public void connectScan(String bleName) {
        if (mBluetoothAdapter == null || TextUtils.isEmpty(bleName)) {
            return;
        }
        //先关闭扫描
        stopScanTimeOut();
        mBluetoothAdapter.stopLeScan(mLeScanCallback);
        this.bleName = bleName;
        LogUtils.e("指定蓝牙名："+bleName);
        startScanTimeOut();
        mBluetoothAdapter.startLeScan(mLeScanCallback);
        return;
    }


    private BluetoothAdapter.LeScanCallback mLeScanCallback = new BluetoothAdapter.LeScanCallback() {
        public void onLeScan(BluetoothDevice device, int rssi, byte[] scanRecord) {
            LogUtils.e("搜索到蓝牙：" + device.getName() + "___" + device.getAddress());
            if (bleName.equals(device.getName())) {
                //停止扫描
                stopScanTimeOut();
                mBluetoothAdapter.stopLeScan(mLeScanCallback);
                //连接
                connect(device.getAddress());
            }
        }
    };


    /**
     * 连接指定蓝牙
     *
     * @param address 蓝牙的地址
     */
    public boolean connect(final String address) {
        if (mBluetoothAdapter == null || TextUtils.isEmpty(address)) {
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
        bleDevice = mBluetoothAdapter.getRemoteDevice(address);
        if (bleDevice == null) {
            return false;
        }
        mBluetoothGatt = bleDevice.connectGatt(this, false, mGattCallback);
        //重新将blueToothActivity的Gatt赋值mBlueToothGatt
        mBluetoothGatt=BlueToothActivity.mBluetoothGatt;
        mBluetoothDeviceAddress = address;
        return true;
    }

    /**
     * 初始化通道
     *
     * @return
     */
    public void enableTXNotification() {
        if (mBluetoothGatt == null) {
            LogUtils.e("初始化通道错误");
            Log("初始化通道错误:mBluetoothGatt=====null");
            broadcastUpdate(DEVICE_DOES_NOT_SUPPORT_UART);
            disconnect();
            return;
        }
        BluetoothGattService RxService = mBluetoothGatt.getService(RX_SERVICE_UUID);
        LogUtils.e("RxService:"+RxService);
        if (RxService == null) {
            Log("初始化通道错误:BluetoothGattService=====null");
            broadcastUpdate(DEVICE_DOES_NOT_SUPPORT_UART);
            disconnect();
            return;
        }
        BluetoothGattCharacteristic TxChar = RxService.getCharacteristic(TX_CHAR_UUID);
        if (TxChar == null) {
            Log("初始化通道错误:BluetoothGattCharacteristic=====null");
            broadcastUpdate(DEVICE_DOES_NOT_SUPPORT_UART);
            disconnect();
            return;
        }
        mBluetoothGatt.setCharacteristicNotification(TxChar, true);
        BluetoothGattDescriptor descriptor = TxChar.getDescriptor(CCCD);
        descriptor.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
        mBluetoothGatt.writeDescriptor(descriptor);
        broadcastUpdate(ENABLE_NOTIFICATION_SUCCES);
    }

    /**
     * 断开BluetoothGatt连接
     */
    public void disconnect() {
        if (mBluetoothAdapter == null || mBluetoothGatt == null) {
            LogUtils.w("BluetoothAdapter not initialized");
            return;
        }
        mBluetoothGatt.disconnect();
        mBluetoothDeviceAddress = null;
        mBluetoothGatt.close();
        mBluetoothGatt = null;
    }

    /**
     * 关闭BluetoothGatt
     */
    public void close() {
        if (mBluetoothGatt == null) {
            return;
        }
        Log("mBluetoothGatt closed");
    }

    /**
     * 读取数据
     */
    public void readCharacteristic(BluetoothGattCharacteristic characteristic) {
        if (mBluetoothAdapter == null || mBluetoothGatt == null) {
            LogUtils.w("读取数据:mBluetoothAdapter===null");
            return;
        }
        mBluetoothGatt.readCharacteristic(characteristic);
    }


    /**
     * 传输数据
     */
    public boolean writeRXCharacteristic(byte[] value) {
        mBluetoothGatt=BlueToothActivity.mBluetoothGatt;
        BluetoothGattService RxService = mBluetoothGatt.getService(RX_SERVICE_UUID);
        if (RxService == null) {
            Log("传输数据：BluetoothGattService==null");
            broadcastUpdate(DEVICE_DOES_NOT_SUPPORT_UART);
            return false;
        }
        BluetoothGattCharacteristic RxChar = RxService.getCharacteristic(RX_CHAR_UUID);
        if (RxChar == null) {
            Log("传输数据：BluetoothGattCharacteristic==null");
            broadcastUpdate(DEVICE_DOES_NOT_SUPPORT_UART);
            return false;
        }
        byte[] encryptValut = value;
        RxChar.setValue(encryptValut);
        return mBluetoothGatt.writeCharacteristic(RxChar);
    }


    /**
     * 发送广播
     **/
    private void broadcastUpdate(final String action) {
        final Intent intent = new Intent(action);
        getApplicationContext().sendBroadcast(intent);
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
        getApplicationContext().sendBroadcast(intent);
    }

    /**
     * Log工具
     */
    private void Log(String msg) {
        LogUtils.e(msg);
    }


    /**
     * 清除蓝牙缓存（反射）
     **/
    public boolean refreshDeviceCache() {
        if (mBluetoothGatt != null) {
            try {
                BluetoothGatt localBluetoothGatt = mBluetoothGatt;
                Method localMethod = localBluetoothGatt.getClass().getMethod("refresh", new Class[0]);
                if (localMethod != null) {
                    boolean bool = ((Boolean) localMethod.invoke(localBluetoothGatt, new Object[0])).booleanValue();
                    return bool;
                }
            } catch (Exception e) {
                Log("清楚蓝牙缓存异常：" + e.getMessage());
            }
        }
        return false;
    }

    /**
     * 清除蓝牙缓存（反射）
     **/
    private void unpairDevice() {
        Set<BluetoothDevice> devices = mBluetoothAdapter.getBondedDevices();
        if (devices.size() > 0) {
            for (BluetoothDevice bluetoothDevice : devices) {
                try {
                    Method m = BluetoothDevice.class
                            .getMethod("removeBond", (Class[]) null);
                    m.invoke(bluetoothDevice, (Object[]) null);
                } catch (Exception e) {
                    Log("清除蓝牙缓存异常：" + e.getMessage());
                }
            }

        }
    }

    Handler handler = new Handler();

    /**
     * 蓝牙连接交互回调
     */
    @SuppressLint("NewApi")
    private final BluetoothGattCallback mGattCallback = new BluetoothGattCallback() {
        @SuppressLint("NewApi")
        @Override
        public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
            if (newState == BluetoothProfile.STATE_CONNECTED) {
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
                Log("蓝牙连接断开");
                disconnect();
                mConnectionState = STATE_DISCONNECTED;
                broadcastUpdate(ACTION_GATT_DISCONNECTED);
                unpairDevice();
            }
        }

        @Override
        public void onServicesDiscovered(BluetoothGatt gatt, int status) {
            if (status == BluetoothGatt.GATT_SUCCESS) {
                //发现服务，建立通道
                enableTXNotification();
            } else {
                Log("onServicesDiscovered 返回状态:" + status);
            }
        }

        //接收数据
        @Override
        public void onCharacteristicRead(BluetoothGatt gatt,
                                         BluetoothGattCharacteristic characteristic,
                                         int status) {
            if (status == BluetoothGatt.GATT_SUCCESS) {
                broadcastUpdate(ACTION_DATA_AVAILABLE, characteristic);
            }
        }

        //接收数据
        @Override
        public void onCharacteristicChanged(BluetoothGatt gatt,
                                            BluetoothGattCharacteristic characteristic) {
            broadcastUpdate(ACTION_DATA_AVAILABLE, characteristic);
        }
    };


    /**
     * 广播
     **/
    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = intent
                        .getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                if (bleName.equals(device.getName())) {
                    connect(device.getAddress());
//                    Toast.makeText(getApplicationContext(), R.string.scanning_bluetooth_began_connect, Toast.LENGTH_SHORT).show();
                    //停止扫描
                    mBluetoothAdapter.cancelDiscovery();
                }
            }
        }
    };

    /**
     * 注册广播
     */
    private void registerReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BluetoothDevice.ACTION_FOUND);//扫描到蓝牙设备
        intentFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);//扫描完成
        registerReceiver(broadcastReceiver, intentFilter);
    }

    public BluetoothGatt getBluetoothGatt() {
        LogUtils.e("BlueToothActivity.mBluetoothGatt:="+BlueToothActivity.mBluetoothGatt);
        return BlueToothActivity.mBluetoothGatt;
//        return mBluetoothGatt;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadcastReceiver);
    }
}
