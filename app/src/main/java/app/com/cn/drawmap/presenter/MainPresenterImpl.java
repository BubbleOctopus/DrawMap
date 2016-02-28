package app.com.cn.drawmap.presenter;

import android.util.Log;

import com.baidu.mapapi.model.LatLng;

import java.util.ArrayList;
import java.util.List;

import app.com.cn.drawmap.view.MainPresenter;

/**
 * 作者：ArMn on 2016/2/28
 * 邮箱：859686819@qq.com
 */
public class MainPresenterImpl implements MainPresenter{
    private static final String TAG = MainPresenterImpl.class.getSimpleName();
    private List<LatLng> mPointer = null;

    private MainInteractor mMainInteractor = null;

    public MainPresenterImpl(MainInteractor mainInteractor){
        mPointer = new ArrayList<>();
        this.mMainInteractor = mainInteractor;
    }

    @Override
    public void onLocationChanged(LatLng pointer) {
        Log.d(TAG,"onLocationChanged LatLng : " + pointer.latitude + " : " + pointer.longitude);
        mPointer.add(pointer);
        onLocationChangedCallBack();
    }

    public void onLocationChangedCallBack(){
        //--百度支持的最值 2 - 10000
        if(mPointer.size() > 10000)
            mPointer.remove(0);
        if(mPointer.size() >= 2)
            mMainInteractor.onDraw(mPointer);
    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onDestroy() {
        mMainInteractor = null;
        mPointer.clear();
        mPointer = null;
    }
}
