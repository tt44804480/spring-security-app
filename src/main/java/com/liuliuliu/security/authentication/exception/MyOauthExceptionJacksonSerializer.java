package com.liuliuliu.security.authentication.exception;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;

/**
 * MyOAuth2Exception的序列化器
 *
 * @Author lty
 * @Date 2019/11/15 11:52
 */
public class MyOauthExceptionJacksonSerializer extends StdSerializer<MyOAuth2Exception> {

    public MyOauthExceptionJacksonSerializer() {
        super(MyOAuth2Exception.class);
    }

    @Override
    public void serialize(MyOAuth2Exception value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        //可以不用下面的代码，只用 jsonGenerator.writeRawValue(myJsonString)  输出自定义字符串
        gen.writeStartObject();
        gen.writeStringField("errorCode", String.valueOf(value.getHttpErrorCode()));
        gen.writeStringField("message", value.getMessage());
        gen.writeStringField("path", request.getServletPath());
        gen.writeStringField("timestamp", String.valueOf(System.currentTimeMillis()));
        if (value.getAdditionalInformation()!=null) {
            for (Map.Entry<String, String> entry : value.getAdditionalInformation().entrySet()) {
                String key = entry.getKey();
                String add = entry.getValue();
                gen.writeStringField(key, add);
            }
        }
        gen.writeEndObject();
    }
}
