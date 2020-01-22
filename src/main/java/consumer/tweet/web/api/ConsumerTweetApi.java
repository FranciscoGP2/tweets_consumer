package consumer.tweet.web.api;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import twitter4j.TwitterException;

public interface ConsumerTweetApi {
	

    @RequestMapping(value = "/tweets",  produces = { "application/json" },  method = RequestMethod.GET)  
    ResponseEntity<List<Tweet>> getTweets();


    @RequestMapping(value = "/tweets/{user}", produces = { "application/json" },  method = RequestMethod.GET)
    ResponseEntity<List<Tweet>> getTweetsByUser( @PathVariable("user") String user);


    @RequestMapping(value = "/tweet/{id}", consumes = { "application/json" }, method = RequestMethod.PUT)
    ResponseEntity<Integer> editValidationTweet(@PathVariable("id") String id, @Valid @RequestBody Tweet body);
    
    
    @RequestMapping(value = "/hashtags/ranking", produces = { "application/json" },  method = RequestMethod.GET)
    ResponseEntity<List<String>> getRankingHashtags( @RequestParam("positions") String positions) throws TwitterException;

}
