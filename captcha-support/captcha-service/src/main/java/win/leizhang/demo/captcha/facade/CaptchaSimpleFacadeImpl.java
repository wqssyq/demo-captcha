package win.leizhang.demo.captcha.facade;

import com.alibaba.dubbo.config.annotation.Service;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.tomcat.util.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.config.RandomValuePropertySource;
import win.leizhang.demo.captcha.api.dto.base.MainInputDTO;
import win.leizhang.demo.captcha.api.dto.base.MainOutputDTO;
import win.leizhang.demo.captcha.api.dto.captcha.CaptchaInputDTO;
import win.leizhang.demo.captcha.api.dto.captcha.CaptchaOutputDTO;
import win.leizhang.demo.captcha.api.facade.CaptchaSimpleFacade;
import win.leizhang.demo.captcha.service.basic.CaptchaCacheService;
import win.leizhang.demo.captcha.utils.ValidateCode;

import javax.imageio.ImageIO;
import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

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

        MainOutputDTO<CaptchaOutputDTO> outputDTO = new MainOutputDTO<>();
        CaptchaOutputDTO outDTO = new CaptchaOutputDTO();

        // 生成
        Map<String, String> map = this.genCaptcha();
        String randomCode = map.get("uuid").toString();
        String captcha = map.get("code").toString();
        String b64Image = map.get("enB64").toString();

        // 写缓存
        captchaCacheService.setCaptcha(captcha, randomCode);

        outDTO.setRandomCode(randomCode);
        outDTO.setB64Image(b64Image);

        // FIXME 压测用
        String codeContent = new String(Base64.decodeBase64(captcha));
        outDTO.setCodeContent(codeContent);

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

    private Map<String, String> genCaptcha() {
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
        // FIXME <RandomStringUtils>这个类要换掉
        String randomCode = "CRT" + RandomStringUtils.randomAlphanumeric(32);

        // TODO 方案貌似不可行
        RandomValuePropertySource rp = new RandomValuePropertySource();
        Object l = rp.getProperty("long");
        log.info("rod.long ==> {}", l);

        Map<String, String> map = new HashMap<>();
        map.put("uuid", randomCode);
        map.put("code", captcha);
        map.put("enB64", b64Image);

        // TODO 改造成<bo>类
        return map;
    }
}
