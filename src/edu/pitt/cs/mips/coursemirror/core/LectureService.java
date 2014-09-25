package edu.pitt.cs.mips.coursemirror.core;

import edu.pitt.cs.mips.coursemirror.util.Constants;
import retrofit.http.GET;
import retrofit.http.Query;


/**
 * Interface for defining the lecture service to communicate with Parse.com
 */
public interface LectureService {

    @GET(Constants.Http.URL_LECTURES_FRAG)
    LectureWrapper getLectures();

    @GET(Constants.Http.URL_LECTURES_FRAG)
    LectureWrapper getCourseLectures(@Query("where") String course);    
    
    @GET(Constants.Http.URL_LECTURES_FRAG)
    LectureWrapper getCourseLectures(@Query("where") String course, @Query("order") String number); 
}
