package org.tiestvilee.tui.manager;

import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertThat;
import static org.hamcrest.core.Is.is;

public class JavascriptRunnerTest {

	@Test
	public void shouldStartANewContext() {
		JavascriptRunner runner = new JavascriptRunner();

		runner.init();

		Object result = runner.evaluate("1 + 1");

		assertThat((Integer) result, is(2));
	}

	@Test
	public void shouldCreateJson() {
		JavascriptRunner runner = new JavascriptRunner();

		runner.init();

		Object result = runner.evaluate("JSON.stringify(1)");

		assertThat((String) result, is("1"));
	}

	@Test
	public void shouldAddObjectsToScope() {
		JavascriptRunner runner = new JavascriptRunner();

		runner.init();

		runner.addObject$As$("a string", "addedObject");


		Object result = runner.evaluate("addedObject.wrapped");
		assertThat((String) result, is("a string"));
	}

	@Test
	public void whatHappensWhenYouJsonAJavaObject() {
		JavascriptRunner runner = new JavascriptRunner();

		runner.init();
		runner.addObject$As$(new File("hello"), "addedObject");

		Object result = runner.evaluate("JSON.stringify({obj:{file:addedObject}, arr : [1,2]})");

		assertThat((String) result, is("{\"obj\":{\"file\":\"addedObject\"},\"arr\":[1,2]}"));
	}
}
