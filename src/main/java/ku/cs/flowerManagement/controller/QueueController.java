package ku.cs.flowerManagement.controller;

import ku.cs.flowerManagement.service.QueueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class QueueController {

    @Autowired
    private QueueService service;

    @GetMapping("/queue") //หน้าตารางคิว
    public String getQueuePage(Model model) {
        model.addAttribute("queueing", service.getAllQueues());
        // ต้องคืนค่าเป็นชื่อไฟล์ html template โดยในเมธอดนี้ คืนค่าเป็น queue.html
        System.out.println("Inside getQueuePage method");
        return "queue";
    }

    @PostMapping("/queue")
    public String runQueue(Model model){
        service.runQueue();
        return "redirect:queue";
    }
}
