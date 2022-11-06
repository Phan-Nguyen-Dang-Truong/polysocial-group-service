package com.polysocial.consts;

import org.springframework.stereotype.Component;

@Component
public class GroupAPI {

    public static final String API_GET_ALL_GROUP = "/api/get/all";
    
    public static final String API_GET_ONE_GROUP = "/api/get/class";

    public static final String API_CREATE_GROUP = "/api/create-group";

    public static final String API_UPDATE_GROUP = "/api/update-group";

    public static final String API_GET_TEACHER = "/api/get-teacher";

    public static final String API_GET_ALL_STUDENT = "/api/get/all-student";

    public static final String API_GET_STUDENT = "/api/get-student";

    public static final String API_CREATE_STUDENT = "/api/create-student";

    public static final String API_FIND_GROUP = "/api/find-group";

    public static final String API_DELETE_GROUP = "/api/delete-group";
  
    public static final String API_REMOVE_STUDENT = "/api/remove-student";

    public static final String API_CREATE_GROUP_EXCEL = "/api/create-file";

    public static final String API_GET_ALL_GROUP_STUDENT = "/api/get-all/group/student";
    
    public static final String API_GET_ALL_GROUP_TEACHER = "/api/get-all/group/teacher";

    public static final String API_GET_ALL_GROUP_FALSE = "/api/get-all/group/false";
}
