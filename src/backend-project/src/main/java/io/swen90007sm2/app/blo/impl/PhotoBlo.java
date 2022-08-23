package io.swen90007sm2.app.blo.impl;

import io.swen90007sm2.alpheccaboot.annotation.ioc.AutoInjected;
import io.swen90007sm2.alpheccaboot.annotation.mvc.Blo;
import io.swen90007sm2.alpheccaboot.exception.RequestException;
import io.swen90007sm2.app.blo.IPhotoBlo;
import io.swen90007sm2.app.common.constant.ResourceConstant;
import io.swen90007sm2.app.common.constant.StatusCodeEnume;
import io.swen90007sm2.app.dao.IPhotoDao;
import io.swen90007sm2.app.db.constant.DbConstant;
import io.swen90007sm2.app.model.entity.Photo;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;
import java.util.UUID;

/**
 *
 * business logic for img uploading
 * @author 996Worker
 * @author  https://www.runoob.com/servlet/servlet-file-uploading.html
 * @create 2022-08-10 16:49
 */

@Blo
public class PhotoBlo implements IPhotoBlo {

    @AutoInjected
    IPhotoDao photoDao;

    private static final Logger LOGGER = LoggerFactory.getLogger(PhotoBlo.class);

    /**
     * handles file (image) upload
     * @param request servlet request
     * @return photoId/fileName
     */
    @Override
    public String doPhotoUpload(HttpServletRequest request, String userId) {
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
            // use (new File("")).getAbsolutePath() to get application root path
            String uploadPath = (new File("")).getAbsolutePath()
                    + File.separator
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



                    // photo_id is the fileName
                    Photo photo = new Photo();
                    photo.setId(RandomStringUtils.randomAlphanumeric(DbConstant.PRIMARY_KEY_LENGTH));
                    photo.setPhotoId(fileName);
                    photo.setDescription("New Photo.");
                    photo.setPhotoUrl(ResourceConstant.UPLOAD_PHOTO_URL_PREFIX + "?photoId=" + fileName);
                    photo.setUserId(userId);
                    photoDao.insertOne(photo);

                    LOGGER.info("uploaded a file, name: [{}] at [{}]", fileName, filePath);
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
    @Override
    public void doPhotoDownload(String photoId, HttpServletRequest request, HttpServletResponse response) {

        try {
            // CORS
            /* 允许跨域的主机地址 */
            response.setHeader("Access-Control-Allow-Origin", "*");
            /* 允许跨域的请求方法GET, POST, HEAD 等 */
            response.setHeader("Access-Control-Allow-Methods", "*");
            /* 重新预检验跨域的缓存时间 (s) */
            response.setHeader("Access-Control-Max-Age", "4200");
            /* 允许跨域的请求头 */
            response.setHeader("Access-Control-Allow-Headers", "*");
            /* 是否携带cookie */
            response.setHeader("Access-Control-Allow-Credentials", "true");

            ServletContext servletContext = request.getServletContext();
            response.setContentType(servletContext.getMimeType(photoId));
            // set Content-Disposition
            response.setHeader("Content-Disposition", "attachment;filename="+photoId);

            // uploadPath
            String uploadPath = (new File("")).getAbsolutePath()
                    + File.separator
                    + ResourceConstant.UPLOAD_DIR_PATH;
            String fullFileName = uploadPath + File.separator + photoId;

            // init streams for file IO
            InputStream inputStream = new FileInputStream(fullFileName);
            ServletOutputStream outStream = response.getOutputStream();

            // file buffer
            int len=-1;
            byte[] b=new byte[2048];
            while((len=inputStream .read(b))!=-1){
                outStream.write(b,0,len);
            }

            //close stream
            inputStream .close();
            outStream.close();

            LOGGER.info("File downloaded: [{}]", fullFileName);
        } catch (IOException e) {
            LOGGER.error("Download error: ", e);
            throw new RuntimeException(e);
        }

    }
}