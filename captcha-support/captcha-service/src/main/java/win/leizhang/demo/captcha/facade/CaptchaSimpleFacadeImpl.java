package win.leizhang.demo.captcha.facade;

import com.alibaba.dubbo.config.annotation.Service;
import org.apache.tomcat.util.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import win.leizhang.demo.captcha.api.dto.base.MainInputDTO;
import win.leizhang.demo.captcha.api.dto.base.MainOutputDTO;
import win.leizhang.demo.captcha.api.dto.captcha.CaptchaInputDTO;
import win.leizhang.demo.captcha.api.dto.captcha.CaptchaOutputDTO;
import win.leizhang.demo.captcha.api.facade.CaptchaSimpleFacade;
import win.leizhang.demo.captcha.service.basic.CaptchaCacheService;
import win.leizhang.demo.captcha.service.bo.CaptchaBO;
import win.leizhang.demo.captcha.service.business.CaptchaGenService;

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

    @Override
    public MainOutputDTO<CaptchaOutputDTO> genCaptchaSimple() {

        // 初始化
        MainOutputDTO<CaptchaOutputDTO> outputDTO = new MainOutputDTO<>();
        CaptchaOutputDTO outDTO = new CaptchaOutputDTO();

        // 生成
        CaptchaBO captchaBO = captchaGenService.genCaptcha();
        // 取参数
        String code = captchaBO.getCode();
        String randomCode = captchaBO.getUuid();

        // 写缓存
        captchaCacheService.setCaptcha(code, randomCode);

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
    public MainOutputDTO<CaptchaOutputDTO> genCaptchaSimple(String resourceId) {
        return null;
    }

    @Override
    public MainOutputDTO verifyCaptchaSimple(MainInputDTO<CaptchaInputDTO> inputDTO) {
        return null;
    }

}
