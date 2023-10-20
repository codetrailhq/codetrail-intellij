package io.codetrail.codetrailintellij.rpc;

public class ConnectionConfiguration {
    private String endpoint;
    private int port;

    public ConnectionConfiguration(String endpoint, int port) {
        this.endpoint = endpoint;
        this.port = port;
    }

    public String getConnection() {
        return endpoint + ":" + port + "/rpc";
    }
}
