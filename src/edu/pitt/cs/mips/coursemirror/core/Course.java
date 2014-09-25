package edu.pitt.cs.mips.coursemirror.core;

import java.io.Serializable;

public class Course implements Serializable {

//  private Location location;
  private String cid;
  private String Title;
  private String URL;
  private String time;
  private String objectId;


  public String getCID() {
      return cid;
  }

  public void setCID(final String cid) {
      this.cid = cid;
  }

  public String getTitle() {
      return Title;
  }

  public void setTitle(final String Title) {
      this.Title = Title;
  }    
  
  

  public String getURL() {
      return URL;
  }

  public void setURL(final String URL) {
      this.URL = URL;
  }    
  
  public String geTime() {
      return time;
  }

  public void setTime(final String time) {
      this.time = time;
  }    
  
  public String getObjectId() {
      return objectId;
  }

  public void setObjectId(final String objectId) {
      this.objectId = objectId;
  }
  
}