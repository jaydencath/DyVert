package com.DyVert.DyVert.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class UserRepository {
    @Autowired
    NamedParameterJdbcTemplate template;
   
    public List<User> findAllUsers() {
        String query = "SELECT accountID, accountType, password FROM users";
        return template.query(query, BeanPropertyRowMapper.newInstance(User.class));
    }
    
    public User getUserById(String accountID) {
        SqlParameterSource namedParameters = new MapSqlParameterSource().addValue(
            "accountID", accountID);
        String query = "select accountID, accountType, password from users where accountID = :accountID ";
        try {
            return template.queryForObject(query, namedParameters,
                    (rs, rowNum) -> {
                        String userId = rs.getString("accountID");
                        String accountType = rs.getString("accountType");
                        String password = rs.getString("password");
                        // Create and return a new User object
                        return new User(userId, accountType, password);
                    });
        } catch (Exception ex) {
            return null;
        }
    }
    
    public void likeCard(String accountID, long cardID) {        
        String query = "INSERT INTO bucket (accountID, contentID) VALUES (:accountID, :cardID)";
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("accountID", accountID);
        parameters.addValue("cardID", cardID);
        template.update(query, parameters);
    }
    
    public void likeAPICard(String accountID, long cardID, String type) {
        String query = "INSERT INTO bucket_api (accountID, apiKey, type) VALUES (:accountID, :cardID, :type)";
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("accountID", accountID);
        parameters.addValue("cardID", cardID);
        parameters.addValue("type", type);
        template.update(query, parameters);
    }
    
    public void addSeen(String accountID, long cardID) {
        String query = "INSERT INTO seen (accountID, contentID) VALUES (:accountID, :cardID)";
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("accountID", accountID);
        parameters.addValue("cardID", cardID);
        template.update(query, parameters);
    }
    
    public void deleteCardFromBucket(String accountID, long cardID) {
        String query = "DELETE FROM bucket WHERE accountID = :accountID AND contentID = :cardID";
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("accountID", accountID);
        parameters.addValue("cardID", cardID);
        template.update(query, parameters);
    }
    
    public void deleteCardFromAPIBucket(String accountID, long cardID) {
        String query = "DELETE FROM bucket_api WHERE accountID = :accountID AND apiKey = :cardID";
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("accountID", accountID);
        parameters.addValue("cardID", cardID);
        template.update(query, parameters);
    }
    
    @Transactional
    public void deleteUser(String accountID) {

        int count = 0;
        String query = "SELECT count(*) FROM bucket WHERE accountID = :accountID";
        MapSqlParameterSource parameter = new MapSqlParameterSource();
        parameter.addValue("accountID", accountID);
        count = template.queryForObject(query, parameter, Integer.class);
        if (count > 0) {
            String bSql = "DELETE FROM bucket WHERE accountID = :accountID";
            template.update(bSql, parameter);
        }
        int count3 = 0;
        String query3 = "SELECT count(*) FROM seen WHERE accountID = :accountID";
        count3 = template.queryForObject(query3, parameter, Integer.class);
        if (count3 > 0) {
            String dSql = "DELETE FROM seen WHERE accountID = :accountID";
            template.update(dSql, parameter);
        }
        int count4 = 0;
        String query4 = "SELECT count(*) FROM seen s JOIN card_data c ON s.contentID = c.cardid WHERE c.userID = :accountID";
        count4 = template.queryForObject(query4, parameter, Integer.class);
        if (count4 > 0) {
            String eSql = "DELETE s FROM seen s JOIN card_data c ON s.contentID = c.cardid WHERE c.userID = :accountID";
            template.update(eSql, parameter);
        }
        int count2 = 0;
        String query2 = "SELECT count(*) FROM card_data WHERE userID = :accountID";
        count2 = template.queryForObject(query2, parameter, Integer.class);
        if (count2 > 0) {
            String cSql = "DELETE FROM card_data WHERE userID = :accountID";
            template.update(cSql, parameter);
        }
       
        String sql = "DELETE FROM users WHERE accountID = :accountID";
        template.update(sql, parameter);
    }
}
