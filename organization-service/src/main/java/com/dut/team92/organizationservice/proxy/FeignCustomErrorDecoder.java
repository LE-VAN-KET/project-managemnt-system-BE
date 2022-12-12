package com.dut.team92.organizationservice.proxy;

import com.dut.team92.common.exception.model.CommonErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import feign.codec.ErrorDecoder;
import io.micrometer.core.instrument.util.IOUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@Component
@Slf4j
public class FeignCustomErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String s, Response response) {
        int status = response.status();
        String errorMessage = null;
        InputStream inputStream = null;
//            capturing error message from response body.
        try {
            inputStream = response.body().asInputStream();
            String payload = IOUtils.toString(inputStream, StandardCharsets.UTF_8);
            ObjectMapper mapper = new ObjectMapper();
            CommonErrorResponse exceptionMessage = mapper.readValue(payload,
                    CommonErrorResponse.class);
            errorMessage = payload;
        } catch (IOException e) {
            log.error("IO Exception on reading exception message feign client" + e);
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                log.error("IO Exception on reading exception message feign client" + e);
            }
        }

        switch (status) {
            case 400:
                log.error("Error in request went through feign client {} ", errorMessage);
                //handle exception
                return new RuntimeException("Bad Request Through Feign");
            case 401:
                log.error("Error in request went through feign client {} ", errorMessage);
                //handle exception
                return new RuntimeException("Unauthorized Request Through Feign");
            case 404:
                log.error("Error in request went through feign client {} ", errorMessage);
                //handle exception
                return new RuntimeException("Unidentified Request Through Feign");
            default:
                log.error("Error in request went through feign client {} ", errorMessage);
                //handle exception
                return new RuntimeException("Common Feign Exception");
        }
    }
}
