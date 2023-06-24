package API;

import java.io.BufferedReader;
import org.json.JSONObject;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class UserController {
	private static final String SERVER_URL = "http://127.0.0.1:8000";

    public String register(String username, String email, String phone_number, String password) {
        String result = null;
    	try {
    		String register_url = SERVER_URL + "/api/user/merchant/register";
        	URL url = new URL(register_url);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // Set the required HTTP method and headers
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");

            // Set the request body
            String requestBody = "{\"name\":\"" + username + "\",\"email\":\"" + email + "\",\"phone_number\":\"" + phone_number + "\",\"password\":\"" + password + "\"}";
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
    
    public String login(String email, String password) {
    	String result = null;
    	try {
    		String register_url = SERVER_URL + "/api/user/login";
        	URL url = new URL(register_url);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // Set the required HTTP method and headers
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");

            // Set the request body
            String requestBody = "{\"email\":\"" + email + "\",\"password\":\"" + password + "\"}";
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

                boolean authorized = jsonObject.getBoolean("authorized");

                System.out.println(authorized);
                if(authorized) {
                	result = "Authorized";
                }else {
                	result = "Unauthorized";
                }
                
                // Add your code to handle the JSON data here
            } else {
                System.out.println("POST request failed. Response Code: " + responseCode);
                result = "Unauthorized";
            }
            connection.disconnect();
           
        } catch (IOException e) {
            e.printStackTrace();
            result = "Error";
        }
    	return result;
    }
}
