package com.xebia;

import java.util.List;

public interface Publisher {
  void publish(List<TestResult> testResults);
}
