package com.recording.controllers;

import com.recording.core.model.Order;
import com.recording.core.service.DBServiceOrder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class MenuController {
    private final DBServiceOrder orderService;

    public MenuController(DBServiceOrder orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/")
    public String index() {
        return "redirect:/orders";
    }

    @GetMapping({"/orders"})
    public String orders(Model model) {
        Optional<List<Order>> allUsers = orderService.findAll();
        List<Order> orders = new ArrayList<>();
        allUsers.ifPresent(orders::addAll);
        model.addAttribute("orders", orders);
        return "orders";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession httpSession = request.getSession();
        httpSession.invalidate();
        return "redirect:/orders";
    }

    @GetMapping({"/createOrder"})
    public String createOrder() {
        return "createOrder";
    }

    @GetMapping({"/createUser"})
    public String createUser() {
        return "createUser";
    }

    @GetMapping({"/settings"})
    public String settings() {
        return "settings";
    }
}
