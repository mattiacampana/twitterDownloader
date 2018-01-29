package it.cnr.iit.twitterDownloader.controller;

import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

import java.util.ArrayList;
import java.util.List;

public class TwitterController {

    private static final String API_KEY = "5jZS19ffLeQWMQGNoeltOid1o";
    private static final String API_SECRET = "pqgysx4g3kpH7SxWQffF9cVFNmrymSBLgUb89Rt7JD6D8oE0EP";

    private static TwitterController instance;
    private Twitter twitter;

    public static synchronized TwitterController getInstance(String accessToken, String accessTokenSecret){

        if(instance == null) instance = new TwitterController(accessToken, accessTokenSecret);
        return instance;
    }


    private TwitterController(String accessToken, String accessTokenSecret){

        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey(API_KEY)
                .setOAuthConsumerSecret(API_SECRET)
                .setOAuthAccessToken(accessToken)
                .setOAuthAccessTokenSecret(accessTokenSecret);

        TwitterFactory tf = new TwitterFactory(cb.build());
        twitter = tf.getInstance();
    }

    private TwitterController(){}

    public Twitter getTwitter() {
        return twitter;
    }

    public User getLoggedUser(){

        User user = null;

        try {
            user = twitter.users().verifyCredentials();
        } catch (TwitterException e) {
            e.printStackTrace();
        }

        return user;
    }

    public List<Status> getUserTimeline(long userId){

        List<Status> statuses = null;

        try {
            statuses = twitter.getUserTimeline(userId);
        } catch (TwitterException e) {
            e.printStackTrace();
        }

        return statuses;
    }

    /**
     * Returns the friends list of the specified user.
     * A friend in Twitter is a user followed by the specified user.
     *
     * @param userId    User's ID
     * @return          The list of friends
     */
    public List<User> getFriendsList(long userId){

        List<User> friends = new ArrayList<User>();

        try {

            long cursor = -1;
            PagableResponseList<User> pagableFriends;

            do {

                pagableFriends = twitter.getFriendsList(userId, cursor);
                friends.addAll(pagableFriends);

            } while ((cursor = pagableFriends.getNextCursor()) != 0);

        } catch (TwitterException e) {
            e.printStackTrace();
        }

        return friends;
    }

    /**
     * Returns the list of Twitter users that following the specified user.
     *
     * @param userId    User's ID
     * @return          The list of followers
     */
    public List<User> getFollowersList(long userId){

        List<User> followers = new ArrayList<User>();

        try {

            long cursor = -1;
            PagableResponseList<User> pagableFollowers;

            do {

                pagableFollowers = twitter.getFollowersList(userId, cursor);
                followers.addAll(pagableFollowers);

            } while ((cursor = pagableFollowers.getNextCursor()) != 0);

        } catch (TwitterException e) {
            e.printStackTrace();
        }

        return followers;
    }



}
