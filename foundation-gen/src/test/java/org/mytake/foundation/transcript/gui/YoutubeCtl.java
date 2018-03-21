/*
 * MyTake.org
 *
 *  Copyright 2017 by its authors.
 *  Some rights reserved. See LICENSE, https://github.com/mytakedotorg/mytakedotorg/graphs/contributors
 */
package org.mytake.foundation.transcript.gui;

import com.diffplug.common.base.StringPrinter;
import com.diffplug.common.swt.ControlWrapper;
import com.diffplug.common.swt.Layouts;
import com.diffplug.common.swt.SwtMisc;
import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

public class YoutubeCtl extends ControlWrapper.AroundControl<Composite> {
	private final Browser browser;
	private final Button checkBox;
	private final Text secondsTxt;

	public YoutubeCtl(Composite parent) {
		super(new Composite(parent, SWT.NONE));
		Layouts.setGrid(wrapped).margin(0);

		browser = new Browser(wrapped, SWT.BORDER);
		Layouts.setGridData(browser).grabAll();

		Composite bottomCmp = new Composite(wrapped, SWT.NONE);
		Layouts.setGridData(bottomCmp).grabHorizontal();
		Layouts.setRow(bottomCmp).margin(0);

		checkBox = new Button(bottomCmp, SWT.CHECK);
		Labels.create(bottomCmp, "Play on click +/-");
		secondsTxt = new Text(bottomCmp, SWT.SINGLE | SWT.BORDER);
		secondsTxt.setText("0.0");
		Layouts.setRowData(secondsTxt).width(4 * SwtMisc.systemFontWidth());
		Labels.create(bottomCmp, "seconds");
	}

	public void setToYoutubeId(String youtubeId) {
		browser.setText(StringPrinter.buildStringFromLines(
				"<html><body>",
				"<script>",
				"var play = function(start, end) {",
				"    document.write('<p>play from ' + start + ' to ' + end + '</p>');",
				"}",
				"</script>",
				"</body></html>"));
	}

	/** Plays the given clip in the youtube player. */
	public void play(double start, double end) {
		double seconds = Double.parseDouble(secondsTxt.getText());
		boolean playIsEnabled = checkBox.getSelection();
		if (playIsEnabled) {
			start -= seconds;
			end += seconds;
			browser.evaluate("play(" + start + ", " + end + ");");
		}
	}
}
