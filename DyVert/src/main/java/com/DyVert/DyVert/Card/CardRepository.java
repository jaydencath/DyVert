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
import java.util.Random;

import java.util.ArrayList;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonElement;
import com.google.gson.JsonArray;


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
        String query = "SELECT cardID, userID, title, synopsis, genres, imagepath, reviews, type FROM card_data WHERE cardID = :cardID";
        SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("cardID", cardID);
        return template.queryForObject(query, namedParameters, (result, rowNum) ->
            new Card(
                result.getLong("cardID"),
                result.getString("userID"),
                result.getString("title"),
                result.getString("synopsis"),
                result.getString("genres"),
                result.getString("imagepath"),
                result.getInt("reviews"),
                result.getString("type")
            )
        );
    }

    
    public void flagCard(long cardID) {        
        String query = "UPDATE card_data SET flagged = 1, reviewed =1 WHERE cardID = :cardID";
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("cardID", cardID);
        template.update(query, parameters);
    }
    
    public void deleteCard(long cardID) {
        String query = "DELETE FROM card_data WHERE cardID = :cardID";
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("cardID", cardID);
        template.update(query, parameters);
    }
    
    public void reviewCard(long cardID) {
        String query = "UPDATE card_data SET reviewed = 1 WHERE cardID = :cardID";
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("cardID", cardID);
        template.update(query, parameters);
    }
    
    public void saveCard(Card card, String userID) {
        // Hard-coded values
        card.setReviews(0);
        card.setReviewed(false);
        card.setFlagged(false);
        card.setFinished(true);

        // Save the card
        String query = "INSERT INTO card_data (title, synopsis, genres, imagepath, userID, type, reviews, reviewed, flagged, finished) "
                + "VALUES (:title, :synopsis, :genres, :imagepath, :userID, :type, :reviews, :reviewed, :flagged, :finished)";
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("title", card.getTitle());
        parameters.addValue("synopsis", card.getSynopsis());
        parameters.addValue("genres", card.getGenres());
        parameters.addValue("imagepath", card.getImagePath());
        parameters.addValue("userID", userID); // Set user ID
        parameters.addValue("type", card.getType()); // Set type
        parameters.addValue("reviews", card.getReviews()); // Set reviews
        parameters.addValue("reviewed", card.isReviewed()); // Set reviewed
        parameters.addValue("flagged", card.isFlagged()); // Set flagged
        parameters.addValue("finished", card.isFinished()); // Set finished

        template.update(query, parameters);
    }

    public List<Card> getUserCards(String userID) {
        String query = "SELECT * FROM card_data WHERE userID = :userID";
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("userID", userID);

        return template.query(query, parameters,
                (result, rowNum) -> new Card(
                        result.getLong("cardID"),
                        result.getString("userID"),
                        result.getString("title"),
                        result.getString("synopsis"),
                        result.getString("genres"),
                        result.getString("imagepath"),
                        result.getInt("reviews"),
                        result.getString("type")));
    }
    
    public List<Card> getAllReviewedCards() {
        String query = "select cardID, userID, title, synopsis, genres, imagepath, reviews, type from card_data WHERE reviewed = 1";
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
    
    public List<Card> getAllReviewedCards(String type) {
        String query = "select cardID, userID, title, synopsis, genres, imagepath, reviews, type from card_data WHERE reviewed = 1 AND type = :type";
        MapSqlParameterSource parameter = new MapSqlParameterSource();
        parameter.addValue("type", type);        
        return template.query(query, parameter,
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
    
    public Card getUnseenCard(String accountID, String type) {
        int cardCount = 0;
        List<Card> reviewedCards;
        if (type.equals("all")) {
            String query = "SELECT count(*) FROM card_data WHERE reviewed = 1";
            cardCount = template.queryForObject(query, new HashMap<>(), Integer.class);
            reviewedCards = getAllReviewedCards();
        } else {
            String query = "SELECT count(*) FROM card_data WHERE reviewed = 1 AND type = :type";
            MapSqlParameterSource parameter = new MapSqlParameterSource();
            parameter.addValue("type", type);
            cardCount = template.queryForObject(query, parameter, Integer.class);
            reviewedCards = getAllReviewedCards(type);
        }        
        if (reviewedCards.isEmpty()) {
            return null; // Return null if there are no reviewed cards
        }
        
        int randomIndex = 0;
        Random random = new Random();
        
        while (true) {
            randomIndex = random.nextInt(cardCount);
            Card randomCard = reviewedCards.get(randomIndex);
            long cardID = randomCard.getCardID();
            String newQuery = "SELECT count(*) FROM seen WHERE contentID = :cardID AND accountID = :accountID";
            MapSqlParameterSource parameters = new MapSqlParameterSource();
            parameters.addValue("cardID", cardID);
            parameters.addValue("accountID", accountID);
            int count = template.queryForObject(newQuery, parameters, Integer.class);
            
            if (count == 0) {
                return randomCard;
            }
        }        
    }
       
    public List<Card> getBucketList(String accountID) {
        String query = "SELECT contentID FROM bucket WHERE accountID = :accountID";

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("accountID", accountID);
    
        List<Long> contentIDs = template.queryForList(query, parameters, Long.class);
        List<Card> cardList = new ArrayList<>();
    
        for (Long contentID : contentIDs) {
            Card card = getCardById(contentID);
            if (card != null) {
                cardList.add(card);
            }
        }
    
        return cardList;
    }
    
    public List<Card> getAPIBucketList(String accountID, String type) {
        String query = "SELECT apiKey FROM bucket_api WHERE accountID = :accountID";
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("accountID", accountID);
    
        List<Long> contentIDs = template.queryForList(query, parameters, Long.class);
        String secQuery = "SELECT type FROM bucket_api WHERE accountID = :accountID";
        
        List<String> types = template.queryForList(secQuery, parameters, String.class);
        
        List<Card> cardList = new ArrayList<>();
    
        for (int i = 0; i < contentIDs.size(); i++) {
            Card card = getAPICardByID(contentIDs.get(i), types.get(i));
            if (type.equals("all")) {
                if (card != null) {
                    cardList.add(card);
                }
            } else {
                if (card != null && card.getType().equals(type)) {
                    cardList.add(card);
                }
            }
        }
    
        return cardList;
    }
    
    public List<Card> getTypeBucketList(String accountID, String type) {
        String query = "SELECT contentID FROM bucket WHERE accountID = :accountID";
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("accountID", accountID);
    
        List<Long> contentIDs = template.queryForList(query, parameters, Long.class);
        List<Card> cardList = new ArrayList<>();
    
        for (Long contentID : contentIDs) {
            Card card = getCardById(contentID);
            if (card != null && type.equalsIgnoreCase(card.getType())) {
                cardList.add(card);
            }
        }
    
        return cardList;
    }
    
    public Card getCardFromAPI(String accountID, String type) {
        Random random = new Random();
        int randomPage = random.nextInt(1, 500);
        int randomIndex = random.nextInt(0, 20);
        String uri = "";
        if (type.equals("Movie")) {
            uri = "https://api.themoviedb.org/3/discover/movie?include_adult=false&include_video=false&language=en-US&page=" + randomPage + "&sort_by=popularity.desc";
        } else {
            uri = "https://api.themoviedb.org/3/discover/tv?include_adult=false&include_null_first_air_dates=false&language=en-US&page=" + randomPage + "&sort_by=popularity.desc";
        }
        try {
            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(uri))
                .header("accept", "application/json")
                .header("Authorization", "Bearer APIKEYHERE")
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
            HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            
            Gson gson = new Gson();
            JsonObject jsonObject = gson.fromJson(response.body(), JsonObject.class);
            
            JsonArray resultsArray = jsonObject.getAsJsonArray("results");
            JsonObject result = resultsArray.get(randomIndex).getAsJsonObject();
            Long id = result.get("id").getAsLong();
            
            String title = (type.equals("Movie")) ? result.get("original_title").getAsString() : result.get("original_name").getAsString();
            
            String synopsis = result.get("overview").getAsString();

            double reviews = result.get("vote_average").getAsDouble();
            String imagePath = result.get("poster_path").getAsString();
            
            JsonArray genreIdsArray = result.getAsJsonArray("genre_ids");
            
            String genres = genreFlatten(genreIdsArray);
            
            return new Card(id, "API", title, synopsis, genres, imagePath, (int)reviews, type);
        }
        catch (Exception e) {
            System.out.println("Error");
        }
        
        return null;
    }
    
    public Card getAPICardByID(Long cardID, String type) {
        String cardRequest;
        if (type.equals("Movie")) {
            cardRequest = "https://api.themoviedb.org/3/movie/" + cardID + "?language=en-US";
        } else {
            cardRequest = "https://api.themoviedb.org/3/tv/"+ cardID + "?language=en-US";
        }
        try {
            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(cardRequest))
                .header("accept", "application/json")
                .header("Authorization", "Bearer APIKEYHERE")
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
            HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        
            Gson gson = new Gson();
            JsonObject content = gson.fromJson(response.body(), JsonObject.class);
            
            Long id = content.get("id").getAsLong();
            String title = (type.equals("Movie")) ? content.get("original_title").getAsString() : content.get("original_name").getAsString();
            String synopsis = content.get("overview").getAsString();
            
            double reviews = content.get("vote_average").getAsDouble();
            String imagePath = content.get("poster_path").getAsString();
            
            JsonArray genresArray = content.getAsJsonArray("genres");
            String genres = "";
            
            for (int i = 0; i < genresArray.size(); i++) {
                JsonObject genreObject = genresArray.get(i).getAsJsonObject();
                String genreName = genreObject.get("name").getAsString();
                
                if (i == (genresArray.size() - 1)) {
                    genres = genres + genreName;
                } else {
                    genres = genres + genreName + ", ";
                }
            }
            
            return new Card(id, "API", title, synopsis, genres, imagePath, (int)reviews, type);
        
        } catch (Exception e) {
            System.out.println("Error");
        }
        
        return null;
    }
    
    public void updateCard(Long id, String title, String synopsis, String genres, String imagePath, String type) {
        String query = "UPDATE card_data SET title = :title, synopsis = :synopsis, genres = :genres, imagepath = :imagepath, type = :type WHERE cardID = :cardID";
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("title", title);
        parameters.addValue("synopsis", synopsis);
        parameters.addValue("genres", genres);
        parameters.addValue("imagepath", imagePath);
        parameters.addValue("type", type);
        parameters.addValue("cardID", id);
        template.update(query, parameters);
    }
    
    private String genreFlatten(JsonArray genres) {
        String genreString = "";
        for(int i = 0; i < genres.size(); i++) {
            int genreID = genres.get(i).getAsInt();
            String genre;
            switch (genreID) {
                case 28: genre = "Action"; break;
                case 12: genre = "Adventure"; break;
                case 16: genre = "Animation"; break;
                case 35: genre = "Comedy"; break;
                case 80: genre = "Crime"; break;
                case 99: genre = "Documentary"; break;
                case 18: genre = "Drama"; break;
                case 10751: genre = "Family"; break;
                case 14: genre = "Fantasy"; break;
                case 36: genre = "History"; break;
                case 27: genre = "Horror"; break;
                case 10402: genre = "Music"; break;
                case 9648: genre = "Mystery"; break;
                case 10749: genre = "Romance"; break;
                case 878: genre = "Science Fiction"; break;
                case 10770: genre = "TV Movie"; break;
                case 53: genre = "Thriller"; break;
                case 10752: genre = "War"; break;
                case 37: genre = "Western"; break;
                default: genre = "Null"; break;
            }
            if (i == (genres.size() - 1)) {
                genreString = genreString + genre;
            } else {
                genreString = genreString + genre + ", ";
            }
        }
        return genreString;
    }
}
