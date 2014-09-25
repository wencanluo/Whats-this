
package edu.pitt.cs.mips.coursemirror.core;

import java.util.List;

import edu.pitt.cs.mips.coursemirror.util.Constants;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * User service for connecting the the REST API and
 * getting the users.
 */
public interface CourseService {

    @GET(Constants.Http.URL_COURSES_FRAG)
    CourseWrapper getCourses();
}
