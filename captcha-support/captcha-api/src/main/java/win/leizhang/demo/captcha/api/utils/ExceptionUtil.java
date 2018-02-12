package win.leizhang.demo.captcha.api.utils;


import win.leizhang.demo.captcha.api.exception.CaptchaResultCode;
import win.leizhang.demo.captcha.api.exception.CaptchaServiceException;

public class ExceptionUtil {

    // 根据CaptchaResultCode创建业务异常
    public static CaptchaServiceException buildBzException(CaptchaResultCode resultCode) {
        if (resultCode != null) {
            return new CaptchaServiceException(resultCode.code(), resultCode.msg());
        } else {
            return new CaptchaServiceException(
                    CaptchaResultCode.GLOBAL_FAIL.code(), CaptchaResultCode.GLOBAL_FAIL.msg());
        }
    }

    public static CaptchaServiceException buildBzException() {
        return new CaptchaServiceException(CaptchaResultCode.GLOBAL_FAIL.code(), CaptchaResultCode.GLOBAL_FAIL.msg());
    }

    public static CaptchaServiceException buildDubboErrorResultException() {
        return new CaptchaServiceException(CaptchaResultCode.THIRD_DUBBO_RETURN_CODE_ERROR.code(), CaptchaResultCode.THIRD_DUBBO_RETURN_CODE_ERROR.msg());
    }

    public static CaptchaServiceException buildBzException(String code, String msg) {
        return new CaptchaServiceException(code, msg);
    }

}
