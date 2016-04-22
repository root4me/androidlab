package me.root4.whereami;

import java.util.Date;

/**
 * Created by harish on 4/2/16.
 *
 * Short cut for getter and setter Alt + insert
 */
public class Location {



    private int id;
    private double lat;
    private double lng;
    private long captureDate;
    private int synced;

    public Location(){

    }

    public Location(double lat, double lng, long captureDate){
        this.lat = lat;
        this.lng = lng;
        this.captureDate = captureDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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


    public String getFormattedCaptureDate() {
        return Utils.getDateTime(captureDate);
    }

    public void setCaptureDate(long captureDate) {
        this.captureDate = captureDate;
    }

    public int getSynced() {
        return synced;
    }

    public void setSynced(int synced) {
        this.synced = synced;
    }


}
