package win.leizhang.demo.captcha.service.business.impl;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import win.leizhang.demo.captcha.api.dto.captcha.CaptchaInputBO;
import win.leizhang.demo.captcha.api.exception.CaptchaResultCode;
import win.leizhang.demo.captcha.api.utils.ExceptionUtil;
import win.leizhang.demo.captcha.service.basic.CaptchaCacheService;
import win.leizhang.demo.captcha.service.business.CaptchaVerifyService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zealous on 2018/2/7.
 */
@Service
public class CaptchaVerifyServiceImpl implements CaptchaVerifyService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    CaptchaCacheService captchaCacheService;

    @Override
    public boolean verify(CaptchaInputBO bo) {
        log.info("入参是 ==> {}", JSON.toJSONString(bo));

        // 初始化
        String uuid = bo.getUuid();
        String codeInput = bo.getCode();

        boolean flag = false;
        List<String> uuidList = (!CollectionUtils.isEmpty(bo.getUuids())) ? bo.getUuids() : new ArrayList<>();
        uuidList.add(uuid);

        // 转数组
        String[] uuids = uuidList.toArray(new String[uuidList.size()]);

        // 解码前
        String codeB64 = captchaCacheService.getCaptcha(uuids);
        // 不能为空
        if (StringUtils.isBlank(codeB64)) {
            log.info("取到的验证码为空！");
            throw ExceptionUtil.buildBzException(CaptchaResultCode.CAPTCH_CODE_INVALID);
        }

        // 转换，解码后
        String codeDecrypt = new String(Base64.decodeBase64(codeB64));

        // 校验，忽略大小写
        if (StringUtils.equalsIgnoreCase(codeInput, codeDecrypt)) {
            flag = true;

            // 删除缓存
            captchaCacheService.deleteCaptcha(uuids);
            // 批量删除，这是耗性能的操作
            //Set<String> keys = captchaCacheService.getCaptchaKeys(resourceId, "*");
            //captchaCacheService.deleteCaptchas(keys);
        }

        return flag;
    }

}
