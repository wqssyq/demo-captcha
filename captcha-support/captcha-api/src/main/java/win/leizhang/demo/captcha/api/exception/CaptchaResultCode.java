package win.leizhang.demo.captcha.api.exception;

/**
 * DESCRIPTION : captcha 返回码
 * AUTHOR : 张磊
 * DATE : 2017/3/11.
 */
public enum CaptchaResultCode {
    /********** sys start ***********/
    SYSTEM_GENERAL_ERROR("E1S00001", "操作失败！"),
    INIT_DATA_ERROR("E1S00006", "初始化数据异常！"),
    DATA_FORMAT_ERROR("E1M00001", "数据格式错误！"),
    PARA_UNRULE_ERROR("E1M00002", "请求参数格式不符合规则"),
    MEDIA_TYPE_ERROR("E1M00003", "请求类型有误"),
    TYPE_MISMATCH_ERROR("E1M00004", "参数类型不匹配!"),
    MISSING_PARAMETER_ERROR("E1M00005", "必传参数缺失!"),
    DATA_BIND_ERROR("E1M00006", "数据绑定错误!"),
    METHOD_ARGUMENT_VALID_ERROR("E1M00007", "参数机校验错误!"),
    INVALID_CHAR_ERROR("E1M00008", "非法字符异常！"),
    THIRD_DUBBO_RETURN_CODE_ERROR("E1M00009", "远程服务调用异常！"),

    /********** sys end ***********/
	
    /********** 全局返回码 ***********/
    GLOBAL_SUCCESS("00000000","操作成功"),
    GLOBAL_FAIL("00000001","系统繁忙，请稍后再试"),
    GLOBAL_SIGN_INVALID("00000002","签名结果不正确"),
    GLOBAL_DATA_FORMAT_ERROR("00000003","日期格式错误"),
    /********** 全局返回码 END ***********/

    /********* 图形验证码 ***********/
    CAPTCH_RESOURCEID_NOTNULL("00100000","resourceId不能为空"),
    CAPTCH_RANDOMCODE_NOTNULL("00100001","随机码不能为空"),
    CAPTCH_CODE_NOTNULL("00100002","验证码不能为空"),
    CAPTCH_CODE_INVALID("00100003","验证码已失效"),
    CAPTCH_VERIFY_SUCCESS("00100004","验证成功"),
    CAPTCH_VERIFY_FAIL("00100005","验证失败"),
    /******** 图形验证码 END *****************/


    ;

    private final String code;
    private final String msg;

    private CaptchaResultCode(String code, String msg) {
        this.code=code;
        this.msg=msg;
    }

    public String code() {
        return this.code;
    }

    public String msg() {
        return this.msg;
    }
}
