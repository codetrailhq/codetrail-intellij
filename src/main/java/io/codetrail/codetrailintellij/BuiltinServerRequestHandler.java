package io.codetrail.codetrailintellij;

import com.intellij.openapi.diagnostic.Logger;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.QueryStringDecoder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.ide.BuiltInServerManager;
import org.jetbrains.ide.RestService;

import java.io.IOException;

public class BuiltinServerRequestHandler extends RestService {
    private static final Logger log = Logger.getInstance(BuiltinServerRequestHandler.class.getName());

    public BuiltinServerRequestHandler() {
        super();
    }

    @Nullable
    @Override
    public String execute(@NotNull QueryStringDecoder urlDecoder, @NotNull FullHttpRequest request,
                          @NotNull ChannelHandlerContext context) throws IOException {
        // todo: need to define what the desktop requests here
        sendOk(request, context);
        return null;
    }

    @NotNull
    @Override
    protected String getServiceName() {
        return "codetrail";
    }
}
