package com.xebia;

import org.apache.maven.plugin.logging.SystemStreamLog;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("The jsonwriter")
class JsonWriterTest {

  private Path path = Paths.get(this.getClass().getResource("/simpleJson.ftl").toURI());

  JsonWriterTest() throws URISyntaxException {
  }

  @Test
  @DisplayName("should write successful testresults")
  void writeSuccessTest() {
    TestResult tr1 = new TestResult("Success", true, Instant.now());

    List<TestResult> c = new ArrayList<>();
    c.add(tr1);

    JsonWriter jw = new JsonWriter(path, new SystemStreamLog());
    String result = jw.write(c);
    assertThat(result).contains("\"success\": true,");

  }

  @Test
  @DisplayName("should write failed testresult")
  void writeFailTest() {
    TestResult tr2 = new TestResult("Fail", false, Instant.now());

    List<TestResult> c = new ArrayList<>();
    c.add(tr2);

    JsonWriter jw = new JsonWriter(path, new SystemStreamLog());
    String result = jw.write(c);
    assertThat(result).contains("\"success\": false,");

    System.out.println(result);
  }
}