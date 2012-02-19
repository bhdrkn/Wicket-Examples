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
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.model.PropertyModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;

public class TwitterPage extends WebPage {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = LoggerFactory.getLogger(TwitterPage.class);

	private Twitter twitter;
	private AccessToken token;

	private Form<Void> tweetForm;
	private TextArea<String> tweetArea;
	private String tweetModel;

	public TwitterPage(AccessToken token) {
		this.token = token;
		addHashTagNews();
		addTweetForm();
	}

	private void addHashTagNews() {

	}

	private void addTweetForm() {
		tweetForm = new Form<Void>("tweetForm");
		tweetArea = new TextArea<String>("tweetArea",
				new PropertyModel<String>(this, "tweetModel"));
		tweetArea.setOutputMarkupId(true);
		tweetForm.add(tweetArea);
		tweetForm.add(new AjaxButton("updateStatus") {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				try {
					twitter = new TwitterFactory().getInstance();
					twitter.setOAuthConsumer(SocialMedia.TWITTER_CONSUMER_KEY,
							SocialMedia.TWITTER_CONSUMER_SECRET);
					twitter.setOAuthAccessToken(token);
					twitter.updateStatus(tweetModel);
					target.appendJavaScript("alert('Status Updated: "
							+ tweetModel + "');");
					tweetModel = "";
					target.add(tweetArea);
				} catch (TwitterException e) {
					logger.error("Error While Updating the Status");
					e.printStackTrace();
				}
			}

			@Override
			protected void onError(AjaxRequestTarget target, Form<?> form) {
				logger.error("Error Before Updating the Status");
			}
		});
		add(tweetForm);
	}
	/*	
	private void addHashTagLink(Form<Void> twitterForm) {
		new AjaxLink<String>("hashTag") {

			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target) {
				Twitter hasTwitter = new TwitterFactory().getInstance();
				String hashTag = "#twitter4j";
				Query query = new Query(hashTag);
				query.since("2012-01-20");
				try {
					QueryResult results = hasTwitter.search(query);
					System.out.println("RESULT: " + results.getTweets().size());
					for (Tweet tweet : results.getTweets()) {
						System.out.println("Tweet: " + tweet.getFromUser()
								+ " " + tweet.getText());
					}
				} catch (TwitterException e) {
					System.out
							.println("Error While getting hashTag information M: "
									+ e.getMessage());
					e.printStackTrace();
				}

			}

		};
	}
*/
}
