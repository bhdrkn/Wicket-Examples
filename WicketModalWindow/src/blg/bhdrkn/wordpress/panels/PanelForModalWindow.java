package blg.bhdrkn.wordpress.panels;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;

public class PanelForModalWindow extends Panel{
	
	private static final long serialVersionUID = -7901234959122172282L;

	public PanelForModalWindow(String id) {
		super(id);
		createInformationLabel();
	}

	private void createInformationLabel() {
		add(new Label("informationLabel", "*** Modal Window Opened OK! ***"));
	}
}
