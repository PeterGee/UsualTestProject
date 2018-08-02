package net.gepergee.usualtestproject.blueTooth.Utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;

import net.gepergee.usualtestproject.blueTooth.BleService;

import java.lang.ref.SoftReference;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Administrator
 *         蓝牙与锁通信协议
 */
public class BtLockTalk {
    //crc32标记
    public static String encryptCRC(String primary) {
        byte[] flag = primary.getBytes();
        byte[] result = new byte[flag.length + 1];
        for (int i = 0; i < flag.length; i++) {
            result[i] = flag[i];
        }
        int index = flag.length;
        for (int j = 0; j < index; j++) {
            result[index] = (byte) (result[index] ^ result[j]);
        }
        return new String(result);
    }

    //crc32校验
    public static boolean decryptCRC(String str) {
        byte[] packet = str.getBytes();
        byte crc = 0;
        for (int i = 0; i < packet.length - 1; i++) {
            crc = (byte) (crc ^ packet[i]);
        }
        if (crc == packet[packet.length - 1]) {
            return true;
        }
        return false;
    }

    //当前时间字符串
    @SuppressLint("SimpleDateFormat")
    public static String nowTimeTimeStr() {
        return new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
    }

    //验证消息
    public static byte[] checkLock(String imei) {
        LogUtils.e("imei:"+imei);
        byte[] im = imeiToByte(imei);
        byte[] result = new byte[im.length + 4];
        result[0] = (byte) 0xAB;
        result[1] = (byte) 0xAB;
        result[2] = (byte) 0x80;
        result[3] = (byte) im.length;

        for (int i = 0; i < im.length; i++) {
            result[i + 4] = im[i];
        }
        return result;
    }


    //开锁命令
    public static byte[] openLock(String imei) {
        byte[] im = imeiToByte(imei);
        byte[] result = new byte[im.length + 4];
        result[0] = (byte) 0xAB;
        result[1] = (byte) 0xAB;
        result[2] = (byte) 0x81;
        result[3] = (byte) im.length;

        for (int i = 0; i < im.length; i++) {
            result[i + 4] = im[i];
        }
        return result;
    }

    //关锁命令
    public static byte[] closeLock(String imei) {
        byte[] im = imeiToByte(imei);
        byte[] result = new byte[im.length + 4];
        result[0] = (byte) 0xAB;
        result[1] = (byte) 0xAB;
        result[2] = (byte) 0x82;
        result[3] = (byte) im.length;

        for (int i = 0; i < im.length; i++) {
            result[i + 4] = im[i];
        }
        return result;
    }

    //寻车命令
    public static byte[] findLock(String imei, int type) {
        byte[] im = imeiToByte(imei);
        byte[] result = new byte[im.length + 5];
        result[0] = (byte) 0xAB;
        result[1] = (byte) 0xAB;
        result[2] = (byte) 0x83;
        result[3] = (byte) im.length;

        for (int i = 0; i < im.length; i++) {
            result[i + 4] = im[i];
        }
        result[result.length - 1] = (byte) type;
        return result;
    }

    //播放音频文件
    public static byte[] playMedia(String imei, int type) {
        byte[] im = imeiToByte(imei);
        byte[] result = new byte[im.length + 5];
        result[0] = (byte) 0xAB;
        result[1] = (byte) 0xAB;
        result[2] = (byte) 0x84;
        result[3] = (byte) im.length;

        for (int i = 0; i < im.length; i++) {
            result[i + 4] = im[i];
        }
        result[result.length - 1] = (byte) type;
        return result;
    }


    //开锁盖
    public static byte[] powerOn(String imei) {
        byte[] im = imeiToByte(imei);
        byte[] result = new byte[im.length + 4];
        result[0] = (byte) 0xAB;
        result[1] = (byte) 0xAB;
        result[2] = (byte) 0x42;
        result[3] = (byte) im.length;
        for (int i = 0; i < im.length; i++) {
            result[i + 4] = im[i];
        }
        return result;
    }

