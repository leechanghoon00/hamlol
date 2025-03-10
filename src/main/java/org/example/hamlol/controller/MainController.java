package org.example.hamlol.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/main")
    public String main() {
        // static 폴더에 위치한 login.html로 포워딩
        return "forward:/login.html";
    }
}
