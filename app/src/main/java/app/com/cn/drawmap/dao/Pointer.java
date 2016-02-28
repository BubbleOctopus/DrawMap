package app.com.cn.drawmap.dao;

/**
 * 作者：ArMn on 2016/2/28
 * 邮箱：859686819@qq.com
 */
public class Pointer {
    private double latitude;
    private double longitude;
    public Pointer(double latitude, double longitude){
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLatitude(){
        return latitude;
    }

    public double getLongitude(){
        return longitude;
    }
}
