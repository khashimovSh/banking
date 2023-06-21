package pl.santander.banking;

import lombok.val;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import pl.santander.banking.model.ForeignExchangeDetails;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext
@Sql(scripts = "classpath:/currency/latestCurrency.sql")
class LatestCurrencyIntegrationTest {

    @LocalServerPort
    private int serverPort;

    @Test
    void shouldReturnLatestInfoForCurrency() {
        val result = given()
                .port(serverPort)
                .param("currency", "USD/PLN")
                .when()
                .get("/currency/getActualForCurrency")
                .then()
                .statusCode(200)
                .extract().response().as(ForeignExchangeDetails.class);

        //the latest date for 'USD/PLN' in resources/currency/latestCurrency.sql
        val expectedTime = "2023-06-21 20:51:38.102";

        assertThat(result.getActualDate().toString()).isEqualTo(expectedTime);
    }
}
