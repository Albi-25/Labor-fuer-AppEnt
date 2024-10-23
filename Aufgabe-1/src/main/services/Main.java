import controller.ApiClient;
import controller.Dataset;
import services.RuntimeCalculator;

import java.util.Map;

public class Main {


    /**
     * The main method serves as the entry point for the {@code ApiClient} class.
     * It fetches a dataset, processes it, and outputs the total runtime per customer.
     *
     * @param args command-line arguments (not used).
     */
    public static void main(String[] args) {
        ApiClient controller = new ApiClient();
        Dataset dataset = controller.fetchDataset(); // Fetch data

        if (dataset != null) {
            RuntimeCalculator calculator = new RuntimeCalculator();
            Map<String, Long> runtimes = calculator.calculateRuntimes(dataset); // Process data

            // Output the total runtimes per customer in milliseconds
            runtimes.forEach((customerId, totalRuntime) ->
                    System.out.println("Customer ID: " + customerId + ", Total Runtime: " + totalRuntime + " ms"));

            // Sende die Ergebnisse an die POST-API
            controller.sendResults(runtimes);
        } else {
            System.out.println("Failed to fetch dataset.");
        }
    }
}