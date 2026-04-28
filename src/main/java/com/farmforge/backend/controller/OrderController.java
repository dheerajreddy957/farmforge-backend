package com.farmforge.backend.controller;
import com.farmforge.backend.model.Order;
import com.farmforge.backend.model.Product;
import com.farmforge.backend.repository.OrderRepository;
import com.farmforge.backend.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ProductRepository productRepository;

    @GetMapping
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @PostMapping
    public Order placeOrder(@RequestBody Map<String, Object> body) {
        Long productId = Long.valueOf(body.get("productId").toString());
        String buyer = body.get("buyer").toString();
        Optional<Product> productOpt = productRepository.findById(productId);
        Order order = new Order();
        order.setOrderId("ORD-" + (int)(Math.random() * 9000 + 1000));
        order.setBuyer(buyer);
        if (productOpt.isPresent()) {
            Product p = productOpt.get();
            order.setProduct(p.getName());
            order.setFarmer(p.getFarmer());
            order.setTotal(p.getPrice());
            if (p.getQuantity() > 0) {
                p.setQuantity(p.getQuantity() - 1);
                productRepository.save(p);
            }
        }
        return orderRepository.save(order);
    }
}
