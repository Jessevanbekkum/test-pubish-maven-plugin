package com.xebia.internal.parser;

import com.xebia.api.Result;
import com.xebia.api.TestResult;
import java.time.Instant;
import java.util.Comparator;
import java.util.UUID;

public class TestResultImpl implements TestResult, Comparable<TestResultImpl> {
    private static Comparator<TestResultImpl> testResultComparator =
        Comparator.comparing(TestResultImpl::getTestInstant)
            .thenComparing(TestResultImpl::getName);

    private final UUID id;
    private final Result success;
    private final String name;
    private final Instant testInstant;

    /**
     * Construct a new testresult
     *
     * @param name        name of the test. Expected to be unique in the set
     * @param success     result of the test
     * @param testInstant timestamp of the test
     */
    public TestResultImpl(String name, Result success, Instant testInstant) {
        this.success = success;
        this.name = name;
        this.testInstant = testInstant;
        id = UUID.randomUUID();
    }

    @Override
    public Result getSuccess() {
        return success;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int compareTo(TestResultImpl testResult) {
        return testResultComparator
            .compare(this, testResult);
    }

    @Override
    public Instant getTestInstant() {
        return testInstant;
    }

    @Override
    public UUID getId() {
        return id;
    }
}
