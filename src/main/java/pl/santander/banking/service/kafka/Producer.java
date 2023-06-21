package pl.santander.banking.service.kafka;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

/**
 * Created by Shakhzod Khashimov on 21/06/23.
 */
@Service
@Log4j2
@AllArgsConstructor
public class Producer {
    private static final String TOPIC = "update-currency-info";

    private final KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage(String message) {
        log.info(String.format("# -> Currency update published -> %s", message));
        kafkaTemplate.send(TOPIC, message);
    }
}
