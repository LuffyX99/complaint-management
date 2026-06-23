package com.college.complaints.controller;

import com.college.complaints.dto.CreateUserForm;
import com.college.complaints.dto.StatusUpdateForm;
import com.college.complaints.model.RequestStatus;
import com.college.complaints.model.Role;
import com.college.complaints.service.RequestService;
import com.college.complaints.service.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final RequestService requestService;
    private final UserService userService;

    public AdminController(RequestService requestService, UserService userService) {
        this.requestService = requestService;
        this.userService = userService;
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("requests", requestService.findAll());
        model.addAttribute("users", userService.findAll());
        model.addAttribute("userForm", new CreateUserForm());
        model.addAttribute("statusForm", new StatusUpdateForm());
        model.addAttribute("statuses", RequestStatus.values());
        model.addAttribute("roles", Role.values());
        model.addAttribute("counts", requestService.statusCounts());
        return "admin-dashboard";
    }

    @PostMapping("/users")
    public String createUser(
            @Valid @ModelAttribute("userForm") CreateUserForm form,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("error", "Please check the user fields and try again.");
            return "redirect:/admin/dashboard";
        }

        try {
            userService.createUser(form);
            redirectAttributes.addFlashAttribute("success", "User ID created successfully.");
        } catch (IllegalArgumentException ex) {
            redirectAttributes.addFlashAttribute("error", ex.getMessage());
        }
        return "redirect:/admin/dashboard";
    }

    @PostMapping("/users/{id}/toggle")
    public String toggleUser(@PathVariable String id, RedirectAttributes redirectAttributes) {
        userService.toggleEnabled(id);
        redirectAttributes.addFlashAttribute("success", "User access updated.");
        return "redirect:/admin/dashboard";
    }

    @PostMapping("/requests/{id}/status")
    public String updateStatus(
            @PathVariable String id,
            @Valid @ModelAttribute StatusUpdateForm form,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("error", "Choose a valid status before updating.");
            return "redirect:/admin/dashboard";
        }

        requestService.updateStatus(id, form);
        redirectAttributes.addFlashAttribute("success", "Request status updated.");
        return "redirect:/admin/dashboard";
    }
}
