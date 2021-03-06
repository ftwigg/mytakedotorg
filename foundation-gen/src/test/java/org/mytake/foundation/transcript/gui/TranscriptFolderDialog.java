/*
 * MyTake.org transcript GUI. 
 * Copyright (C) 2018-2020 MyTake.org, Inc.
 * 
 * The MyTake.org transcript GUI is licensed under EPLv2
 * because SWT is incompatible with AGPLv3, the rest of
 * MyTake.org is licensed under AGPLv3.
 *
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 */
package org.mytake.foundation.transcript.gui;

import com.diffplug.common.base.Errors;
import com.diffplug.common.base.Throwables;
import com.diffplug.common.rx.Rx;
import com.diffplug.common.swt.Corner;
import com.diffplug.common.swt.Fonts;
import com.diffplug.common.swt.Layouts;
import com.diffplug.common.swt.Shells;
import com.diffplug.common.swt.SwtMisc;
import com.diffplug.common.swt.os.OS;
import io.reactivex.subjects.PublishSubject;
import java.io.File;
import java.util.List;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.widgets.Text;
import org.mytake.foundation.transcript.TranscriptFolder;
import org.mytake.foundation.transcript.TranscriptMatch;

public class TranscriptFolderDialog {
	private final Text transcriptTxt;
	private final Text folderTxt;
	private final Button saveBtn, refreshBtn;
	private final TranscriptCtl transcriptCtl;
	private final PublishSubject<Boolean> saveEnabled = PublishSubject.create();

	private TranscriptFolderDialog(Composite parent) {
		Layouts.setGrid(parent).margin(0).spacing(0);
		SashForm top = new SashForm(parent, SWT.HORIZONTAL);
		Layouts.setGridData(top).grabHorizontal();

		Label sep = new Label(parent, SWT.SEPARATOR | SWT.HORIZONTAL);
		Layouts.setGridData(sep).grabHorizontal();

		transcriptCtl = new TranscriptCtl(parent, saveEnabled, this::save);
		Layouts.setGridData(transcriptCtl).grabAll();

		Composite folderCmp = new Composite(top, SWT.NONE);
		Layouts.setGrid(folderCmp).margin(0).numColumns(3);
		Label folderLbl = Labels.create(folderCmp, "Folder");
		Layouts.setGridData(folderLbl).horizontalIndent(Layouts.defaultMargin());
		folderLbl.setFont(Fonts.systemBold());
		folderTxt = new Text(folderCmp, SWT.SINGLE | SWT.READ_ONLY | SWT.BORDER);
		Layouts.setGridData(folderTxt).grabHorizontal();
		Button folderBtn = new Button(folderCmp, SWT.PUSH);
		folderBtn.setText("...");
		folderBtn.addListener(SWT.Selection, e -> {
			DirectoryDialog dialog = new DirectoryDialog(parent.getShell());
			dialog.setFilterPath(folderTxt.getText());
			String newDir = dialog.open();
			if (newDir != null) {
				setFolder(new File(newDir));
			}
		});

		Composite transcriptCmp = new Composite(top, SWT.NONE);
		Layouts.setGrid(transcriptCmp).margin(0).numColumns(5);

		Label label = Labels.create(transcriptCmp, "Date");
		label.setFont(Fonts.systemBold());
		Layouts.setGridData(label).horizontalIndent(Layouts.defaultMargin());
		transcriptTxt = new Text(transcriptCmp, SWT.SINGLE | SWT.READ_ONLY | SWT.BORDER);
		Layouts.setGridData(transcriptTxt).grabHorizontal();
		Button transcriptBtn = new Button(transcriptCmp, SWT.PUSH);
		transcriptBtn.setText("...");
		transcriptBtn.addListener(SWT.Selection, e -> {
			Shells.builder(SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL, this::openOptions)
					.setTitle("Transcript details")
					.setLocation(Corner.TOP_LEFT, Corner.BOTTOM_LEFT.getPosition(label))
					.openOnActive();
		});
		saveBtn = new Button(transcriptCmp, SWT.PUSH);
		saveBtn.setText("Save");
		saveBtn.setEnabled(false);
		saveBtn.addListener(SWT.Selection, e -> save());
		Rx.subscribe(saveEnabled, saveBtn::setEnabled);

		refreshBtn = new Button(transcriptCmp, SWT.PUSH);
		refreshBtn.setText("Refresh");
		refreshBtn.setEnabled(false);
		refreshBtn.addListener(SWT.Selection, e -> {
			setTranscript(transcript);
		});
	}

