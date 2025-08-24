package integration;

import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;

public class UrlAccessibilityTest {

    private List<String> loadUrlPatterns() throws Exception {
        List<String> patterns = new ArrayList<>();
        Document doc = DocumentBuilderFactory.newInstance()
                .newDocumentBuilder()
                .parse(Paths.get("src/main/webapp/WEB-INF/web.xml").toFile());
        NodeList nodes = doc.getElementsByTagName("url-pattern");
        for (int i = 0; i < nodes.getLength(); i++) {
            String pattern = nodes.item(i).getTextContent().trim();
            if (!pattern.contains("*")) {
                patterns.add(pattern);
            }
        }
        return patterns;
    }

    private boolean isServerAvailable(String baseUrl) {
        try {
            HttpURLConnection conn = (HttpURLConnection) new URL(baseUrl).openConnection();
            conn.setConnectTimeout(1000);
            conn.connect();
            int code = conn.getResponseCode();
            return code < 500;
        } catch (IOException ex) {
            return false;
        }
    }

    @Test
    public void testAllUrlsAccessible() throws Exception {
        String baseUrl = "http://localhost:8080/BasicWebAuthorization";
        Assumptions.assumeTrue(isServerAvailable(baseUrl), "Application server is not running");
        List<String> urls = loadUrlPatterns();
        WebDriver driver = new HtmlUnitDriver();
        for (String path : urls) {
            driver.get(baseUrl + path);
            assertFalse(driver.getPageSource().isEmpty(), "Empty response for " + path);
        }
        driver.quit();
    }
}
