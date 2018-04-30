# test-pubish-maven-plugin

This is a maven module to publish test results to an external service. Currently supported are REST

## Usage:

Parameters:
* endpoint - The endpoint that can receive the test data
* template - The template of the json/xml documents

## Templating
The rest bodies are constructed using freemarker templates. This allows to configure the body exactly to the users
needs.

## Licence
This project is released under the Apache 2.0 license