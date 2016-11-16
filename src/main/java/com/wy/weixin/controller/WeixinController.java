package com.wy.weixin.controller;

import com.wy.weixin.base.BaseController;
import com.wy.weixin.constants.WeixinConfigConstant;
import com.wy.weixin.model.User;
import com.wy.weixin.service.IWeixinCoreService;
import com.wy.weixin.service.IWeixinService;
import com.wy.weixin.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wxiao on 2016.11.8.
 *
 * 微信主控制器
 */

@Controller
public class WeixinController extends BaseController {

    @Autowired
    private IWeixinCoreService weixinCoreService;

    @Autowired
    private IWeixinService weixinService;


    /**
     * 微信开发校验
     */
    @RequestMapping(value = "/main", method = RequestMethod.GET)
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
     * 微信网页授权回调处理
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


    /**
     * 微信JSSDK测试
     */
    @RequestMapping("/share")
    public ModelAndView share() {
        // 当前地址
        String url = "http://wxiaoxin.tunnel.qydev.com/weixin/share";
        // 获取JSSDK签名
        Map<String, String> map = weixinService.jsApiTicketSign(url);

        ModelAndView mv = new ModelAndView("share");
        mv.addObject("map", map);
        mv.addObject("appId", WeixinConfigConstant.APPID);

        return mv;
    }

    /**
     * 微信支付页面
     * @return
     */
    @RequestMapping("/pay")
    public ModelAndView pay(String userOpenId) {
        // 当前地址
        String url = "http://wxiaoxin.tunnel.qydev.com/weixin/pay?userOpenId=" + userOpenId;
        // 支付回调地址
        String urlBack = "http://wxiaoxin.tunnel.qydev.com/weixin/payback";

        // 获取JSSDK签名
        Map<String, String> jssdkMap = weixinService.jsApiTicketSign(url);
        logger.debug(jssdkMap);

        // 微信支付提交参数
        Map<String, String> map = new HashMap<>();
        map.put("body", "约惠商城-测试商品");
        map.put("attach", "附加信息");
        map.put("totalFee", "1234");
        map.put("ip", Utils.getIPAddress(request));
        map.put("notifyUrl", urlBack);
        map.put("productId", "123456");
        map.put("openId", userOpenId);

        // 微信支付签名
        Map<String, String> paySignMap = weixinService.paySign(map);
        logger.debug(paySignMap);

        ModelAndView mv = new ModelAndView("pay");
        mv.addObject("jssdkMap", jssdkMap);
        mv.addObject("paySignMap", paySignMap);
        mv.addObject("appId", WeixinConfigConstant.APPID);
        return mv;
    }

    /**
     * 支付回调
     * @return
     */
    @RequestMapping("/payback")
    @ResponseBody
    public String payBack() {
        return "支付回调";
    }

}
