package pl.santander.banking.service.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import pl.santander.banking.model.ForeignExchangeDetails;
import pl.santander.banking.service.CalculationService;
import pl.santander.banking.util.CsvStringToPojoUtils;

import java.io.IOException;
import java.util.List;

/**
 * Created by Shakhzod Khashimov on 21/06/23.
 */
@Service
@Log4j2
@RequiredArgsConstructor
public class Consumer {
    private final CalculationService calculationService;

    @KafkaListener(topics = "update-currency-info", groupId = "santander")
    public void consume(String message) {
        log.info(String.format("# -> Received currency update -> %s", message));
        List<ForeignExchangeDetails> foreignExchangeDetails = CsvStringToPojoUtils.parseCsvString(message);
        calculationService.calculateCommissionAndSave(foreignExchangeDetails);
    }

}
