package win.leizhang.demo.captcha.service.business.impl;

import org.apache.commons.lang3.RandomUtils;
import org.apache.tomcat.util.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import win.leizhang.demo.captcha.service.bo.CaptchaBO;
import win.leizhang.demo.captcha.service.business.CaptchaGenService;
import win.leizhang.demo.captcha.utils.ValidateCode;

import javax.imageio.ImageIO;
import java.io.ByteArrayOutputStream;

/**
 * Created by zealous on 2018/2/7.
 */
@Service
public class CaptchaGenServiceImpl implements CaptchaGenService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    public CaptchaBO genCaptcha() {

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
