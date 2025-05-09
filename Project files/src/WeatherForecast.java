import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import com.google.gson.*;

public class WeatherForecast {

    public static void main(String[] args) {
        // Variables are parameters for GET request
        double X = 0;
        double Y = 0;
        String Z = "temperature_2m";
        String W = "";
        String V = "EST";


        try{
            // Fills in parameters if they are present in command line argument
            for (int i = 0; i < args.length; i += 2) {
                if (i < args.length - 1){
                    if (args[i].equals("--latitude")){
                        X = Double.parseDouble(args[i + 1]);
                    }
                    else if (args[i].equals("--longitude")){
                        Y = Double.parseDouble(args[i + 1]);
                    }
                    else if (args[i].equals("--unit")){
                        if (args[i + 1].equals("C")){
                            W = "celsius";
                        }
                        else if (args[i + 1].equals("F")){
                            W = "fahrenheit";
                        }
                    }
                    else{
                        throw new IOException();
                    }
                }
            }

            // Establish connection
            URL url = new URL("https://api.open-meteo.com/v1/forecast?latitude="
                    + X + "&longitude="
                    + Y + "&hourly="
                    + Z + "&temperature_unit="
                    + W + "&timezone=" + V);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            // Throw IOException if connection is unsuccessful
            if (connection.getResponseCode() != 200){
                throw new IOException();
            }

            // Get data from weather API
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder builder = new StringBuilder();
            String current = "";
            while ((current = br.readLine()) != null){
                builder.append(current);
            }
            String reqData = builder.toString();

            // Organize elements from weather data
            JsonElement element = JsonParser.parseString(reqData);
            JsonObject hourly = element.getAsJsonObject().getAsJsonObject("hourly");
            JsonObject units = element.getAsJsonObject().getAsJsonObject("hourly_units");
            JsonArray times = hourly.getAsJsonArray("time");
            JsonArray temps = hourly.getAsJsonArray("temperature_2m");

            // Output 7-day weather forecast to System.out
            System.out.println("7-Day Forecast in Fahrenheit:");
            for (int i = 0; i < times.size() / 24; i++) {
                System.out.println("Forecast for " + times.get(i * 24).toString().split("T")[0].substring(1) + ":");
                for (int j = 0; j < 8; j++) {
                    System.out.println(times.get((i * 24) + (j * 3)).toString().split("T")[1].substring(0, 5)
                    + ": " + temps.get((i * 24) + (j * 3)).toString() + units.get("temperature_2m").toString().substring(1, 3));
                }
            }
        }
        catch(IOException ex){
            System.out.println("Error: Unexpected input. Make sure Latitude, Longitude, and Unit are inputted correctly.");
        }


    }


}
