package pl.santander.banking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.santander.banking.model.ForeignExchangeDetailsEntity;

import java.util.List;

/**
 * Created by Shakhzod Khashimov on 21/06/23.
 */
@Repository
public interface ForeignExchangeDetailsRepository extends JpaRepository<ForeignExchangeDetailsEntity, Long> {

    ForeignExchangeDetailsEntity findTopByCurrencyOrderByActualDateDesc(String currency);

    List<ForeignExchangeDetailsEntity> findByCurrencyOrderByActualDateDesc(String currency);

}
