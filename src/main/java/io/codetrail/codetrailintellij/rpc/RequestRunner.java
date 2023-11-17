package io.codetrail.codetrailintellij.rpc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import io.codetrail.codetrailintellij.rpc.requests.RPCRequest;
import org.apache.commons.io.Charsets;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import javax.annotation.Nullable;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.Callable;

public class RequestRunner implements Callable {
    private RPCRequest request;
    private ConnectionConfiguration config;

    public RequestRunner(RPCRequest request, ConnectionConfiguration config) {
        this.request = request;
        this.config = config;
    }

    @Override
    @Nullable
    public RPCResponse call() {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost post = new HttpPost(config.getConnection());

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String requestJson = null;

        try {
            requestJson = ow.writeValueAsString(request);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        // attention: for any payloads, make sure that all fields are **public** and **have getters**! otherwise, they'll not get serialized
        StringEntity requestEntity = new StringEntity(
            requestJson,
            "UTF-8"
        );

        post.setEntity(requestEntity);
        post.setHeader("Content-Type", "application/json");

        try {
            HttpResponse response = httpclient.execute(post);
            HttpEntity entity = response.getEntity();
            Header encodingHeader = entity.getContentEncoding();

            Charset encoding = encodingHeader == null ? StandardCharsets.UTF_8 :
            Charsets.toCharset(encodingHeader.getValue());

            String json = EntityUtils.toString(entity, StandardCharsets.UTF_8);
            ObjectMapper mapper = new ObjectMapper();

            return mapper.readValue(json, RPCResponse.class);
        } catch (IOException e) {
            return null;
        }
    }
}
