package com.mli.mpro.location.models;

import com.mli.mpro.utils.Utility;

public class Location {
    private String lat;
    private String lng;

    public Location() {

    }

    public Location(String lat, String lng) {
	super();
	this.lat = lat;
	this.lng = lng;
    }

    @Override
    public String toString() {
        if(Utility.isCalledFromLogs(Thread.currentThread())){
            return Utility.toString(this);
        }
	return "Location [lat=" + lat + ", lng=" + lng + "]";
    }
}