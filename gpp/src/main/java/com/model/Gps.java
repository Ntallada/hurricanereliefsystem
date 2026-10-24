package com.model;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class Gps {
    private String location;
    private ArrayList<String> sheterAddress;
    private ArrayList<String> affestedAreas;
    private boolean satelliteCon;
    private boolean disabilityCheck;

    private static final String GEOCODE_URL = "https://api.heigit.org/pelias/v1/search";
    private static final String ROUTE_URL = "https://api.heigit.org/openrouteservice/v2/directions/driving-car";
    private static final String KEY_FILE = "gpp/ors_key.txt";

    public void disableRoute() {

    }

    // reads the API key from the local file (never committed to git)
    private static String readKey() {
        try {
            return Files.readString(Path.of(KEY_FILE)).trim();
        } catch (Exception e) {
            System.out.println("Could not read " + KEY_FILE);
            return null;
        }
    }

    // sends a web request, returns the reply text, or null if it failed
    private static String httpGet(String url) {
        try {
            HttpRequest request = HttpRequest.newBuilder(URI.create(url)).GET().build();
            HttpResponse<String> response = HttpClient.newHttpClient()
                    .send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() != 200) {
                System.out.println("Map service error " + response.statusCode());
                return null;
            }
            return response.body();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // turns an address into {longitude, latitude}, or null if not found
    public static double[] geocode(String address) {
        String key = readKey();
        if (key == null) {
            return null;
        }
        String url = GEOCODE_URL + "?api_key=" + key + "&size=1&text="
                + URLEncoder.encode(address, StandardCharsets.UTF_8);
        String body = httpGet(url);
        if (body == null) {
            return null;
        }
        try {
            JSONObject root = (JSONObject) new JSONParser().parse(body);
            JSONArray features = (JSONArray) root.get("features");
            if (features == null || features.isEmpty()) {
                return null;
            }
            JSONObject geometry = (JSONObject) ((JSONObject) features.get(0)).get("geometry");
            JSONArray coords = (JSONArray) geometry.get("coordinates");
            return new double[] { ((Number) coords.get(0)).doubleValue(),
                                  ((Number) coords.get(1)).doubleValue() };
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // asks for a driving route between two addresses, returns its "properties" object
    private static JSONObject getRoute(String fromAddress, String toAddress) {
        double[] from = geocode(fromAddress);
        double[] to = geocode(toAddress);
        String key = readKey();
        if (from == null || to == null || key == null) {
            return null;
        }
        String url = ROUTE_URL + "?api_key=" + key
                + "&start=" + from[0] + "," + from[1]
                + "&end=" + to[0] + "," + to[1];
        String body = httpGet(url);
        if (body == null) {
            return null;
        }
        try {
            JSONObject root = (JSONObject) new JSONParser().parse(body);
            JSONArray features = (JSONArray) root.get("features");
            return (JSONObject) ((JSONObject) features.get(0)).get("properties");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // driving distance in miles, or -1 if it could not be found
    public static double getDrivingDistance(String fromAddress, String toAddress) {
        JSONObject route = getRoute(fromAddress, toAddress);
        if (route == null) {
            return -1;
        }
        JSONObject summary = (JSONObject) route.get("summary");
        double meters = ((Number) summary.get("distance")).doubleValue();
        return meters / 1609.344;
    }

    // turn-by-turn directions as a list of lines (empty list if it failed)
    public static ArrayList<String> getDirections(String fromAddress, String toAddress) {
        ArrayList<String> steps = new ArrayList<>();
        JSONObject route = getRoute(fromAddress, toAddress);
        if (route == null) {
            return steps;
        }
        JSONArray segments = (JSONArray) route.get("segments");
        JSONArray jsonSteps = (JSONArray) ((JSONObject) segments.get(0)).get("steps");
        for (Object o : jsonSteps) {
            JSONObject step = (JSONObject) o;
            double miles = ((Number) step.get("distance")).doubleValue() / 1609.344;
            steps.add(step.get("instruction") + String.format(" (%.1f mi)", miles));
        }
        return steps;
    }
}