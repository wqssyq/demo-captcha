package win.leizhang.demo.captcha.api.dto.captcha;

import java.io.Serializable;

/**
 * Created by zealous on 2018/2/3.
 */
public class CaptchaInputDTO implements Serializable {

    // 随机码
    private String randomCode;
    // 验证码
    private String code;

    // 资源id
    private String resourceId;

    public String getRandomCode() {
        return randomCode;
    }

    public void setRandomCode(String randomCode) {
        this.randomCode = randomCode;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }
}
