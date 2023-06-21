package pl.santander.banking.model;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * Created by Shakhzod Khashimov on 21/06/23.
 */
@Data
@Builder
public class ForeignExchangeDetails {

    private Long id;
    private String currency;
    private BigDecimal bid;
    private BigDecimal ask;
    private Timestamp actualDate;

}
