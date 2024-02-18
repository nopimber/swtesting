package ku.cs.flowerManagement;

import ku.cs.flowerManagement.entity.Field;
import ku.cs.flowerManagement.entity.Flower;
import ku.cs.flowerManagement.repository.FieldRepository;
import ku.cs.flowerManagement.repository.FlowerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class MockFields implements CommandLineRunner {
    @Autowired
    FieldRepository repository;

    @Autowired
    FlowerRepository flowerRepository;

    @Override
    public void run(String ...args) throws Exception{
        char beginChar = 'A';
        String[] FName = {"กุหลาบ","เบญจมาศ","ทานตะวัน","ทิวลิป","ลิลลี่","ไฮเดรนเยีย","คัตเตอร์","มากาเร็ต","เยอบีร่า","คาเนชั่น"};
        int[] harvestAmounts = {5,2,1,1,5,5,1,1,3,2};
        int[] times = {70,100,100,40,90,65,60,60,90,150};
        float[] prices = {10,20,10,15,15,15,15,10,15,15};
        for (int i = 0; i < 10; i++) {

            Field field = new Field();
            field.setIsUsed(false);
            field.setFieldName(String.valueOf(beginChar));
            field.setQueue(null);
            beginChar++;
            repository.save(field);

            Flower flower = new Flower();
            flower.setFName(FName[i]);
            flower.setHarvestAmount(harvestAmounts[i]);
            flower.setTime(times[i]);
            flower.setPrice(prices[i]);
            flowerRepository.save(flower);
        }
    }
}

