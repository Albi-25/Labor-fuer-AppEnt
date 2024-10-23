package controller;

import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The {@code ApiClient} class is responsible for fetching a dataset from a REST API
 * and using a {@code RuntimeCalculator} to process and calculate runtimes based on
 * the retrieved data.
 */
public class ApiClient {
    // Url zurück zu localhost wenn die Anwendung doch nicht ausgefphrt werden soll mit Docker-Compose
    private static final String GET_URL = "http://backend:8080/v1/dataset";
    private static final String POST_URL = "http://backend:8080/v1/result";

    private HttpClient client;

    public ApiClient() {
        this.client = HttpClient.newHttpClient();
    }

    public ApiClient(HttpClient client) {
        this.client = client != null ? client : HttpClient.newHttpClient();
    }

    /**
     * Fetches a dataset from a predefined URL using an HTTP GET request.
     *
     * @return the {@code Dataset} object parsed from the JSON response, or {@code null} if the request fails.
     */
    public Dataset fetchDataset() {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                                             .uri(new URL(GET_URL).toURI())
                                             .GET()
                                             .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                String jsonResponse = response.body();
                Gson gson = new Gson();
                return gson.fromJson(jsonResponse, Dataset.class);
            } else {
                System.out.println("GET request failed: " + response.statusCode());
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Sends the runtime results to the specified POST URL.
     *
     * @param runtimes a map containing customer IDs and their total runtimes.
     */
    public void sendResults(Map<String, Long> runtimes) {
        try {
            Gson gson = new Gson();
            // Create a structure that matches the expected format
            Map<String, Object> requestBody = new HashMap<>();
            List<Map<String, Object>> resultsList = new ArrayList<>();
            for (Map.Entry<String, Long> entry : runtimes.entrySet()) {
                Map<String, Object> resultEntry = new HashMap<>();
                resultEntry.put("customerId", entry.getKey());
                resultEntry.put("consumption", entry.getValue());
                resultsList.add(resultEntry);
            }
            requestBody.put("result", resultsList); // Wrap the list in a map with key "result"
            // Convert the request body to JSON
            String json = gson.toJson(requestBody);
            HttpRequest request = HttpRequest.newBuilder()
                                             .uri(URI.create(POST_URL))
                                             .header("Content-Type", "application/json")
                                             .POST(HttpRequest.BodyPublishers.ofString(json))
                                             .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                System.out.println("Results successfully sent.");
            } else {
                System.out.println("Failed to send results: " + response.statusCode());
                System.out.println("Response body: " + response.body());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}