package win.leizhang.demo.captcha.api.exception;

public class CaptchaServiceException extends RuntimeException {

    static final long serialVersionUID = 1L;

    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public CaptchaServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public CaptchaServiceException(String code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    public CaptchaServiceException(Throwable cause) {
        this(null, cause);
    }

    public CaptchaServiceException(String code, String message) {
        this(code, message, null);
    }

}
