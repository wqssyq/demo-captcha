package win.leizhang.demo.captcha.utils;

/**
 * DESCRIPTION : Redis统一管理
 * AUTHOR : 张磊
 * DATE : 2017/3/11.
 */
public abstract class RedisKeyConstants {
    private static final String CAPTCHA_BASE_NAMESPACE = "CAPTCHA";
    private static final String REDIS_NAMESPACE_SEPARATOR = ":";

    // uuid等同于randomCode
    public final static String REDIS_UUID_SIMPLECODE_PRE = "SIMPLE";
    public final static String REDIS_UUID_RESOURCE_SIMPLECODE_PRE = "SIMPLE_RESOURCE";
    public final static String REDIS_UUID_CUSTOMCODE_PRE = "CUSTOM";

    private static String concat(String serviceNameSpace, String... keys) {
        StringBuffer sb = new StringBuffer(CAPTCHA_BASE_NAMESPACE);
        sb.append(REDIS_NAMESPACE_SEPARATOR);
        sb.append(serviceNameSpace);
        for (int i = 0; i < keys.length; i++) {
            sb.append(REDIS_NAMESPACE_SEPARATOR);
            sb.append(keys[i]);
        }
        return sb.toString();
    }

    /**
     * uuid->simpleCode
     *
     * @param key
     * @return
     */
    public static String getUuid2SimpleCode(String... key) {
        return concat(REDIS_UUID_SIMPLECODE_PRE, key);
    }

    /**
     * uuid&resource->simpleCode
     *
     * @param key
     * @return
     */
    public static String getUuid2SimpleResourceCode(String... key) {
        return concat(REDIS_UUID_RESOURCE_SIMPLECODE_PRE, key);
    }

    /**
     * uuid->code
     *
     * @param key
     * @return
     */
    public static String getUuid2Code(String... key) {
        return concat(REDIS_UUID_CUSTOMCODE_PRE, key);
    }

}