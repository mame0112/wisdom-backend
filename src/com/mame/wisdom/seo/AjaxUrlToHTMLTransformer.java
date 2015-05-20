package com.mame.wisdom.seo;

import org.apache.commons.collections.Transformer;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.SilentCssErrorHandler;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebClientOptions;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class AjaxUrlToHTMLTransformer implements Transformer {

	/**
	 * {@link WebClient#waitForBackgroundJavaScript(long)}
	 * 
	 * @default 15000
	 */
	private int javaScriptWaitSecs = 15000;

	/**
	 * {@link BrowserVersion}
	 * 
	 * @default {@link BrowserVersion#FIREFOX_24}
	 */
	private BrowserVersion browser = BrowserVersion.FIREFOX_17;

	/**
	 * Connect to servers that have any SSL certificate
	 * 
	 * @see WebClientOptions#setUseInsecureSSL(boolean)
	 */
	private boolean supportInsecureSSL = true;

	/**
	 * If false will ignore JavaScript errors
	 * 
	 * @default false
	 */
	private boolean haltOnJSError = false;

	private static final SilentCssErrorHandler cssErrhandler = new SilentCssErrorHandler();

	@Override
	public Object transform(Object input) {
		if (input == null) {
			return null;
		}

		final WebClient webClient = new WebClient(browser);
		WebClientOptions options = webClient.getOptions();
		options.setJavaScriptEnabled(true);
		options.setThrowExceptionOnScriptError(haltOnJSError);
		options.setUseInsecureSSL(supportInsecureSSL);

		// Optimizations
		// options.setPopupBlockerEnabled(true); //No use for popups
		options.setCssEnabled(false); // For crawling we don't care about CSS
										// since its going to be Window less
		webClient.setCssErrorHandler(cssErrhandler);
		options.setAppletEnabled(false);

		// The following two lines make it possible to wait for the initial JS
		// to load the products via AJAX and include it in the final HTML
		webClient.waitForBackgroundJavaScript(javaScriptWaitSecs); // Wait for
																	// document.ready
																	// Auto
																	// search to
																	// fire and
																	// fetch
																	// page
																	// results
																	// via AJAX
		webClient.setAjaxController(new NicelyResynchronizingAjaxController());

		try {
			final HtmlPage page = webClient.getPage(input.toString());

			final String pageAsXml = page.asXml();
			webClient.closeAllWindows();

			return pageAsXml;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
