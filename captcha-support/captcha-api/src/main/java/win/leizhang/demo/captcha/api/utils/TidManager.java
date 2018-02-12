package win.leizhang.demo.captcha.api.utils;


import java.util.UUID;

/**
 * ***********************************************************
 *
 * @类名 : TidManager.java
 * @DESCRIPTION : tid管理工具类
 * @AUTHOR : zealous
 * @DATE : 2016年1月15日
 * ***********************************************************
 */
public class TidManager {
    private static final ThreadLocal<String> TIDTREAD_LOCAL = new ThreadLocal<>();
    private static final ThreadLocal<Long> REQUEST_BEGIN_TIME_MS = new ThreadLocal<>();
    private static final ThreadLocal<String> FROM_LOCAL = new ThreadLocal<>();
    private static final ThreadLocal<String> SOURCE_FROM_POINTS_GW = new ThreadLocal<>();

    /**
     * 设置TID
     *
     * @param str
     */
    public static void setTid(String str) {
        TIDTREAD_LOCAL.set(str);
    }

    /**
     * 获取TID
     *
     * @return
     */
    public static String getTransactionUuid() {
        String transactionUuid = TIDTREAD_LOCAL.get();
        // 当tid为空时，则生成一个tid
        if (null == transactionUuid || 0 == transactionUuid.trim().length()) {
            transactionUuid = UUID.randomUUID().toString().replace("-", "");
            setTid(transactionUuid);
        }
        return transactionUuid;
    }

    public static void setSysSourceFrom(String sysSourceFrom) {
        FROM_LOCAL.set(sysSourceFrom);
    }

    public static void setSourceFromPointsGateway(String pwFlag) {
        SOURCE_FROM_POINTS_GW.set(pwFlag);
    }

    public static String getSysSourceFrom() {
        return FROM_LOCAL.get();
    }

    public static void setRequestBeginTime(Long timestamp) {
        REQUEST_BEGIN_TIME_MS.set(timestamp);
    }

    public static Long getRequestBeginTime() {
        return REQUEST_BEGIN_TIME_MS.get();
    }

    public static String getSourceFromPointsGateway() {
        return SOURCE_FROM_POINTS_GW.get();
    }

    public static void clear() {
        TIDTREAD_LOCAL.remove();
        REQUEST_BEGIN_TIME_MS.remove();
        FROM_LOCAL.remove();
        SOURCE_FROM_POINTS_GW.remove();
    }
}
