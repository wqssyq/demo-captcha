package win.leizhang.demo.captcha.facade;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import win.leizhang.demo.captcha.api.dto.base.MainInputDTO;
import win.leizhang.demo.captcha.api.dto.base.MainOutputDTO;
import win.leizhang.demo.captcha.api.dto.captcha.CaptchaInputDTO;
import win.leizhang.demo.captcha.api.dto.captcha.CaptchaOutputDTO;
import win.leizhang.demo.captcha.api.exception.CaptchaResultCode;
import win.leizhang.demo.captcha.api.facade.CaptchaSimpleFacade;
import win.leizhang.demo.captcha.service.basic.CaptchaCacheService;
import win.leizhang.demo.captcha.service.bo.CaptchaBO;
import win.leizhang.demo.captcha.service.business.CaptchaGenService;
import win.leizhang.demo.captcha.service.business.CaptchaVerifyService;

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

    @Override
    public MainOutputDTO<CaptchaOutputDTO> genCaptchaSimple() {
        return genCaptchaSimple(null);
    }

    @Override
    public MainOutputDTO<CaptchaOutputDTO> genCaptchaSimple(List<String> idList) {

        // 初始化
        MainOutputDTO<CaptchaOutputDTO> outputDTO = new MainOutputDTO<>();
        CaptchaOutputDTO outDTO = new CaptchaOutputDTO();

        // 生成
        CaptchaBO captchaBO = captchaGenService.genCaptcha();
        // 取参数
        String code = captchaBO.getCode();
        String randomCode = captchaBO.getUuid();

        // 写缓存
        if (null == idList) {
            captchaCacheService.setCaptcha(code, randomCode);
        } else {
            idList.add(randomCode);
            // 转数组
            String[] resourceIds = idList.toArray(new String[idList.size()]);
            captchaCacheService.setCaptcha(code, resourceIds);
        }

        // 数据转换
        outDTO.setRandomCode(randomCode);
        outDTO.setB64Image(captchaBO.getEnB64());
        // FIXME 压测用
        outDTO.setCodeContent(new String(Base64.decodeBase64(code)));

        // 返回
        outputDTO.setOutputParam(outDTO);
        return outputDTO;
    }

    @Override
    public MainOutputDTO verifyCaptchaSimple(MainInputDTO<CaptchaInputDTO> inputDTO) {
        log.info("校验，入参是 ==> {}", JSON.toJSONString(inputDTO));

        // 初始化
        MainOutputDTO outputDTO = new MainOutputDTO();

        CaptchaInputDTO inputBO = inputDTO.getInputParam();
        // radom非空检查
        if (StringUtils.isBlank(inputBO.getRandomCode())) {
            outputDTO.setCode(CaptchaResultCode.CAPTCH_RANDOMCODE_NOTNULL.code());
            outputDTO.setMsg(CaptchaResultCode.CAPTCH_RANDOMCODE_NOTNULL.msg());
            return outputDTO;
        }
        if (StringUtils.isBlank(inputBO.getCode())) {
            outputDTO.setCode(CaptchaResultCode.CAPTCH_CODE_NOTNULL.code());
            outputDTO.setMsg(CaptchaResultCode.CAPTCH_CODE_NOTNULL.msg());
            return outputDTO;
        }

        CaptchaBO captchaBO = new CaptchaBO();
        captchaBO.setUuid(inputBO.getRandomCode());
        captchaBO.setCode(inputBO.getCode());
        // 暂用存资源id
        captchaBO.setEnB64(inputBO.getResourceId());

        // 校验
        boolean flag = captchaVerifyService.verify(captchaBO);

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
