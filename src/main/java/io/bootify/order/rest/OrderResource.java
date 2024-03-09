package io.bootify.order.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.bootify.order.model.OrderDTO;
import io.bootify.order.service.OrderService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(value = "/api/orders", produces = MediaType.APPLICATION_JSON_VALUE)
public class OrderResource {

    private final OrderService orderService;

    public OrderResource(final OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public ResponseEntity<List<OrderDTO>> getAllOrders() {
        return ResponseEntity.ok(orderService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDTO> getOrder(@PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(orderService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createOrder(@RequestBody @Valid final OrderDTO orderDTO) {
        final Long createdId;
        try {
            createdId = orderService.create(orderDTO);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateOrder(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final OrderDTO orderDTO) {
        orderService.update(id, orderDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteOrder(@PathVariable(name = "id") final Long id) {
        orderService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
