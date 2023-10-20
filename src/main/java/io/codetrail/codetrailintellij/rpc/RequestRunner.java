package io.codetrail.codetrailintellij.rpc;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONObject;

public class RequestRunner implements Runnable {
    private RPCRequest request;
    private ConnectionConfiguration config;

    public RequestRunner(RPCRequest request, ConnectionConfiguration config) {
        this.request = request;
        this.config = config;
    }

    @Override
    public void run() {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost post = new HttpPost(config.getConnection());

        JSONObject obj = new JSONObject(request);

        StringEntity requestEntity = new StringEntity(
            obj.toString(),
            "UTF-8"
        );

        post.setEntity(requestEntity);
        post.setHeader("Content-Type", "application/json");

        try {
            HttpResponse response = httpclient.execute(post);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
