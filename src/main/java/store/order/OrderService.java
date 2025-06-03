package store.order;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.StreamSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ItemRepository itemRepository;

    public Order create(Order order) {
        order.date(new Date());
        final double[] valorTotal = {0.0};

        order.items().forEach(i -> {
            valorTotal[0] += i.quantity() * i.product().price();
            i.total(i.quantity() * i.product().price());
        });

        order.total(valorTotal[0]);

        OrderModel savedModel = orderRepository.save(new OrderModel(order));
        Order savedOrder = savedModel.to();

        order.items().forEach(i -> {
            i.order(savedOrder);
            ItemModel itemModel = new ItemModel(i);
            Item savedItem = itemRepository.save(itemModel).to();
            savedOrder.items().add(savedItem);
        });

        return savedOrder;
    }

    @Cacheable(value = "orderById", key = "#id")
    public Order findById(String id) {
        OrderModel model = orderRepository.findById(id)
            .orElseThrow(() -> new NoSuchElementException("Pedido não encontrado"));

        Order order = model.to();

        List<Item> items = StreamSupport
            .stream(itemRepository.findByIdOrder(id).spliterator(), false)
            .map(ItemModel::to)
            .toList();

        order.items(items);
        logger.debug("Order found: {}", order);
        return order;
    }

    @Cacheable(value = "ordersByAccount", key = "#idAccount")
    public List<Order> findAll(String idAccount) {
        List<Order> orders = StreamSupport
            .stream(orderRepository.findByIdAccount(idAccount).spliterator(), false)
            .map(OrderModel::to)
            .toList();

        orders.forEach(order -> {
            List<Item> items = StreamSupport
                .stream(itemRepository.findByIdOrder(order.id()).spliterator(), false)
                .map(ItemModel::to)
                .toList();
            order.items(items);
        });

        return orders;
    }
}
