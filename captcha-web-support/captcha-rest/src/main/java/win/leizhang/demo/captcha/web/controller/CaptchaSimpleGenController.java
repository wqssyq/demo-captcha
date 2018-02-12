package win.leizhang.demo.captcha.web.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import win.leizhang.demo.captcha.api.dto.base.MainInputDTO;
import win.leizhang.demo.captcha.api.dto.base.MainOutputDTO;
import win.leizhang.demo.captcha.api.dto.captcha.CaptchaInputDTO;
import win.leizhang.demo.captcha.api.dto.captcha.CaptchaOutputDTO;
import win.leizhang.demo.captcha.api.facade.CaptchaSimpleFacade;

import java.util.ArrayList;
import java.util.List;

/**
 * 简单的生成，测试
 *
 * @author zealous
 * @date 2017/3/18.
 */
@RequestMapping("/simpleGen")
@RestController
public class CaptchaSimpleGenController {

    @Reference
    CaptchaSimpleFacade captchaSimpleFacade;

    // 生成1
    @RequestMapping(value = "/gen1", method = RequestMethod.POST)
    public MainOutputDTO<CaptchaOutputDTO> getSimple1() {
        return captchaSimpleFacade.genCaptchaSimple();
    }

    // 生成2
    @RequestMapping(value = "/gen2", method = RequestMethod.POST)
    public MainOutputDTO<CaptchaOutputDTO> getSimple2(@RequestBody MainInputDTO<CaptchaInputDTO> inputDTO) {
        // 入参
        String resourceId = inputDTO.getInputParam().getResourceId();
        // 转list
        List<String> idList = new ArrayList<>();
        idList.add(resourceId);

        MainOutputDTO<CaptchaOutputDTO> outputDTO = captchaSimpleFacade.genCaptchaSimple(idList);
        return outputDTO;
    }

}
