package win.leizhang.demo.captcha.api.facade;

import win.leizhang.demo.captcha.api.dto.base.MainInputDTO;
import win.leizhang.demo.captcha.api.dto.base.MainOutputDTO;
import win.leizhang.demo.captcha.api.dto.captcha.CaptchaInputDTO;
import win.leizhang.demo.captcha.api.dto.captcha.CaptchaOutputDTO;

import java.util.List;

/**
 * Created by zealous on 2018/2/3.
 */
public interface CaptchaSimpleFacade {

    // 生成
    MainOutputDTO<CaptchaOutputDTO> genCaptchaSimple();

    MainOutputDTO<CaptchaOutputDTO> genCaptchaSimple(List<String> idList);

    // 校验
    MainOutputDTO verifyCaptchaSimple(MainInputDTO<CaptchaInputDTO> inputDTO);
}
