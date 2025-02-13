package com.jovisco.tutorial.rsocket;

import com.jovisco.tutorial.rsocket.service.SocketAcceptorImpl;
import io.rsocket.core.RSocketServer;
import io.rsocket.transport.netty.server.TcpServerTransport;

public class Main {

    public static void main(String[] args) {

        var server = RSocketServer.create(new SocketAcceptorImpl());
        var closableChannel = server.bindNow((TcpServerTransport.create(6565)));

        // keep listening
        closableChannel.onClose().block();
    }
}