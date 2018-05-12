package com.xebia.api;

import com.xebia.internal.MojoExecuter;
import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.Objects;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

@Mojo(name = "log")
public class TestLogMojo extends AbstractMojo {
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

    @Parameter(defaultValue = "REST")
    private String mode;

    @Parameter(defaultValue = "true")
    private boolean continueOnFailure;

    public TestLogMojo() {
    }

    @Override
    public void execute() {

        Objects.requireNonNull(mode);
        Objects.requireNonNull(endpoint, "Please supply an url");
        MojoExecuter mojoExecuter = new MojoExecuter(template.toPath(), this::getLog, createUri(endpoint),
            time.toInstant(), testDirectory.toPath(), continueOnFailure);

        mojoExecuter.execute();
    }

    private URI createUri(String endpoint) {
        try {
            return new URI(endpoint);
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException(endpoint + " is not a valid url", e);
        }
    }
}