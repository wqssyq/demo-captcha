package win.leizhang.demo.captcha.utils;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.concurrent.TimeUnit;

@Component
public class RedisUtils {

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    /**
     * 需要前缀，根据动态key值获取加上前缀的整个key
     *
     * @param prefix
     * @param key
     * @return String
     */
    @Deprecated
    public static String getKeyString(String prefix, String key) throws RuntimeException {
        if (StringUtils.isBlank(prefix) || StringUtils.isBlank(key)) {
            throw new IllegalArgumentException("prefix和key入参不能为空");
        }
        String redisKeyTemp = prefix + "|" + key;
        return redisKeyTemp;
    }

    /**
     * 设置缓存
     *
     * @param redisKey
     * @param value
     * @return boolean
     */
    public boolean set(String redisKey, Object value) throws RuntimeException {
        if (value == null)
            return false;
        try {
            if (value instanceof String) {
                stringRedisTemplate.opsForValue().set(redisKey, value.toString());
            } else {
                stringRedisTemplate.opsForValue().set(redisKey, JSON.toJSONString(value));
            }
            return true;
        } catch (Exception ex) {
            // no
        }
        return false;
    }

    /**
     * 设置缓存，加上失效时间
     *
     * @param redisKey
     * @param value
     * @param seconds
     * @return boolean
     */
    public boolean set(String redisKey, Object value, Long seconds) throws RuntimeException {
        boolean result = set(redisKey, value);
        if (result) {
            Boolean i = stringRedisTemplate.expire(redisKey, seconds, TimeUnit.SECONDS);
            return i;
        }
        return false;
    }

    /**
     * 通过key获得string value
     *
     * @param redisKey
     * @return
     */
    public String get(String redisKey) {
        Object value = stringRedisTemplate.opsForValue().get(redisKey);
        return value == null ? null : (String) value;
    }

    /**
     * 获得keys，模糊查询
     *
     * @param redisKey
     * @return
     */
    public Set<String> getKeys(String redisKey) {
        Set<String> value = stringRedisTemplate.keys(redisKey);
        if (0 == value.size()) {
            return null;
        }
        return value;
    }

    /**
     * 获得HashSet对象
     *
     * @param redisKey
     * @return Json String or String value
     */
    public <T> T get(String redisKey, Class<T> clazz) throws RuntimeException {
        String values = this.get(redisKey);
        T t = JSON.parseObject(values, clazz);
        return t;
    }

    /**
     * delete
     *
     * @param redisKey
     */
    public void delete(String redisKey) {
        stringRedisTemplate.delete(redisKey);
    }

    /**
     * 批量删除
     *
     * @param redisKeys
     */
    public void delete(Set<String> redisKeys) {
        stringRedisTemplate.delete(redisKeys);
    }

    /**
     * 设置Redis中的过期时间
     *
     * @param redisKey
     * @param seconds
     * @return
     */
    public boolean expire(String redisKey, int seconds) {
        Boolean i = stringRedisTemplate.expire(redisKey, seconds, TimeUnit.SECONDS);
        return i;
    }

    /**
     * 判断key是否已经存在Redis中
     *
     * @param redisKey
     * @return
     */
    public boolean exists(String redisKey) {
        return stringRedisTemplate.hasKey(redisKey);
    }

    /**
     * 获取key对应的Redis大小
     *
     * @param virtualCardNoSequence
     * @return
     */
    public long countList(String virtualCardNoSequence) {
        return stringRedisTemplate.opsForList().size(virtualCardNoSequence);
    }

    /**
     * FIXME 这个需要单独测试
     *
     * @param key
     * @return
     */
    public String lpop(String key) {
        Object value = stringRedisTemplate.opsForList().leftPop(key);
        return value == null ? null : (String) value;
    }

    /**
     * FIXME 这个需要单独测试
     *
     * @param key
     * @param value
     */
    public void rpush(String key, String value) {
        stringRedisTemplate.opsForList().rightPush(key, value);
    }

}
