package blg.bhdrkn.facebook.application;

import org.apache.wicket.Component;
import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.Session;
import org.apache.wicket.authorization.Action;
import org.apache.wicket.authorization.IAuthorizationStrategy;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.request.IRequestHandler;
import org.apache.wicket.request.IRequestMapper;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.Response;
import org.apache.wicket.request.Url;
import org.apache.wicket.request.component.IRequestableComponent;
import org.apache.wicket.request.resource.caching.QueryStringWithVersionResourceCachingStrategy;

import blg.bhdrkn.facebook.pages.AuthenticatedWebPage;
import blg.bhdrkn.facebook.pages.LoginPage;
import blg.bhdrkn.facebook.pages.SignedInPage;

public class FacebookApplication extends WebApplication{

    public Class getHomePage() {
        return LoginPage.class;
    }
    
	@Override
	protected void init() {
		getDebugSettings().setAjaxDebugModeEnabled(false);
		mountPage("SignedIn", SignedInPage.class);
		getSecuritySettings().setAuthorizationStrategy(new IAuthorizationStrategy() {
			
			@Override
			public <T extends IRequestableComponent> boolean isInstantiationAuthorized(
					Class<T> componentClass) {
				if (AuthenticatedWebPage.class
						.isAssignableFrom(componentClass)) {
					// Is user signed in?
					if (((LoginSession) Session.get()).isSignedIn()) {
						// okay to proceed
						return true;
					}
					// Force sign in
					throw new RestartResponseAtInterceptPageException(
							LoginPage.class);
				}
				return true;
			}

			@Override
			public boolean isActionAuthorized(Component arg0, Action arg1) {
				return true;
			}
		});
	}
	
	@Override
	public Session newSession(Request request, Response response) {
		return new LoginSession(request);
	}

}
