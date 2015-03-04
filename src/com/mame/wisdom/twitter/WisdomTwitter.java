package com.mame.wisdom.twitter;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

import com.mame.wisdom.util.DbgUtil;

import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;

public class WisdomTwitter {

	private final static String TAG = WisdomTwitter.class.getSimpleName();

	public WisdomTwitter() {

		List<Status> statuses;
		try {
			Twitter twitter = TwitterFactory.getSingleton();
			statuses = twitter.getHomeTimeline();
			DbgUtil.showLog(TAG, "Showing home timeline.");
			for (Status status : statuses) {
				DbgUtil.showLog(TAG,
						status.getUser().getName() + ":" + status.getText());
			}
		} catch (TwitterException e) {
			DbgUtil.showLog(TAG, "TwitterException: " + e.getMessage());
		}
	}

	public String getAuthorizationURL() {
		DbgUtil.showLog(TAG, "getAuthorizationUR");
		Twitter twitter = TwitterFactory.getSingleton();
		// twitter.setOAuthConsumer(TwitterConstant.CONSUMER_KEY,
		// TwitterConstant.CONSUMER_SECRET);
		RequestToken requestToken;
		String result = null;
		try {
			requestToken = twitter.getOAuthRequestToken();
			result = requestToken.getAuthenticationURL();
		} catch (TwitterException e) {
			DbgUtil.showLog(TAG, "TwitterException: " + e.getMessage());
		}
		return result;
		// AccessToken accessToken = getOAuthAccessToken(twitter);
		// try {
		// RequestToken requestToken = twitter.getOAuthRequestToken();
		// return requestToken.getAuthorizationURL();
		// // DbgUtil.showLog(TAG,
		// // "RequestToken: " + requestToken.getAuthorizationURL());
		// // twitter.getOAuthAccessToken(requestToken);
		// } catch (TwitterException e1) {
		// DbgUtil.showLog(TAG, "TwitterException: " + e1.getMessage());
		// }
	}

	// static AccessToken getOAuthAccessToken(Twitter twitter) {
	// DbgUtil.showLog(TAG, "getOAuthAccessToken");
	// RequestToken requestToken = null;
	// AccessToken accessToken = null;
	//
	// try {
	// // リクエストトークンの作成
	// // (メモ) アクセストークンを取得後，保存して再利用するならば
	// // リクエストトークンの作成は１度きりでよい．
	// requestToken = twitter.getOAuthRequestToken();
	//
	// // ブラウザで Twitter 認証画面を表示するよう促し，
	// // PIN コードを入力させ，アクセストークンを作成（取得）する
	// BufferedReader br = new BufferedReader(new InputStreamReader(
	// System.in));
	// while (null == accessToken) {
	// System.out
	// .println("Open the following URL and grant access to your account:");
	// System.out.println(requestToken.getAuthorizationURL());
	// System.out
	// .print("Enter the PIN(if aviailable) or just hit enter.[PIN]:");
	// String pin = br.readLine();
	// try {
	// if (pin.length() > 0) {
	// accessToken = twitter.getOAuthAccessToken(requestToken,
	// pin);
	// } else {
	// accessToken = twitter.getOAuthAccessToken();
	// }
	// } catch (TwitterException te) {
	// if (401 == te.getStatusCode()) {
	// System.out.println("Unable to get the access token.");
	// } else {
	// te.printStackTrace();
	// }
	// }
	// }
	// } catch (Exception e) {
	// DbgUtil.showLog(TAG, "Exception: " + e.getMessage());
	// }
	// return accessToken;
	// }
}
