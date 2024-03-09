package io.bootify.order.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.bootify.order.domain.Order;
import io.bootify.order.domain.User;
import io.bootify.order.model.OrderDTO;
import io.bootify.order.repos.OrderRepository;
import io.bootify.order.repos.UserRepository;
import io.bootify.order.util.NotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Sort;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final KafkaTemplate<String, String> kafkaTemplate;

    public OrderService(final OrderRepository orderRepository, final UserRepository userRepository, final KafkaTemplate<String, String> kafkaTemplate) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.kafkaTemplate = kafkaTemplate;
    }
    public List<OrderDTO> findAll() {
        final List<Order> orders = orderRepository.findAll(Sort.by("id"));
        return orders.stream()
                .map(order -> mapToDTO(order, new OrderDTO()))
                .toList();
    }

    public OrderDTO get(final Long id) {
        return orderRepository.findById(id)
                .map(order -> mapToDTO(order, new OrderDTO()))
                .orElseThrow(NotFoundException::new);
    }
    @Transactional
    public Long create(final OrderDTO orderDTO) throws JsonProcessingException {
        final Order order = new Order();
        ObjectMapper mapper = new ObjectMapper();
        mapToEntity(orderDTO, order);
        kafkaTemplate.send("order", mapper.writeValueAsString(order));
        return orderRepository.save(order).getId();
    }

    public void update(final Long id, final OrderDTO orderDTO) {
        final Order order = orderRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(orderDTO, order);
        orderRepository.save(order);
    }

    public void delete(final Long id) {
        orderRepository.deleteById(id);
    }

    private OrderDTO mapToDTO(final Order order, final OrderDTO orderDTO) {
        orderDTO.setId(order.getId());
        orderDTO.setProcessed(order.getProcessed());
        orderDTO.setUser(order.getUser() == null ? null : order.getUser().getId());
        return orderDTO;
    }

    private Order mapToEntity(final OrderDTO orderDTO, final Order order) {
        order.setProcessed(orderDTO.getProcessed());
        final User user = orderDTO.getUser() == null ? null : userRepository.findById(orderDTO.getUser())
                .orElseThrow(() -> new NotFoundException("user not found"));
        order.setUser(user);
        return order;
    }

}
