package store.order;

import java.io.Serializable;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;
import store.account.AccountOut;
import java.util.Date;
import java.util.List;

@Builder
@Data @Accessors(fluent = true)
public class Order implements Serializable{

    private String id;
    private Double total;
    private List<Item> items;
    private Date date;
    private AccountOut account;
    
}