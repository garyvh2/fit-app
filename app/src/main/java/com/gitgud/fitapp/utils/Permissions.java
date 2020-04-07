package com.gitgud.fitapp.utils;

import android.app.Activity;
import android.content.pm.PackageManager;

import androidx.core.content.ContextCompat;

import java.util.List;

public class Permissions {
    static public boolean arePermissionsGranted(Activity activity, List<String> permissions) {
        return permissions.stream().allMatch((permission) -> isPermissionGranted(activity, permission));
    }

    static public boolean isPermissionGranted(Activity activity, String permission) {
        return ContextCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED;
    }
}