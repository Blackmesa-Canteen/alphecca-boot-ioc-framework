package io.swen90007sm2.app.controller.shared.api;

import io.swen90007sm2.alpheccaboot.annotation.filter.AppliesFilter;
import io.swen90007sm2.alpheccaboot.annotation.ioc.AutoInjected;
import io.swen90007sm2.alpheccaboot.annotation.mvc.Controller;
import io.swen90007sm2.alpheccaboot.annotation.mvc.HandlesRequest;
import io.swen90007sm2.alpheccaboot.annotation.mvc.PathVariable;
import io.swen90007sm2.alpheccaboot.annotation.mvc.QueryParam;
import io.swen90007sm2.alpheccaboot.bean.R;
import io.swen90007sm2.alpheccaboot.common.constant.RequestMethod;
import io.swen90007sm2.alpheccaboot.exception.RequestException;
import io.swen90007sm2.app.blo.IPhotoBlo;
import io.swen90007sm2.app.common.constant.StatusCodeEnume;
import io.swen90007sm2.app.security.bean.AuthToken;
import io.swen90007sm2.app.security.constant.SecurityConstant;
import io.swen90007sm2.app.security.util.SecurityUtil;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller(path = "/api/shared/photo")
public class PhotoController {

    @AutoInjected
    private IPhotoBlo photoBlo;

    /**
     * only login user can upload the file
     * @param request
     * @param response
     * @return
     */
    @HandlesRequest(path = "/", method = RequestMethod.POST)
    @AppliesFilter(filterNames = {SecurityConstant.LOGIN_USER})
    public R handleUploadPhoto(HttpServletRequest request, HttpServletResponse response) {
        // parse incoming token
        String tokenStr = request.getHeader(SecurityConstant.JWT_HEADER_NAME);
        AuthToken incomingTokenObj = SecurityUtil.parseAuthTokenString(tokenStr);
        String userId = incomingTokenObj.getUserId();
        String photoId = photoBlo.doPhotoUpload(request, userId);
        return R.ok().setData(photoId);
    }

    /**
     * handles photo download, everyone can download the file with the photoId
     * @param photoId photo Id/Name
     * @param request servletRequest
     * @param response servletResponse
     */
    @HandlesRequest(path = "/", method = RequestMethod.GET)
    public void handleDownloadPhoto(@QueryParam(value = "photoId") String photoId, HttpServletRequest request, HttpServletResponse response) {
        photoBlo.doPhotoDownload(photoId, request, response);
    }
}
