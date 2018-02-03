package win.leizhang.demo.captcha.api.constants;

import org.apache.commons.lang3.StringUtils;

public enum BindTypeEnum {

    WECHAT("10001", "微信公众号/unionid"),
    WECHAT_SERVICE_NUMBER("10003", "微信服务号openid"),
    WECHAT_APPLET("10005", "微信小程序openid"),;

    private String code;
    private String desc;
    private String fullDescStr;

    BindTypeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
        this.fullDescStr = code + "-" + desc;
    }

    /**
     * 将String类型的类型编码解析成枚举
     *
     * @param code
     * @return
     * @throws IllegalArgumentException
     */
    public static BindTypeEnum parseCode(String code) throws IllegalArgumentException {
        if (StringUtils.isBlank(code)) {
            return null;
        }
        for (BindTypeEnum t : BindTypeEnum.values()) {
            if (t.code.equals(code)) {
                return t;
            }
        }
        throw new IllegalArgumentException("无效的绑定类型code:" + code);
    }

    /**
     * 是否存在
     *
     * @param code
     * @return
     */
    public static boolean contains(String code) {
        boolean flag = false;
        if (!StringUtils.isBlank(code)) {
            for (BindTypeEnum obj : BindTypeEnum.values()) {
                if (obj.getCode().equals(code)) {
                    flag = true;
                }
            }
        }
        return flag;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    @Override
    public String toString() {
        return fullDescStr;
    }

}
