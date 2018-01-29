package it.cnr.iit.twitterDownloader;

import it.cnr.iit.twitterDownloader.controller.TwitterController;
import twitter4j.Status;
import twitter4j.User;

import java.util.List;

public class Main {

    public static void main(String[] args) {

        String accessToken = "113737085-wbBcjqeSJfMgw81h0L0RkHrDkHbvKlGwkj7o5ff0";
        String tokenSecret = "Jn7cdHg9SapgMfbp0zAShoMHgUTfUMdzwq8M2cGh0XLpT";

        TwitterController controller = TwitterController.getInstance(accessToken, tokenSecret);

        User loggedUser = controller.getLoggedUser();

        for(Status status : controller.getUserTimeline(loggedUser.getId())){
            System.out.println(status.getUser().getName() + ": " + status.getText());
        }

        List<User> friends = controller.getFriendsList(loggedUser.getId());

        for(User user : friends){
            System.out.println(user.getName());
        }

    }
}
