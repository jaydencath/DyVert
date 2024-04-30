package com.DyVert.DyVert.Card;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author sergioguerra
 */
@Service
public class CardService {
    @Autowired
    CardRepository repo;
    
    public List<Card> getUnreviewedCards() {
        return repo.findUnreviewed();
    }
    
    /**
     * Find one session by ID.
     * @param id
     * @return the user
     */
    public Card getCard(long id) {
        return repo.getCardById(id);
    }
    
    public void flagCard(long cardID) {
        repo.flagCard(cardID);
    }
    
    public void reviewCard(long cardID) {
        repo.reviewCard(cardID);
    }
    
    public List<Card> getAllReviewedCards() {
        return repo.getAllReviewedCards();
    }
    
    public Card getUnseenCard(String accountID, String type) {
        return repo.getUnseenCard(accountID, type);
    }
    
    public List<Card> getBucketList(String accountID) {
        return repo.getBucketList(accountID);
    }
    
    public List<Card> getAPIBucketList(String accountID, String type) {
        return repo.getAPIBucketList(accountID, type);
    }
    
    public List<Card> getTypeBucketList(String accountID, String type) {
        return repo.getTypeBucketList(accountID, type);
    }
    
    public Card getCardFromAPI(String accountID, String type) {
        return repo.getCardFromAPI(accountID, type);
    }
    
    public Card getAPICardByID(Long cardID, String type) {
        return repo.getAPICardByID(cardID, type);
    }
    
    public List<Card> getUserCards(String accountID) {
        return repo.getUserCards(accountID);
    }
    
    public void updateCard(Long id, String title, String synopsis, String genres, String imagePath, String type) {
        repo.updateCard(id, title, synopsis, genres, imagePath, type);
    }
    
    public void saveCard(Card card, String userID) {
        repo.saveCard(card, userID);
    }
    
    public void deleteCard(long cardID) {
        repo.deleteCard(cardID);
    }
}
