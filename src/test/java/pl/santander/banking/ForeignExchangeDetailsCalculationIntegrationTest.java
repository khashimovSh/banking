package pl.santander.banking;

import io.restassured.mapper.TypeRef;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;
import pl.santander.banking.model.ForeignExchangeDetails;
import pl.santander.banking.service.kafka.Consumer;
import pl.santander.banking.service.kafka.Producer;

import java.math.BigDecimal;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext
@EmbeddedKafka(partitions = 1, brokerProperties = { "listeners=PLAINTEXT://localhost:9092", "port=9092" })
class ForeignExchangeDetailsCalculationIntegrationTest {

    @LocalServerPort
    private int serverPort;

    @Autowired
    private Producer producer;
    @Autowired
    private Consumer consumer;

    @Test
    void givenEmbeddedKafkaBroker_whenSendingWithSimpleProducer_thenMessageReceived() {
        val currencyInfo = "106, EUR/USD, 1.1000, 1.2000, 01-06-2020 12:01:01:001\n" +
                "107, EUR/JPY, 119.60, 119.90, 01-06-2020 12:01:02:002\n" +
                "108,GBP/USD,1.2500,1.2560,01-06-2020 12:01:02:002\n" +
                "109,GBP/USD, 1.2499, 1.2561, 01-06-2020 12:01:02:100\n" +
                "110, EUR/JPY, 119.61, 119.91, 01-06-2020 12:01:02:110";
        val result = given()
                .port(serverPort)
                .param("actualCurrencyInfo", currencyInfo)
                .when()
                .post("/currency/publishUpdate")
                .then()
                .statusCode(200)
                .extract().response().as(new TypeRef<List<ForeignExchangeDetails>>() {
                });

        assertThat(result.size()).isEqualTo(5);
        assertThat(result.get(0).getBid()).isEqualByComparingTo(new BigDecimal("1.0989000"));
        assertThat(result.get(0).getAsk()).isEqualByComparingTo(new BigDecimal("1.2012000"));
        assertThat(result.get(4).getBid()).isEqualByComparingTo(new BigDecimal("119.4903900"));
        assertThat(result.get(4).getAsk()).isEqualByComparingTo(new BigDecimal("120.0299100"));
    }
}
