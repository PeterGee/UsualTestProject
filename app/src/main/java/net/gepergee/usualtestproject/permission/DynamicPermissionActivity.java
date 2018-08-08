package net.gepergee.usualtestproject.permission;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import net.gepergee.usualtestproject.R;

import java.io.File;

/**
 * 动态申请权限
 *
 * @author petergee
 * @date 2018/8/8
 */
public class DynamicPermissionActivity extends AppCompatActivity {
    private static final int CODE_CAMERA_REQUEST = 0x01;
    private static final int PERMISSION_REQUEST_CODE = 10000;
    private String pai = getSdcardPath() + "pic.jpg";
    private String[] permissions = new String[]{
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA
    };
    private ImageView imgShow;
    private boolean isAllGranted = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dynamic_permission);
        imgShow = findViewById(R.id.img_show);
        findViewById(R.id.btn_request).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                request();
            }
        });
    }

    /**
     * 申请权限
     */
    private void request() {
        // 检查是否具有响应权限
        isAllGranted = checkPermissionAllGranted(permissions);
        if (isAllGranted) {
            takePhoto();
            return;
        }
        // 请求权限
        ActivityCompat.requestPermissions(this, permissions, PERMISSION_REQUEST_CODE);
    }

    /**
     * 检查权限是否已申请
     */
    private boolean checkPermissionAllGranted(String[] permissions) {
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    /**
     * 拍照
     */
    private void takePhoto() {
        Intent intentFromCapture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intentFromCapture.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(pai)));
        startActivityForResult(intentFromCapture, CODE_CAMERA_REQUEST);
    }

    /**
     * 申请权限结果
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        isAllGranted=true;
        if (requestCode == PERMISSION_REQUEST_CODE) {
            // 判断是否已授权
            for (int grant : grantResults) {
                if (grant != PackageManager.PERMISSION_GRANTED) {
                    isAllGranted = false;
                    break;
                }
            }
            if (isAllGranted) {
                takePhoto();
            } else {
                // 弹出未授权提示
                showDialog();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case CODE_CAMERA_REQUEST:
                if (resultCode == RESULT_OK) {
                    File file = new File(pai);
                    Glide.with(this).load(Uri.fromFile(file)).into(imgShow);
                }
                break;
            default:
                break;
        }
    }

    /**
     * 打开设置
     */
    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("拍照需要访问 “相机” 和 “外部存储器”，请到 “应用信息 -> 权限” 中授予！");
        builder.setPositiveButton("去手动授权", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                intent.addCategory(Intent.CATEGORY_DEFAULT);
                intent.setData(Uri.parse("package:" + getPackageName()));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                startActivity(intent);
            }
        });
        builder.setNegativeButton("取消", null);
        builder.show();
    }

    /**
     * 获取sd卡目录
     *
     * @return
     */
    public static String getSdcardPath() {
        String path = Environment.getExternalStorageDirectory() + File.separator + "petergee" + File.separator;
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        return path;
    }
}
