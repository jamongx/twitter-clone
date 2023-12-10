import React, { useState, useEffect } from "react";
import TweetComponent from "./TweetComponent";
import TweetDetailComponent from "./TweetDetailComponent";
import { DummyForYouTweets } from "../../utils/DummyForYouTweets";
import { Constants } from "../../utils/Constants";
import "./TweetForYouComponent.css";

function TweetForYouComponent() {
  const [tweets, setTweets] = useState([]);
  const [selectedTweet, setSelectedTweet] = useState(null); // State for the selected tweet
  const [isLoading, setIsLoading] = useState(false);
  const [lastScrollPosition, setLastScrollPosition] = useState(0); // State for the scroll position

  useEffect(() => {
    window.addEventListener("scroll", handleScroll);
    loadFollowingTweets(); // Load initial tweets
    return () => window.removeEventListener("scroll", handleScroll);
  }, []);

  useEffect(() => {
    if (!selectedTweet) {
      // Restore scroll position when returning to the list of TweetComponents
      window.scrollTo(0, lastScrollPosition);
    }
  }, [selectedTweet]);

  const loadFollowingTweets = async () => {
    if (isLoading || tweets.length >= Constants.MAX_TWEETS) {
      // Do not load more if loading or no more tweets to load
      return;
    }

    setIsLoading(true);
    // Tweet loading logic (API calls, etc.)
    setTweets((prevTweets) => [...prevTweets, ...DummyForYouTweets]); // Add new tweets to existing ones
    setIsLoading(false);
  };

  const handleTweetClick = (tweet) => {
    setLastScrollPosition(window.scrollY); // Save the current scroll position
    setSelectedTweet(tweet); // Update state of the selected tweet when a tweet is clicked
  };

  const handleScroll = () => {
    const scrollPosition =
      window.innerHeight + document.documentElement.scrollTop;
    const offsetHeight = document.documentElement.offsetHeight;

    // Load additional data when the scroll is near the end of the document
    if (
      scrollPosition >= offsetHeight - Constants.REMAINED_TWEETS &&
      !isLoading
    ) {
      loadFollowingTweets();
    }
  };

  return (
    <div className="tweet-foryou-component">
      {selectedTweet ? (
        <TweetDetailComponent tweetData={selectedTweet} />
      ) : (
        tweets.map((tweet) => (
          <TweetComponent
            key={tweet.id}
            tweetData={tweet}
            onClick={() => handleTweetClick(tweet)}
          />
        ))
      )}
      {isLoading && <div>Loading more tweets...</div>}
    </div>
  );
}

export default TweetForYouComponent;
