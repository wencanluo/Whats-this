package edu.pitt.cs.mips.coursemirror.core;

import java.util.List;

import edu.pitt.cs.mips.coursemirror.util.Constants;
import retrofit.http.GET;

public interface ReflectionService {

    @GET(Constants.Http.URL_REFLECTIONS_FRAG)
    ReflectionWrapper getReflections();
}
