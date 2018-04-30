package com.xebia;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class RestPublisher implements Publisher {
  private CloseableHttpClient httpclient = HttpClients.createDefault();
  private final JsonWriter jsonWriter;
  private final URI baseUrl;

  public RestPublisher(JsonWriter jsonWriter, URI baseUrl) {
    this.jsonWriter = jsonWriter;
    this.baseUrl = baseUrl;
  }

  @Override
  public void publish(List<TestResult> testResults) {
    HttpPost httpPost = new HttpPost(baseUrl);
    StringEntity entity;
    try {
      entity = new StringEntity(jsonWriter.write(testResults));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    httpPost.setEntity(entity);
    try (CloseableHttpResponse response2 = httpclient.execute(httpPost)) {

      HttpEntity entity2 = response2.getEntity();
      EntityUtils.consume(entity2);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
