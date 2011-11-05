package blg.bhdrkn.facebook.pages;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;

import blg.bhdrkn.facebook.application.LoginSession;
import blg.bhdrkn.facebook.domain.Facebook;
import blg.bhdrkn.facebook.domain.User;

public class SignedInPage extends WebPage implements AuthenticatedWebPage {

	private static final long serialVersionUID = -5476595548698214837L;
	private User signedInUser;

	private static final String Message = "Facebook Test Message...";

	public SignedInPage() {
		publishUserInformation();
		createLogOutLink();
		createSendMessagetoFacebookWallLink();
	}

	private void createSendMessagetoFacebookWallLink() {
		add(new Link<String>("send") {

			@Override
			public void onClick() {
				Facebook.sendToWall(Message, signedInUser.getId());
			}

		});

	}

	private void createLogOutLink() {
		add(new Link<String>("logout") {

			@Override
			public void onClick() {
				getSession().invalidate();
				setResponsePage(LoginPage.class);
			}

		});
	}

	private void publishUserInformation() {
		signedInUser = ((LoginSession) getSession()).getUser();
		add(new Label("userId", String.valueOf(signedInUser.getId())));
		add(new Label("firstName", signedInUser.getFirstName()));
		add(new Label("lastName", signedInUser.getLastName()));
		add(new Label("email", signedInUser.getEmail()));
	}
}
