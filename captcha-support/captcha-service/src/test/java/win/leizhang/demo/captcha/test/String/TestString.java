package win.leizhang.demo.captcha.test.String;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

/**
 * Created by zealous on 2018/2/7.
 */
public class TestString {

    @Test
    public void test1() {

        String randomCode = "CRT" + RandomStringUtils.randomAlphanumeric(32);

        //String code = StringUtils;

        System.out.println(randomCode);
        //System.out.println(code);

    }

}
