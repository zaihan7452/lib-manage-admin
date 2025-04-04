package com.hanzai.app.util;

import org.apache.hc.client5.http.classic.methods.HttpDelete;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.classic.methods.HttpPut;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.HttpHeaders;
import org.apache.hc.core5.http.HttpStatus;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.springframework.http.MediaType;

/**
 * Utility class for making HTTP requests using Apache HttpClient.
 * Provides methods for GET, POST, PUT, and DELETE requests.
 */
public class HttpClientUtil {

    /**
     * Makes an HTTP GET request to the specified URL.
     * @param url The URL to send the GET request to.
     * @return The response body as a String.
     */
    public static String sendGetRequest(String url) {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet httpGet = new HttpGet(url);
            return httpClient.execute(httpGet, response -> {
                if (response.getCode() == HttpStatus.SC_SUCCESS) {
                    return EntityUtils.toString(response.getEntity());
                } else {
                    throw new RuntimeException("Send GET request failed with status code: " + response.getCode());
                }
            });
        } catch (Exception e) {
            throw new RuntimeException("Failed to send GET request", e);
        }
    }

    /**
     * Makes an HTTP POST request to the specified URL with the given JSON payload.
     * @param url The URL to send the POST request to.
     * @param jsonBody The JSON body to send in the POST request.
     * @return The response body as a String.
     */
    public static String sendPostRequest(String url, String jsonBody) {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPost httpPost = new HttpPost(url);
            httpPost.setEntity(new StringEntity(jsonBody));
            httpPost.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
            httpPost.setHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);

            return httpClient.execute(httpPost, response -> {
                if (response.getCode() == HttpStatus.SC_SUCCESS) {
                    return EntityUtils.toString(response.getEntity());
                } else {
                    throw new RuntimeException("Send POST request failed with status code: " + response.getCode());
                }
            });
        } catch (Exception e) {
            throw new RuntimeException("Failed to send POST request", e);
        }
    }

    /**
     * Makes an HTTP PUT request to the specified URL with the given JSON payload.
     * @param url The URL to send the PUT request to.
     * @param jsonBody The JSON body to send in the PUT request.
     * @return The response body as a String.
     */
    public static String sendPutRequest(String url, String jsonBody) {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPut httpPut = new HttpPut(url);
            httpPut.setEntity(new StringEntity(jsonBody));

            return httpClient.execute(httpPut, response -> {
                if (response.getCode() == HttpStatus.SC_SUCCESS) {
                    return EntityUtils.toString(response.getEntity());
                } else {
                    throw new RuntimeException("Send PUT request failed with status code: " + response.getCode());
                }
            });
        } catch (Exception e) {
            throw new RuntimeException("Failed to send PUT request", e);
        }
    }

    /**
     * Makes an HTTP DELETE request to the specified URL.
     * @param url The URL to send the DELETE request to.
     * @return The response body as a String.
     */
    public static String sendDeleteRequest(String url)  {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpDelete httpDelete = new HttpDelete(url);
            return httpClient.execute(httpDelete, response -> {
                if (response.getCode() == HttpStatus.SC_SUCCESS) {
                    return EntityUtils.toString(response.getEntity());
                } else {
                    throw new RuntimeException("Send DELETE request failed with status code: " + response.getCode());
                }
            });
        } catch (Exception e) {
            throw new RuntimeException("Failed to send DELETE request", e);
        }
    }
}
