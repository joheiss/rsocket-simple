package com.jovisco.tutorial.rsocket.service;

import io.rsocket.RSocket;
import io.rsocket.core.RSocketConnector;
import io.rsocket.transport.netty.client.TcpClientTransport;
import io.rsocket.util.DefaultPayload;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import reactor.test.StepVerifier;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class RSocketTest {

    private RSocket rSocket;

    @BeforeAll
    void setUp() {
        rSocket = RSocketConnector.create()
                .connect(TcpClientTransport.create("localhost", 6565))
                .block();
    }

    // @RepeatedTest(3)
    @Test
    void testFireAndForget() {
        var payload = DefaultPayload.create("Hi RSocket!");
        rSocket.fireAndForget(payload)
                .as(StepVerifier::create)
                .verifyComplete();
    }
}