package win.leizhang.demo.captcha.service.business;

import win.leizhang.demo.captcha.api.dto.captcha.CaptchaInputBO;

/**
 * Created by zealous on 2018/2/7.
 */
public interface CaptchaVerifyService {

    boolean verify(CaptchaInputBO bo);

}
