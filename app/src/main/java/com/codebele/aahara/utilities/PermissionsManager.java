package com.codebele.aahara.utilities;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

public class PermissionsManager {
    private static final int REQUEST_PERMISSIONS = 1337;
    private Activity activity;
    private Fragment fragment;
    private PermissionsResultCallback callback;

    public PermissionsManager(Activity activity, PermissionsResultCallback callback) {
        this.activity = activity;
        this.callback = callback;
    }

    public PermissionsManager(Fragment fragment, PermissionsResultCallback callback) {
        this.fragment = fragment;
        this.callback = callback;
    }

    public static boolean isPermissionAvailable(Context context, String permission) {
        return ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED;
    }

    public void requestPermissions(String... permissions) {
        List<String> availablePermissions = new ArrayList<>();
        List<String> unavailablePermissions = new ArrayList<>();
        for (String permission : permissions) {
            if (activity != null) {
                if (ContextCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED) {
                    unavailablePermissions.add(permission);
                } else {
                    availablePermissions.add(permission);
                }
            } else if (fragment != null) {
                if (ContextCompat.checkSelfPermission(fragment.getContext(), permission) != PackageManager.PERMISSION_GRANTED) {
                    unavailablePermissions.add(permission);
                } else {
                    availablePermissions.add(permission);
                }
            }
        }


        if (unavailablePermissions.size() > 0)

        {
            String[] permissionsArr = unavailablePermissions.toArray(new String[unavailablePermissions.size()]);
            if (activity != null) {
                ActivityCompat.requestPermissions(activity, permissionsArr, REQUEST_PERMISSIONS);
            } else if (fragment != null) {
                fragment.requestPermissions(permissionsArr, REQUEST_PERMISSIONS);
            }
        }

        if (availablePermissions.size() > 0) {
            callback.onPermissionsAvailable(availablePermissions);
        }

    }

    public void requestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_PERMISSIONS) {
            List<String> grantedPermissions = new ArrayList<>();
            List<String> deniedPermissions = new ArrayList<>();
            for (int i = 0; i < permissions.length; i++) {
                String permission = permissions[i];
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    grantedPermissions.add(permission);
                } else {
                    deniedPermissions.add(permission);
                }
            }

            callback.onPermissionsGranted(grantedPermissions);
            if (deniedPermissions.size() > 0) {
                callback.onPermissionsDenied(deniedPermissions);
            }
        }
    }

    public interface PermissionsResultCallback {
        void onPermissionsGranted(List<String> permissions);

        void onPermissionsDenied(List<String> permissions);

        void onPermissionsAvailable(List<String> permissions);
    }
}
