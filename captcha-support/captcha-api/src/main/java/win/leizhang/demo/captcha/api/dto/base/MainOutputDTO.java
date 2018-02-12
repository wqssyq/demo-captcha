package win.leizhang.demo.captcha.api.dto.base;

import java.io.Serializable;

import static win.leizhang.demo.captcha.api.exception.CaptchaResultCode.GLOBAL_SUCCESS;

public class MainOutputDTO<T> extends AbstractBaseDTO implements Serializable {

    public MainOutputDTO() {
        this.code = GLOBAL_SUCCESS.code();
        this.msg = GLOBAL_SUCCESS.msg();
    }

    public MainOutputDTO(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private String transactionUuid;

    private T outputParam;

    /**
     * 返回结果编码
     */
    protected String code;

    /**
     * 错误描述
     */
    protected String msg;

    public final String getCode() {
        return code;
    }

    public final void setCode(String code) {
        this.code = code;
    }

    public final String getMsg() {
        return msg;
    }

    public final void setMsg(String msg) {
        this.msg = msg;
    }

    public final String getTransactionUuid() {
        return transactionUuid;
    }

    public final void setTransactionUuid(String transactionUuid) {
        this.transactionUuid = transactionUuid;
    }

    public T getOutputParam() {
        return outputParam;
    }

    public void setOutputParam(T outputParam) {
        this.outputParam = outputParam;
    }
}
