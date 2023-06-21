package pl.santander.banking;

import lombok.val;
import org.junit.jupiter.api.Test;
import pl.santander.banking.util.CsvStringToPojoUtils;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

/**
 * Created by Shakhzod Khashimov on 21/06/23.
 */
class CsvStringToPojoUtilsTest {

    @Test
    void shouldConvertCsvStringToPojo() {
        //given
        val csvString = "109,GBP/USD, 1.2499, 1.2561, 01-06-2020 12:01:02:100\n" +
                "110, EUR/JPY, 119.61, 119.91, 01-06-2020 12:01:02:110";

        //when
        val result = CsvStringToPojoUtils.parseCsvString(csvString);

        //then
        assertThat(result.size()).isEqualTo(2);
        assertThat(result.get(1).getCurrency()).isEqualTo("EUR/JPY");
    }

    @Test
    void shouldThrowExceptionWhenEnteredExtraField() {
        val csvString = "107, EUR/JPY, 119.60, 119.90, 01-06-2020 12:01:02:002, WAW";
        try {
            val result = CsvStringToPojoUtils.parseCsvString(csvString);
        } catch (IllegalStateException e) {
            assertThat(e.getMessage()).isEqualTo("Data with extra field inserted");
        }
    }

    @Test
    void shouldThrowExceptionWhenIllegalFormatFieldEntered() {
        val csvString = "110, EUR/JPY, 119.6k, 119.91, 01-06-2020 12:01:02:110";
        try {
            val result = CsvStringToPojoUtils.parseCsvString(csvString);
        } catch (IllegalStateException e) {
            assertThat(e.getMessage()).isEqualTo("Entered wrong format data");
        }
    }

}
