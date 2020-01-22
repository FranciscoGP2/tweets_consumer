package consumer.tweet.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import consumer.tweet.TweetMapper;
import consumer.tweet.config.ConsumerTweetConstants;
import consumer.tweet.domain.TweetEntity;
import consumer.tweet.repository.ConsumerTweetRepository;
import consumer.tweet.service.ConsumerTweetService;
import consumer.tweet.web.api.Tweet;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Trend;
import twitter4j.Trends;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

@Service
public class ConsumerTweetsServiceImple implements ConsumerTweetService {
	
	@Autowired
	private ConsumerTweetRepository consumerTweetRepository;
	
	
	
	@Override
	public List<Tweet> getTweets() {
		List<Tweet> tweets = new ArrayList<>();
		tweets = consumerTweetRepository.findAll().stream().map(entity -> TweetMapper.mapperFromTweetEntityToTweet(entity)).collect(Collectors.toList());
		return tweets;
	}

	@Override
	public Integer validateTweet(String id, Tweet tweet) {
		if(consumerTweetRepository.existsById(UUID.fromString(id))) {
			TweetEntity tweetEntity = TweetMapper.mapperFromTweetToTweetEntity(tweet);
			consumerTweetRepository.save(tweetEntity);
		}
		return null;
	}

	@Override
	public List<Tweet> getTweetsValidatedByUser(String user) {
		List<Tweet> tweets = new ArrayList<>();
		Long idUser = Long.parseLong(user);
		tweets = consumerTweetRepository.getTweetsByUserAndValidated(idUser).stream().map(entity -> TweetMapper.mapperFromTweetEntityToTweet(entity)).collect(Collectors.toList());
		return tweets;
	}

	@Override
	public List<String> getHashtagsRanking(String positions) throws TwitterException {
		List<String> hashtags = new ArrayList<String>();
		 Twitter twitter = new TwitterFactory().getInstance();
		Query query = new Query("source: a");
		QueryResult result;
		result = twitter.search(query);
		Trends trends = twitter.getPlaceTrends(1);
		int limit = positions == null ? ConsumerTweetConstants.NUM_HASHTAGS : Integer.parseInt(positions);
		int count = 0;
		
		for (Trend trend : trends.getTrends()) {
			if(limit == count) break;
			hashtags.add(trend.getName());
			count ++;
		}
		return hashtags;
	}


}
