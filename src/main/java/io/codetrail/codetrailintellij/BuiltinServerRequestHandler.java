package io.codetrail.codetrailintellij;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.diagnostic.Logger;
import io.codetrail.codetrailintellij.rpc.ide.DisplayRecordedAnnotationRequest;
import io.codetrail.codetrailintellij.rpc.ide.PrepareStoryRequest;
import io.codetrail.codetrailintellij.rpc.ide.RPCIDERequest;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.ide.RestService;

import java.io.IOException;
import java.nio.ByteBuffer;
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
        if (!request.headers().get("Content-Type").startsWith("application/json")) {
            sendStatus(HttpResponseStatus.UNSUPPORTED_MEDIA_TYPE, false, context.channel());
            return null;
        }

        // parse request body as json
        String body = request.content().toString(StandardCharsets.UTF_8);
        log.info("received request: " + body);
        ObjectMapper mapper = new ObjectMapper();

        try {
            RPCIDERequest rpcRequest = mapper.readValue(body, RPCIDERequest.class);

            if (rpcRequest == null) {
                sendStatus(HttpResponseStatus.BAD_REQUEST, false, context.channel());
                return null;
            }

            ApplicationManager.getApplication().invokeLater(() -> handleRpcRequest(rpcRequest));

            // TODO Send a proper response
            ByteBuffer jsonResp = ByteBuffer.wrap("{\"status\": \"ok\"}".getBytes(StandardCharsets.UTF_8));
            ByteBuf content = Unpooled.wrappedBuffer(jsonResp);
            HttpResponse resp = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,HttpResponseStatus.OK, content);
            sendResponse(request, context, resp);

            return null;
        } catch (Exception e) {
            log.error("could not parse request", e);
            sendStatus(HttpResponseStatus.BAD_REQUEST, false, context.channel());
        }

        return null;
    }

    @NotNull
    @Override
    protected String getServiceName() {
        return "codetrail";
    }

    @Override
    protected boolean isMethodSupported(@NotNull HttpMethod method) {
        return true;
    }

    private boolean handleRpcRequest(RPCIDERequest request) {
        ExtensionService service = ExtensionService.getInstance();

        // todo: handle all possible requests
        switch (request.getAction()) {
            case "desktop_ping":
                // happens when the IDE reconnects to make sure it is running, afterwards prepareStory is called
                // we don't need to do anything, but we'll acknowledge the ping
                break;
            case "prepareStory":
                // happens when we want to play a story
                PrepareStoryRequest storyRequest = (PrepareStoryRequest) request;
                service.displayStory(storyRequest.getPayload().getStory());
                break;
            case "displayRecordedAnnotation":
                // happens when we've added an annotation in the desktop companion
                DisplayRecordedAnnotationRequest annotationRequest = (DisplayRecordedAnnotationRequest) request;
                service.addAnnotation(annotationRequest.getPayload().getAnnotation());
                break;
            case "refreshAnnotations":
                // is not in use right now
                break;
            default:
                return false;
        }

        return true;
    }
}
