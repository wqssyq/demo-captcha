package win.leizhang.demo.captcha.api.facade;

import win.leizhang.demo.captcha.api.dto.base.MainInputDTO;
import win.leizhang.demo.captcha.api.dto.base.MainOutputDTO;
import win.leizhang.demo.captcha.api.dto.captcha.CaptchaBO;
import win.leizhang.demo.captcha.api.dto.captcha.CaptchaInputBO;

import java.util.List;

/**
 * Created by zealous on 2018/2/3.
 */
public interface CaptchaSimpleFacade {

    // 生成
    MainOutputDTO<CaptchaBO> genCaptchaSimple();

    MainOutputDTO<CaptchaBO> genCaptchaSimple(List<String> resourceIdList);

    // 校验
    MainOutputDTO verifyCaptchaSimple(MainInputDTO<CaptchaInputBO> inputDTO);
}
