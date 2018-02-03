package win.leizhang.demo.captcha.api.dto.captcha;

import java.io.Serializable;

/**
 * Created by zealous on 2018/2/3.
 */
public class CaptchaOutputDTO implements Serializable {

    // 随机码
    private String randomCode;
    // 验证码图片
    private String b64Image;

    private String codeContent;

    public String getRandomCode() {
        return randomCode;
    }

    public void setRandomCode(String randomCode) {
        this.randomCode = randomCode;
    }

    public String getB64Image() {
        return b64Image;
    }

    public void setB64Image(String b64Image) {
        this.b64Image = b64Image;
    }

    public String getCodeContent() {
        return codeContent;
    }

    public void setCodeContent(String codeContent) {
        this.codeContent = codeContent;
    }
}
