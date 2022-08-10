package io.swen90007sm2.app.controller.shared.api;

import io.swen90007sm2.alpheccaboot.annotation.ioc.AutoInjected;
import io.swen90007sm2.alpheccaboot.annotation.mvc.Controller;
import io.swen90007sm2.alpheccaboot.annotation.mvc.HandlesRequest;
import io.swen90007sm2.alpheccaboot.annotation.mvc.PathVariable;
import io.swen90007sm2.alpheccaboot.annotation.mvc.QueryParam;
import io.swen90007sm2.alpheccaboot.bean.R;
import io.swen90007sm2.alpheccaboot.common.constant.RequestMethod;
import io.swen90007sm2.app.blo.IPhotoBlo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller(path = "/api/shared")
public class PhotoController {

    @AutoInjected
    private IPhotoBlo photoBlo;

    @HandlesRequest(path = "/photo", method = RequestMethod.POST)
    public R handleUploadPhoto(HttpServletRequest request, HttpServletResponse response) {
        String userId = "admin@996workers.icu";
        String photoId = photoBlo.doPhotoUpload(request, userId);
        return R.ok().setData(photoId);
    }

    /**
     * handles photo download
     * @param photoId photo Id/Name
     * @param request servletRequest
     * @param response servletResponse
     */
    @HandlesRequest(path = "/photo", method = RequestMethod.GET)
    public void handleDownloadPhoto(@QueryParam(value = "photoId") String photoId, HttpServletRequest request, HttpServletResponse response) {
        photoBlo.doPhotoDownload(photoId, request, response);
    }
}
