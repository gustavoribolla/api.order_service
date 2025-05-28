package store.order;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import store.account.AccountOut;

@RestController
public class OrderResource implements OrderController {

    @Autowired
    private OrderService orderService;
    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);

    @Override
    public ResponseEntity<OrderOut> create(OrderIn orderIn, String idAccount) {
        Order order = OrderParser.to(orderIn);
        order.account(AccountOut.builder().id(idAccount).build());
        Order created = orderService.create(order);
        return ResponseEntity.ok().body(OrderParser.to(created));
    }

    @Override
    public ResponseEntity<List<OrderOut>> findAll(String idAccount){
        return ResponseEntity
            .ok()
            .body(orderService.findAll(idAccount)
                .stream()
                .map(OrderParser::to)
                .toList());
    }

    @Override
    public ResponseEntity<OrderOut> findById(String id, String idAccount) {
        Order order = orderService.findById(id);

        logger.debug("Order resource: " + order);
        logger.debug("Account id: " + idAccount);
        
        if (!order.account().id().equals(idAccount)) {
            logger.debug("Order not found for account: " + idAccount);
            return ResponseEntity
                .notFound()
                .build();
        }
        return ResponseEntity 
            .ok()
            .body(OrderParser.to(order));
    }
}
