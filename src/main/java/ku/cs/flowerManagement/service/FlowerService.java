package ku.cs.flowerManagement.service;

import ku.cs.flowerManagement.entity.Flower;
import ku.cs.flowerManagement.model.FlowerRequest;
import ku.cs.flowerManagement.repository.FlowerRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FlowerService {

    @Autowired
    private FlowerRepository flowerRepository;

    @Autowired
    private ModelMapper modelMapper;

    // Get Flower
    public List<FlowerRequest> getFlowers() {
        return flowerRepository.findAll().stream().map(flower -> modelMapper.map(flower, FlowerRequest.class)).collect(Collectors.toList());
    }

    // Get Flower By Id
    public FlowerRequest getFlowerById(int id) {
        return  modelMapper.map(flowerRepository.findById(id).orElse(null), FlowerRequest.class);
    }

    // Create Flower
    public FlowerRequest addFlower(FlowerRequest flowerRequest) {
        Flower flower = modelMapper.map(flowerRequest, Flower.class);
        return modelMapper.map(flowerRepository.save(flower), FlowerRequest.class);
    }

    // Update Flower
    public FlowerRequest updateFlower(FlowerRequest flowerRequest) {
//        System.out.println(flowerRequest.getFID());
        Flower flower1 =flowerRepository.findById(flowerRequest.getFID()).orElse(null);
        if (flower1 == null) return null;
        flower1.setFName(flowerRequest.getFName());
        flower1.setPrice(flowerRequest.getPrice());
        flower1.setTime(flowerRequest.getTime());
        flower1.setHow_to_plant(flowerRequest.getHow_to_plant());
        flower1.setHow_to_harvest(flowerRequest.getHow_to_harvest());
        flower1.setHarvestAmount(flower1.getHarvestAmount());
        flower1.setHow_to_take_care(flowerRequest.getHow_to_take_care());
        flowerRepository.save(flower1);
//        System.out.println("Flower1");
//        System.out.println(flower1.getFID());
        return modelMapper.map(flower1, FlowerRequest.class);
    }
}
