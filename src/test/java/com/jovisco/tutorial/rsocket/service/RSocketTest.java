package com.jovisco.tutorial.rsocket.service;

import com.jovisco.tutorial.rsocket.dto.RequestDto;
import com.jovisco.tutorial.rsocket.dto.ResponseDto;
import com.jovisco.tutorial.rsocket.mapper.Mapper;
import io.rsocket.RSocket;
import io.rsocket.core.RSocketConnector;
import io.rsocket.transport.netty.client.TcpClientTransport;
import io.rsocket.util.DefaultPayload;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertEquals;

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

    @Test
    void testFireAndForgetWithMappedPayload() {
        var payload = DefaultPayload.create(Mapper.toPayload(new RequestDto(5)));
        rSocket.fireAndForget(payload)
                .as(StepVerifier::create)
                .verifyComplete();
    }

    @Test
    void testRequestResponse() {
        var payload = DefaultPayload.create(Mapper.toPayload(new RequestDto(5)));
        rSocket.requestResponse(payload)
                .map(p -> Mapper.fromPayload(p, ResponseDto.class))
                .doOnNext(System.out::println)
                .as(StepVerifier::create)
                .assertNext(r -> assertEquals(25, r.output()))
                // .expectNextCount(1L)
                .expectComplete()
                .verify();
    }
}