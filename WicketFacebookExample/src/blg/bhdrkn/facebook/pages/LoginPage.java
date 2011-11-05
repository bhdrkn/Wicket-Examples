package blg.bhdrkn.facebook.pages;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.link.Link;

import blg.bhdrkn.facebook.domain.Facebook;


public class LoginPage extends WebPage{
	
	public LoginPage() {
		add(new Link<String>("myLink") {
			
			@Override
			protected CharSequence getURL() { 
				return Facebook.getLoginRedirectURL();
			}

			@Override
			public void onClick() {
				System.out.println(getRequest().getUrl().toString());
			}
		});
	}

}
