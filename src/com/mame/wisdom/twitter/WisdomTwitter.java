package com.mame.wisdom.twitter;

import java.util.List;

import com.mame.wisdom.util.DbgUtil;

import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

public class WisdomTwitter {

	private final static String TAG = WisdomTwitter.class.getSimpleName();

	public WisdomTwitter() {
		Twitter twitter = TwitterFactory.getSingleton();
		List<Status> statuses;
		try {
			statuses = twitter.getHomeTimeline();
			System.out.println("Showing home timeline.");
			for (Status status : statuses) {
				DbgUtil.showLog(TAG,
						status.getUser().getName() + ":" + status.getText());
			}
		} catch (TwitterException e) {
			DbgUtil.showLog(TAG, "TwitterException: " + e.getMessage());
		}
	}
}
