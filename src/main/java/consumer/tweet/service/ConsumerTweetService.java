package consumer.tweet.service;

import java.util.List;

import consumer.tweet.web.api.Tweet;
import twitter4j.TwitterException;

public interface ConsumerTweetService {
		
	List<Tweet> getTweets();
	
	Integer validateTweet(String id, Tweet tweet);
	
	List<Tweet> getTweetsValidatedByUser(String user);
	
	List<String> getHashtagsRanking(String positions) throws TwitterException;

}
