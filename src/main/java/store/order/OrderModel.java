package store.order;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import store.account.AccountOut;
import java.util.ArrayList;
import java.util.Date;

@Entity
@Table(name = "orders")
@Setter @Accessors(fluent = true)
@NoArgsConstructor
public class OrderModel {

    @Id
    @Column(name = "id_order")
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "dt_date")
    private Date date;

    @Column(name = "nr_total")
    private Double total;

    @Column(name = "id_account")
    private String idAccount;

    public OrderModel(Order a) {
        this.id = a.id();
        this.date = a.date();
        this.total = a.total();
        this.idAccount = a.account().id();
    }

    public Order to() {
        return Order.builder()
            .id(this.id)
            .date(this.date)
            .total(this.total)
            .items(new ArrayList<>())
            .account(AccountOut.builder().id(this.idAccount).build())
            .build();
        }
}
