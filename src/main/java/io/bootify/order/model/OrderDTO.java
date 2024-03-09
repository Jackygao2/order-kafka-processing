package io.bootify.order.model;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class OrderDTO {

    private Long id;
    private Boolean processed;
    private Long user;

}
