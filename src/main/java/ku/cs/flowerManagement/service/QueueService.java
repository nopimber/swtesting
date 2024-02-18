package ku.cs.flowerManagement.service;

import ku.cs.flowerManagement.entity.Field;
import ku.cs.flowerManagement.entity.OrderFlower;
import ku.cs.flowerManagement.entity.Queue;
import ku.cs.flowerManagement.model.QueueResponse;
import ku.cs.flowerManagement.repository.FieldRepository;
import ku.cs.flowerManagement.repository.QueueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class QueueService {

    @Autowired
    private QueueRepository repository;

    @Autowired
    private FieldRepository fieldRepository;

    public List<QueueResponse> getAllQueues(){
        List<Queue> queues = repository.findAllBySoftDeleteIsFalse();
        List<QueueResponse> queueResponses = new ArrayList<>();
        for (Queue queue : queues
        ){
            QueueResponse queueResponse = new QueueResponse();
            queueResponse.setId(queue.getId());
            queueResponse.setStatus(queue.getStatus());
            queueResponse.setFlowerName(queue.getOrderFlower().getFlower().getFName());
            List<String> fieldsNames = new ArrayList<>();
            queue.getFields().forEach(field -> fieldsNames.add( field.getFieldName() ));
            queueResponse.setFieldName(String.join(",",fieldsNames));
            queueResponse.setFieldNeeded(queue.getFieldNeeded());
            queueResponse.setPlantedAmount(queue.getPlantedAmount());
            queueResponse.setFlowerAmount(queue.getOrderFlower().getQuantity());
            queueResponse.setOrderNumber(queue.getOrderFlower().getOID());

            queueResponses.add(queueResponse);

        }
        return queueResponses;
    }

    public void runQueue(){
        Queue firstQueue = repository.findMinQueue();
        Set<Field> fields = firstQueue.getFields();
        fields.forEach(field -> field.setIsUsed(false));
        fieldRepository.saveAll(fields);
        firstQueue.setFields(null);
        firstQueue.setSoftDelete(true);
        repository.save(firstQueue);
    }
}
