package io.codetrail.codetrailintellij;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.intellij.openapi.diagnostic.Logger;
import io.codetrail.codetrailintellij.rpc.extension.RPCResponse;
import io.codetrail.codetrailintellij.rpc.ide.RPCIDERequest;
import io.codetrail.codetrailintellij.rpc.ide.RPCIDEResponse;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.ide.BuiltInServerManager;
import org.jetbrains.ide.RestService;

import com.intellij.util.ui.JBUI;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class BuiltinServerRequestHandler extends RestService {
    private static final Logger log = Logger.getInstance(BuiltinServerRequestHandler.class.getName());

    public BuiltinServerRequestHandler() {
        super();
    }

    @Nullable
    @Override
    public String execute(@NotNull QueryStringDecoder urlDecoder, @NotNull FullHttpRequest request,
                          @NotNull ChannelHandlerContext context) throws IOException {
        // make sure we're only receiving JSON requests
        if (!request.headers().get("Content-Type").equalsIgnoreCase("application/json")) {
            sendStatus(HttpResponseStatus.UNSUPPORTED_MEDIA_TYPE, false, context.channel());
            return null;
        }

        // parse request body as json
        String body = request.content().toString(StandardCharsets.UTF_8);
        log.info("received request: " + body);
        ObjectMapper mapper = new ObjectMapper();
        RPCIDERequest rpcRequest = mapper.readValue(body, RPCIDERequest.class);

        if (rpcRequest == null) {
            sendStatus(HttpResponseStatus.BAD_REQUEST, false, context.channel());
            return null;
        }

        RPCIDEResponse response = handleRpcRequest(rpcRequest);
        // todo: respond with generated response

        sendOk(request, context);
        return null;
    }

    @NotNull
    @Override
    protected String getServiceName() {
        return "codetrail";
    }

    private RPCIDEResponse handleRpcRequest(RPCIDERequest request) {
        // todo: handle all possible requests
        switch (request.getAction()) {
            case "desktop_ping":
            case "prepareStory":
            case "refreshAnnotations":
            case "displayRecordedAnnotation":
            default:
                break;
        }

        // todo: actually make a response here
        return new RPCIDEResponse();
    }
}
