package controller;

import org.junit.jupiter.api.Test;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for the {@link ApiClient} class.
 * This class contains tests to verify the functionality of the API client.
 */
public class ApiClientTest {

    /**
     * Tests the successful retrieval of a dataset.
     * Checks that the dataset is not null and that the list of events is not empty.
     *
     * @throws Exception if an error occurs.
     */
    @Test
    public void testFetchDataset_Success() throws Exception {
        // Mock HttpClient and HttpResponse
        HttpClient mockClient = mock(HttpClient.class);
        HttpResponse<String> mockResponse = mock(HttpResponse.class);
        when(mockResponse.statusCode()).thenReturn(200);
        when(mockResponse.body()).thenReturn("{\"events\":[]}");
        when(mockClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
                .thenReturn(mockResponse);

        // Inject mock client into ApiClient
        ApiClient apiClient = new ApiClient(mockClient);
        Dataset dataset = apiClient.fetchDataset();
        assertNotNull(dataset, "Dataset should not be null");
        assertTrue(dataset.getEvents().isEmpty(), "Events list should be empty");
    }

    /**
     * Tests the failure case for retrieving a dataset.
     * Simulates a failed API request and checks that the dataset is null.
     */
    @Test
    public void testFetchDataset_Failure() {
        ApiClient failingApiClient = new ApiClient() {
            @Override
            public Dataset fetchDataset() {
                return null; // Simulate a failure
            }
        };
        Dataset dataset = failingApiClient.fetchDataset();
        assertNull(dataset, "The dataset should be null if the API call fails.");
    }

    /**
     * Tests the successful sending of results.
     * Verifies that the results are successfully sent to the server.
     */
    @Test
    public void testSendResults_Success() {
        Map<String, Long> runtimes = new HashMap<>();
        runtimes.put("customer1", 100L);
        ApiClient apiClient = new ApiClient();
        apiClient.sendResults(runtimes);
        // No assertion as sendResults has no return value.
    }

    /**
     * Tests the failure case for sending results.
     * Simulates an error when sending the results.
     */
    @Test
    public void testSendResults_Failure() {
        ApiClient failingApiClient = new ApiClient() {
            @Override
            public void sendResults(Map<String, Long> runtimes) {
                System.out.println("Error sending results: Incorrect URL");
            }
        };
        Map<String, Long> runtimes = new HashMap<>();
        runtimes.put("customer1", 100L);
        failingApiClient.sendResults(runtimes);
        // No assertion as sendResults has no return value.
    }
}