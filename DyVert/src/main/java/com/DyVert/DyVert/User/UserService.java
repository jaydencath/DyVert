package com.DyVert.DyVert.User;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author sergioguerra
 */
@Service
public class UserService {
    @Autowired
    UserRepository repo;
    
    public List<User> findAllUsers() {
        return repo.findAllUsers();
    }
    
    public User getUser(String accountID) {
        return repo.getUserById(accountID);
    }
    
    public void likeCard(String accountID, long cardID) {
        repo.likeCard(accountID, cardID);
    }
    
    public void likeAPICard(String accountID, long cardID, String type) {
        repo.likeAPICard(accountID, cardID, type);
    }
    
    public void addSeen(String accountID, long cardID) {
        repo.addSeen(accountID, cardID);
    }
    
    public void deleteCardFromBucket(String accountID, long cardID) {
        repo.deleteCardFromBucket(accountID, cardID);
    }
    
    public void deleteCardFromAPIBucket(String accountID, long cardID) {
        repo.deleteCardFromAPIBucket(accountID, cardID);
    }
    
    public void deleteUser(String accountID) {
        repo.deleteUser(accountID);
    }
}
