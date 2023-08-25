package tacos.kitchen;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import tacos.Order;

@Component
@Slf4j
public class KitchenUI {

  // 주문을 저장할 필드
  //private Order lastReceivedOrder;

  public void displayOrder(Order order) {
    // TODO: Beef this up to do more than just log the received taco.
    //       To display it in some sort of UI.
    // 주문 정보를 로그 및 내부 필드에 저장
    log.info("RECEIVED ORDER: " + order);
    //lastReceivedOrder = order;
  }

//  public Order getLastReceivedOrder() {
//    return lastReceivedOrder;
//  }
  
}
