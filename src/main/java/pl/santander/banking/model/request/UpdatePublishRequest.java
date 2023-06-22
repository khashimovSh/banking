package pl.santander.banking.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Created by Shakhzod Khashimov on 21/06/23.
 */
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Getter
@Builder
public class UpdatePublishRequest {

    private String actualCurrencyInfo;

}
