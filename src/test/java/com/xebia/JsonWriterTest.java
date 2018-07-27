package com.xebia;

import com.xebia.api.Result;
import com.xebia.internal.parser.TestResultImpl;
import com.xebia.internal.rest.TemplateWriter;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import org.apache.maven.plugin.logging.SystemStreamLog;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("The jsonwriter")
class JsonWriterTest {

    private Path path = Paths.get(this.getClass().getResource("/simpleJson.ftl").toURI());

    JsonWriterTest() throws URISyntaxException {
    }

    @Test
    @DisplayName("should write successful testresults")
    void writeSuccessTest() {
        TestResultImpl tr1 = new TestResultImpl("Success", Result.SUCCESS, Instant.now());

        List<TestResultImpl> c = new ArrayList<>();
        c.add(tr1);

        TemplateWriter jw = new TemplateWriter(path, SystemStreamLog::new);
        String result = jw.write(c);
        assertThat(result).contains("\"success\": \"SUCCESS\",");

    }

    @Test
    @DisplayName("should write failed testresult")
    void writeFailTest() {
        TestResultImpl tr2 = new TestResultImpl("Fail", Result.FAILURE, Instant.now());

        List<TestResultImpl> c = new ArrayList<>();
        c.add(tr2);

        TemplateWriter jw = new TemplateWriter(path, SystemStreamLog::new);
        String result = jw.write(c);
        assertThat(result).contains("\"success\": \"FAILURE\",");

        System.out.println(result);
    }
}