    //关锁盖
    public static byte[] PowerOff(String imei) {
        byte[] im = imeiToByte(imei);
        byte[] result = new byte[im.length + 4];
        result[0] = (byte) 0xAB;
        result[1] = (byte) 0xAB;
        result[2] = (byte) 0x4C;
        result[3] = (byte) im.length;
        for (int i = 0; i < im.length; i++) {
            result[i + 4] = im[i];
        }
        return result;
    }

    //比较锁程序版本
    public static byte[] getLockVersion(String imei) {
        byte[] im = imeiToByte(imei);
        byte[] result = new byte[im.length + 4];
        result[0] = (byte) 0xAB;
        result[1] = (byte) 0xAB;
        result[2] = (byte) 0x85;
        result[3] = (byte) im.length;

        for (int i = 0; i < im.length; i++) {
            result[i + 4] = im[i];
        }
        return result;
    }

    public static byte[] imeiToByte(String imei) {
        String imeiHx = Long.toHexString(Long.valueOf(imei));
        int len = imeiHx.length();
        if (len <= 16) {
            for (int i = 0; i < 16 - len; i++) {
                imeiHx = "0" + imeiHx;
            }
            return hex2Byte(imeiHx.toLowerCase());
        }
        return null;
    }

    //  十六进制的字符串转换成byte数组
    public static byte[] hex2Byte(String hex) {
        if (hex == null) {
            return new byte[]{};
        }
        String digital = "0123456789abcdef";
        char[] hex2char = hex.toCharArray();

        byte[] bytes = new byte[hex.length() / 2];
        int temp;
        for (int i = 0; i < bytes.length; i++) {
            // 其实和上面的函数是一样的 multiple 16 就是右移4位 这样就成了高4位了
            // 然后和低四位相加， 相当于 位操作"|"
            //相加后的数字 进行 位 "&" 操作 防止负数的自动扩展. {0xff byte最大表示数}
            temp = digital.indexOf(hex2char[2 * i]) * 16;
            temp += digital.indexOf(hex2char[2 * i + 1]);
            bytes[bytes.length - i - 1] = (byte) (temp & 0xff);
        }
        return bytes;
    }


    //向蓝牙发送命令
    public static void bleSendMessage(byte[] cmd, BleService mService, boolean encode) {
        if (cmd == null) {
            return;
        }
        byte[] msg;
        if (cmd.length <= 16) {
            try {
                new Thread().sleep(150);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            msg = cmd;
            if (encode) {
                mService.writeRXCharacteristic(AesUtils.encrypt(msg));
            } else {
                LogUtils.e("mService:"+mService);
                mService.writeRXCharacteristic(msg);
            }
        } else {
            for (int i = 0; i < cmd.length; i += 16) {
                try {
                    new Thread().sleep(150);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if ((i + 16) >= cmd.length) {
                    byte[] cut = new byte[cmd.length - i];
                    for (int j = 0; j < cut.length; j++) {
                        cut[j] = cmd[i + j];
                    }
                    mService.writeRXCharacteristic(AesUtils.encrypt(cut));
                } else {
                    byte[] cut = new byte[16];
                    for (int j = 0; j < 16; j++) {
                        cut[j] = cmd[i + j];
                    }
                    mService.writeRXCharacteristic(AesUtils.encrypt(cut));
                }
            }
        }
    }

    /***
     * 判断蓝牙是否开启
     *
     * @return true 已开启 false 关闭
     */
    public static boolean isEnabled(Activity activity, BluetoothAdapter bluetoothAdapter) {
        //软饮用，防止内存泄漏
        SoftReference<Activity> activitySoftReference = new SoftReference<>(activity);
        // 确保蓝牙在设备上可以开启
        if (bluetoothAdapter == null || !bluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            activitySoftReference.get().startActivity(enableBtIntent);
            return false;
        }
        return true;
    }
}
