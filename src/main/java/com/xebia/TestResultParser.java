package com.xebia;

import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;
import java.util.function.Supplier;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import org.apache.commons.io.filefilter.AndFileFilter;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.filefilter.NotFileFilter;
import org.apache.commons.io.filefilter.SuffixFileFilter;
import org.apache.commons.io.filefilter.WildcardFileFilter;
import org.apache.maven.plugin.logging.Log;

public class TestResultParser {

  private final File testDirectory;
  private final Supplier<Log> log;
  private final Instant startInstant;
  private static final IOFileFilter XML_FILTER = new SuffixFileFilter("xml");

  /**
   * @param testDirectory Directory where to find the failsafe test reports
   * @param log           Supplier for the logger. Documentation discourages storing log in an instant variable
   * @param startInstant
   */
  public TestResultParser(File testDirectory, Supplier<Log> log, Instant startInstant) {
    this.testDirectory = Objects.requireNonNull(testDirectory);
    this.log = Objects.requireNonNull(log);
    this.startInstant = Objects.requireNonNull(startInstant);
  }

  List<TestResult> parseAll() {
    File file = new File(testDirectory, "failsafe-reports");

    if (!file.exists()) {
      log.get().error("Could not find " + file.getAbsolutePath());
      return Collections.emptyList();
    }

    if (!file.isDirectory()) {
      log.get().error("Not a directory");
      return Collections.emptyList();
    }

    if (file.listFiles() == null) {
      log.get().error("Not a list?");
      return Collections.emptyList();
    }
    IOFileFilter summaryFilter = new NotFileFilter(new WildcardFileFilter("failsafe-summary.xml"));
    FilenameFilter and = new AndFileFilter(XML_FILTER, summaryFilter);
    List<TestResult> results = new ArrayList<>();

    for (File f : file.listFiles(and)) {
      results.addAll(parse(f));
    }

    return results;
  }

  private Set<TestResult> parse(File file) {
    Set<TestResult> results = new HashSet<>();

    try (FileInputStream adrFile = new FileInputStream(file)) {
      JAXBContext ctx = JAXBContext.newInstance(TestSuite.class);
      Unmarshaller um = ctx.createUnmarshaller();
      TestSuite rootElement = (TestSuite) um.unmarshal(adrFile);
      for (TestCase testCase : rootElement.getTestCases()) {
        log.get().info(testCase.toString());
        String name = testCase.getClassname() + "." + testCase.getName();
        boolean success = testCase.getFailure() != null;
        TestResult tr = new TestResult(name, success, startInstant);
        results.add(tr);
      }

    } catch (Exception e) {
      throw new RuntimeException(e);
    }

    return results;
  }
}
