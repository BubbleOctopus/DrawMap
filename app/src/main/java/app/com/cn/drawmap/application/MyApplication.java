package app.com.cn.drawmap.application;

import android.app.Application;

import com.baidu.mapapi.SDKInitializer;

/**
 * 作者：ArMn on 2016/2/28
 * 邮箱：859686819@qq.com
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        // 在使用 SDK 各组间之前初始化 context 信息，传入 ApplicationContext
        SDKInitializer.initialize(this);
    }
}
