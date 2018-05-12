package com.xebia;

import com.xebia.internal.parser.TestResultImpl;
import java.util.List;

public interface Publisher {
  void publish(List<TestResultImpl> testResults);
}
