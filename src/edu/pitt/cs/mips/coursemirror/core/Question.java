package edu.pitt.cs.mips.coursemirror.core;

public class Question {

//  private Location location;
  private String qid;
  private String question_main;
  private String question_sub;
  private String URL;
  private String time;
  private String objectId;


  public String getQID() {
      return qid;
  }

  public void setQID(final String qid) {
      this.qid = qid;
  }

  public String getQuestionMain() {
      return question_main;
  }

  public void setQuestionMain(final String Title) {
      this.question_main = Title;
  }   
  public String getQuestionSub() {
      return question_sub;
  }

  public void setQuestionSub(final String Title) {
      this.question_sub = Title;
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