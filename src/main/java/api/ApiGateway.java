package api;

import spark.Spark;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

import static spark.Spark.*;

public class ApiGateway {

    private static final String USER_AGENT = "Mozilla/5.0";
    private static final String SERVER_URL = "http://localhost:8080";
    private static final String POST_AUTH_URL = SERVER_URL + "/auth/user";
    private static final String POST_CREATE_USER_URL = SERVER_URL + "/users/user";
    private static final String POST_CREATE_POST_URL = SERVER_URL + "/users/post";
    private static final String POST_CREATE_REPLY_URL = SERVER_URL + "/users/reply";
    private static final String GET_ALL_POSTS_URL = SERVER_URL + "/users/posts";
    private static final String GET_POSTS_BY_AUTHOR_URL = SERVER_URL + "/users/postsByAuthor";


    public static void main(String[] args) {
        port(getPort());
        staticFiles.location("/public");

        post("/auth", (req, res) -> {
            String postData = req.body();
            String token = sendPostRequest(POST_AUTH_URL, postData);
            return token;
        });

        before("/users/*", (req, res) -> {
            String jwtToken = req.headers("Authorization");
            if (jwtToken == null || !jwtToken.startsWith("Bearer ")) {
                Spark.halt(401, "Missing or invalid authorization token");
            }
        });

        // Endpoint de creación de usuario
        post("/users/user", (req, res) -> {
            String postData = req.body();
            String response = sendPostRequest(POST_CREATE_USER_URL, postData);
            return response;
        });

        // Endpoint de creación de post
        post("/users/post", (req, res) -> {
            String postData = req.body();
            String response = sendPostRequest(POST_CREATE_POST_URL, postData);
            return response;
        });

        // Endpoint de creación de respuesta a post
        post("/users/reply", (req, res) -> {
            String postData = req.body();
            String response = sendPostRequest(POST_CREATE_REPLY_URL, postData);
            return response;
        });

        // Endpoint para obtener todos los posts
        get("/users/posts", (req, res) -> {
            String page = req.queryParams("page");
            String pageSize = req.queryParams("pageSize");
            String response = sendGetRequest(GET_ALL_POSTS_URL + "?page=" + page + "&pageSize=" + pageSize);
            return response;
        });

        // Endpoint para obtener posts por autor
        get("/users/postsByAuthor", (req, res) -> {
            String authorID = req.queryParams("authorID");
            String page = req.queryParams("page");
            String pageSize = req.queryParams("pageSize");
            String response = sendGetRequest(GET_POSTS_BY_AUTHOR_URL + "?authorID=" + authorID + "&page=" + page + "&pageSize=" + pageSize);
            return response;
        });


    }


    private static int getPort() {
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 4573;
    }

    public static String sendPostRequest(String urlString, String postData) {
        StringBuilder response = new StringBuilder();

        try {
            URL url = new URL(urlString);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("User-Agent", USER_AGENT);
            con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            con.setDoOutput(true);

            try (OutputStream os = con.getOutputStream();
                 BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"))) {
                writer.write(postData);
                writer.flush();
            }

            int responseCode = con.getResponseCode();
            System.out.println("POST Response Code :: " + responseCode);

            if (responseCode == HttpURLConnection.HTTP_OK) {
                try (BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
                    String inputLine;
                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }
                }
            } else {
                System.out.println("POST request not worked");
            }
        } catch (IOException e) {
            System.out.println("Error sending POST request: " + e.getMessage());
        }

        System.out.println("POST DONE");
        return response.toString();
    }

    public static String sendGetRequest(String urlString) {
        StringBuilder response = new StringBuilder();

        try {
            URL url = new URL(urlString);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("User-Agent", USER_AGENT);

            int responseCode = con.getResponseCode();
            System.out.println("GET Response Code :: " + responseCode);

            if (responseCode == HttpURLConnection.HTTP_OK) {
                try (BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
                    String inputLine;
                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }
                }
            } else {
                System.out.println("GET request not worked");
            }
        } catch (IOException e) {
            System.out.println("Error sending GET request: " + e.getMessage());
        }

        System.out.println("GET DONE");
        return response.toString();
    }
}
