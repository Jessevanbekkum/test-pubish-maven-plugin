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
     * Instant the complete suite was started. This is not the moment this particular test started
     * @return
     */
    Instant getTestInstant();

    UUID getId();
}
