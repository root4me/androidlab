package me.root4.whereami;

import java.util.Date;

/**
 * Created by harish on 4/2/16.
 *
 * Short cut for getter and setter Alt + insert
 */
public class Location {

    private double lat;
    private double lng;
    private long captureDate;
    private long createDate;
    private boolean synced;

    public Location(){

    }

    public Location(double lat, double lng, long captureDate){
        this.lat = lat;
        this.lng = lng;
        this.captureDate = captureDate;
        this.createDate = new Date().getTime();
        this.synced = false;
    }


    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public long getCaptureDate() {
        return captureDate;
    }

    public void setCaptureDate(long captureDate) {
        this.captureDate = captureDate;
    }

    public long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(long createDate) {
        this.createDate = createDate;
    }

    public boolean isSynced() {
        return synced;
    }

    public void setSynced(boolean synced) {
        this.synced = synced;
    }

}
