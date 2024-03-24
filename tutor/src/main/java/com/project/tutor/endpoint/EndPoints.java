package com.project.tutor.endpoint;

public class EndPoints {
    public static final String [] PUBLIC_ENDPOINTS = {
            //AUTH
            "/auth/**",
            // TUTOR
            "/api/tutor/add","/api/tutor/list","/api/tutor/search-and-sort","/api/tutor/*",
            //SUBJECT
            "/api/subject/list","/api/subject/search-and-sort",
            // TEACHING
            "/api/teaching/list" , "/api/teaching/*",
            // FEEDBACK
            "/api/feedback/list"
    };



    public static final String [] ADMIN_ENDPOINTS = {
            // USER AND ROLE
            "/api/user/list",
            "/api/user/search-and-sort",
            "/api/user/*",
            "/api/user/delete/*",
            "/api/user/update/*",
            // ROLE
            "/api/role/**",
            // SUBJECT
            "/api/subject/add", "/api/subject/delete/*", "/api/subject/update/*",
            // TUTOR
            "/api/tutor/delete/*", "/api/tutor/update/*", "/api/tutor/*","/api/tutor/list/approved",
            // TEACHING
            "/api/teaching/add", "/api/teaching/delete/*", "/api/teaching/update/*",
            // PAYMENT
            "/api/payment/list", "/api/payment/add", "/api/payment/delete/*", "/api/payment/update/*",
            //BOOKING
            "/api/booking/list", "/api/booking/delete/*"
    };

    public static final String [] USER_ADMIN_ENPOINTS = {
            // SUBJECT
            "/api/subject/list",
            // TUTOR
            "/api/tutor/list",
            // BOOKING
            "/api/booking/*","/api/booking/update/*"
    };

    public static final String [] USER_ENDPOINTS = {
            // BOOKING
            "/api/booking/add",
            // FEEDBACK
            "/api/feedback/**","/api/feedback/delete/*",
            // USER
            "/api/user/profile"

    };
    public static final String [] USER_POST_ENDPOINT = {
            // USER AND ROLE
            "/api/user/**", "/api/role/**",
            // SUBJECT
            "/api/subject/add", "/api/subject/delete/*", "/api/subject/update/*",
            // TUTOR
            "/api/tutor/delete/*", "/api/tutor/update/*", "/api/tutor/*","/api/tutor/list/approved",
            // TEACHING
            "/api/teaching/add", "/api/teaching/delete/*", "/api/teaching/update/*",
            // PAYMENT
            "/api/payment/list", "/api/payment/add", "/api/payment/delete/*", "/api/payment/update/*",
            //BOOKING
            "/api/booking/list", "/api/booking/delete/*"
    };
}
