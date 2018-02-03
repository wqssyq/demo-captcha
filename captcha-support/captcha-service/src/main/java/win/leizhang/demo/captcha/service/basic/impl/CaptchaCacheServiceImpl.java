package win.leizhang.demo.captcha.service.basic.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import win.leizhang.demo.captcha.service.basic.CaptchaCacheService;
import win.leizhang.demo.captcha.utils.RedisKeyConstants;
import win.leizhang.demo.captcha.utils.RedisUtils;

import java.util.Set;

/**
 * Created by zealous on 2018/2/3.
 */
@Service
public class CaptchaCacheServiceImpl implements CaptchaCacheService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Value("${captcha.expire.second}")
    private Long CAPTCHA_EXPIRE_SECOND;

    @Autowired
    RedisUtils redisUtils;

    @Override
    public void setCaptcha(String code, String... key) {
        String redisKey = RedisKeyConstants.getUuid2SimpleCode(key);
        if (redisUtils.exists(redisKey)) {
            redisUtils.delete(redisKey);
        }
        redisUtils.set(redisKey, code, CAPTCHA_EXPIRE_SECOND);
    }

    @Override
    public void deleteCaptcha(String... key) {
        String redisKey = RedisKeyConstants.getUuid2SimpleCode(key);
        if (redisUtils.exists(redisKey)) {
            redisUtils.delete(redisKey);
        } else {
            //throw new CaptchaServiceException(CaptchaResultCode.CAPTCH_CODE_INVALID.code(), CaptchaResultCode.CAPTCH_CODE_INVALID.msg());
            log.error("error!");
        }
    }

    @Override
    public void deleteCaptchas(Set<String> keys) {
        redisUtils.delete(keys);
    }

    @Override
    public String getCaptcha(String... key) {

        String redisKey = RedisKeyConstants.getUuid2SimpleCode(key);
        if (redisUtils.exists(redisKey)) {
            String b64Code = redisUtils.get(redisKey);
            return b64Code;
        }
        //throw new CaptchaServiceException(CaptchaResultCode.CAPTCH_CODE_INVALID.code(), CaptchaResultCode.CAPTCH_CODE_INVALID.msg());
        log.error("error!");
        return null;
    }

    @Override
    public Set<String> getCaptchaKeys(String... key) {
        String redisKey = RedisKeyConstants.getUuid2SimpleCode(key);
        Set<String> keys = redisUtils.getKeys(redisKey);
        if (null == keys) {
            //throw new CaptchaServiceException(CaptchaResultCode.CAPTCH_CODE_INVALID.code(), CaptchaResultCode.CAPTCH_CODE_INVALID.msg());
            log.error("error!");
        }
        return keys;
    }

}
