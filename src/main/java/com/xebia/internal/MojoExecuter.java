package com.xebia.internal;

import com.xebia.internal.parser.TestResultImpl;
import com.xebia.internal.parser.TestResultParser;
import com.xebia.internal.rest.JsonWriter;
import com.xebia.internal.rest.RestPublisher;
import java.net.URI;
import java.nio.file.Path;
import java.time.Instant;
import java.util.List;
import java.util.function.Supplier;
import org.apache.maven.plugin.logging.Log;

public class MojoExecuter {

    private final Path template;
    private final Supplier<Log> log;
    private final URI endpoint;
    private final Instant time;
    private final Path testDirectory;
    private final boolean continueOnFailure;

    /**
     *  @param template
     * @param log
     * @param endpoint
     * @param time
     * @param testDirectory
     * @param continueOnFailure
     */
    public MojoExecuter(Path template, Supplier<Log> log, URI endpoint, Instant time, Path testDirectory, boolean continueOnFailure) {
        this.template = template;
        this.log = log;
        this.endpoint = endpoint;
        this.time = time;
        this.testDirectory = testDirectory;
        this.continueOnFailure = continueOnFailure;
    }

    /**
     *
     */
    public void execute() {
        JsonWriter jsonWriter = new JsonWriter(template, log);

        RestPublisher restPublisher = new RestPublisher(jsonWriter, endpoint, continueOnFailure, log);

        TestResultParser testResultParser = new TestResultParser(testDirectory, log, time);

        List<TestResultImpl> results = testResultParser.parseAll();
        restPublisher.publish(results);
    }
}
