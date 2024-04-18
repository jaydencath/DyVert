package com.DyVert.DyVert.Card;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


/**
 *
 * @author sergioguerra
 */
@Controller
@RequestMapping("/card")
public class CardController {
    @Autowired
    private CardService service;
   
    
    @GetMapping("/all-unreviewed")
    public String getAllUnreviewedCards(Model model) {
        model.addAttribute("unreviewedCardList", service.getUnreviewedCards());
        return "admin";
    }
    
    @GetMapping("/{id}")
    public String getSession(@PathVariable("id") long id, Model model) {
        model.addAttribute("card", service.getCard(id));
        return "view-card";
    }
    
    @PostMapping("/flag")
    public String flagCard(@RequestParam("id") long cardID, Model model) {
        service.flagCard(cardID);
        model.addAttribute("unreviewedCardList", service.getUnreviewedCards());
        return "admin";
    }

}
