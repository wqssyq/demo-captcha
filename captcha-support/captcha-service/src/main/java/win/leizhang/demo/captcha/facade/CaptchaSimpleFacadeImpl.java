package win.leizhang.demo.captcha.facade;

import com.alibaba.dubbo.config.annotation.Service;
import org.apache.commons.lang3.RandomUtils;
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
import win.leizhang.demo.captcha.utils.ValidateCode;

import javax.imageio.ImageIO;
import java.io.ByteArrayOutputStream;

/**
 * Created by zealous on 2018/2/3.
 */
@Service
public class CaptchaSimpleFacadeImpl implements CaptchaSimpleFacade {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    CaptchaCacheService captchaCacheService;

    @Override
    public MainOutputDTO<CaptchaOutputDTO> genCaptchaSimple() {

        // 初始化
        MainOutputDTO<CaptchaOutputDTO> outputDTO = new MainOutputDTO<>();
        CaptchaOutputDTO outDTO = new CaptchaOutputDTO();

        // 生成
        CaptchaBO captchaBO = genCaptcha();
        // 取参数
        String code = captchaBO.getCode();
        String randomCode = captchaBO.getUuid();

        // 写缓存
        captchaCacheService.setCaptcha(code, randomCode);

        // 返回
        outDTO.setRandomCode(randomCode);
        outDTO.setB64Image(captchaBO.getEnB64());
        // FIXME 压测用
        outDTO.setCodeContent(new String(Base64.decodeBase64(code)));

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

    private CaptchaBO genCaptcha() {

        // 初始化
        CaptchaBO bo = new CaptchaBO();

        // 生成
        ValidateCode vCode = new ValidateCode(180, 60, 4, 200);// lineCount=500,干扰线增加
        // 转为base64
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            ImageIO.write(vCode.getBuffImg(), "jpg", out);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 验证码
        String captcha = new String(Base64.encodeBase64(vCode.getCode().getBytes()));
        // 图片流
        String b64Image = new String(Base64.encodeBase64(out.toByteArray()));
        // 随机码
        String randomCode = "TMP" + RandomUtils.nextLong();

        // 返回
        bo.setUuid(randomCode);
        bo.setCode(captcha);
        bo.setEnB64(b64Image);

        return bo;
    }
}
