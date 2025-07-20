package com.mr486.safetynet.configuration;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;

/**
 * A filter that logs the details of incoming HTTP requests and outgoing HTTP responses.
 * It wraps the request and response to capture their content for logging purposes.
 */
@Slf4j
@Configuration
public class RequestResponseLoggingFilter extends OncePerRequestFilter {

  /**
   * Filters the request and response, logging their details.
   *
   * @param request     the incoming HTTP request
   * @param response    the outgoing HTTP response
   * @param filterChain the filter chain to pass the request and response to the next filter
   * @throws ServletException if an error occurs during filtering
   * @throws IOException      if an I/O error occurs during filtering
   */
  @Override
  protected void doFilterInternal(HttpServletRequest request,
                                  HttpServletResponse response,
                                  FilterChain filterChain)
          throws ServletException, IOException {

    // Wrap the request and response to enable content caching
    ContentCachingRequestWrapper wrappedRequest = new ContentCachingRequestWrapper(request);
    ContentCachingResponseWrapper wrappedResponse = new ContentCachingResponseWrapper(response);

    // Proceed with the filter chain
    filterChain.doFilter(wrappedRequest, wrappedResponse);

    // Log the request and response details
    logRequest(wrappedRequest);
    logResponse(wrappedResponse);

    // Copy the cached response body back to the original response
    wrappedResponse.copyBodyToResponse();
  }

  /**
   * Logs the details of the incoming HTTP request.
   *
   * @param request the wrapped HTTP request
   */
  private void logRequest(ContentCachingRequestWrapper request) {
    String method = request.getMethod();
    String uri = request.getRequestURI();
    String query = request.getQueryString();
    String headers = getHeadersAsString(request);
    String body = new String(request.getContentAsByteArray(), StandardCharsets.UTF_8);

    log.info("==== Incoming request ====");
    log.info("Method: {}", method);
    log.info("URI: {}{}", uri, (query != null ? "?" + query : ""));
    log.info("Headers: {}", headers);
    log.info("Request body: {}", body);
  }

  /**
   * Logs the details of the outgoing HTTP response.
   *
   * @param response the wrapped HTTP response
   */
  private void logResponse(ContentCachingResponseWrapper response) {
    String body = new String(response.getContentAsByteArray(), StandardCharsets.UTF_8);

    if (response.getStatus() >= 400) {
      log.error("==== Outgoing request ====");
      log.error("Status: {}", response.getStatus());
      log.error("Response body: {}", body);
    } else {
      log.info("==== Outgoing request ====");
      log.info("Status: {}", response.getStatus());
      log.info("Response body: {}", body);
    }

  }

  /**
   * Retrieves the headers of the HTTP request as a formatted string.
   *
   * @param request the HTTP request
   * @return a string representation of the request headers
   */
  private String getHeadersAsString(HttpServletRequest request) {
    StringBuilder headers = new StringBuilder();
    Enumeration<String> headerNames = request.getHeaderNames();
    while (headerNames.hasMoreElements()) {
      String name = headerNames.nextElement();
      String value = request.getHeader(name);
      headers.append(name).append(": ").append(value).append("; ");
    }
    return headers.toString();
  }
}
