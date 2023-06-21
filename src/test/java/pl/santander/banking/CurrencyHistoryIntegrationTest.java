package pl.santander.banking;

import com.google.common.collect.Ordering;
import io.restassured.mapper.TypeRef;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import pl.santander.banking.model.ForeignExchangeDetails;

import java.util.List;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Created by Shakhzod Khashimov on 21/06/23.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext
@Sql(scripts = "classpath:/currency/latestCurrency.sql")
class CurrencyHistoryIntegrationTest {

    @LocalServerPort
    private int serverPort;

    @Test
    void shouldReturnOrderedByTimeHistoryForCurrency() {
        //given
        val currency = "USD/PLN";

        //when
        val result = given()
                .port(serverPort)
                .param("currency", currency)
                .when()
                .get("/currency/getHistoryForCurrency")
                .then()
                .statusCode(200)
                .extract().response().as(new TypeRef<List<ForeignExchangeDetails>>() {
                });

        //then
        val timeStamps = result.stream().map(ForeignExchangeDetails::getActualDate)
                .collect(Collectors.toList());
        assertTrue(Ordering.natural().reverse().isOrdered(timeStamps));
    }
}