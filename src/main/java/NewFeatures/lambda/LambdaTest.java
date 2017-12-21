package NewFeatures.lambda;

import org.junit.Test;

/**
 * Created by Administrator on 2017/3/3.
 */
public class LambdaTest {
    @Test
    public void lambdaInterface() {
        Converter<String, Integer> converter = new Converter<String, Integer>() {
            @Override
            public Integer convert(String from) {
                return Integer.valueOf(from);
            }
        };
        System.out.println(converter.convert("23"));

        Converter<String, Integer> converter1 =
                (from) -> Integer.parseInt(from);
        System.out.println(converter1.convert("33"));

        Converter<String, Integer> converter2 = Integer::parseInt;
        System.out.println(converter2.convert("66"));
    }
}
