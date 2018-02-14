package win.leizhang.demo.captcha.service.business.impl;

import org.apache.commons.lang3.RandomUtils;
import org.apache.tomcat.util.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import win.leizhang.demo.captcha.api.dto.captcha.CaptchaBO;
import win.leizhang.demo.captcha.api.exception.CaptchaResultCode;
import win.leizhang.demo.captcha.api.utils.ExceptionUtil;
import win.leizhang.demo.captcha.common.captcha.simple.ValidateCode;
import win.leizhang.demo.captcha.service.business.CaptchaGenService;

import javax.imageio.ImageIO;
import java.io.ByteArrayOutputStream;

/**
 * Created by zealous on 2018/2/7.
 */
@Service
public class CaptchaGenServiceImpl implements CaptchaGenService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private final static String DEFAULT_IMAGE_FORMAT = "jpg";

    @Override
    public CaptchaBO genCaptcha() {

        // 初始化
        CaptchaBO bo = new CaptchaBO();

        // 生成
        ValidateCode vCode = new ValidateCode(180, 60, 4, 200);// lineCount=500,干扰线增加
        // 转为base64
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            ImageIO.write(vCode.getBuffImg(), DEFAULT_IMAGE_FORMAT, out);
        } catch (Exception e) {
            log.error("生成图片发生异常，详情==> {}", e.fillInStackTrace());
            throw ExceptionUtil.buildBzException(CaptchaResultCode.INIT_DATA_ERROR);
        }

        // 随机码
        String uuid = "TMP" + RandomUtils.nextLong();
        // 验证码
        String code = new String(Base64.encodeBase64(vCode.getCode().getBytes()));
        // 图片流
        String codeImage = new String(Base64.encodeBase64(out.toByteArray()));

        // 返回
        bo.setUuid(uuid);
        bo.setCode(code);
        bo.setCodeImage(codeImage);

        return bo;
    }

}
