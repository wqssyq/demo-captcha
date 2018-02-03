package win.leizhang.demo.captcha.service.basic;

import java.util.Set;

/**
 * 图形验证码缓存服务
 * Created by zealous on 2018/2/3.
 */
public interface CaptchaCacheService {

    // 存
    void setCaptcha(String code, String... key);

    // 删除
    void deleteCaptcha(String... key);
    void deleteCaptchas(Set<String> key);

    // 查
    String getCaptcha(String... key);
    Set<String> getCaptchaKeys(String... keys);

}
