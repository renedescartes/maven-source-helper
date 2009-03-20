package com.ekanathk.maven.gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.ekanathk.maven.core.Artifact;

public class ArtifactDialog extends JPanel {

	private JTextField group, artifact, version;
	
	public ArtifactDialog() {
		GridBagLayout gridbag = new GridBagLayout();
		GridBagConstraints constraints = new GridBagConstraints();
		this.setLayout(gridbag);
		// ensure there is always a gap between components
		constraints.insets = new Insets(2, 2, 2, 2);

		group = addInputField(gridbag, constraints, "Group Id", "commons-lang");
		artifact = addInputField(gridbag, constraints, "Artifact Id", "commons-lang");
		version = addInputField(gridbag, constraints, "Version", "2.4");
	}

	private final JTextField addInputField(GridBagLayout gridbag,
			GridBagConstraints constraints, String text, String value) {
		// add group id field
		JLabel labelField = new JLabel(text);
		constraints.weightx = 0.0;
		constraints.gridwidth = 1;
		constraints.anchor = GridBagConstraints.EAST;
		gridbag.setConstraints(labelField, constraints);
		this.add(labelField);

		JTextField textField = new JTextField(25);
		textField.setText(value);
		constraints.gridwidth = GridBagConstraints.REMAINDER;
		constraints.anchor = GridBagConstraints.WEST;
		gridbag.setConstraints(textField, constraints);
		this.add(textField);
		
		return textField;
	}
	
	public Artifact getArtifact() {
		return new Artifact(group.getText(), artifact.getText(), version.getText());
	}
}
