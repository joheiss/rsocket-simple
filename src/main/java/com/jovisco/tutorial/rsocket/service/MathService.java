package com.jovisco.tutorial.rsocket.service;

import com.jovisco.tutorial.rsocket.dto.ResponseDto;
import com.jovisco.tutorial.rsocket.mapper.Mapper;
import com.jovisco.tutorial.rsocket.dto.RequestDto;
import io.rsocket.Payload;
import io.rsocket.RSocket;
import reactor.core.publisher.Mono;

public class MathService implements RSocket {

    @Override
    public Mono<Void> fireAndForget(Payload payload) {
        // System.out.println("Receiving: " + payload.getDataUtf8());
        System.out.println("Receiving: " + Mapper.fromPayload(payload, RequestDto.class));
        return Mono.empty();
    }

    @Override
    public Mono<Payload> requestResponse(Payload payload) {
        System.out.println("Receiving: " + Mapper.fromPayload(payload, RequestDto.class));
        return Mono.fromSupplier(() -> {
            var requestDto = Mapper.fromPayload(payload, RequestDto.class);
            var responseDto = new ResponseDto(
                    requestDto.input(),
                    requestDto.input() * requestDto.input()
            );
            return Mapper.toPayload(responseDto);
        });
    }
}
