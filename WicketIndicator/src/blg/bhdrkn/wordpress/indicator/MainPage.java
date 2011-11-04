package blg.bhdrkn.wordpress.indicator;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.extensions.ajax.markup.html.AjaxIndicatorAppender;
import org.apache.wicket.extensions.ajax.markup.html.IndicatingAjaxButton;
import org.apache.wicket.extensions.ajax.markup.html.IndicatingAjaxLink;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.PropertyModel;

public class MainPage extends WebPage {

	private String selected;
	private AjaxIndicatorAppender appender;

	public MainPage() {
		createIndicatorAjaxLink();
		createIndicatorButtonLink();
		createDropDownChoice();
	}

	private void createIndicatorAjaxLink() {
		add(new IndicatingAjaxLink<String>("indiLink") {

			private static final long serialVersionUID = 1950266229701768559L;

			@Override
			public void onClick(AjaxRequestTarget arg0) {
				testMethod();
			}
		});
	}

	private void createIndicatorButtonLink() {
		Form<Void> testForm = new Form<Void>("testForm");
		testForm.add(new IndicatingAjaxButton("indiButton") {

			private static final long serialVersionUID = 8622275727240710989L;

			@Override
			protected void onSubmit(AjaxRequestTarget arg0, Form<?> arg1) {
				testMethod();

			}
		});
		add(testForm);

	}

	private void createDropDownChoice() {
		List<String> choices = new ArrayList<String>();
		choices.add("choice 1");
		choices.add("choice 2");
		choices.add("choice 3");
		selected = "choice 2";
		DropDownChoice<String> choice = new DropDownChoice<String>(
				"dropDownChoice", new PropertyModel<String>(this, "selected"),
				choices);
		choice.add(new AjaxFormComponentUpdatingBehavior("onchange") {

			private static final long serialVersionUID = -965699876933426907L;

			@Override
			protected void onUpdate(AjaxRequestTarget arg0) {
				System.out.println("SELECTED CHOICE CHANGED: " + selected);
				testMethod();
			}

			@Override
			protected String findIndicatorId() {
				return appender.getMarkupId();
			}
		});
		appender = new AjaxIndicatorAppender();
		choice.add(appender);
		add(choice);
	}

	protected void testMethod() {
		System.out.println("STARTED");
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// Ignore
		}
		System.out.println("END");
	}
}
