package sk.tuke.gamestudio.client;

import sk.tuke.gamestudio.entity.Rating;
import sk.tuke.gamestudio.service.RatingService;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

public class RatingRestServiceClient implements RatingService {
    private static final String URL = "http://localhost:8080/gamestudio_war_exploded/api/rating";

    @Override
    public void addRating(Rating rating) {
        if (rating.getRating() > 10) {
            rating.setRating(10);
        }
        if (rating.getRating() < 1) {
            rating.setRating(1);
        }
        try {
            Client client = ClientBuilder.newClient();
            Response response = client.target(URL + "/new")
                    .request(MediaType.APPLICATION_JSON)
                    .post(Entity.entity(rating, MediaType.APPLICATION_JSON), Response.class);
        } catch (Exception e) {
            throw new RuntimeException("Error uploading rating", e);
        }
    }

    @Override
    public List getRating() {
        return null;
    }


    @Override
    public String getAvgRating(String game) {

        String rating = "UNRATED YET";
        try {
            Client client = ClientBuilder.newClient();
            rating = client.target(URL)
                    .path("/ratingof/" + game)
                    .request(MediaType.APPLICATION_JSON)
                    .get(new GenericType<String>() {
                    });
        } catch (Exception e) {
            //System.err.println("Error loading rating "+ e);
            System.out.println(game + "\n      --> unrated yet");

        }
        rating = rating.substring(0, (rating.length() < 4 ? rating.length() : 4));

        game = game.substring(0, 1).toUpperCase() + game.substring(1);
        System.out.println(game + "\n      --> " + rating + "/10\n");

        return rating;
    }

    @Override
    public int setRating(Rating rating) {
        return 0;
    }
}
