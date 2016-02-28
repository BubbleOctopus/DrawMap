package app.com.cn.drawmap.presenter;

import com.baidu.mapapi.model.LatLng;

import java.util.List;


/**
 * 作者：ArMn on 2016/2/28
 * 邮箱：859686819@qq.com
 */
public interface MainInteractor {

    void onDraw(List<LatLng> pointers);
}
