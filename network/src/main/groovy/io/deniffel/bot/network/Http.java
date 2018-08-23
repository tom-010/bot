package io.deniffel.bot.network;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.stream.Collectors;

public class Http {

    public static String post(String url, Object data) throws Exception {
        HttpURLConnection conn = buildConnection(new URL(url));

        writeOutput(data, conn);
        String result = readResponse(conn);

        conn.disconnect();
        return result;
    }

    private static void writeOutput(Object data, HttpURLConnection conn) throws IOException {
        OutputStream os = conn.getOutputStream();
        os.write(serializeContent(data).getBytes("UTF-8"));
        os.close();
    }

    private static String readResponse(HttpURLConnection conn) throws IOException {
        // read the response
        InputStream in = new BufferedInputStream(conn.getInputStream());
        String result = new BufferedReader(new InputStreamReader(in)) .lines().collect(Collectors.joining("\n"));
        in.close();
        return result;
    }

    private static String serializeContent(Object data) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper(new JsonFactory());
        String content = mapper.writeValueAsString(data);
        System.out.println(content);
        return content;
    }

    private static HttpURLConnection buildConnection(URL urlObj) throws IOException {
        HttpURLConnection conn = (HttpURLConnection) urlObj.openConnection();
        conn.setConnectTimeout(5000);
        conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
        conn.setDoOutput(true);
        conn.setDoInput(true);
        conn.setRequestMethod("POST");
        return conn;
    }

}
