package io.swen90007sm2.app.blo.impl;

import io.swen90007sm2.alpheccaboot.exception.RequestException;
import io.swen90007sm2.app.common.constant.ResourceConstant;
import io.swen90007sm2.app.common.constant.StatusCodeEnume;
import io.swen90007sm2.app.common.util.FileUtil;
import io.swen90007sm2.app.common.util.ResourceUtil;
import io.swen90007sm2.app.common.util.TimeUtil;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.UUID;

/**
 *
 * business logic for img uploading
 * @author 996Worker
 * @author  https://www.runoob.com/servlet/servlet-file-uploading.html
 * @description
 * @create 2022-08-10 16:49
 */
public class PhotoBlo {

    private static final Logger LOGGER = LoggerFactory.getLogger(PhotoBlo.class);

    /**
     * handles file (image) upload
     * @param request servlet request
     * @return photoId/fileName
     */
    public String doPhotoUpload(HttpServletRequest request) {
        try {
            // check is multipartContent uploading
            if (!ServletFileUpload.isMultipartContent(request)) {
                throw new RequestException(
                        StatusCodeEnume.INVALID_UPLOAD_TYPE.getMessage(),
                        StatusCodeEnume.INVALID_UPLOAD_TYPE.getCode()
                );
            }

            // config upload params
            DiskFileItemFactory factory = new DiskFileItemFactory();
            // if exceeds, will be saved in OS's tmp folder
            factory.setSizeThreshold(ResourceConstant.UPLOAD_MEMORY_THRESHOLD);
            factory.setRepository(new File(System.getProperty("java.io.tmpdir")));

            ServletFileUpload upload = new ServletFileUpload(factory);
            upload.setSizeMax(ResourceConstant.UPLOAD_MAX_REQUEST_SIZE);
            // config upload size limit
            upload.setFileSizeMax(ResourceConstant.UPLOAD_MAX_FILE_SIZE);

            // construct upload dir
            String uploadPath = request.getServletContext().getRealPath("./")
                    + File.pathSeparator
                    + ResourceConstant.UPLOAD_DIR_PATH;

            // create upload dir if needed
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }
            List<FileItem> formItems = upload.parseRequest(request);

            if (formItems != null && formItems.size() > 0) {
                for (FileItem item : formItems) {
                    // format postfix
                    String format=item.getName().substring(item.getName().indexOf("."), item.getName().length());
                    String fileName = UUID.randomUUID().toString().replaceAll("-", "")+format;

                    String filePath = uploadPath + File.separator + fileName;
                    File fileToStore = new File(filePath);

                    item.write(fileToStore);

                    LOGGER.info("uploaded a file, name: [{}] at [{}]", fileName, filePath);

                    // TODO store file meta-data in db
                    // photo id is the fileName

                    return fileName;
                }
            }
        } catch (FileUploadException e) {
            LOGGER.error("File Upload Exception: ", e);
            throw new RuntimeException(e);
        } catch (Exception e) {
            LOGGER.error("File Upload Runtime Exception: ", e);
            throw new RuntimeException(e);
        }

        return null;
    }

    /**
     * download photos from upload dir
     * @param photoId photoId
     * @param response response obj
     */
    public void doPhotoDownload(String photoId, HttpServletResponse response) {
        response.setContentType(FileUtil.getMimeType(photoId));
        // set Content-Disposition
        response.setHeader("Content-Disposition", "attachment;filename="+photoId);
        // TODO handle file download
    }

    /**
     * download file/photo from resources dir
     * @param fileName fileName
     * @param response response obj
     */
    public void doDownloadFromResources(String fileName, HttpResponse response) {

    }
}