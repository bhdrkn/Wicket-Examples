package blg.bhdrkn.wordpress.modal;

import org.apache.wicket.Page;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.WebPage;

import blg.bhdrkn.wordpress.panels.PanelForModalWindow;

public class MyModalWindow extends ModalWindow {

	private static final long serialVersionUID = -4132592600685752985L;

	private PanelForModalWindow panelForModalWindow;

	public MyModalWindow(String id) {
		super(id);
		panelForModalWindow = new PanelForModalWindow(this.getContentId());
		setContent(panelForModalWindow);

		// Modal Window Olustururken Page'te kullanilabilir.
		// Fakat Form submit edildiginde sorun cikarabiliyor
		// this.setPageCreator(new PageCreator() {
		//
		// @Override
		// public Page createPage() {
		// return new AWebPage();
		// }
		// });
	}

}
