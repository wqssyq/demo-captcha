package win.leizhang.demo.captcha.web.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import win.leizhang.demo.captcha.api.dto.base.MainInputDTO;
import win.leizhang.demo.captcha.api.dto.base.MainOutputDTO;
import win.leizhang.demo.captcha.api.dto.captcha.CaptchaBO;
import win.leizhang.demo.captcha.api.dto.captcha.CaptchaInputBO;
import win.leizhang.demo.captcha.api.exception.CaptchaResultCode;
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
    public MainOutputDTO verifySimple1(@RequestBody MainInputDTO<CaptchaInputBO> inputDTO) {

        // 初始化
        MainOutputDTO<CaptchaBO> outputDTO = new MainOutputDTO<>();
        CaptchaInputBO inputBO = inputDTO.getInputParam();

        // 校验
        if (null == inputBO.getUuids() || StringUtils.isBlank(inputBO.getUuid())) {
            outputDTO.setCode(CaptchaResultCode.CAPTCH_RESOURCEID_NOTNULL.code());
            outputDTO.setMsg(CaptchaResultCode.CAPTCH_RESOURCEID_NOTNULL.msg());
            return outputDTO;
        }

        outputDTO = captchaSimpleFacade.verifyCaptchaSimple(inputDTO);
        return outputDTO;
    }

}
