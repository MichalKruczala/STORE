package pl.camp.it.book.store.controllers;

import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.camp.it.book.store.exceptions.NotEnoughBookException;
import pl.camp.it.book.store.services.IOrderService;
import pl.camp.it.book.store.session.SessionObject;

@Controller
public class OrderController {

    @Autowired
    IOrderService orderService;

    @Resource
    SessionObject sessionObject;

    @RequestMapping(path = "/order/confirm", method = RequestMethod.GET)
    public String orderConfirmation() {
        try {
            this.orderService.confirmOrder();
        } catch (NotEnoughBookException e) {
            this.sessionObject.setInfo("Niepoprawna ilość produktów !!");
            return "redirect:/cart";
        }
        return "redirect:/cart";
    }

    @RequestMapping(path = "/order", method = RequestMethod.GET)
    public String order(Model model) {
        model.addAttribute("sessionObject", this.sessionObject);
        model.addAttribute("orders",
                this.orderService.getOrderForCurrentUser());
        return "orders";
    }
}
