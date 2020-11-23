package app.config;

import feign.Response;
import feign.Util;
import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

/**
 * @作者  乾之轩
 * @功能：将Feign发回的错过格式进行封装,封装后的 e.getMesage 为一个json对象,可以通过Gson将该对象转化为map
 * @时间: 2018-07-24 11:27
 */
@Configuration
public class FeignErrorDecoder implements ErrorDecoder {
    @Override
    public Exception decode(String s, Response response) {
        try {
            // 这里直接拿到我们想过的异常信息
            String message = Util.toString(response.body().asReader());
            return new RuntimeException(message);
        } catch (IOException ignored) {
        }
        return decode(s, response);
    }
}
