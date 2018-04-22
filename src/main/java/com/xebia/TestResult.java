package com.xebia;

import java.time.Instant;

public class TestResult implements Comparable<TestResult>{

    private final Boolean success;
    private final String name;
    private final Instant testInstant;

    public TestResult(String name, Boolean success) {
        this(name, success, Instant.now());

    }

    public TestResult(String name, Boolean success, Instant testInstant) {
        this.success = success;
        this.name = name;
        this.testInstant = testInstant;
    }


    public Boolean getSuccess() {
        return success;
    }

    public String getName() {
        return name;
    }

    @Override
    public int compareTo(TestResult testResult) {
        return name.compareTo(testResult.name);
    }

    public Instant getTestInstant() {
        return testInstant;
    }
}
