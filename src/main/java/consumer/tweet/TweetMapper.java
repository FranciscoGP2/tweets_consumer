package consumer.tweet;

import java.util.UUID;

import consumer.tweet.domain.TweetEntity;
import consumer.tweet.web.api.Tweet;
import twitter4j.Status;

public class TweetMapper {

	public static Tweet mapperFromTweetEntityToTweet(TweetEntity tweetEntity) {
		Tweet tweet = new Tweet();
		tweet.setId(tweetEntity.getId().toString());
		tweet.setIdUser(tweetEntity.getIdUser());
		tweet.setUserName(tweetEntity.getUserName());
		tweet.setText(tweetEntity.getText());
		tweet.setLocation(tweetEntity.getLocation());
		tweet.setValidated(tweetEntity.getValidated());

		return tweet;
	}

	public static TweetEntity mapperFromTweetToTweetEntity(Tweet tweet) {
		TweetEntity tweetEntity = new TweetEntity();
		tweetEntity.setId(UUID.fromString(tweet.getId()));
		tweetEntity.setIdUser(tweet.getIdUser());
		tweetEntity.setUserName(tweet.getUserName());
		tweetEntity.setText(tweet.getText());
		tweetEntity.setLocation(tweet.getLocation());
		tweetEntity.setValidated(tweet.getValidated());

		return tweetEntity;
	}

	public static TweetEntity mapperTweets(Status item) {
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
