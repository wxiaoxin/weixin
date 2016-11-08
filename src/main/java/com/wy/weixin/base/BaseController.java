package com.wy.weixin.base;

import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by wxiao on 2016.11.8.
 */

public class BaseController {

    protected HttpServletRequest request;

    protected HttpServletResponse response;

    @ModelAttribute
    protected void setReqAndResp(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
    }

}
