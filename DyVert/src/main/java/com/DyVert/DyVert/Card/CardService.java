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
}
