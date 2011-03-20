package org.tiestvilee.tui.manager;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

public class JavascriptRunner {

	private static ThreadLocal threadLocalContext = new ThreadLocal() {
        protected synchronized Object initialValue() {
            return Context.enter();
        }
    };

	// private Context context;
	private Scriptable scope;
	

	public void init() {
		Context context = getContext();
		scope = context.initStandardObjects();

		Reader platformReader = new InputStreamReader(this.getClass().getClassLoader().getResourceAsStream("org/tiestvilee/tui/manager/platform.js"));
		try {
			context.evaluateReader(scope, platformReader, "platform.js", 0, null);
		} catch (IOException e) {
			throw new RuntimeException("error loading platform.js", e);
		}
	}

	private Context getContext() {
		return (Context) threadLocalContext.get();
	}

	public Object evaluate(String expression) {
		return getContext().evaluateString(scope, expression, "evaluation", 0, null);
	}

	public void addObject$As$(Object object, String name) {
		Object wrappedObject = getContext().javaToJS(object, scope);
		ScriptableObject.putProperty(scope, name + "_native", wrappedObject);
		evaluate(String.format("%s = {wrapped : %s_native, toJSON : function() { return '%s'; }}", name, name, name));
	}
}
