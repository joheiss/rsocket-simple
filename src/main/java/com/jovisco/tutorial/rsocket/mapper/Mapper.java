package com.jovisco.tutorial.rsocket.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.rsocket.Payload;
import io.rsocket.util.DefaultPayload;
import lombok.SneakyThrows;

public class Mapper {

    @SneakyThrows
    public static Payload toPayload(Object obj) {
        var objectMapper = new ObjectMapper();
        var bytes = objectMapper.writeValueAsBytes(obj);
        return DefaultPayload.create(bytes);
    }

    @SneakyThrows
    public static <T> T fromPayload(Payload payload, Class<T> clazz) {
        var objectMapper = new ObjectMapper();
        var bytes = payload.getData().array();
        return objectMapper.readValue(bytes, clazz);
    }
}
