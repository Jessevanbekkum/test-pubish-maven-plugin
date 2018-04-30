package com.xebia;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

@Mojo(name = "log")
public class MyMojo extends AbstractMojo {
  /**
   *
   */
  @Parameter(defaultValue = "${project.build.directory}")
  private File testDirectory;

  @Parameter
  private File template;

  @Parameter(property = "endpoint")
  private String endpoint;

  @Parameter(defaultValue = "${session.request.startTime}", readonly = true)
  private Date time;


  public MyMojo() {
  }

  public void execute() {

    Objects.requireNonNull(endpoint, "Please supply an url");

    JsonWriter jsonWriter = new JsonWriter(template.toPath(), this::getLog);

    RestPublisher restPublisher = new RestPublisher(jsonWriter, createUri(endpoint));

    TestResultParser testResultParser = new TestResultParser(testDirectory, this::getLog, time.toInstant());

    List<TestResult> results = testResultParser.parseAll();
    restPublisher.publish(results);
  }

  private URI createUri(String endpoint) {
    try {
      return new URI(endpoint);
    } catch (URISyntaxException e) {
      throw new IllegalArgumentException(endpoint + " is not a valid url", e);
    }
  }
}