package win.leizhang.demo.captcha.web.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import win.leizhang.demo.captcha.api.dto.base.MainInputDTO;
import win.leizhang.demo.captcha.api.dto.base.MainOutputDTO;
import win.leizhang.demo.captcha.api.dto.captcha.CaptchaInputDTO;
import win.leizhang.demo.captcha.api.dto.captcha.CaptchaOutputDTO;
import win.leizhang.demo.captcha.api.facade.CaptchaSimpleFacade;

/**
 * 简单的，测试
 *
 * @author zealous
 * @date 2017/3/18.
 */
@RequestMapping("/simple")
@RestController
public class CaptchaSimpleController {

    private static final Logger logger = LogManager.getLogger(CaptchaSimpleController.class);

    @Reference
    CaptchaSimpleFacade captchaSimpleFacade;

    @RequestMapping(value = "/gen1", method = RequestMethod.POST)
    public MainOutputDTO<CaptchaOutputDTO> getSimple1() {
        MainOutputDTO<CaptchaOutputDTO> outputDTO = captchaSimpleFacade.genCaptchaSimple();
        return outputDTO;
    }

    @RequestMapping(value = "/gen2", method = RequestMethod.POST)
    public MainOutputDTO<CaptchaOutputDTO> getSimple2(@RequestBody MainInputDTO<CaptchaInputDTO> inputDTO) {

        String resourceId = inputDTO.getInputParam().getResourceId();

        return null;
    }

    @RequestMapping(value = "/verify", method = RequestMethod.POST)
    public MainOutputDTO verifySimple1(@RequestBody MainInputDTO<CaptchaInputDTO> inputDTO) {

        MainOutputDTO outputDTO = captchaSimpleFacade.verifyCaptchaSimple(inputDTO);
        return outputDTO;
    }

}
