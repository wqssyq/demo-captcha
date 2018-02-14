package win.leizhang.demo.captcha.api.dto.captcha;

import java.util.List;

/**
 * Created by zealous on 2018/2/14.
 */
public class CaptchaInputBO extends CaptchaBO {

    // uuid的集合
    private List<String> uuids;

    public List<String> getUuids() {
        return uuids;
    }

    public void setUuids(List<String> uuids) {
        this.uuids = uuids;
    }
}
