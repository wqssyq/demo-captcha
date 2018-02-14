package win.leizhang.demo.captcha.web.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
@Api(tags = "验证图形验证码")
@RequestMapping("/captcha/verify")
@RestController
public class CaptchaSimpleVerifyController {

    @Reference
    CaptchaSimpleFacade captchaSimpleFacade;

    @ApiOperation(value = "普通验证", notes = "统用方法")
    @PostMapping("/simpleVerify")
    public MainOutputDTO vf1(@ApiParam(name = "input对象", required = true) @RequestBody MainInputDTO<CaptchaInputBO> inputDTO) {

        // 初始化
        MainOutputDTO<CaptchaBO> outputDTO = new MainOutputDTO<>();
        CaptchaInputBO inputBO = inputDTO.getInputParam();

        // 校验
        if (StringUtils.isBlank(inputBO.getUuid())) {
            outputDTO.setCode(CaptchaResultCode.CAPTCH_RESOURCEID_NOTNULL.code());
            outputDTO.setMsg(CaptchaResultCode.CAPTCH_RESOURCEID_NOTNULL.msg());
            return outputDTO;
        }

        outputDTO = captchaSimpleFacade.verifyCaptchaSimple(inputDTO);
        return outputDTO;
    }

}
