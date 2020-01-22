package consumer.tweet;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import consumer.tweet.config.ConsumerTweetConstants;
import consumer.tweet.config.ServerProperties;
import consumer.tweet.domain.TweetEntity;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

@SpringBootApplication
@EnableConfigurationProperties(ServerProperties.class)
public class App {

	
	public static void main(String[] args) throws TwitterException {
		SpringApplication app = new SpringApplication(App.class);
		app.run();
		loadTweets();
	}
	
	private static void loadTweets() throws TwitterException {
		Twitter twitter = new TwitterFactory().getInstance();
		Query query = new Query("source: a");
		QueryResult result;
		query.setCount(1000000);
		result = twitter.search(query);
		List<Status> tweets = result.getTweets();
		List<TweetEntity> tweetList = tweets.stream()
				.filter((tweet) -> tweet.getUser().getFollowersCount() >= ConsumerTweetConstants.FOLLOWERS && ConsumerTweetConstants.VALID_lANGUAGES.contains(tweet.getLang()))
				.map(item -> mapperTweets(item))
				.collect(Collectors.toList());
	}
	
	
	private static TweetEntity mapperTweets(Status item) {
		TweetEntity tweet = new TweetEntity();
		tweet.setIdUser(item.getUser().getId());
		tweet.setUserName(item.getUser().getScreenName());
		tweet.setText(item.getText());
		String place = item.getPlace() != null ? item.getPlace().getFullName() : "";
		tweet.setLocation(place);
		tweet.setValidated(false);
		
		return tweet;
	}

}
