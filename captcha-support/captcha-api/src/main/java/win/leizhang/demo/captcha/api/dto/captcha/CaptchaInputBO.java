package win.leizhang.demo.captcha.api.dto.captcha;

/**
 * Created by zealous on 2018/2/14.
 */
public class CaptchaInputBO extends CaptchaBO {

    // uuid的数组
    private String[] uuids;

    public String[] getUuids() {
        return uuids;
    }

    public void setUuids(String[] uuids) {
        this.uuids = uuids;
    }
}
