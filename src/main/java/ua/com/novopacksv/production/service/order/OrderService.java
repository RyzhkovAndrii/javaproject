package ua.com.novopacksv.production.service.order;

import ua.com.novopacksv.production.model.orderModel.Order;
import ua.com.novopacksv.production.service.BaseEntityService;

public interface OrderService extends BaseEntityService<Order> {

    void addOrderItemToOrder(Long orderId, Long OrderItemId);

    void removeOrderItemFromOrder(Long orderId, Long OrderItemId);

}