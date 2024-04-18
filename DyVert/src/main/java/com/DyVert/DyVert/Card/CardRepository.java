package com.DyVert.DyVert.Card;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

/**
 *
 * @author sergioguerra
 */
@Repository
public class CardRepository {
    @Autowired
    NamedParameterJdbcTemplate template;
    
    
    List<Card> findUnreviewed() {
        String query = "select cardID, userID, title, synopsis, genres, imagepath, reviews, type from card_data WHERE reviewed = 0";
        return template.query(query,
                (result, rowNum)
                -> new Card(
                        result.getLong("cardID"),
                        result.getString("userID"),
                        result.getString("title"),
                        result.getString("synopsis"),
                        result.getString("genres"),
                        result.getString("imagepath"),
                        result.getInt("reviews"),
                        result.getString("type")));
    }
    
    public Card getCardById(long cardID) {
        SqlParameterSource namedParameters = new MapSqlParameterSource().addValue(
                "cardID", cardID);
        String query = "select * from card_data where cardID = :cardID ";
        return template.queryForObject(query, namedParameters,
                BeanPropertyRowMapper.newInstance(Card.class));
    }
    
    public void flagCard(long cardID) {        
        String query = "UPDATE card_data SET flagged = 1 WHERE cardID = :cardID";
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("cardID", cardID);
        template.update(query, parameters);
    }
}