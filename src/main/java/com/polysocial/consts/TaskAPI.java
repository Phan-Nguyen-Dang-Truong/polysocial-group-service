package com.polysocial.consts;

import org.springframework.stereotype.Component;

@Component
public class TaskAPI {
    
    public static final String API_TASK_FILE_CREATE = "/api/task/file/create";

    public static final String API_TASK_EX_UPDATE = "/api/task/file/update";

    public static final String API_GET_FILE_UPLOAD_GROUP = "/api/task/file/get-file";

    public static final String API_DELETE_FILE_UPLOAD = "/api/task/file/delete-file";

    public static final String API_CREATE_MARK = "/api/task/create-mark";

    public static final String API_GET_ALL_TASK_FILE = "/api/task/get-all-task-file";

    public static final String API_GET_ALL_TASK_EX = "/api/task/get-all-task-ex";
}
