package store.order;

import java.util.Date;
import java.util.List;
import java.util.stream.StreamSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import store.product.ProductController;
import store.product.ProductOut;

@Service
public class OrderService {

    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductController productController;

    @Autowired
    private ItemRepository itemRepository;
    
    public Order create(Order order) {
        order.date(new Date());
        final double[] valorTotal = {0.0};
        order.items().forEach(i -> {
            ProductOut prod = productController.findById(i.product().id()).getBody();
            valorTotal[0] += i.quantity() * prod.price();
            i.product(prod);
        });
        order.total(valorTotal[0]);
        Order savedOrder = orderRepository.save(new OrderModel(order)).to();
        
        order.items().forEach(i -> {
            i.order(savedOrder);
            i.total(i.quantity() * i.product().price());
            ItemModel itemModel = new ItemModel(i);
            Item savedItem = itemRepository.save(itemModel).to();
            savedOrder.items().add(savedItem);
        });
        return savedOrder;
    }

    public Order findById(String id) {
        Order order = orderRepository.findById(id).get().to();
        List<Item> items = StreamSupport
            .stream(itemRepository.findByIdOrder(id).spliterator(), false)
            .map(ItemModel::to)
            .toList();
        order.items(items);
        logger.debug("Order found: " + order);
        return order;   
    }

    public List<Order> findAll(String idAccount) {
        
        return StreamSupport
            .stream(orderRepository.findByIdAccount(idAccount).spliterator(), false)
            .map(OrderModel::to)
            .toList();
    }
}
