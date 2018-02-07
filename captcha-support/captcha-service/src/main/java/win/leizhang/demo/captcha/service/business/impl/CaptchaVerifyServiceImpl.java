package win.leizhang.demo.captcha.service.business.impl;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import win.leizhang.demo.captcha.service.basic.CaptchaCacheService;
import win.leizhang.demo.captcha.service.bo.CaptchaBO;
import win.leizhang.demo.captcha.service.business.CaptchaVerifyService;

/**
 * Created by zealous on 2018/2/7.
 */
@Service
public class CaptchaVerifyServiceImpl implements CaptchaVerifyService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    CaptchaCacheService captchaCacheService;

    @Override
    public boolean verify(CaptchaBO bo) {
        log.info("入参是 ==> {}", JSON.toJSONString(bo));

        // 初始化
        boolean flag = false;

        // 解码前
        String b64Code;
        // 解码后
        String b64CodeDe;

        // 入参是
        String resourceId = bo.getEnB64();
        String uuid = bo.getUuid();
        String inputCode = bo.getCode();

        if (StringUtils.isBlank(resourceId)) {
            b64Code = captchaCacheService.getCaptcha(uuid);
        } else {
            b64Code = captchaCacheService.getCaptcha(resourceId, uuid);
        }

        // 不能为空
        if (StringUtils.isBlank(b64Code)) {
            log.info("取到的验证码为空！");
            return flag;
        }

        // 校验
        b64CodeDe = new String(Base64.decodeBase64(b64Code));
        // 忽略大小写
        if (StringUtils.equalsIgnoreCase(b64CodeDe, inputCode)) {
            flag = true;

            // 删除缓存
            captchaCacheService.deleteCaptcha(uuid);
            captchaCacheService.deleteCaptcha(resourceId, uuid);
            // 耗性能的操作
            //Set<String> keys = captchaCacheService.getCaptchaKeys(resourceId, "*");
            //captchaCacheService.deleteCaptchas(keys);
        }

        return flag;
    }

}
