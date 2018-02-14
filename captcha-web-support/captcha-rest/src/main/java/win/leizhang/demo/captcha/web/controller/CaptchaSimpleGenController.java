package win.leizhang.demo.captcha.web.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;
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
@Api(tags = "生成图形验证码")
@RequestMapping("/captcha/gen")
@RestController
public class CaptchaSimpleGenController {

    @Reference
    CaptchaSimpleFacade captchaSimpleFacade;

    @ApiOperation(value = "普通生成", notes = "无参数")
    @GetMapping("/simpleGen1")
    public MainOutputDTO<CaptchaBO> gen1() {
        return captchaSimpleFacade.genCaptchaSimple();
    }

    @ApiOperation(value = "普通生成2", notes = "有参数，需要传入资源id数组对象，参数用回车或逗号隔开即可")
    @PostMapping("/simpleGen2")
    public MainOutputDTO<CaptchaBO> gen2(@RequestParam List<String> idList) {

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

    @ApiIgnore
    @DeleteMapping("/simpleGen1/del/{id}")
    public String deleteById(@PathVariable String uuid) {
        return "delete captcha : " + uuid;
    }

}
