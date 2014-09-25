package edu.pitt.cs.mips.coursemirror.core;

import java.io.Serializable;

public class Reflection implements Serializable{

//    private Location location;
    private String cid;
    private int lecture_number;
    private String user;
    private String q1;
    private String q2;
    private String q3;    
    private String objectId;


    public String getcid() {
        return cid;
    }

    public void setcid(final String cid) {
        this.cid = cid;
    }

    public int getlecture_number() {
        return lecture_number;
    }

    public void setlecture_number(final int lecture_number) {
        this.lecture_number = lecture_number;
    }    
    
    public String getuser() {
        return user;
    }

    public void setuser(final String user) {
        this.user = user;
    }



    
    public String getq1() {
        return q1;
    }

    public void setq1(final String q1) {
        this.q1 = q1;
    }

    public String getq2() {
        return q2;
    }

    public void setq2(final String q2) {
        this.q2 = q2;
    }

    public String getq3() {
        return q3;
    }

    public void setq3(final String q3) {
        this.q3 = q3;
    }

    
    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(final String objectId) {
        this.objectId = objectId;
    }
}
