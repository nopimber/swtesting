package ku.cs.flowerManagement.service;

import com.google.gson.Gson;
import jakarta.persistence.EntityNotFoundException;
import ku.cs.flowerManagement.common.FieldStatus;
import ku.cs.flowerManagement.common.OrderMethods;
import ku.cs.flowerManagement.common.PlantStatus;
import ku.cs.flowerManagement.common.Status;
import ku.cs.flowerManagement.entity.Field;
import ku.cs.flowerManagement.entity.Flower;
import ku.cs.flowerManagement.entity.OrderFlower;
import ku.cs.flowerManagement.entity.Queue;
import ku.cs.flowerManagement.model.OrderFlowerRequest;
import ku.cs.flowerManagement.model.OrderFlowerResponse;
import ku.cs.flowerManagement.repository.FieldRepository;
import ku.cs.flowerManagement.repository.FlowerRepository;
import ku.cs.flowerManagement.repository.OrderRepository;
import ku.cs.flowerManagement.repository.QueueRepository;
import org.json.JSONException;
import org.json.JSONObject;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;


@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private FlowerRepository flowerRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private QueueRepository queueRepository;

    @Autowired
    private FieldRepository fieldRepository;

    // Get Orders
    public List<OrderFlowerRequest> getOrders() {
        List<OrderFlower> orders = orderRepository.findAll();

        List<OrderFlowerRequest> orderFlowerRequests = new ArrayList<>();

        for (OrderFlower ord:orders) {
            OrderFlowerRequest orderFlowerRequest = modelMapper.map(ord, OrderFlowerRequest.class);
            orderFlowerRequest.setFName(ord.getFlower().getFName());
            orderFlowerRequest.setFID(ord.getFlower().getFID());

            orderFlowerRequest.setFlowerPrice(ord.getPrice());

            orderFlowerRequests.add(orderFlowerRequest);
        }


        return orderFlowerRequests;
    }

    // Get order By Id
    public OrderFlowerRequest getOrderById(int id) {
        OrderFlower orderFlower = orderRepository.findById(id).orElse(null);
        if (orderFlower == null) {
            throw new EntityNotFoundException();
        }
        OrderFlowerRequest orderFlowerRequest = modelMapper.map(orderFlower, OrderFlowerRequest.class);
        orderFlowerRequest.setFName(orderFlower.getFlower().getFName());
        orderFlowerRequest.setFID(orderFlower.getFlower().getFID());
        return orderFlowerRequest;
    }

    // Cancel Order
    public void cancelOrderById(int id) {
        OrderFlower orderFlower = orderRepository.findById(id).orElse(null);
        if (orderFlower == null) {
            System.out.println("Order not found.");
            return;
        }
        orderFlower.setStatus(Status.CANCELED);
        orderRepository.save(orderFlower);
    }

    public void confirmOrderById(int id) {
        OrderFlower orderFlower = orderRepository.findById(id).orElse(null);
        if (orderFlower == null) {
            System.out.println("Order not found.");
            return;
        }
        orderFlower.setStatus(Status.COMPLETED);
        orderRepository.save(orderFlower);
    }

    public List<OrderFlowerResponse> getAllQueueOrders(){
        List<OrderFlower> queues = orderRepository.findAll();
        List<OrderFlowerResponse> orderFlowerResponses = new ArrayList<>();
        queues.forEach(queueOrder -> {
            OrderFlowerResponse orderFlowerResponse = new OrderFlowerResponse();
            orderFlowerResponse.setOrderMethods(queueOrder.getOrder_method());
            orderFlowerResponse.setOderNumber(queueOrder.getOrderNumber());
            orderFlowerResponse.setStatus(queueOrder.getStatus());
            orderFlowerResponse.setFlowerAmount(queueOrder.getQuantity());
            orderFlowerResponse.setFlowerName(queueOrder.getFlowerName());
            orderFlowerResponses.add(orderFlowerResponse);
        });

        return orderFlowerResponses;
    }

    private Long getLatestOrderNumber(){
        Long latestOrderNumber = orderRepository.findLatestOrderNumber();
        if(latestOrderNumber == null || latestOrderNumber == 0){
            latestOrderNumber = 0L;
        }
        return latestOrderNumber;
    }

    public OrderFlowerRequest createQueueOrder(OrderFlowerRequest orderFlowerRequest){
        OrderFlower orderFlower = new OrderFlower();
        Flower flower = flowerRepository.findById(orderFlowerRequest.getFID()).orElse(null);
        if(flower == null){
            System.out.println("Flower not found");
            return null;
        }
        orderFlower.setStatus(Status.PENDING);
        orderFlower.setQuantity(orderFlowerRequest.getOrderQuantity());
        orderFlower.setFlowerName(flower.getFName());
        orderFlower.setFlower(flower);
        orderFlower.setPrice(orderFlowerRequest.getOrderQuantity() * flower.getPrice());
        orderFlower.setPlant_status(PlantStatus.SEEDING);
        orderFlower.setOrderNumber(getLatestOrderNumber() + 1);
        if(orderFlowerRequest.getOrder_method() == OrderMethods.ORDER){
            orderFlower.setOrder_method(OrderMethods.ORDER);
            int fieldNumber = orderFlowerRequest.getOrderQuantity() / 90;
            Queue queue = new Queue();
            queue.setOrderFlower(orderFlower);
            queue.setPlantedAmount((100 - ( orderFlowerRequest.getOrderQuantity() % 100) + orderFlowerRequest.getOrderQuantity()));
            Set<ku.cs.flowerManagement.entity.Field> fields = fieldRepository.findAllByIsUsedFalseOrderByFieldNameAsc();
            Set<ku.cs.flowerManagement.entity.Field> selectFields = new HashSet<>();
            if(fields.isEmpty()){
                System.out.println("All fields are full");
                return null;
            }
            int currentIndex = 0;
            for (Field field :
                    fields) {
                if (currentIndex < fieldNumber){
                    selectFields.add(field);
                    field.setIsUsed(Boolean.TRUE);
                    field.setQueue(queue);
                    currentIndex++;
                }
                else{
                    break;
                }
            }
            queue.setFields(selectFields);
            queue.setFieldNeeded(fieldNumber);
            queue.setStatus(FieldStatus.PLANTING);
            orderFlower.setQueue(queue);
        }else{
            orderFlower.setOrder_method(OrderMethods.RETAIL);
            orderFlower.setQueue(null);
        }

        OrderFlower saved = orderRepository.save(orderFlower);
        OrderFlowerResponse orderFlowerResponse = new OrderFlowerResponse();
        orderFlowerResponse.setOrderMethods(saved.getOrder_method());
        orderFlowerResponse.setOderNumber(saved.getOrderNumber());
        orderFlowerResponse.setStatus(saved.getStatus());
        orderFlowerResponse.setFlowerAmount(saved.getQuantity());
        orderFlowerResponse.setFlowerName(saved.getFlowerName());
        return orderFlowerRequest;

    }

    public boolean CancelOrder(int orderId){
        OrderFlower orderFlower = orderRepository.findById(orderId).orElse(null);
        if(orderFlower == null){
            return false;
        }
        orderFlower.setStatus(Status.CANCELED);

        if(orderFlower.getOrder_method() == OrderMethods.ORDER){
            Queue queue = orderFlower.getQueue();
            queue.setFields(null);
        }

        orderRepository.save(orderFlower);
        return true;

    }

}
