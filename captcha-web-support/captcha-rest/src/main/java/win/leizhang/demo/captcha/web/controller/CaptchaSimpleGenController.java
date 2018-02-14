package win.leizhang.demo.captcha.web.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import win.leizhang.demo.captcha.api.dto.base.MainOutputDTO;
import win.leizhang.demo.captcha.api.dto.captcha.CaptchaBO;
import win.leizhang.demo.captcha.api.exception.CaptchaResultCode;
import win.leizhang.demo.captcha.api.facade.CaptchaSimpleFacade;

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
    public MainOutputDTO<CaptchaBO> getSimple1() {
        return captchaSimpleFacade.genCaptchaSimple();
    }

    // 生成2
    @RequestMapping(value = "/gen2", method = RequestMethod.POST)
    public MainOutputDTO<CaptchaBO> getSimple2(@RequestBody List<String> idList) {

        // 初始化
        MainOutputDTO<CaptchaBO> outputDTO = new MainOutputDTO<>();

        // 校验
        if (CollectionUtils.isEmpty(idList)) {
            outputDTO.setCode(CaptchaResultCode.CAPTCH_RESOURCEID_NOTNULL.code());
            outputDTO.setMsg(CaptchaResultCode.CAPTCH_RESOURCEID_NOTNULL.msg());
            return outputDTO;
        }

        outputDTO = captchaSimpleFacade.genCaptchaSimple(idList);
        return outputDTO;
    }

}
