package com.wy.weixin.controller;

import com.wy.weixin.base.BaseController;
import com.wy.weixin.model.User;
import com.wy.weixin.service.IWeixinCoreService;
import com.wy.weixin.service.IWeixinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

/**
 * Created by wxiao on 2016.11.8.
 *
 * 微信主控制器
 */

@Controller
@RequestMapping("/weixin")
public class WeixinController extends BaseController {

    @Autowired
    private IWeixinCoreService weixinCoreService;

    @Autowired
    private IWeixinService weixinService;


    /**
     * 微信开发校验
     */
    @RequestMapping(value = "main", method = RequestMethod.GET)
    @ResponseBody
    public String index() {

        // 校验
        boolean flag = weixinCoreService.checkSignature(request);
        if (flag) {
            // 校验成功，返回随机字符串
            return request.getParameter("echostr");
        } else {
            return "";
        }

    }


    /**
     * 接收并处理微信消息
     */
    @RequestMapping(value = "/main", method = RequestMethod.POST)
    @ResponseBody
    public String main() {
        String result = weixinCoreService.handleMsg(request, response);
        return result;
    }


    /**
     * 微信授权回调处理
     */
    @RequestMapping("/authorize")
    public ModelAndView authorize(String code, String state) {
        Map<String, String> map = weixinService.getWebAuthToken(code);
        User user = weixinService.getWebAuthUserInfo(map);
        logger.debug(user);
        ModelAndView mv = new ModelAndView("authorize");
        mv.addObject("user", user);
        return mv;
    }

}
