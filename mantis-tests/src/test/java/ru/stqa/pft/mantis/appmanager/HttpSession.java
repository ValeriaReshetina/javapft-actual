package ru.stqa.pft.mantis.appmanager;

import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.LaxRedirectStrategy;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HttpSession {
    private CloseableHttpClient httpClient;
    private ApplicationManager app;

    public HttpSession(ApplicationManager app) {
        this.app = app;
        httpClient = HttpClients.custom().setRedirectStrategy(new LaxRedirectStrategy()).build();
    }

    public boolean login(String username, String password) throws IOException {
//        HttpPost post = new HttpPost(app.getProperty("web.baseUrl") + "/login.php");
//        List<NameValuePair> params = new ArrayList<>();
//        params.add(new BasicNameValuePair("username", username));
//        params.add(new BasicNameValuePair("username", username));
//        params.add(new BasicNameValuePair("username", username));
//        params.add(new BasicNameValuePair("username", username));
        return false;
    }
}
