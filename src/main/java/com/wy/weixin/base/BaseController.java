package com.wy.weixin.base;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by wxiao on 2016.11.8.
 */

public class BaseController {

    protected HttpServletRequest request;

    protected HttpServletResponse response;

    protected Logger logger = Logger.getLogger(getClass());

    @ModelAttribute
    protected void setReqAndResp(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
    }

}
