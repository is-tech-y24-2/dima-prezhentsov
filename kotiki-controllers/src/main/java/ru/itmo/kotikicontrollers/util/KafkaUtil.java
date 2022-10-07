package ru.itmo.kotikicontrollers.util;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class KafkaUtil {
    @Autowired
    KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage(String topic, Map<String, String> data) {
        String message = serializeToJson(data);
        kafkaTemplate.send(topic, message);
    }

    public static String serializeToJson(Object object) {
        Gson gson = new Gson();
        String json = gson.toJson(object);
        return json;
    }

    public static <T> T deserializeFromJson(String json, Class<T> type) {
        Gson gson = new Gson();
        T object = gson.fromJson(json, type);
        return object;
    }
}
