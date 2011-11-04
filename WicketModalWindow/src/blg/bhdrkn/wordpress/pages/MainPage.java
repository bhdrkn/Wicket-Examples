package blg.bhdrkn.wordpress.pages;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.WebPage;

import blg.bhdrkn.wordpress.modal.MyModalWindow;

public class MainPage extends WebPage{
	
	private MyModalWindow myModalWindow;

	public MainPage() {
		createAjaxLink();
		createModalWindow();
	}

	private void createAjaxLink() {
		add(new AjaxLink<String>("link"){

			private static final long serialVersionUID = -4317039855431131783L;

			@Override
			public void onClick(AjaxRequestTarget arg0) {
				System.out.println("onclick");
				myModalWindow.show(arg0);
			}
			
		});
	}
	
	private void createModalWindow() {
		myModalWindow = new MyModalWindow("myModalWindow");
		add(myModalWindow);
	}
}
