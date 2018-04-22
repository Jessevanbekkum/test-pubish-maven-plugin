package com.xebia;

import freemarker.template.TemplateException;
import org.apache.commons.io.filefilter.*;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.*;


@Mojo(name = "log")
public class MyMojo extends AbstractMojo {
    /**
     *
     */
    @Parameter(defaultValue = "${project.build.directory}")
    private File testDirectory;

    JsonWriter jsonWriter = new JsonWriter();

    public MyMojo() throws IOException, URISyntaxException {
    }

    public void execute() throws MojoExecutionException {
        File file = new File(testDirectory, "failsafe-reports");

        if (!file.exists()) {
            getLog().error("Could not find " + file.getAbsolutePath());
            return;
        }

        if (!file.isDirectory()) {
            getLog().error("Not a directory");
            return;
        }

        if (file.listFiles() == null) {
            getLog().error("Not a list?");
            return;
        }
        IOFileFilter filenameFilter = new SuffixFileFilter("xml");
        IOFileFilter summaryFilter = new NotFileFilter(new WildcardFileFilter("failsafe-summary.xml"));
        FilenameFilter and = new AndFileFilter(filenameFilter, summaryFilter);
        Map<String, TestResult> results = new TreeMap<>();

        for (File f : file.listFiles(and)) {
            for (TestResult tr : parse(f)){
                results.put(tr.getName(), tr);
            }
        }

        results.forEach((name, tr) -> getLog().info(name + " - " + tr.getSuccess()));


        try {
            List<TestResult> l = new ArrayList<>(results.values());



            jsonWriter.write(l);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        }
    }

    private Set<TestResult> parse(File file) {
        Set<TestResult> results = new HashSet<>();

        try (FileInputStream adrFile = new FileInputStream(file)) {
            JAXBContext ctx = JAXBContext.newInstance(TestSuite.class);
            Unmarshaller um = ctx.createUnmarshaller();
            TestSuite rootElement = (TestSuite) um.unmarshal(adrFile);
            for (TestCase testCase : rootElement.getTestCases()) {
                getLog().info(testCase.toString());
                TestResult tr = new TestResult(testCase.getClassname() + "." + testCase.getName(), testCase.getFailure() != null);
                results.add(tr);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return results;
    }


}