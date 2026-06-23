package com.college.complaints.controller;

import com.college.complaints.dto.CreateRequestForm;
import com.college.complaints.model.RequestStatus;
import com.college.complaints.service.RequestService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/student")
public class StudentController {

    private final RequestService requestService;

    public StudentController(RequestService requestService) {
        this.requestService = requestService;
    }

    @GetMapping("/dashboard")
    public String dashboard(Authentication authentication, Model model) {
        model.addAttribute("requestForm", new CreateRequestForm());
        model.addAttribute("requests", requestService.findForUser(authentication.getName()));
        model.addAttribute("statuses", RequestStatus.values());
        return "student-dashboard";
    }

    @PostMapping("/requests")
    public String createRequest(
            Authentication authentication,
            @Valid @ModelAttribute("requestForm") CreateRequestForm form,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("error", "Please complete every field before submitting.");
            return "redirect:/student/dashboard";
        }

        requestService.create(authentication.getName(), form);
        redirectAttributes.addFlashAttribute("success", "Your entry has been submitted.");
        return "redirect:/student/dashboard";
    }
}
