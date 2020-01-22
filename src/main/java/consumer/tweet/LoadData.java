package consumer.tweet;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import consumer.tweet.config.ConsumerTweetConstants;
import consumer.tweet.domain.TweetEntity;
import consumer.tweet.repository.ConsumerTweetRepository;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;

@Component
public class LoadData implements CommandLineRunner{

	@Autowired
	private ConsumerTweetRepository consumerTweetRepository;
	
	@Override
	public void run(String... args) throws Exception {
		Twitter twitter = new TwitterFactory().getInstance();
		Query query = new Query("source: a");
		QueryResult result;
		query.setCount(1000000);
		result = twitter.search(query);
		List<Status> tweets = result.getTweets();
		List<TweetEntity> tweetList = tweets.stream()
				.filter((tweet) -> tweet.getUser().getFollowersCount() >= ConsumerTweetConstants.FOLLOWERS && ConsumerTweetConstants.VALID_lANGUAGES.contains(tweet.getLang()))
				.map(item -> TweetMapper.mapperTweets(item))
				.collect(Collectors.toList());
		consumerTweetRepository.saveAll(tweetList);		
	}

}
