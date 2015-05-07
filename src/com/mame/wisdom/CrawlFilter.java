package com.mame.wisdom;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class CrawlFilter implements Filter {

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException {

		// if ((queryString != null)
		// && (queryString.contains("_escaped_fragment_"))) {
		//
		// // rewrite the URL back to the original #! version
		// // remember to unescape any %XX characters
		// String url_with_hash_fragment =
		// rewriteQueryString(url_with_escaped_fragment);
		//
		// // use the headless browser to obtain an HTML snapshot
		// final WebClient webClient = new WebClient();
		// HtmlPage page = webClient.getPage(url_with_hash_fragment);
		//
		// // important! Give the headless browser enough time to execute
		// // JavaScript
		// // The exact time to wait may depend on your application.
		// webClient.waitForBackgroundJavaScript(2000);
		//
		// // return the snapshot
		// out.println(page.asXml());
		// } else {
		// try {
		// // not an _escaped_fragment_ URL, so move up the chain of
		// // servlet (filters)
		// chain.doFilter(request, response);
		// } catch (ServletException e) {
		// System.err.println("Servlet exception caught: " + e);
		// e.printStackTrace();
		// }
		// }
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}
}
