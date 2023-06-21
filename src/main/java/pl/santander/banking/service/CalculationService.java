package pl.santander.banking.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.santander.banking.model.ForeignExchangeDetails;
import pl.santander.banking.model.ForeignExchangeDetailsMapper;
import pl.santander.banking.repository.ForeignExchangeDetailsRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CalculationService {

    private static final BigDecimal PERCENTAGE = new BigDecimal("0.1");
    private final ForeignExchangeDetailsRepository foreignExchangeDetailsRepository;

    @Transactional
    public List<ForeignExchangeDetails> calculateCommissionAndSave(List<ForeignExchangeDetails> foreignExchangeDetails) {
        calculateBidAndAsk(foreignExchangeDetails);
        return saveEntities(foreignExchangeDetails);
    }

    private void calculateBidAndAsk(List<ForeignExchangeDetails> foreignExchangeDetails) {
        foreignExchangeDetails.forEach(exchangeDetails -> {
            exchangeDetails.setBid(getProcessedCommissionValue(exchangeDetails.getBid(), true));
            exchangeDetails.setAsk(getProcessedCommissionValue(exchangeDetails.getAsk(), false));
        });
    }

    private BigDecimal getProcessedCommissionValue(BigDecimal numberToCalculate, boolean isBid) {
        BigDecimal commission = numberToCalculate.multiply(PERCENTAGE.divide(BigDecimal.valueOf(100f)));
        if (isBid) {
            return numberToCalculate.subtract(commission);
        }
        return numberToCalculate.add(commission);
    }

    private List<ForeignExchangeDetails> saveEntities(List<ForeignExchangeDetails> foreignExchangeDetails) {
        return foreignExchangeDetails.stream()
                .map(ForeignExchangeDetailsMapper.INSTANCE::toEntity)
                .map(foreignExchangeDetailsRepository::save)
                .map(ForeignExchangeDetailsMapper.INSTANCE::fromEntity)
                .collect(Collectors.toList());
    }
}
