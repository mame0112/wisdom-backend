package com.mame.wisdom.seo;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.mame.wisdom.util.DbgUtil;

public class CrawlServlet implements Filter {

	private final static String TAG = CrawlServlet.class.getSimpleName();

	private final static String GOOGLE_AJAX_CRAWL_FRAGMENT = "?_escaped_fragment_=";

	private final static String HASH = "#!";

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {

		DbgUtil.showLog(TAG, "doFilter");

		// if ((queryString != null)
		// && (queryString.contains("_escaped_fragment_"))) {
		if (isSEOCrawlRequest(request)) {
			DbgUtil.showLog(TAG, "This is SEO crawl!");

			String escapedFragmentUrl = extractRequestUrl(request);

			// rewrite the URL back to the original #! version
			// remember to unescape any %XX characters
			String hashedFragmentUrl = rewriteQueryString(escapedFragmentUrl);

			// use the headless browser to obtain an HTML snapshot
			final WebClient webClient = new WebClient();
			HtmlPage page = webClient.getPage(hashedFragmentUrl);

			// important! Give the headless browser enough time to execute
			// JavaScript
			// The exact time to wait may depend on your application.
			webClient.waitForBackgroundJavaScript(5000);

			PrintWriter out = new PrintWriter(new OutputStreamWriter(
					response.getOutputStream(), "UTF-8"));

			// return the snapshot
			out.println(page.asXml());
			out.flush();
			out.close();
		} else {
			try {
				// not an _escaped_fragment_ URL, so move up the chain of
				// servlet (filters)
				chain.doFilter(request, response);
			} catch (ServletException e) {
				System.err.println("Servlet exception caught: " + e);
				e.printStackTrace();
			}
		}

	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}

	private String rewriteQueryString(String escapedUrl) {

		if (escapedUrl != null) {
			String result = escapedUrl
					.replace(GOOGLE_AJAX_CRAWL_FRAGMENT, HASH);
			DbgUtil.showLog(TAG, "rewrite result: " + result);
			return result;
		}

		return null;
	}

	private String extractRequestUrl(final ServletRequest request) {
		if (request instanceof HttpServletRequest) {
			String url = ((HttpServletRequest) request).getRequestURL()
					.toString();
			DbgUtil.showLog(TAG, "request url: " + url);
			return url;
		}

		return null;
	}

	private boolean isSEOCrawlRequest(final ServletRequest request) {
		DbgUtil.showLog(TAG, "isSEOCrawlRequest");

		if (request instanceof HttpServletRequest) {
			String q = ((HttpServletRequest) request).getQueryString();
			DbgUtil.showLog(TAG, "q: " + q);

			String url = ((HttpServletRequest) request).getRequestURL()
					.toString();
			DbgUtil.showLog(TAG, "url: " + url);

			if (q != null && q.contains(GOOGLE_AJAX_CRAWL_FRAGMENT)) {
				return true;
			}
		} else {
			DbgUtil.showLog(TAG, "Illegal instance type");
		}

		return false;
		// return q != null ? q.contains(GOOGLE_AJAX_CRAWL_FRAGMENT)
		// && !q.contains("seoajaxcycle=true") /* Avoid recursion */
		// : false;
	}
}
