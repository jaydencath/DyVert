package com.DyVert.DyVert.Admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.DyVert.DyVert.Card.CardService;
import com.DyVert.DyVert.User.UserService;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private UserService service;
    @Autowired
    private CardService cardService;
    
    @GetMapping("/{id}/accounts")
    public String adminAccountsPage(@PathVariable("id") String userID, Model model) {
        model.addAttribute("accounts", service.findAllUsers());
        model.addAttribute("userID", userID);
        return "admin-accounts";
    }
    
    @GetMapping("/{id}/all-unreviewed")
    public String getAllUnreviewedCards(@PathVariable("id") String userID, Model model) {
        model.addAttribute("unreviewedCardList", cardService.getUnreviewedCards());
        model.addAttribute("userID", userID);
        return "admin";
    }
    
    @PostMapping("/deleteUser")
    public String deleteUser(@RequestParam("id") String accountID, @RequestParam("userID") String userID, Model model) {
        service.deleteUser(accountID);
        model.addAttribute("accounts", service.findAllUsers());
        model.addAttribute("userID", userID);
        return "admin-accounts";
    }
    
    @PostMapping("/delete")
    public String deleteCard(@RequestParam("id") long cardID, @RequestParam("userID") String userID, Model model) {
        cardService.deleteCard(cardID);
        model.addAttribute("unreviewedCardList", cardService.getUnreviewedCards());
        model.addAttribute("userID", userID);
        return "admin";
    }
    
    @PostMapping("/flag")
    public String flagCard(@RequestParam("id") long cardID, @RequestParam("userID") String userID, Model model) {
        cardService.flagCard(cardID);
        model.addAttribute("unreviewedCardList", cardService.getUnreviewedCards());
        model.addAttribute("userID", userID);
        return "admin";
    }
    
    @PostMapping("/review")
    public String reviewCard(@RequestParam("id") long cardID, @RequestParam("userID") String userID, Model model) {
        cardService.reviewCard(cardID);
        model.addAttribute("unreviewedCardList", cardService.getUnreviewedCards());
        model.addAttribute("userID", userID);
        return "admin";
    }
    
    @GetMapping("/{userID}/card/{id}")
    public String getCard(@PathVariable("userID") String userID, @PathVariable("id") long id, Model model) {
        model.addAttribute("card", cardService.getCard(id));
        model.addAttribute("userID", userID);
        return "admin-view-card";
    }
    
}
