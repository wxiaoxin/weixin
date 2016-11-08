package com.wy.weixin.controller;

import com.wy.weixin.base.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by wxiao on 2016.11.8.
 *
 * 首页控制器
 */

@Controller
@RequestMapping("/weixin")
public class IndexController extends BaseController {

    @RequestMapping("/index")
    public String index() {
        return "index";
    }

}
