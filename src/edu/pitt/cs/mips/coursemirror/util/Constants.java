

package edu.pitt.cs.mips.coursemirror.util;

/**
 * CourseMIRROR constants
 */
public final class Constants {
    private Constants() {}

    public static final class Auth {
        private Auth() {}

        /**
         * Account type id
         */
        public static final String COURSEMIRROR_ACCOUNT_TYPE = "edu.pitt.cs.mips.coursemirror";

        /**
         * Account name
         */
        public static final String COURSEMIRROR_ACCOUNT_NAME = "CourseMIRROR";

        /**
         * Provider id
         */
        public static final String COURSEMIRROR_PROVIDER_AUTHORITY = "edu.pitt.cs.mips.coursemirror.sync";

        /**
         * Auth token type
         */
        public static final String AUTHTOKEN_TYPE = COURSEMIRROR_ACCOUNT_TYPE;
    }

    /**
     * All HTTP is done through a REST style API built for demonstration purposes on Parse.com
     * Thanks to the nice people at Parse for creating such a nice system for us to use for CourseMIRROR!
     */
    public static final class Http {
        private Http() {}


        /**
         * Base URL for all requests
         */
        public static final String URL_BASE = "https://api.parse.com";


        /**
         * Authentication URL
         */
        public static final String URL_AUTH_FRAG = "/1/login";
        public static final String URL_AUTH = URL_BASE + URL_AUTH_FRAG;

        /**
         * List Users URL
         */
        public static final String URL_USERS_FRAG =  "/1/users";
        public static final String URL_USERS = URL_BASE + URL_USERS_FRAG;

        /**
         * List Courses URL
         */
        public static final String URL_COURSES_FRAG = "/1/classes/Course";
        public static final String URL_COURSES = URL_BASE + URL_COURSES_FRAG;
        

        /**
         * List Lectures URL
         */
        public static final String URL_LECTURES_FRAG = "/1/classes/Lecture";
        public static final String URL_LECTURES = URL_BASE + URL_LECTURES_FRAG;


        /**
         * List Checkin's URL
         */
        public static final String URL_REFLECTIONS_FRAG = "/1/classes/Reflection";
        			                                                  
        public static final String URL_REFLECTIONS = URL_BASE + URL_REFLECTIONS_FRAG;

        /**
         * PARAMS for auth
         */
        public static final String PARAM_USERNAME = "username";
        public static final String PARAM_PASSWORD = "password";


        public static final String PARSE_APP_ID = "YMGbDwY7f98JJQax7ErPyQHrjdGHXLKjzGcPCpUC";
        public static final String PARSE_REST_API_KEY = "pEyDlfI7xPIg4qSrDwmvyldKoy1zlMzFLwZTiXAq";
        public static final String PARSE_CLIENT_KEY = "5kwK971smxQ1FqDZy5djicIpZBTDimDET4DIwEt2";
        public static final String HEADER_PARSE_REST_API_KEY = "X-Parse-REST-API-Key";
        public static final String HEADER_PARSE_APP_ID = "X-Parse-Application-Id";
        public static final String CONTENT_TYPE_JSON = "application/json";
        public static final String USERNAME = "username";
        public static final String PASSWORD = "password";
        public static final String SESSION_TOKEN = "sessionToken";


    }


    public static final class Extra {
        private Extra() {}

        public static final String COURSE_ITEM = "course_item";

        public static final String LECTURE_ITEM = "lecture_item";

        public static final String REFLECTION_ITEM = "reflection_item";
        
//        public static final String USER = "user";

    }

    public static final class Intent {
        private Intent() {}

        /**
         * Action prefix for all intents created
         */
        public static final String INTENT_PREFIX = "edu.pitt.cs.mips.coursemirror.";

    }

    public static class Notification {
        private Notification() {
        }

        public static final int TIMER_NOTIFICATION_ID = 1000; // Why 1000? Why not? :)
    }

}


