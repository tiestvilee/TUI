package org.tiestvilee.tui.manager;

import org.mozilla.javascript.Scriptable;
import org.tiestvilee.tui.view.ViewBuffer;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ImageLoader {

	private static final Pattern MATCH_DIGITS_AT_FRONT_OF_FILE = Pattern.compile("(\\d+).*js");

	private FileWrapper fileWrapper;

	public ImageLoader(FileWrapper fileWrapper) {

		this.fileWrapper = fileWrapper;
	}


	public JavascriptRunner loadImage(String imageName, Map<String, Object> integrationObjects) throws Exception {
		JavascriptRunner runner = new JavascriptRunner();
		runner.init();

		deserializeImage(runner, imageName);

		int scriptNumber = getMostRecentScriptNumber(runner);

		addJavaIntegrationObjects(integrationObjects, runner);

		applyNewScripts(runner, scriptNumber);

		return runner;
	}

	private void addJavaIntegrationObjects(Map<String, Object> integrationObjects,JavascriptRunner runner) {
        for (Map.Entry<String, Object> entry : integrationObjects.entrySet()) {
            runner.addObject$As$(entry.getValue(), entry.getKey());
        }
	}

	private int getMostRecentScriptNumber(JavascriptRunner runner) {
		return runner.evaluateInteger("image.scriptNumber");
	}

	private void deserializeImage(JavascriptRunner runner, String imageName) {
		InputStream stream = fileWrapper.inputStreamFor(imageName);
		runner.deserialize$toRootWithName$andDestroyCurrentScope(stream, "image");
	}

	private void applyNewScripts(JavascriptRunner runner, int deployNumber) {

		System.out.println("loading files after " + deployNumber);

		for(File file : fileWrapper.getFilesInDirectory("scripts")) {
			// alphabetical order.
			Matcher matcher = MATCH_DIGITS_AT_FRONT_OF_FILE.matcher(file.getName());
			if(matcher.matches()) {
				int scriptNumber = Integer.parseInt(matcher.group(1));
				if(scriptNumber > deployNumber) {
					String contents = fileWrapper.readFile(file);
					System.out.println(String.format("loading file %s, contents are:\n%s", file.getName(), contents));
					runner.evaluate(contents);
				}
			}
		}
	}

    public void saveImage(JavascriptRunner runner, String imageName) {
		OutputStream stream = fileWrapper.outputStreamFor(imageName);
        runner.serialize$to$andCloseStream((Scriptable) runner.evaluate("image = image;"), stream);
    }
}
