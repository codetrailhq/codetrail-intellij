package io.codetrail.codetrailintellij.rpc.extension;

class RPCResponseError {
    private String code;
    private String message;

    public RPCResponseError(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
