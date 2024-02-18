package ku.cs.flowerManagement.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewSalesController {

    @GetMapping("/view-sales")
    public String getViewSalesPage(Model model) {
        return "view-sales";
    }
}
