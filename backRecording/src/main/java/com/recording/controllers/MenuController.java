package com.recording.controllers;

import com.recording.core.model.AvailableSlot;
import com.recording.core.model.Order;
import com.recording.core.model.Settings;
import com.recording.core.model.User;
import com.recording.core.service.DBServiceOrder;
import com.recording.core.service.DBServiceSettings;
import com.recording.core.service.DBServiceUser;
import com.recording.core.service.DbServiceAvailableSlot;
import com.recording.core.utils.UserUtil;
import org.springframework.security.core.Authentication;
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
    private final DBServiceSettings serviceSettings;
    private final DBServiceUser serviceUser;
    private final DbServiceAvailableSlot serviceAvailableSlot;

    public MenuController(DBServiceOrder orderService, DBServiceSettings serviceSettings, DBServiceUser serviceUser, DbServiceAvailableSlot serviceAvailableSlot) {
        this.orderService = orderService;
        this.serviceSettings = serviceSettings;
        this.serviceUser = serviceUser;
        this.serviceAvailableSlot = serviceAvailableSlot;
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
    public String createOrder(Model model) {
        Optional<List<AvailableSlot>> allDays = serviceAvailableSlot.findAllDay();
        List<AvailableSlot> days = new ArrayList<>();
        allDays.ifPresent(days::addAll);
        model.addAttribute("days", days);
        return "createOrder";
    }

    @GetMapping({"/users"})
    public String createUser(Model model, Authentication auth) {
        Optional<List<User>> allUsers = serviceUser.findAll();
        List<User> users = new ArrayList<>();
        allUsers.ifPresent(users::addAll);
        model.addAttribute("users", users);
        model.addAttribute("isAdmin", UserUtil.isAdmin(auth));
        return "users";
    }

    @GetMapping({"/settings"})
    public String settings(Model model) {
        Optional<List<Settings>> allSettings = serviceSettings.findAll();
        List<Settings> settings = new ArrayList<>();
        allSettings.ifPresent(settings::addAll);
        model.addAttribute("settings", settings);
        return "settings";
    }

    @GetMapping({"/createUser"})
    public String createUser(Authentication auth) {
        if(UserUtil.isAdmin(auth)){
            return "createUser";
        }
        return "error";
    }
}
