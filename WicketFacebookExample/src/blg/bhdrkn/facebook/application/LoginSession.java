package blg.bhdrkn.facebook.application;

import java.net.URL;

import org.apache.wicket.protocol.http.WebSession;
import org.apache.wicket.request.Request;
import org.json.JSONObject;

import blg.bhdrkn.facebook.domain.User;

import com.visural.common.IOUtil;

public class LoginSession extends WebSession {

	private static final long serialVersionUID = 1L;
	
	private User user;

	public LoginSession(Request request) {
		super(request);
	}

	public boolean isSignedIn() {
		return user != null;
	}

	public User getUser() {
		return user;
	}

	public boolean authenticateFacebookLogin(String accessToken) {
		try{
			if(user == null){
				JSONObject resp = new JSONObject(
		                IOUtil.urlToString(new URL("https://graph.facebook.com/me?access_token=" + accessToken)));
				if(resp == null){
					return false;
				}
				user = new User();
				System.out.println(resp.getString("id"));
				user.setFirstName(resp.getString("first_name"));
				user.setLastName(resp.getString("last_name"));
				user.setId(Long.valueOf(resp.getString("id")));
				user.setEmail(resp.getString("email"));
			}
		}catch(Exception e){
			return false;
		}
		return true;
	}

}
