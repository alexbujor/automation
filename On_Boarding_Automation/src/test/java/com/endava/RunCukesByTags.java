package com.endava;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(value = Cucumber.class)
@CucumberOptions(tags = {"~@ignore"} ,plugin = {"pretty",  "json:target/json/cucumber.json"}, features="src/test/resources")
public class RunCukesByTags {
}