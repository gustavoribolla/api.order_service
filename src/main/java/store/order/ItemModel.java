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
import store.product.ProductOut;


@Entity
@Table(name = "item")
@Setter @Accessors(fluent = true)
@NoArgsConstructor
public class ItemModel {

    @Id
    @Column(name = "id_item")
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "nr_quantity")
    private Integer quantity;

    @Column(name = "nr_total")
    private Double total;

    @Column(name = "id_order")
    private String idOrder;

    @Column(name = "id_product")
    private String id_product;

    public ItemModel(Item a) {
        this.quantity = a.quantity();
        this.total = a.total();
        this.idOrder = a.order().id();
        this.id_product = a.product().id();
    }

    public Item to() {

        return Item.builder()
            .id(this.id)
            .quantity(this.quantity)
            .total(this.total)
            .order(Order.builder().id(this.idOrder).build())
            .product(ProductOut.builder().id(this.id_product).build())
            .build();
        }

    @Override
    public String toString() {
        return "ItemModel{" +
            "id='" + id + '\'' +
            ", quantity=" + quantity +
            ", total=" + total +
            ", id_order='" + idOrder + '\'' +
            ", id_product='" + id_product + '\'' +
            '}';
    }
}
