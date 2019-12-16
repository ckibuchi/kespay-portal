package hr254.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ReportsController {
    @Value("${kespay.api.url}")
    private String kespayurl;

    @GetMapping("txnreports")
    public String txnreports (Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        try{
            System.out.println(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
            model.addAttribute("userName",auth.getName()); }
        catch(Exception e)
        {}
        model.addAttribute("apiUrl",kespayurl);
        return "txnreports";
    }
}
