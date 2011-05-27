package org.tiestvilee.tui.manager;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;
import org.mozilla.javascript.serialize.ScriptableInputStream;
import org.mozilla.javascript.serialize.ScriptableOutputStream;

import java.io.*;

public class JavascriptRunner {

	// private Context context;
	private Scriptable scope;


	public void init() {
		try {
			Context context = enterContext();
			scope = context.initStandardObjects();
		} finally {
			Context.exit();
		}
	}

	private Context enterContext() {
		Context context = Context.enter();
		context.setOptimizationLevel(-1);
		return context;
	}

	public Object evaluate(String expression) {
		try {
			return enterContext().evaluateString(scope, expression, "evaluation", 0, null);
		} finally {
			Context.exit();
		}
	}

	public void addObject$As$(Object object, String name) {
		try {
			Object wrappedObject = enterContext().javaToJS(object, scope);
			ScriptableObject.putProperty(scope, name, wrappedObject);
		} finally {
			Context.exit();
		}
	}

	public void serialize$to$andCloseStream(Scriptable root, OutputStream outputStream) {
		try {
			enterContext();
			ScriptableOutputStream stream = new ScriptableOutputStream(outputStream, scope);
			stream.writeObject(root);
			stream.close();
		} catch (IOException e) {
			throw new RuntimeException("problem serializing object", e);
		} finally {
			Context.exit();
		}
	}

	public void deserialize$toRootWithName$andDestroyCurrentScope(InputStream inputStream, String objectName) {
		try {
			// enterContext();

			Context context = enterContext();
			scope = context.initStandardObjects(); // is this a bug?

			ScriptableInputStream stream = new ScriptableInputStream(inputStream, scope);
			Scriptable deserialized = (Scriptable) stream.readObject();
			stream.close();

			ScriptableObject.putProperty(scope, objectName, deserialized);
		} catch (Exception e) {
			throw new RuntimeException("problem deserializing object", e);
		} finally {
			Context.exit();
		}
	}

	public int evaluateInteger(String expression) {
		Object result = evaluate(expression);
		if(result instanceof Integer) {
			return ((Integer) result).intValue();
		} else if (result instanceof Double) {
			return ((Double) result).intValue();
		} else if (result instanceof String) {
			return Integer.parseInt((String) result);
		}
		throw new RuntimeException(String.format("couldn't convert %s into integer", result));
	}
}
