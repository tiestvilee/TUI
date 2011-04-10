package org.tiestvilee.tui.manager;

import org.tiestvilee.tui.primitives.CharacterMap;
import org.tiestvilee.tui.view.ViewBuffer;

import java.io.File;
import java.io.InputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ImageLoader {

	private static final Pattern MATCH_DIGITS_AT_FRONT_OF_FILE = Pattern.compile("(\\d+).*js");

	private FileWrapper fileWrapper;

	public ImageLoader(FileWrapper fileWrapper) {

		this.fileWrapper = fileWrapper;
	}


	public JavascriptRunner loadImage(String imageName, ViewBuffer view, CharacterMap characterMap) throws Exception {
		JavascriptRunner runner = new JavascriptRunner();
		runner.init();

		deserializeImage(runner, imageName);

		int scriptNumber = getMostRecentScriptNumber(runner);

		addViewAndCharacterMap(view, characterMap, runner);

		applyNewScripts(runner, scriptNumber);

		return runner;
	}

	private void addViewAndCharacterMap(ViewBuffer view, CharacterMap characterMap, JavascriptRunner runner) {
		runner.addObject$As$(view, "view");
		runner.addObject$As$(characterMap, "characterMap");
	}

	private int getMostRecentScriptNumber(JavascriptRunner runner) {
		return runner.evaluateInteger("image.scriptNumber");
	}

	private void deserializeImage(JavascriptRunner runner, String imageName) {
		InputStream stream = fileWrapper.streamFor(imageName);
		runner.deserialize$to$andDestroyCurrentScope(stream, "image");
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
}
