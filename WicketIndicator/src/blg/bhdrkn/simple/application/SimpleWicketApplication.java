package blg.bhdrkn.simple.application;

import org.apache.wicket.Page;
import org.apache.wicket.protocol.http.WebApplication;

import blg.bhdrkn.wordpress.indicator.MainPage;

public class SimpleWicketApplication extends WebApplication{

	@Override
	public Class<? extends Page> getHomePage() {
		return MainPage.class;
	}

}
