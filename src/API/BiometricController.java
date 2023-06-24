package API;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONObject;

public class BiometricController {
	private static final String SERVER_URL = "http://127.0.0.1:8000";

    public static String register(String email) {
        String result = null;
    	try {
    		String register_url = SERVER_URL + "/api/biometric/register";
        	URL url = new URL(register_url);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // Set the required HTTP method and headers
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");

            // Set the request body
            String requestBody = "{\"email\":\"" + email + "\"}";
            connection.setDoOutput(true);
            DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream());
            outputStream.writeBytes(requestBody);
            outputStream.flush();
            outputStream.close();

            // Get the response from the server
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;
                StringBuilder response = new StringBuilder();
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                // Process the JSON response
                String jsonResponse = response.toString();
                JSONObject jsonObject = new JSONObject(jsonResponse);

                boolean created = jsonObject.getBoolean("created");
                String error = jsonObject.getString("error");

               if(created) {
            	   result = "Success";
               }else {
            	   result = error;
               }
                // Add your code to handle the JSON data here
            } else {
                System.out.println("POST request failed. Response Code: " + responseCode);
                result = "Failed";
            }
            connection.disconnect();
           
        } catch (IOException e) {
            e.printStackTrace();
            result = "Failed";
        }
		return result;
    }
    
    public static String pay(String payeeEmail, String merchantEmail, String amount) {
    	String result = null;
    	try {
    		String register_url = SERVER_URL + "/api/trx/pay";
        	URL url = new URL(register_url);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // Set the required HTTP method and headers
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");

            // Set the request body
            String requestBody = "{\"payeremail\":\"" + payeeEmail + "\",\"payeeemail\":\"" + merchantEmail + "\",\"amount\":\"" + amount + "\"}";
            connection.setDoOutput(true);
            DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream());
            outputStream.writeBytes(requestBody);
            outputStream.flush();
            outputStream.close();

            // Get the response from the server
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;
                StringBuilder response = new StringBuilder();
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                // Process the JSON response
                String jsonResponse = response.toString();
                JSONObject jsonObject = new JSONObject(jsonResponse);

                boolean status = jsonObject.getBoolean("status");
                String error = jsonObject.getString("error");

                System.out.println(status);
                if(status) {
                	result = "Success";
                }else {
                	result = error;
                }
                
                // Add your code to handle the JSON data here
            } else {
                System.out.println("POST request failed. Response Code: " + responseCode);
                result = "Error performing transaction";
            }
            connection.disconnect();
           
        } catch (IOException e) {
            e.printStackTrace();
            result = "Error";
        }
    	return result;
    }
}
