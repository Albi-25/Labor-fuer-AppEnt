import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class CloudUsageCalculator {

    // Step 1: Fetch event data
    public JSONArray fetchEventData() {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8080/v1/dataset";

        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

        // Parse the response into a JSON Array
        JSONObject jsonResponse = new JSONObject(response.getBody());
        return jsonResponse.getJSONArray("events");
    }

    // Step 2: Calculate total usage per customer
    public Map<String, Long> calculateUsage(JSONArray events) {
        Map<String, Map<String, Long>> usageByCustomer = new HashMap<>();
        Map<String, Long> totalUsageByCustomer = new HashMap<>();

        for (int i = 0; i < events.length(); i++) {
            JSONObject event = events.getJSONObject(i);
            String customerId = event.getString("customerId");
            String workloadId = event.getString("workloadId");
            long timestamp = event.getLong("timestamp");
            String eventType = event.getString("eventType");

            usageByCustomer.putIfAbsent(customerId, new HashMap<>());

            if (eventType.equals("start")) {
                // Save start time
                usageByCustomer.get(customerId).put(workloadId, timestamp);
            } else if (eventType.equals("stop")) {
                // Calculate usage time
                if (usageByCustomer.get(customerId).containsKey(workloadId)) {
                    long startTime = usageByCustomer.get(customerId).get(workloadId);
                    long usageTime = timestamp - startTime;

                    // Add to total usage time for the customer
                    totalUsageByCustomer.put(customerId,
                            totalUsageByCustomer.getOrDefault(customerId, 0L) + usageTime);
                }
            }
        }

        return totalUsageByCustomer;
    }

    // Step 3: Send results via POST request
    public void sendResults(Map<String, Long> totalUsageByCustomer) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8080/v1/result";

        // Create JSON body to send
        JSONArray resultArray = new JSONArray();

        for (Map.Entry<String, Long> entry : totalUsageByCustomer.entrySet()) {
            JSONObject result = new JSONObject();
            result.put("customerId", entry.getKey());
            result.put("consumption", entry.getValue());
            resultArray.put(result);
        }

        JSONObject requestBody = new JSONObject();
        requestBody.put("result", resultArray);

        // Set headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Send POST request
        HttpEntity<String> entity = new HttpEntity<>(requestBody.toString(), headers);
        restTemplate.postForEntity(url, entity, String.class);
    }

    // Main function
    public static void main(String[] args) {
        CloudUsageCalculator calculator = new CloudUsageCalculator();

        // Step 1: Fetch the event data
        JSONArray events = calculator.fetchEventData();

        // Step 2: Calculate total usage
        Map<String, Long> totalUsageByCustomer = calculator.calculateUsage(events);

        // Step 3: Send the results
        calculator.sendResults(totalUsageByCustomer);

        System.out.println("Leistungsnachweis and submission completed.");
    }
}
