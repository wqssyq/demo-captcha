package win.leizhang.demo.captcha.api.dto.base;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

import static org.apache.commons.lang3.builder.ToStringStyle.JSON_STYLE;

/**
 * DESCRIPTION : 请在这里补充描述
 * AUTHOR : 李毅
 * DATE : 2017/4/17.
 */
public abstract class AbstractBaseDTO implements Serializable {

    private static final long serialVersionUID = -2834276657868938074L;

    @Override
    public String toString() {
        if (this == null) {
            return "";
        }
        return ToStringBuilder.reflectionToString(this, JSON_STYLE);
    }
}
