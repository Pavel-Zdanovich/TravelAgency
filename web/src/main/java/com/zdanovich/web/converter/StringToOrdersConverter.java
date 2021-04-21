package com.zdanovich.web.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Log4j2
@Component
public class StringToOrdersConverter implements Converter<String, List<Sort.Order>> {

    private static final String DIRECTION = "direction";
    private static final String PROPERTY = "property";
    private static final String NULL_HANDLING = "nullHandling";

    private final ObjectMapper objectMapper;

    public StringToOrdersConverter(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public List<Sort.Order> convert(String source) {
        if (source.startsWith("[") && source.endsWith("]")) {
            return Arrays.stream(source.split(",")).map(this::convertInternal).collect(Collectors.toList());
        } else {
            return Collections.singletonList(this.convertInternal(source));
        }
    }

    private Sort.Order convertInternal(String source) {
        try {
            Map<String, String> map = this.objectMapper.readValue(source, objectMapper.getTypeFactory().constructMapType(Map.class, String.class, String.class));
            return new Sort.Order(
                    map.containsKey(DIRECTION) ? Sort.Direction.fromString(map.get(DIRECTION)) : null,
                    map.get(PROPERTY),
                    map.containsKey(NULL_HANDLING) ? Sort.NullHandling.valueOf(map.get(NULL_HANDLING)) : Sort.NullHandling.NATIVE
            );
        } catch (JsonProcessingException e) {
            String error = String.format("Deserialize %s error", Sort.Order.class);
            log.error(error, e);
            throw new RuntimeException(error, e);
        }
    }
}
