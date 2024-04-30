package com.DyVert.DyVert.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.DyVert.DyVert.Card.CardService;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author sergioguerra
 */
@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService service;
    @Autowired
    private CardService cardService;
    
//    @GetMapping("/home/{id}")
//    public String getCardToDisplay(@PathVariable("id") String id, Model model) {
//        model.addAttribute("user", service.getUser(id));
//        model.addAttribute("card", cardService.getUnseenCard(id, "All"));
//        return "user-view";
//    }
//    
    @GetMapping("/{id}/home/{type}")
    public String getMovieCardToDisplay(@PathVariable("id") String id, @PathVariable("type") String type, Model model) {
        if (type.equals("TV-Show")) {type = "TV Show";}
        
        model.addAttribute("user", service.getUser(id));
        model.addAttribute("card", cardService.getUnseenCard(id, type));
        return "user-view";
    }
    
    @PostMapping("/{id}/add-to-bucket")
    public String addToBucket(@RequestParam("cardID") long cardID, @RequestParam("userID") String userID, Model model) {
        service.likeCard(userID, cardID);
        service.addSeen(userID, cardID);
        model.addAttribute("user", service.getUser(userID));
        model.addAttribute("card", cardService.getUnseenCard(userID, "all"));
        return "user-view";
    }
    
    @PostMapping("/{id}/pass")
    public String markAsSeen(@RequestParam("cardID") long cardID, @RequestParam("userID") String userID, Model model) {
        service.addSeen(userID, cardID);
        model.addAttribute("user", service.getUser(userID));
        model.addAttribute("card", cardService.getUnseenCard(userID, "all"));
        return "user-view";
    }
    
    @GetMapping("/{id}/bucket")
    public String getBucket(@PathVariable("id") String id, Model model) {
        model.addAttribute("bucketList", cardService.getBucketList(id));
        model.addAttribute("userId", id);
        return "bucket";
    }
    
    @GetMapping("/{id}/bucket/card-detail/{cardID}")
    public String getCardDetail(@PathVariable("id") String id, @PathVariable("cardID") long cardID, Model model) {
        model.addAttribute("card", cardService.getCard(cardID));
        model.addAttribute("user", service.getUser(id));
        return "card-detail";
    }
    
    @PostMapping("/{id}/delete-from-bucket")
    public String deleteCardFromBucket(@RequestParam("id") String id, @RequestParam("cardID") long cardID, Model model) {
        service.deleteCardFromBucket(id, cardID);
        model.addAttribute("bucketList", cardService.getBucketList(id));
        model.addAttribute("userId", id);
        return "bucket";
    }
    
    @GetMapping("/{id}/bucket/movies")
    public String getBucketMovie(@PathVariable("id") String id, Model model) {
        model.addAttribute("bucketList", cardService.getTypeBucketList(id, "Movie"));
        model.addAttribute("userId", id);
        return "bucket";
    }
    
    @GetMapping("{id}/bucket/shows")
    public String getBucketShows(@PathVariable("id") String id, Model model) {
        model.addAttribute("bucketList", cardService.getTypeBucketList(id, "TV Show"));
        model.addAttribute("userId", id);
        return "bucket";
    }
    
    @GetMapping("{id}/bucket/books")
    public String getBucketBooks(@PathVariable("id") String id, Model model) {
        model.addAttribute("bucketList", cardService.getTypeBucketList(id, "Book"));
        model.addAttribute("userId", id);
        return "bucket";
    }
    
    @GetMapping("{id}/home/api-content/{type}")
    public String getCardFromAPI(@PathVariable("id") String id, @PathVariable("type") String type, Model model) {
        if (type.equals("TV-Show")) { type = "TV Show"; }
        
        model.addAttribute("user", service.getUser(id));
        model.addAttribute("card", cardService.getCardFromAPI(id, type));
        return "user-api-view";
    }
    
    @PostMapping("/{id}/add-to-bucket-api")
    public String addToBucketAPI(@RequestParam("cardID") long cardID, @RequestParam("userID") String userID, @RequestParam("type") String type, Model model) {
        service.likeAPICard(userID, cardID, type);
        model.addAttribute("user", service.getUser(userID));
        model.addAttribute("card", cardService.getCardFromAPI(userID, type));
        return "user-api-view";
    }
    
    @GetMapping("/{id}/bucket/api-content")
    public String getAPIBucket(@PathVariable("id") String id, Model model) {
        model.addAttribute("bucketList", cardService.getAPIBucketList(id, "all"));
        model.addAttribute("userId", id);
        return "bucket-api";
    }
    
    @GetMapping("/{id}/bucket/api-content/{type}")
    public String getAPIBucketType(@PathVariable("id") String id, @PathVariable("type") String type, Model model) {
        if (type.equals("TV-Show")) { type = "TV Show"; }
        
        model.addAttribute("bucketList", cardService.getAPIBucketList(id, type));
        model.addAttribute("userId", id);
        return "bucket-api";
    }
    
    @GetMapping("{id}/bucket/card-detail/{cardID}/api/{type}")
    public String getAPICardDetail(@PathVariable("id") String id, @PathVariable("cardID") long cardID, @PathVariable("type") String type, Model model) {
        model.addAttribute("card", cardService.getAPICardByID(cardID, type));
        model.addAttribute("user", service.getUser(id));
        return "card-detail-api";
    }
    
    @PostMapping("/{id}/delete-from-bucket-api")
    public String deleteCardFromAPIBucket(@RequestParam("id") String id, @RequestParam("cardID") long cardID, Model model) {
        service.deleteCardFromAPIBucket(id, cardID);
        model.addAttribute("bucketList", cardService.getAPIBucketList(id, "all"));
        model.addAttribute("userId", id);
        return "bucket-api";
    }
}