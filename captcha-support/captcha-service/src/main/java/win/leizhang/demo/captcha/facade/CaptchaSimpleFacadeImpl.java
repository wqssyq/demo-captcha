package win.leizhang.demo.captcha.facade;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.CollectionUtils;
import win.leizhang.demo.captcha.api.dto.base.MainInputDTO;
import win.leizhang.demo.captcha.api.dto.base.MainOutputDTO;
import win.leizhang.demo.captcha.api.dto.captcha.CaptchaBO;
import win.leizhang.demo.captcha.api.dto.captcha.CaptchaInputBO;
import win.leizhang.demo.captcha.api.exception.CaptchaResultCode;
import win.leizhang.demo.captcha.api.facade.CaptchaSimpleFacade;
import win.leizhang.demo.captcha.service.basic.CaptchaCacheService;
import win.leizhang.demo.captcha.service.business.CaptchaGenService;
import win.leizhang.demo.captcha.service.business.CaptchaVerifyService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zealous on 2018/2/3.
 */
@Service
public class CaptchaSimpleFacadeImpl implements CaptchaSimpleFacade {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    CaptchaCacheService captchaCacheService;
    @Autowired
    CaptchaGenService captchaGenService;
    @Autowired
    CaptchaVerifyService captchaVerifyService;

    @Value("${captcha.testflag}")
    private boolean testFlag;

    @Override
    public MainOutputDTO<CaptchaBO> genCaptchaSimple() {
        return genCaptchaSimple(null);
    }

    @Override
    public MainOutputDTO<CaptchaBO> genCaptchaSimple(List<String> resourceIdList) {

        // 初始化
        MainOutputDTO<CaptchaBO> outputDTO = new MainOutputDTO<>();

        // 生成验证码
        CaptchaBO captchaBO = captchaGenService.genCaptcha();
        String code = captchaBO.getCode();

        // 构造对象
        List<String> uuidList = new ArrayList<>();
        // 入参的资源id不为空时，加入到list
        if (!CollectionUtils.isEmpty(resourceIdList)) {
            uuidList.addAll(resourceIdList);
        }
        // 加入到list
        uuidList.add(captchaBO.getUuid());

        // 转数组
        String[] uuids = uuidList.toArray(new String[uuidList.size()]);
        // 写缓存
        captchaCacheService.setCaptcha(code, uuids);

        // 测试开关
        if (testFlag) {
            captchaBO.setCode(new String(Base64.decodeBase64(code)));
        } else {
            captchaBO.setCode(null);
        }

        // 返回
        outputDTO.setOutputParam(captchaBO);
        return outputDTO;
    }

    @Override
    public MainOutputDTO verifyCaptchaSimple(MainInputDTO<CaptchaInputBO> inputDTO) {
        log.info("校验，入参是 ==> {}", JSON.toJSONString(inputDTO));

        // 初始化
        MainOutputDTO outputDTO = new MainOutputDTO();

        CaptchaInputBO inputBO = inputDTO.getInputParam();
        // 校验
        if (StringUtils.isBlank(inputBO.getUuid())) {
            outputDTO.setCode(CaptchaResultCode.CAPTCH_RANDOMCODE_NOTNULL.code());
            outputDTO.setMsg(CaptchaResultCode.CAPTCH_RANDOMCODE_NOTNULL.msg());
            return outputDTO;
        }
        if (StringUtils.isBlank(inputBO.getCode())) {
            outputDTO.setCode(CaptchaResultCode.CAPTCH_CODE_NOTNULL.code());
            outputDTO.setMsg(CaptchaResultCode.CAPTCH_CODE_NOTNULL.msg());
            return outputDTO;
        }

        // 校验
        boolean flag = captchaVerifyService.verify(inputBO);

        if (flag) {
            outputDTO.setCode(CaptchaResultCode.CAPTCH_VERIFY_SUCCESS.code());
            outputDTO.setMsg(CaptchaResultCode.CAPTCH_VERIFY_SUCCESS.msg());
        } else {
            outputDTO.setCode(CaptchaResultCode.CAPTCH_VERIFY_FAIL.code());
            outputDTO.setMsg(CaptchaResultCode.CAPTCH_VERIFY_FAIL.msg());
        }

        // 返回
        return outputDTO;
    }

}
