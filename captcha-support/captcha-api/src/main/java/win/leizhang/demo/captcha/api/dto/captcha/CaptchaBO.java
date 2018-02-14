package win.leizhang.demo.captcha.api.dto.captcha;

import java.io.Serializable;

/**
 * 图形验证码，通用对象
 * Created by zealous on 2018/2/14.
 */
public class CaptchaBO implements Serializable {

    // 通用唯一识别码
    private String uuid;
    // 验证码，默认是b64结果
    private String code;
    // 验证码图片b64的
    private String codeImage;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCodeImage() {
        return codeImage;
    }

    public void setCodeImage(String codeImage) {
        this.codeImage = codeImage;
    }
}
