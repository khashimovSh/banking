package pl.santander.banking.util;

import lombok.experimental.UtilityClass;
import pl.santander.banking.model.ForeignExchangeDetails;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Shakhzod Khashimov on 21/06/23.
 */
@UtilityClass
public class CsvStringToPojoUtils {

    public static List<ForeignExchangeDetails> parseCsvString(String message) {
        String[] eachLine = message.split("\n");
        List<ForeignExchangeDetails> foreignExchangeDetails = new ArrayList<>();
        for (String line : eachLine) {
            String[] values = line.split(",");
            if (values.length != 5) {
                throw new IllegalStateException("Data with extra field inserted");
            }
            try {
                foreignExchangeDetails.add(ForeignExchangeDetails.builder()
                        .id(Long.valueOf(values[0].trim()))
                        .currency(values[1].trim())
                        .bid(new BigDecimal(values[2].trim()))
                        .ask(new BigDecimal(values[3].trim()))
                        .actualDate(Timestamp.valueOf(LocalDateTime.parse(values[4].trim(),
                                DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss:SSS")))).build());
            } catch (IllegalArgumentException | DateTimeParseException e) {
                throw new IllegalStateException("Entered wrong format data", e);
            }

        }
        return foreignExchangeDetails;
    }
}
