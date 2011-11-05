package blg.bhdrkn.facebook.filter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.wicket.Session;

import blg.bhdrkn.facebook.application.LoginSession;
import blg.bhdrkn.facebook.domain.Facebook;

import com.visural.common.StringUtil;

public class FacebookAuthentication implements Filter{

	private static final long serialVersionUID = -460291618325615633L;

	public void doFilter(ServletRequest sr, ServletResponse sr1, FilterChain fc)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) sr;
		HttpServletResponse res = (HttpServletResponse) sr1;
		String code = sr.getParameter("code");
		if (StringUtil.isNotBlankStr(code)) {
			String authURL = Facebook.getAuthURL(code);
			URL url = new URL(authURL);
			try {
				String result = readURL(url);
				String accessToken = null;
				Integer expires = null;
				String[] pairs = result.split("&");
				for (String pair : pairs) {
					String[] kv = pair.split("=");
					if (kv.length != 2) {
						throw new RuntimeException("Unexpected auth response");
					} else {
						if (kv[0].equals("access_token")) {
							accessToken = kv[1];
						}
						if (kv[0].equals("expires")) {
							expires = Integer.valueOf(kv[1]);
						}
					}
				}
				if (accessToken != null){
					LoginSession session = (LoginSession)Session.get();
					if(session.authenticateFacebookLogin(accessToken))
						res.sendRedirect("http://localhost:8080/WicketFacebookExample/SignedIn");
					else
						res.sendRedirect("http://localhost:8080/WicketFacebookExample/");
				} else {
					System.out.println("ACCESSTOKEN: " + accessToken + " EXPIRES: " + expires);
					throw new RuntimeException(
							"Access token and expires not found");
				}
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}

	private String readURL(URL url) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		InputStream is = url.openStream();
		int r;
		while ((r = is.read()) != -1) {
			baos.write(r);
		}
		return new String(baos.toByteArray());
	}

	public void destroy() {
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}
}