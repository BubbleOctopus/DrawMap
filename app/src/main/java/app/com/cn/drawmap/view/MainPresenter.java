package app.com.cn.drawmap.view;

import com.baidu.mapapi.model.LatLng;

/**
 * 作者：ArMn on 2016/2/28
 * 邮箱：859686819@qq.com
 */
public interface MainPresenter {

    void onLocationChanged(LatLng pointer);

    void onResume();

    void onPause();

    void onDestroy();
}
