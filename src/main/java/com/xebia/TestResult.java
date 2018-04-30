package com.xebia;

import java.time.Instant;
import java.util.Comparator;
import java.util.UUID;

public class TestResult implements Comparable<TestResult> {
  private static Comparator<TestResult> testResultComparator =
    Comparator.comparing(TestResult::getTestInstant)
      .thenComparing(TestResult::getName);

  private final UUID id;
  private final Boolean success;
  private final String name;
  private final Instant testInstant;

  /**
   * Construct a new testresult
   *
   * @param name        name of the test. Expected to be unique in the set
   * @param success     result of the test
   * @param testInstant timestamp of the test
   */
  public TestResult(String name, Boolean success, Instant testInstant) {
    this.success = success;
    this.name = name;
    this.testInstant = testInstant;
    id = UUID.randomUUID();
  }

  public Boolean getSuccess() {
    return success;
  }

  public String getName() {
    return name;
  }

  @Override
  public int compareTo(TestResult testResult) {
    return testResultComparator
      .compare(this, testResult);
  }

  public Instant getTestInstant() {
    return testInstant;
  }

  public UUID getId() {
    return id;
  }
}
