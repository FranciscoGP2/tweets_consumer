package consumer.tweet.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import consumer.tweet.domain.TweetEntity;

@Repository
public interface ConsumerTweetRepository extends JpaRepository<TweetEntity, UUID> {

	@Query("select tw from TweetEntity tw where tw.idUser=:idUser and validated = 1")
	public List<TweetEntity> getTweetsByUserAndValidated(@Param("idUser") Long idUser);
}
