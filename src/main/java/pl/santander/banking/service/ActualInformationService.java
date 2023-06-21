package pl.santander.banking.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.santander.banking.model.ForeignExchangeDetails;
import pl.santander.banking.model.ForeignExchangeDetailsMapper;
import pl.santander.banking.repository.ForeignExchangeDetailsRepository;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Shakhzod Khashimov on 21/06/23.
 */
@Service
@RequiredArgsConstructor
public class ActualInformationService {

    private final ForeignExchangeDetailsRepository foreignExchangeDetailsRepository;

    public List<ForeignExchangeDetails> getLatestPublishedCurrencyInfo() {
        return foreignExchangeDetailsRepository.findAll()
                .stream().map(ForeignExchangeDetailsMapper.INSTANCE::fromEntity)
                .collect(Collectors.toList());
    }

    public ForeignExchangeDetails getLatestInfoForCurrency(String currency) {
        return ForeignExchangeDetailsMapper.INSTANCE.fromEntity(foreignExchangeDetailsRepository
                .findTopByCurrencyOrderByActualDateDesc(currency));
    }

    public List<ForeignExchangeDetails> getOrderedHistoryForCurrency(String currency) {
        return foreignExchangeDetailsRepository.findByCurrencyOrderByActualDateDesc(currency)
                .stream().map(ForeignExchangeDetailsMapper.INSTANCE::fromEntity)
                .collect(Collectors.toList());
    }
}
