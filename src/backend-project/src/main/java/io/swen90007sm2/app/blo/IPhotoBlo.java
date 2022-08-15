package io.swen90007sm2.app.blo;

import io.swen90007sm2.alpheccaboot.exception.RequestException;
import io.swen90007sm2.app.common.constant.ResourceConstant;
import io.swen90007sm2.app.common.constant.StatusCodeEnume;
import io.swen90007sm2.app.model.entity.Photo;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;

public interface IPhotoBlo {

    /**
     * upload the photo, and mark the owner of the photo
     * @param request servletRequest
     * @param userId userId that performed that upload
     * @return photo file name as the photo ID
     */
    String doPhotoUpload(HttpServletRequest request, String userId);

    /**
     * download photos from upload dir with the user ID info
     * @param photoId photoId
     * @param response response obj
     */
    void doPhotoDownload(String photoId, HttpServletRequest request, HttpServletResponse response);
}
