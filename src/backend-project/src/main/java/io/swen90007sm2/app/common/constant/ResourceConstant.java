package io.swen90007sm2.app.common.constant;

public interface ResourceConstant {

    String IMG_DIR_PATH = "images";

    // uploads
    String UPLOAD_DIR_PATH = "upload";
    int UPLOAD_MEMORY_THRESHOLD = 1024 * 1024 * 5; // 5MB
    int UPLOAD_MAX_FILE_SIZE = 1024 * 1024 * 50; // 50MB
    int UPLOAD_MAX_REQUEST_SIZE = 1024 * 1024 * 60; // 60MB

    String UPLOAD_PHOTO_URL_PREFIX = "/api/shared/photo";

}
