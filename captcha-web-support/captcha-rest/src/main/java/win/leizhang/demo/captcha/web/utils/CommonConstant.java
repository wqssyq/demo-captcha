package win.leizhang.demo.captcha.web.utils;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**
 * Created by zealous on 2017/12/28.
 */
public class CommonConstant {

    // 当前时区
    public static final ZoneId DEFAULT_ZONEID = ZoneId.systemDefault();

    // 时间格式
    public static final String DEFAULT_FORMAT_LDT = "yyyy-MM-dd HH:mm:ss";
    public static final String DEFAULT_FORMAT_LD = "yyyy-MM-dd";
    public static final String DEFAULT_FORMAT_LD_CN = "yyyy年MM月dd日";
    public static final String DEFAULT_FORMAT_LT = "HH:mm:ss";
    // 时间格式化
    public static final DateTimeFormatter DEFAULT_FORMATTER_LDT = DateTimeFormatter.ofPattern(DEFAULT_FORMAT_LDT);
    public static final DateTimeFormatter DEFAULT_FORMATTER_LD = DateTimeFormatter.ofPattern(DEFAULT_FORMAT_LD);
    public static final DateTimeFormatter DEFAULT_FORMATTER_LD_CN = DateTimeFormatter.ofPattern(DEFAULT_FORMAT_LD_CN);
    public static final DateTimeFormatter DEFAULT_FORMATTER_LT = DateTimeFormatter.ofPattern(DEFAULT_FORMAT_LT);

    // other

}
