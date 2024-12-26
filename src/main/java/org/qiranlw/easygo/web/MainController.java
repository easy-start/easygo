package org.qiranlw.easygo.web;

import org.qiranlw.easygo.utils.EasygoUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author qiranlw
 */
@Controller
public class MainController {

    @GetMapping({"/", "/index.html"})
    public String index() {
        return "index";
    }

    @GetMapping("/login.html")
    public String login() {
        if (EasygoUtils.userLogin()) {
            return "redirect:/index.html";
        }
        return "login";
    }

    @GetMapping("/welcome.html")
    public String welcome() {
        return "welcome";
    }
}
