package win.leizhang.demo.captcha.test.String;

import org.apache.commons.lang3.RandomUtils;
import org.junit.Test;

/**
 * Created by zealous on 2018/2/7.
 */
public class TestString {

    @Test
    public void test1() {

        Double code = RandomUtils.nextDouble();

        for (int i = 0; i < 101; i++) {
            Long code2 = RandomUtils.nextLong();
            System.out.println(code2 + ",size=" + code2.toString().length());
        }

    }

}
