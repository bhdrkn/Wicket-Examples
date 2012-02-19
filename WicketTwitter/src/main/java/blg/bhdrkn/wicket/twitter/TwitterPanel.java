/*
*   Copyright 2012 Bahadýr AKIN
*
*   Licensed under the Apache License, Version 2.0 (the "License");
*   you may not use this file except in compliance with the License.
*   You may obtain a copy of the License at
*
*       http://www.apache.org/licenses/LICENSE-2.0
*
*   Unless required by applicable law or agreed to in writing, software
*   distributed under the License is distributed on an "AS IS" BASIS,
*   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
*   See the License for the specific language governing permissions and
*   limitations under the License.
*/
package blg.bhdrkn.wicket.twitter;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.PropertyModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;

public class TwitterPanel extends Panel {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = LoggerFactory.getLogger(TwitterPanel.class);

	private Twitter twitter;
	private RequestToken requestToken;
	private String twitterPinModel = "";
	
	private AccessToken accessToken;

	public TwitterPanel(String id) {
		super(id);
		createTwitter();
		Form<Void> twitterForm = new Form<Void>("twitterForm");
		addTwitter(twitterForm);
		addFormSubmit(twitterForm);
		add(twitterForm);
	}

	private void createTwitter() {
		try {
			twitter = new TwitterFactory().getInstance();
			twitter.setOAuthConsumer(SocialMedia.TWITTER_CONSUMER_KEY,
					SocialMedia.TWITTER_CONSUMER_SECRET);
			requestToken = twitter.getOAuthRequestToken();
		} catch (TwitterException e) {
			e.printStackTrace();
		}
	}

	private void addFormSubmit(Form<Void> twitterForm) {
		twitterForm.add(new AjaxButton("button") {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				try {
					accessToken = twitter.getOAuthAccessToken(
							requestToken, twitterPinModel);
					setResponsePage(new TwitterPage(accessToken));
				} catch (TwitterException e) {
					logger.error("Error While Getting AccessToken: "
							+ e.getMessage());
					e.printStackTrace();
				}
			}

			@Override
			protected void onError(AjaxRequestTarget target, Form<?> form) {
				logger.error("Error");
			}

		});
	}

	private void addTwitter(Form<Void> twitterForm) {
		
		Link<String> simpleLink = new Link<String>("twitterLink") {
			
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick() {
				
			}
			
			@Override
			protected CharSequence getURL() {
				return requestToken.getAuthenticationURL();
			}
		};

		TextField<String> twitterPIN = new TextField<String>("twitterPIN",
				new PropertyModel<String>(this, "twitterPinModel"));

		twitterForm.add(simpleLink, twitterPIN);
	}
}

class SocialMedia{
	public static final String TWITTER_CONSUMER_KEY = "your_counsumer_key";
	public static final String TWITTER_CONSUMER_SECRET = "your_counsumer_secret"; 
}
