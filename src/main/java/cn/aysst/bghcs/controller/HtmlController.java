package cn.aysst.bghcs.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author lcy
 * @version 2019-12-18
 * 实现html访问
 */
@Controller
@RequestMapping("/")
public class HtmlController {

    @RequestMapping("/index")
    public String index(){
        return "index";
    }

    @RequestMapping("/login")
    public String login(){
        return "login";
    }

    @RequestMapping("/table")
    public String tables(){
        return "table";
    }

    @RequestMapping("/link")
    public String a(){
        return "link";
    }

    @RequestMapping("/register")
    public String register(){
        return "register";
    }

    @RequestMapping("/404")
    public String handleError(){
        return "error";
    }

    @RequestMapping("/contactus")
    public String contactus(){
        return "contactus";
    }

    @RequestMapping("/imagedetail")
    public String imageDetail(){
        return "imagedetail";
    }

    @RequestMapping("/profile")
    public String profile(){
        return "profile";
    }


}
