package com.xebia.api;

import java.time.Instant;
import java.util.UUID;

public interface TestResult {
    Result getSuccess();

    /**
     * Unique name for this test
     * @return
     */
    String getName();

    /**
     * Instant the complete suite was started. This is not the moment this particular test started, but when the whole
     * suite was started. This allows to correlate different results of one run.
     * @return
     */
    Instant getTestInstant();

    /**
     * A random UUID of this test result
     * @return
     */
    UUID getId();
}
