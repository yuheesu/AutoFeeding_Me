package com.example.heejung.autofeeding;

/**
 * Created by uhees on 2017-05-19.
 */

public class AlarmData {
//    public int im;
    public int hh;
    public int mm;
    public int reqCode;

    public AlarmData(int hh, int mm, int reqCode) {
        this.hh = hh;
        this.mm = mm;
        this. reqCode = reqCode;
    }

    @Override
    public String toString() {
        return hh+":"+mm +" and requestCode : "+reqCode;
    }


}