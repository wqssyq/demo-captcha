package win.leizhang.demo.captcha.web.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by zealous on 2018/2/3.
 */
@RequestMapping("/demo")
@RestController
public class DemoController {

    @RequestMapping("/t1")
    public String test1(String str) {
        if (null == str) {
            str = "hello world!";
        }
        return str;
    }
    
}
