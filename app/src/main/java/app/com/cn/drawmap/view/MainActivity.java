package app.com.cn.drawmap.view;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.map.TextOptions;
import com.baidu.mapapi.model.LatLng;

import java.util.ArrayList;
import java.util.List;

import app.com.cn.drawmap.R;
import app.com.cn.drawmap.dao.Pointer;
import app.com.cn.drawmap.presenter.MainInteractor;
import app.com.cn.drawmap.presenter.MainPresenterImpl;

public class MainActivity extends Activity implements MainInteractor {

    private MapView mMapView = null;
    private BaiduMap mBaiduMap = null;
    private LocationClient mLocClient = null;

    private boolean isFirstLoc = true;// 是否首次定位

    private MyLocationListenner myListener = new MyLocationListenner();

    private List<LatLng> mPointers = new ArrayList<>();

    private MainPresenterImpl mMainPresenterImpl = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mMapView = (MapView) findViewById(R.id.bmapView);

        mBaiduMap = mMapView.getMap();

        // 开启定位图层
        mBaiduMap.setMyLocationEnabled(true);
        // 定位初始化
        mLocClient = new LocationClient(this);
        mLocClient.registerLocationListener(myListener);
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true);// 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setScanSpan(1000);
        mLocClient.setLocOption(option);
        mLocClient.start();

        // 设置排版和图标
        mBaiduMap.setMyLocationConfigeration(new MyLocationConfiguration(
                MyLocationConfiguration.LocationMode.NORMAL, true, null));

        mMainPresenterImpl = new MainPresenterImpl(this);
    }

    @Override
    protected void onResume() {
        mMapView.onResume();
        super.onResume();
    }

    @Override
    protected void onPause() {
        mMapView.onPause();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        // 清空缓存数据
        mPointers.clear();
        mPointers = null;

        mLocClient.unRegisterLocationListener(myListener);
        mLocClient.stop();
        // 关闭定位图层
        mBaiduMap.setMyLocationEnabled(false);
        mMapView.onDestroy();
        mMapView = null;
        super.onDestroy();
    }

    @Override
    public void onDraw(List<LatLng> pointers) {
        if (pointers == null || mMapView == null)
            return;

        OverlayOptions ooPolyline = new PolylineOptions().width(10)
                .color(0xAAFF0000).points(pointers);
        mBaiduMap.addOverlay(ooPolyline);
    }

    /**
     * 定位SDK监听函数
     */
    public class MyLocationListenner implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            // map view 销毁后不在处理新接收的位置
            if (location == null || mMapView == null)
                return;

            MyLocationData locData = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                            // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(100).latitude(location.getLatitude())
                    .longitude(location.getLongitude()).build();
            mBaiduMap.setMyLocationData(locData);

            LatLng ll = new LatLng(location.getLatitude(),location.getLongitude());
            if (isFirstLoc) {
                isFirstLoc = false;
                MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(ll);
                mBaiduMap.animateMapStatus(u);

                //-- start label
                LatLng llText = new LatLng(location.getLatitude(),location.getLongitude());
                OverlayOptions ooText = new TextOptions().bgColor(0xAAFFFF00)
                        .fontSize(48).fontColor(0xFFFF00FF).text("ArMn SDK").rotate(-30)
                        .position(llText);
                mBaiduMap.addOverlay(ooText);
            }

            mMainPresenterImpl.onLocationChanged(ll);
        }

        public void onReceivePoi(BDLocation poiLocation) {
        }
    }
}
