package com.example.forum.util;

import android.app.Activity;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class PermissionUtil {
    public static boolean checkPermission(Activity activity, String permission, int requestCode) {
        return checkPermission(activity, new String[]{permission}, requestCode);
    }

    public static boolean checkPermission(Activity activity, String[] permissions, int requestCode) {
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(activity, permission)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(activity, permissions, requestCode);
                return false;
            }
        }
        return true;
    }

    public static boolean checkGranted(int[] grantResults) {
        if (grantResults == null) return false;
        for (int grantResult : grantResults) {
            if (grantResult != PackageManager.PERMISSION_GRANTED)
                return false;
        }
        return true;
    }
}
