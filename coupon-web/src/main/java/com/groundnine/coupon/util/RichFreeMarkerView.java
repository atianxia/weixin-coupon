package com.groundnine.coupon.util;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.view.freemarker.FreeMarkerView;

public class RichFreeMarkerView extends FreeMarkerView {

    /** 项目根路径 **/
    private static final String ANOTHER_CONTEXT_PATH = "rootPath";
    /** 项目上下文 **/
    private static final String CONTEXT_PATH_SIGN    = "contextPath";
    
    /** 图片根路径 **/
    private static final String IMAGE_ROOT_PATH = "imageRootPath";

    /**
     * 在model中增加部署路径base，方便处理部署路径问题。
     */
    protected void exposeHelpers(Map<String, Object> model,
                                 HttpServletRequest request) throws Exception {
        super.exposeHelpers(model, request);
        String url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
        String rootPath = url + request.getContextPath();
        String imageRootPath = url + "/images";
        model.put(ANOTHER_CONTEXT_PATH, rootPath);
        model.put(CONTEXT_PATH_SIGN, request.getContextPath());
        model.put(IMAGE_ROOT_PATH, imageRootPath);
    }

}
