package win.leizhang.demo.captcha.service.bo;

import java.io.Serializable;

/**
 * Created by zealous on 2018/2/7.
 */
public class CaptchaBO implements Serializable {

    private String uuid;
    private String code;
    private String enB64;

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

    public String getEnB64() {
        return enB64;
    }

    public void setEnB64(String enB64) {
        this.enB64 = enB64;
    }
}
