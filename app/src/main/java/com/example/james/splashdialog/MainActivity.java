package com.example.james.splashdialog;

import android.app.Activity;
import android.app.Dialog;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends Activity {

    private class StateSaver{
        private boolean showSplasheScreen = true;
    }

    private static final int MAX_SPLASH_SECONDS = 10;
    private Dialog splashDialog;

    /** Called when the activity is first created. */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*
        getLastNonConfigurationInstance 方法的好处：
        1、当Activity曾经通过某个资源得到一些图片或者信息，那么当再次恢复后，无需重新通过原始资源地址获取，可以快速的加载整个Activity状态信息。
        2、当Activity包含有许多线程时，在变化后依然可以持有原有线程，无需通过重新创建进程恢复原有状态。
        3、当Activity包含某些Connection Instance时，同样可以在整个变化过程中保持连接状态。
         */
        StateSaver data = (StateSaver) getLastNonConfigurationInstance();
        if(data != null){
            if(data.showSplasheScreen){
                showSplashScreen();
            }
            setContentView(R.layout.activity_main);
        }
        else{
            showSplashScreen();
            setContentView(R.layout.activity_main);
        }

    }

    @Override
    public Object onRetainNonConfigurationInstance() {
        //save important data into this object
        StateSaver data = new StateSaver();

        if(splashDialog != null){
            data.showSplasheScreen = true;
            removeSplashScreen();
        }

        return data;
    }

    private void showSplashScreen(){
        splashDialog = new Dialog(this);
        splashDialog.setContentView(R.layout.splashscreen);
        splashDialog.setCancelable(false);
        splashDialog.show();

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                removeSplashScreen();
            }
        }, MAX_SPLASH_SECONDS * 1000);
    }

    private void removeSplashScreen(){
        if(splashDialog != null){
            splashDialog.dismiss();
            splashDialog = null;
        }
    }
}
