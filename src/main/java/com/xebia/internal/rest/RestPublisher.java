package com.xebia.internal.rest;

import com.xebia.internal.Publisher;
import com.xebia.internal.parser.TestResultImpl;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Path;
import java.util.List;
import java.util.function.Supplier;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.maven.plugin.logging.Log;

public class RestPublisher implements Publisher {
    private CloseableHttpClient httpclient = HttpClients.createDefault();
    private final TemplateWriter templateWriter;
    private final URI baseUrl;
    private final boolean continueOnFailure;
    private final Supplier<Log> log;

    /**
     *
     * @param templateFile
     * @param baseUrl
     * @param continueOnFailure
     * @param log
     */
    public RestPublisher(Path templateFile, URI baseUrl, boolean continueOnFailure, Supplier<Log> log) {
        this.templateWriter = new TemplateWriter(templateFile, log);
        this.baseUrl = baseUrl;
        this.continueOnFailure = continueOnFailure;
        this.log = log;
    }

    @Override
    public void publish(List<TestResultImpl> testResults) {
        log.get().debug("About to post " + testResults.size() + " testresults");
        HttpPost httpPost = new HttpPost(baseUrl);
        StringEntity entity;
        try {
            entity = new StringEntity(templateWriter.write(testResults));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        httpPost.setEntity(entity);
        post(httpPost);
    }

    private void post(HttpPost httpPost) {
        try (CloseableHttpResponse response2 = httpclient.execute(httpPost)) {

            HttpEntity entity2 = response2.getEntity();
            EntityUtils.consume(entity2);
        } catch (IOException e) {
            if (continueOnFailure) {
                log.get().warn("Could not publish logging results:");
                log.get().warn(e);
            } else {
                throw new RuntimeException(e);
            }
        }
    }
}
