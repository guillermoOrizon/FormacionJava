package com.orizon.prometheus.client.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import io.prometheus.client.Counter;
import io.prometheus.client.Histogram;
import io.prometheus.client.Summary;

public class MetricsFilter implements Filter {
	static final String PATH_COMPONENT_PARAM = "path-components";
	static final String HELP_PARAM = "help";
	static final String METRIC_NAME_PARAM = "metric-name";
	static final String BUCKET_CONFIG_PARAM = "buckets";
	static final String UNKNOWN_HTTP_STATUS_CODE = "";

	private Summary requestDurationSummary = null;
	private Counter requestCount = null;
	private Histogram requestDuration = null;

	// Package-level for testing purposes.
	int pathComponents = 1;
	private String metricName = null;
	private String help = "The time taken fulfilling servlet requests";
	private double[] buckets = null;

	public MetricsFilter() {
	}

	public MetricsFilter(String metricName, String help, Integer pathComponents, double[] buckets) {
		this.metricName = metricName;
		this.buckets = buckets;
		if (help != null) {
			this.help = help;
		}
		if (pathComponents != null) {
			this.pathComponents = pathComponents;
		}
	}

	private boolean isEmpty(String s) {
		return s == null || s.length() == 0;
	}

	private String getComponents(String str) {
		if (str == null || pathComponents < 1) {
			return str;
		}
		int count = 0;
		int i = -1;
		do {
			i = str.indexOf("/", i + 1);
			if (i < 0) {
				// Path is longer than specified pathComponents.
				return str;
			}
			count++;
		} while (count <= pathComponents);

		return str.substring(0, i);
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

		Histogram.Builder builder = Histogram.build().labelNames("path", "method");

		if (filterConfig == null && isEmpty(metricName)) {
			throw new ServletException("No configuration object provided, and no metricName passed via constructor");
		}

		if (filterConfig != null) {
			if (isEmpty(metricName)) {
				metricName = filterConfig.getInitParameter(METRIC_NAME_PARAM);
				if (isEmpty(metricName)) {
					throw new ServletException(
							"Init parameter \"" + METRIC_NAME_PARAM + "\" is required; please supply a value");
				}
			}

			if (!isEmpty(filterConfig.getInitParameter(HELP_PARAM))) {
				help = filterConfig.getInitParameter(HELP_PARAM);
			}

			// Allow overriding of the path "depth" to track
			if (!isEmpty(filterConfig.getInitParameter(PATH_COMPONENT_PARAM))) {
				pathComponents = Integer.valueOf(filterConfig.getInitParameter(PATH_COMPONENT_PARAM));
			}

			// Allow users to override the default bucket configuration
			if (!isEmpty(filterConfig.getInitParameter(BUCKET_CONFIG_PARAM))) {
				String[] bucketParams = filterConfig.getInitParameter(BUCKET_CONFIG_PARAM).split(",");
				buckets = new double[bucketParams.length];

				for (int i = 0; i < bucketParams.length; i++) {
					buckets[i] = Double.parseDouble(bucketParams[i]);
				}
			}
		}

		if (buckets != null) {
			builder = builder.buckets(buckets);
		}

		requestDuration = builder.name("request_duration").help("Time for HTTP request.").register();

		requestCount = Counter.build().name("request_count").help("Number of requests.").register();

		requestDurationSummary = Summary.build().quantile(0.5, 0.05) // Add 50th percentile (= median) with 5% tolerated
				.quantile(0.9, 0.01) // Add 90th percentile with 1% tolerated error
				.name("request_duration_summary").help("Time for HTTP request.").register();
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		if (!(servletRequest instanceof HttpServletRequest)) {
			filterChain.doFilter(servletRequest, servletResponse);
			return;
		}

		HttpServletRequest request = (HttpServletRequest) servletRequest;

		String path = request.getRequestURI();
		// String components = getComponents(path);
		String components = path;
		String method = request.getMethod();

		requestDurationSummary.time(() -> {

			Histogram.Timer timer = requestDuration.labels(components, method).startTimer();

			try {
				filterChain.doFilter(servletRequest, servletResponse);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ServletException e) {
				e.printStackTrace();
			} finally {
				timer.observeDuration();
				requestCount.inc();
			}
		});
	}

	private String getStatusCode(ServletResponse servletResponse) {
		if (!(servletResponse instanceof HttpServletResponse)) {
			return UNKNOWN_HTTP_STATUS_CODE;
		}

		return Integer.toString(((HttpServletResponse) servletResponse).getStatus());
	}

	@Override
	public void destroy() {
	}
}