	private void openOptions(Composite parent) {
		Layouts.setGrid(parent);
		Composite transcriptCmp = new Composite(parent, SWT.NONE);
		Layouts.setGridData(transcriptCmp).grabHorizontal();
		Layouts.setGrid(transcriptCmp).margin(0).numColumns(2);
		for (String transcript : folder.transcripts()) {
			try {
				TranscriptMatch match = folder.loadTranscript(transcript);

				Link link = new Link(transcriptCmp, SWT.NONE);
				link.setText("<a>" + transcript + "</a>");
				link.addListener(SWT.Selection, e -> {
					setTranscript(transcript);
					parent.getShell().dispose();
				});
				String msg = match.edits().isEmpty() ? "Complete" : match.edits().size() + " unmatched";
				Labels.create(transcriptCmp, msg);
			} catch (Exception e) {
				Labels.create(transcriptCmp, transcript);

				Throwable root = Throwables.getRootCause(e);
				String msg = root.getMessage();
				msg = msg == null ? "Error" : msg.substring(0, Math.min(30, msg.length()));
				Link link = new Link(transcriptCmp, SWT.NONE);
				link.setText("<a>" + msg + "</a>");
				link.addListener(SWT.Selection, event -> {
					e.printStackTrace();
				});
			}
		}
		List<String> incomplete = folder.incompleteTranscripts();
		if (incomplete.isEmpty()) {
			Labels.create(parent, "Incomplete").setFont(Fonts.systemBold());
			for (String s : incomplete) {
				Labels.create(parent, s);
			}
		}
		Button btn = new Button(parent, SWT.PUSH);
		Layouts.setGridData(btn).horizontalAlignment(SWT.RIGHT);
		btn.setText("Cancel");
		btn.addListener(SWT.Selection, e -> {
			parent.getShell().dispose();
		});
		parent.getDisplay().addFilter(SWT.KeyDown, e -> {
			if ((e.stateMask | e.keyCode) == (CTRL | 's')) {
				save();
				e.doit = false;
			}
		});
	}

	private static final int CTRL = OS.getNative().isMac() ? SWT.COMMAND : SWT.CTRL;

	private TranscriptFolder folder;
	private String transcript;

	private void setFolder(File folder) {
		Errors.dialog().run(() -> {
			File canonical = folder.getCanonicalFile();
			this.folder = new TranscriptFolder(canonical);
			this.folderTxt.setText(canonical.getAbsolutePath());
		});
	}

	private void setTranscript(String transcript) {
		this.transcript = transcript;
		Errors.dialog().run(() -> {
			folder = new TranscriptFolder(new File(folderTxt.getText()));
			TranscriptMatch match = folder.loadTranscript(transcript);
			transcriptCtl.setTo(match);
			transcriptTxt.setText(transcript);
			refreshBtn.setEnabled(true);
		});
	}

	private void save() {
		Errors.dialog().run(() -> transcriptCtl.save(folder, transcript));
		saveBtn.setEnabled(false);
	}

	public static void main(String[] args) {
		Shells.builder(SWT.SHELL_TRIM, cmp -> {
			TranscriptFolderDialog dialog = new TranscriptFolderDialog(cmp);
			dialog.setFolder(new File("../presidential-debates"));
		})
				.setTitle("MyTake.org Transcript Editor")
				.setSize(SwtMisc.scaleByFontHeight(60, 40))
				.openOnDisplayBlocking();
	}
}
