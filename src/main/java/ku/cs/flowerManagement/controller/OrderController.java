package ku.cs.flowerManagement.controller;

import ku.cs.flowerManagement.common.OrderMethods;
import ku.cs.flowerManagement.model.OrderFlowerRequest;
import ku.cs.flowerManagement.service.FlowerService;
import ku.cs.flowerManagement.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private FlowerService flowerService;

    @GetMapping("/order")
    public String getQueueOrderPage(Model model) {
        model.addAttribute("queue_ordering",orderService.getAllQueueOrders());
        model.addAttribute("flowers",flowerService.getFlowers());
        model.addAttribute("queue_create_order",new OrderFlowerRequest());
        model.addAttribute("selected_queue", new OrderFlowerRequest());
//        model.addAttribute("errorMessage",errorMessage);
        Map<OrderMethods,String> orderMethodsStringMap = new HashMap<>();
        orderMethodsStringMap.put(OrderMethods.RETAIL,"ปลีก");
        orderMethodsStringMap.put(OrderMethods.ORDER,"Order");
        model.addAttribute("order_methods", orderMethodsStringMap);
        // ต้องคืนค่าเป็นชื่อไฟล์ html template โดยในเมธอดนี้ คืนค่าเป็น queue-order.html
        return "/order";
    }

    @PostMapping("/order")
    public String createOrder(Model model, @ModelAttribute OrderFlowerRequest orderFlowerRequest,
                              @RequestParam(name = "cancelId",required = false,defaultValue = "0") Long id){

        if(orderFlowerRequest == null && id == 0){
            return "redirect:/order";
        }
        System.out.println("OID = " + orderFlowerRequest.getOrderNumber() );
        if (id != 0 && orderFlowerRequest.getOrderNumber() != 0){
            orderService.CancelOrder(orderFlowerRequest.getOrderNumber());
        }
        if(orderFlowerRequest.getOrderQuantity() % 90 != 0 || orderFlowerRequest.getOrderQuantity() == 0 || orderFlowerRequest.getOrderQuantity() > 900){
            return "redirect:/order";
        }
        else if(orderFlowerRequest != null){
            orderService.createQueueOrder(orderFlowerRequest);
        }
        return "redirect:/order";
    }

    @PutMapping("/order/{id}")
    public String cancelOrder(@PathVariable int id, Model model){
        orderService.cancelOrderById(id);
        model.addAttribute("order", new OrderFlowerRequest());
        model.addAttribute("orders", orderService.getOrders());
        model.addAttribute("options", flowerService.getFlowers());
        model.addAttribute("canceledOrder",orderService.getOrderById(id));
        return "order";
    }

    @PutMapping("/confirmOrder/{id}")
    public String confirmOrder(@PathVariable int id, Model model){
        orderService.confirmOrderById(id);
        model.addAttribute("order", new OrderFlowerRequest());
        model.addAttribute("orders", orderService.getOrders());
        model.addAttribute("options", flowerService.getFlowers());
        model.addAttribute("confirmOrder", orderService.getOrderById(id));
        return "order";
    }

//    @GetMapping("/order")
//    public String getQueueOrderPage(Model model) {
//        return "order";
//    }

//    new ResponseEntity<String>("redirect:/order", HttpStatus.OK);
}
