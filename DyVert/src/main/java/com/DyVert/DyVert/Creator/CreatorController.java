package com.DyVert.DyVert.Creator;

import com.DyVert.DyVert.Card.Card;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.DyVert.DyVert.Card.CardService;
import com.DyVert.DyVert.User.UserService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/creator")
public class CreatorController {
    @Autowired
    private UserService service;
    @Autowired
    private CardService cardService;
    
    @PostMapping("/publish")
    public String publishCard(@ModelAttribute Card card, @RequestParam("image") MultipartFile file, @RequestParam("userID") String userID) {
        if (!file.isEmpty()) {
            String fileName = StringUtils.cleanPath(file.getOriginalFilename());
            String uploadDir = "static/content-images";

            try {
                String resourceDir = getClass().getClassLoader().getResource(uploadDir).getPath();
                String absolutePath = Paths.get(resourceDir).toAbsolutePath().toString();
                Path filePath = Paths.get(absolutePath, fileName);
                Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
                card.setImagePath(fileName);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        // Save the card
        cardService.saveCard(card, userID);
        // Redirect to the creator page
        return "redirect:/creator/" + userID + "/create";
    }


    
    @GetMapping("/{id}/create")
    public String showCreateForm(@PathVariable("id") String userID, Model model) {
        List<Card> userCards = cardService.getUserCards(userID);
        model.addAttribute("userID", userID);
        model.addAttribute("card", new Card()); 
        model.addAttribute("userCards", userCards); 
        return "create";
    }
    
    @GetMapping("/{userID}/edit/{id}")
    public String editCard(@PathVariable("userID") String userID, @PathVariable("id") long cardID, Model model) {
        Card card = cardService.getCard(cardID);
        List<Card> userCards = cardService.getUserCards(userID);
        model.addAttribute("userID", userID);
        model.addAttribute("userCards", userCards);
        model.addAttribute("card", card);
        return "edit";
    }
    
    @PostMapping("/e")
    public String editCard(@ModelAttribute Card cardForm, @RequestParam("userID") String userID) {
        // Access form parameters via cardForm object
        String title = cardForm.getTitle();
        String synopsis = cardForm.getSynopsis();
        String genres = cardForm.getGenres();
        String imagePath = cardForm.getImagePath();
        String type = cardForm.getType();
        Long id = cardForm.getCardID();
        
        cardService.updateCard(id, title, synopsis, genres, imagePath, type);

        return "redirect:/creator/"+userID+"/create";
    }
    
    @PostMapping("/d")
    public String deleteC(@RequestParam("id") long cardID, @RequestParam("userID") String userID, Model model) {
        cardService.deleteCard(cardID);
        List<Card> userCards = cardService.getUserCards(userID);
        model.addAttribute("userID", userID);
        model.addAttribute("card", new Card()); // Add an empty card object to bind the form data
        model.addAttribute("userCards", userCards); // Add user cards to the model
        return "create";
    }
}
