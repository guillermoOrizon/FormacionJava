package com.orizon.prometheus.client.exporter;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.orizon.prometheus.client.hotspot.DefaultExports;

import io.prometheus.client.CollectorRegistry;
import io.prometheus.client.exporter.common.TextFormat;

/**
 * Servlet implementation class MetricsServlet
 */
@WebServlet("/MetricsServlet")
public class MetricsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private CollectorRegistry registry;
	

	  /**
	   * Construct a MetricsServlet for the default registry.
	   */
	  public MetricsServlet() {
	    this(CollectorRegistry.defaultRegistry);
	  }

	  /**
	   * Construct a MetricsServlet for the given registry.
	   * @param registry collector registry
	   */
	  public MetricsServlet(CollectorRegistry registry) {
	    this.registry = registry;
	    
	    //Registro las métricas definidas en la clase DefaultExports con información de la JVM HotsPot
	    DefaultExports.register(registry);
	  }

	  @Override
	  protected void doGet(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
	    
		resp.setStatus(HttpServletResponse.SC_OK);
	    String contentType = TextFormat.chooseContentType(req.getHeader("Accept"));
	    resp.setContentType(contentType);

	    Writer writer = new BufferedWriter(resp.getWriter());
	    try {
	      TextFormat.writeFormat(contentType, writer, registry.filteredMetricFamilySamples(parse(req)));
	      writer.flush();
	    } finally {
	      writer.close();
	    }
	  }

	  private Set<String> parse(HttpServletRequest req) {
	    String[] includedParam = req.getParameterValues("name[]");
	    if (includedParam == null) {
	      return Collections.emptySet();
	    } else {
	      return new HashSet<String>(Arrays.asList(includedParam));
	    }
	  }

	  @Override
	  protected void doPost(final HttpServletRequest req, final HttpServletResponse resp)
	          throws ServletException, IOException {
	    doGet(req, resp);
	  }

	}
