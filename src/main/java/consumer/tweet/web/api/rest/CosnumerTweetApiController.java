package consumer.tweet.web.api.rest;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import consumer.tweet.service.ConsumerTweetService;
import consumer.tweet.web.api.ConsumerTweetApi;
import consumer.tweet.web.api.Tweet;
import twitter4j.TwitterException;

@RestController
public class CosnumerTweetApiController implements ConsumerTweetApi {

	@Autowired
	private ConsumerTweetService consumerTweetService;
	
	@Override
	public ResponseEntity<List<Tweet>> getTweets() {
		return new ResponseEntity<List<Tweet>>(consumerTweetService.getTweets(), HttpStatus.OK);
	}
	
	@Override
	public ResponseEntity<List<Tweet>> getTweetsByUser(@PathVariable("user") String user) {
		return new ResponseEntity<List<Tweet>>(consumerTweetService.getTweetsValidatedByUser(user), HttpStatus.OK);
	}
	
	@Override
	public ResponseEntity<Integer> editValidationTweet(@PathVariable("id") String id, @Valid @RequestBody Tweet body){
		return new ResponseEntity<Integer>(consumerTweetService.validateTweet(id, body), HttpStatus.OK);
	}
	
	@Override
	public ResponseEntity<List<String>> getRankingHashtags( @RequestParam("positions") String positions) throws TwitterException{
		return new ResponseEntity<List<String>>(consumerTweetService.getHashtagsRanking(positions), HttpStatus.OK);
	}

}
