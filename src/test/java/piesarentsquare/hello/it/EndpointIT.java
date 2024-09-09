package piesarentsquare.hello.it;

import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.InputStreamReader;


public class EndpointIT {
    private static String siteUrl;

    @BeforeAll
    public static void init() {
        String port = System.getProperty("http.port");
        String war = System.getProperty("war.name");
        siteUrl = "http://localhost:" + port + "/" + war + "/servlet";
    }

    @Test
    public void testServlet() throws Exception {
        CloseableHttpClient client = HttpClientBuilder.create().build();
        HttpGet httpGet = new HttpGet(siteUrl);
        CloseableHttpResponse response = null;

        try {

            response = client.execute(httpGet);

            int statusCode = response.getStatusLine().getStatusCode();
            Assertions.assertEquals(HttpStatus.SC_OK, statusCode, "HTTP GET failed");

            BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            String line;
            StringBuffer buffer = new StringBuffer();
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            reader.close();
            Assertions.assertTrue(buffer.toString().contains("Hello, how are you?"),
                    "Unexpected response body: " + buffer.toString());

        } finally {
            response.close();
            httpGet.releaseConnection();
        }
    }
}
