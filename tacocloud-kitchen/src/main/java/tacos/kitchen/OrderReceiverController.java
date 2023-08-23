package tacos.kitchen;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import tacos.Order;

@Profile({"jms-template", "rabbitmq-template", "kafka-listener"})
@Controller
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderReceiverController {

  private final KitchenUI kitchenUI;
  private final OrderReceiver orderReceiver;
  
  @GetMapping("/receive")
  public String receiveOrder(Model model) {
    Order order = null;

    // kafka는 메세지가 남아있기 때문에 order 객체를 가지고 올 수 있음
    order = orderReceiver.receiveOrder();
    if (order != null) {
      model.addAttribute("order", order);
      return "receiveOrder";

    } else {
      order = kitchenUI.getLastReceivedOrder();

      if (order != null) {
        model.addAttribute("order", order);
        return "receiveOrder";

      } else {
        return "noOrder";
      }
    }
  }
}
