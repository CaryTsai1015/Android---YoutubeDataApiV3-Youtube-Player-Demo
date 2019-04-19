package com.example.tsaicary.youtubeplayer;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import java.net.URL;

/**
 * Created by tsaicary on 2018/4/3.
 */

public
class MingjingHuopaiBaseActivity extends AppCompatActivity {

    private ProgressDialog mProgressDialog;
    private URL url = null;
    private int permissionRequestCode = 88;
    private PermissionCallback permissionRunnable ;
    private InterstitialAd interstitial;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * setToolbar                            ·
     *
     */

    protected void setToolbar(Toolbar toolbar, String title) {
        // toolbar.setNavigationIcon(navigationIconId);
        toolbar.setTitle(title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    /**
     * CaryTsai 2018/04/03
     * showToast
     */

    protected void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }

    /**
     * CaryTsai 2018/02/22
     * showProgress
     */

    protected void showProgress(String msg ,String msgsee) {
        try {
            if (mProgressDialog != null)
                dismissProgress();

            mProgressDialog = ProgressDialog.show(this,msgsee, msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void dismissProgress() {
        try {
            if (mProgressDialog != null) {
                mProgressDialog.dismiss();
                mProgressDialog = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public interface PermissionCallback{
        void hasPermission();
        void noPermission();
    }
    /**
     * Android M运行时权限请求封装
     * @param permissionDes 权限描述
     * @param runnable 请求权限回调
     * @param permissions 请求的权限（数组类型），直接从Manifest中读取相应的值，比如Manifest.permission.WRITE_CONTACTS
     */





    public void performCodeWithPermission(@NonNull String permissionDes, PermissionCallback runnable, @NonNull String... permissions){
        if(permissions == null || permissions.length == 0)return;
//        this.permissionrequestCode = requestCode;
        this.permissionRunnable = runnable;
        if((Build.VERSION.SDK_INT < Build.VERSION_CODES.M) || checkPermissionGranted(permissions)){
            if(permissionRunnable!=null){
                permissionRunnable.hasPermission();
                permissionRunnable = null;
            }
        }else{
            //permission has not been granted.
            requestPermission(permissionDes,permissionRequestCode,permissions);
        }

    }
    private boolean checkPermissionGranted(String[] permissions){
        boolean flag = true;
        for(String p:permissions){
            if(ActivityCompat.checkSelfPermission(this, p) != PackageManager.PERMISSION_GRANTED){
                flag = false;
                break;
            }
        }
        return flag;
    }
    private void requestPermission(String permissionDes,final int requestCode,final String[] permissions){
        if(shouldShowRequestPermissionRationale(permissions)){

            //如果用户之前拒绝过此权限，再提示一次准备授权相关权限
            new AlertDialog.Builder(this)
                    .setTitle("提示")
                    .setMessage(permissionDes)
                    .setPositiveButton("關閉", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(MingjingHuopaiBaseActivity.this, permissions, requestCode);
                        }
                    }).show();

        }else{
            // Contact permissions have not been granted yet. Request them directly.
            ActivityCompat.requestPermissions(MingjingHuopaiBaseActivity.this, permissions, requestCode);
        }
    }
    private boolean shouldShowRequestPermissionRationale(String[] permissions){
        boolean flag = false;
        for(String p:permissions){
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,p)){
                flag = true;
                break;
            }
        }
        return flag;
    }

    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if(requestCode == permissionRequestCode){
            if(verifyPermissions(grantResults)){
                if(permissionRunnable!=null) {
                    permissionRunnable.hasPermission();
                    permissionRunnable = null;
                }
            }else{
                showToast("需允許權限才可使用此APP");
                if(permissionRunnable!=null) {
                    permissionRunnable.noPermission();
                    permissionRunnable = null;
                }
            }
        }else{
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }

    }
    public boolean verifyPermissions(int[] grantResults) {
        // At least one result must be checked.
        if(grantResults.length < 1){
            return false;
        }

        // Verify that each required permission has been granted, otherwise return false.
        for (int result : grantResults) {
            if (result != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }
    //********************** END Android M Permission ****************************************



    public void setInterstitial(){


        interstitial = new InterstitialAd(this);
        interstitial.setAdUnitId("ca-app-pub-6428195540030903/4149440022");

        // Create ad request.
        AdRequest adRequest = new AdRequest.Builder().build();

        // Begin loading your interstitial.
        interstitial.loadAd(adRequest);

    }


    // Invoke displayInterstitial() when you are ready to display an interstitial.
    public
    void displayInterstitial() {
        if (interstitial.isLoaded()) {
            interstitial.show();
        }
    }

}
