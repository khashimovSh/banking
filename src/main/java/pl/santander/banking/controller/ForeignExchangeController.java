package pl.santander.banking.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.santander.banking.model.ForeignExchangeDetails;
import pl.santander.banking.model.request.UpdatePublishRequest;
import pl.santander.banking.service.ActualInformationService;
import pl.santander.banking.service.kafka.Producer;

import java.util.List;

/**
 * Created by Shakhzod Khashimov on 21/06/23.
 */
@RestController
@RequestMapping(value = "/currency")
@AllArgsConstructor
public class ForeignExchangeController {

    private final ActualInformationService actualInformationService;
    private final Producer producer;

    @PostMapping(value = "/publishUpdate")
    public List<ForeignExchangeDetails> publishNewFXDetails(UpdatePublishRequest updatePublishRequest) throws InterruptedException {
        producer.sendMessage(updatePublishRequest.getActualCurrencyInfo());
        Thread.sleep(500);
        return actualInformationService.getLatestPublishedCurrencyInfo();
    }

    @GetMapping(value = "/getActualForCurrency")
    public ForeignExchangeDetails getActualCurrencyFor(@RequestParam String currency) {
        return actualInformationService.getLatestInfoForCurrency(currency);
    }

    @GetMapping(value = "/getHistoryForCurrency")
    public List<ForeignExchangeDetails> getHistoryOfCurrencyFor(@RequestParam String currency) {
        return actualInformationService.getOrderedHistoryForCurrency(currency);
    }

}
