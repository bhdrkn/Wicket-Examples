package blg.bhdrkn.facebook.domain;

import com.google.code.facebookapi.FacebookException;
import com.google.code.facebookapi.FacebookJsonRestClient;
import com.visural.common.StringUtil;

public class Facebook {
    private static final String api_key = "[API_KEY]";
    private static final String secret = "[SECRET]";
    private static final String client_id = "[API_KEY_?]";  

    private static final String redirect_uri = "http://localhost:8080/WicketFacebookExample/fbauth";
    private static final String[] perms = new String[] {"publish_stream", "email",  "offline_access"};

    public static String getAPIKey() {
        return api_key;
    }

    public static String getSecret() {
        return secret;
    }

    public static String getLoginRedirectURL() {
        return "https://graph.facebook.com/oauth/authorize?client_id=" +
            client_id + "&display=page&redirect_uri=" +
            redirect_uri+"&scope="+StringUtil.delimitObjectsToString(",", perms);
    }

    public static String getAuthURL(String authCode) {
        return "https://graph.facebook.com/oauth/access_token?client_id=" +
            client_id+"&redirect_uri=" +
            redirect_uri+"&client_secret="+secret+"&code="+authCode;
    }
    
    public static void sendToWall(String message, long userId){
    	FacebookJsonRestClient client = new FacebookJsonRestClient(Facebook.getAPIKey(), Facebook.getSecret());
    	try {
			client.stream_publish(message, null, null, null, userId);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (FacebookException e) {
			System.out.println("FACEBOOK EX: M: " + e.getMessage() + " C: " + e.getCause());
			e.printStackTrace();
		}
    }
}
