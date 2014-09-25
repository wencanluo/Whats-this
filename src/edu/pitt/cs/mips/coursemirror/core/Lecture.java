package edu.pitt.cs.mips.coursemirror.core;

import java.io.Serializable;

public class Lecture implements Serializable {

    private static final long serialVersionUID = -6641292855569752036L;

    private String Title;
    private String cid;
    private String date;   
    private int number;
    private String objectId;

    
    public String getcid() {
        return cid;
    }

    public void setcid(final String cid) {
        this.cid = cid;
    }
    
    public String getTitle() {
        return Title;
    }

    public void setTitle(final String Title) {
        this.Title = Title;
    }

    public int getnumber() {
        return number;
    }

    public void setnumber(final int number) {
        this.number = number;
    }

    
    public String getdate() {
        return date;
    }

    public void setdate(final String date) {
        this.date = date;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(final String objectId) {
        this.objectId = objectId;
    }
}
