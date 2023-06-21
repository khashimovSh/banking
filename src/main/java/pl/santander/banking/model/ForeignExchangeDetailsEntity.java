package pl.santander.banking.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Objects;

/**
 * Created by Shakhzod Khashimov on 21/06/23.
 */
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "FX_DETAILS")
public class ForeignExchangeDetailsEntity {
    @Id
    private Long id;
    @Column(name = "currency")
    private String currency;
    @Column(name = "bid", scale = 7, precision = 30)
    private BigDecimal bid;
    @Column(name = "ask", scale = 7, precision = 30)
    private BigDecimal ask;
    @Column(name = "actual_date")
    private Timestamp actualDate;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        ForeignExchangeDetailsEntity that = (ForeignExchangeDetailsEntity) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
