
package edu.pitt.cs.mips.coursemirror.core;

import java.util.ArrayList;
import java.util.List;

import android.util.Log;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import edu.pitt.cs.mips.coursemirror.CourseMirrorModule;

import retrofit.RestAdapter;

/**
 * CourseMirror API service
 */
public class CourseMirrorService {

    private RestAdapter restAdapter;

    /**
     * Create CourseMirror service
     * Default CTOR
     */
    public CourseMirrorService() {
    }

    /**
     * Create CourseMirror service
     *
     * @param restAdapter The RestAdapter that allows HTTP Communication.
     */
    public CourseMirrorService(RestAdapter restAdapter) {
        this.restAdapter = restAdapter;
    }

    private CourseService getCourseService() {
        return getRestAdapter().create(CourseService.class);
    }

    private UserService getUserService() {
        return getRestAdapter().create(UserService.class);
    }


    
    private LectureService getLectureService() {
        return getRestAdapter().create(LectureService.class);
    }

    private ReflectionService getReflectionService() {
        return getRestAdapter().create(ReflectionService.class);
    }

    private RestAdapter getRestAdapter() {
        return restAdapter;
    }

    /**
     * Get all CourseMirror Lectures that exists on Parse.com
     */
    public List<Lecture> getLectures() {
        return getLectureService().getLectures().getResults();
    }//get all lectures
    
    public List<Lecture> getCourseLectures(String cid) {
        return getLectureService().getCourseLectures(cid).getResults();
    }    //get lectures with cid
    
    public List<Lecture> getCourseLectures(String cid, String number) {
        return getLectureService().getCourseLectures(cid, number).getResults();
    }    //get lectures with cid and sorted by lecture number

   
    public void getQuestions(){
    	 //final ArrayList<Question> questionList;
    	 ParseQuery<ParseObject> query = ParseQuery.getQuery("Question");
         
          query.addAscendingOrder("QuestionID");
          query.findInBackground(new FindCallback<ParseObject>() {
              public void done(List<ParseObject> scoreList, ParseException e) {
                  if (e == null) {
                      Log.d("score", "Retrieved " + scoreList.size() + " scores");
                      //NUM_ITEMS=scoreList.size()+2;
                      //questionList=new ArrayList<Question>(scoreList.size());
                      ArrayList<Question> questionList = new ArrayList<Question>(scoreList.size()+2);
                     // ArrayList<String> questionDescriptionList = new ArrayList<String>(scoreList.size());
                      ArrayList<String> answerList = new ArrayList<String>(scoreList.size()+2);//-start page, -token page
//                      for(int i=0;i<scoreList.size();i++){
//                      	questionList.add(i,"question");
//                      	questionDescriptionList.add(i,"description");
//                      	//answerList.add(i,"");
//                      }
                      Question que = new Question();
                      questionList.add(que);//placeholder for the start page
                      questionList.add(que);//placeholder for token input
                      for(int i=0;i<scoreList.size();i++){
                      	ParseObject q = scoreList.get(i);
                      	//questionList.add(i, q.getString("QuestionDescription"));
                      	//questionDescriptionList.add(i, q.getString("QuestionDescription"));
                      	Question question = new Question();
                      	question.setQuestionMain(q.getString("QuestionDescription"));
                      	question.setQuestionSub(q.getString("QuestionSubDescription"));
                      	questionList.add(question);
                      	
                      }
                      CourseMirrorModule.SetQuestions(questionList);
                      for(int i=0;i<scoreList.size()+2;i++){
                        	answerList.add(i,"");
                        }
                      CourseMirrorModule.SetAnswers(answerList);
                  } else {
                      Log.d("score", "Error: " + e.getMessage());
                  }
              }
          });
    }
    /**
     * Get all CourseMirror Users that exist on Parse.com
     */
    public List<Course> getCourses() {
        return getCourseService().getCourses().getResults();
    }

    /**
     * Get all CourseMirror Reflections that exists on Parse.com
     */
    public List<Reflection> getReflections() {
       return getReflectionService().getReflections().getResults();
    }

    public User authenticate(String email, String password) {
        return getUserService().authenticate(email, password);
    }
}