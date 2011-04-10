package org.tiestvilee.tui.manager;

import org.junit.Test;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.NativeJavaObject;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;
import org.mozilla.javascript.serialize.ScriptableInputStream;
import org.mozilla.javascript.serialize.ScriptableOutputStream;

import java.io.*;

import static org.junit.Assert.assertThat;
import static org.hamcrest.core.Is.is;

public class JavascriptRunnerTest {

	@Test
	public void shouldStartANewContext() {
		JavascriptRunner runner = new JavascriptRunner();

		runner.init();

		Object result = runner.evaluate("1 + 1");

		assertThat((Double) result, is(2.0));
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


		Object result = runner.evaluate("addedObject");
		assertThat((String) result, is("a string"));
	}

	@Test
	public void shouldSerializeAndDeserializeAnObjectToAStream() throws Exception {
		JavascriptRunner outputRunner = new JavascriptRunner();

		outputRunner.init();
		outputRunner.addObject$As$(new File("hello"), "addedObject");
//		outputRunner.addObject$As$("hello", "addedObject");
		outputRunner.evaluate("var something = {contents:'something'};");
		Scriptable result = (Scriptable) outputRunner.evaluate("result = {obj:{file:addedObject, ha : something}, arr : [1,2], ha2 : something, notha : {contents:'something'} };");

		ByteArrayOutputStream resultStream = new ByteArrayOutputStream();
		outputRunner.serialize$to$andCloseStream(result, resultStream);

		resultStream.close();

		byte[] bytes = resultStream.toByteArray();
		System.out.println("serialized contents is : " + bytes.length);

		JavascriptRunner inputRunner = new JavascriptRunner();

		ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
		inputRunner.deserialize$to$andDestroyCurrentScope(inputStream, "result");
		inputStream.close();

		assertThat((String) inputRunner.evaluate("hey = result.obj.ha.contents"), is("something"));
		assertThat((Double) inputRunner.evaluate("hey = result.arr[0]"), is(1.0));
		assertThat((Boolean) inputRunner.evaluate("hey = (result.obj.ha === result.ha2);"), is(true));
		assertThat((Boolean) inputRunner.evaluate("hey = (result.obj.ha !== result.notha);"), is(true));

		System.out.println(((NativeJavaObject) inputRunner.evaluate("result.obj.file")).unwrap());
	}

	@Test
	public void createEmptyImage() throws Exception {
		JavascriptRunner outputRunner = new JavascriptRunner();

		outputRunner.init();
		Scriptable result = (Scriptable) outputRunner.evaluate("image = {scriptNumber : 0};");

		FileOutputStream resultStream = new FileOutputStream(new File("blank.image"));
		outputRunner.serialize$to$andCloseStream(result, resultStream);

		JavascriptRunner inputRunner = new JavascriptRunner();

		FileInputStream input = new FileInputStream(new File("blank.image"));
		inputRunner.deserialize$to$andDestroyCurrentScope(input, "image");
		input.close();

		assertThat((Double) inputRunner.evaluate("hey = image.scriptNumber;"), is(0.0));

	}


	@Test
	public void serializationExample() throws Exception {
		Context context = Context.enter();
		Scriptable scope = context.initStandardObjects();

		Scriptable obj = (Scriptable) context.evaluateString(scope, "image = {scriptNumber : 0};", "ha", 0, null);

		FileOutputStream fos = new FileOutputStream("blank.image");
		ScriptableOutputStream out = new ScriptableOutputStream(fos, scope);
		out.writeObject(obj);
		out.close();

		context.exit();

		context = Context.enter();
		scope = context.initStandardObjects();

		FileInputStream fis = new FileInputStream("blank.image");
		ScriptableInputStream in = new ScriptableInputStream(fis, scope);
		Scriptable deserialized = (Scriptable) in.readObject();
		in.close();

		ScriptableObject.putProperty(scope, "image", deserialized);
		System.out.println(context.evaluateString(scope, "result = image.scriptNumber;", "", 0, null));

		context.exit();
	}
}
