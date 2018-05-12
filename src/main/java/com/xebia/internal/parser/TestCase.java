package com.xebia.internal.parser;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "testcase")
public class TestCase {
  private String name;

  private String classname;

  private Failure failure;

  public String getName() {
    return name;
  }

  @XmlAttribute(name = "name")
  public void setName(String name) {
    this.name = name;
  }

  public String getClassname() {
    return classname;
  }

  @XmlAttribute(name = "classname")
  public void setClassname(String classname) {
    this.classname = classname;
  }

  public Failure getFailure() {
    return failure;
  }

  @XmlElement
  public void setFailure(Failure failure) {
    this.failure = failure;
  }

  @Override
  public String toString() {
    String result = failure == null ? "OK" : "FAIL";
    return classname + "." + name + " - " + result;
  }
}
