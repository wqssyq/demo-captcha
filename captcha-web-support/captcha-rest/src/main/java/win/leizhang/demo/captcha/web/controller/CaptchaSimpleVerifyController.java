package win.leizhang.demo.captcha.web.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import win.leizhang.demo.captcha.api.dto.base.MainInputDTO;
import win.leizhang.demo.captcha.api.dto.base.MainOutputDTO;
import win.leizhang.demo.captcha.api.dto.captcha.CaptchaInputDTO;
import win.leizhang.demo.captcha.api.facade.CaptchaSimpleFacade;

/**
 * 简单的验证，测试
 * Created by zealous on 2018/2/12.
 */
@RequestMapping("/simpleVerify")
@RestController
public class CaptchaSimpleVerifyController {

    @Reference
    CaptchaSimpleFacade captchaSimpleFacade;

    // 验证
    @RequestMapping(value = "/verify", method = RequestMethod.POST)
    public MainOutputDTO verifySimple1(@RequestBody MainInputDTO<CaptchaInputDTO> inputDTO) {
        return captchaSimpleFacade.verifyCaptchaSimple(inputDTO);
    }

}
