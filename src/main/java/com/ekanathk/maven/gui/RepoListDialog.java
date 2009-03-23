package com.ekanathk.maven.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

import javax.swing.*;
import javax.swing.event.*;

import com.ekanathk.maven.core.CommonUtil;

public class RepoListDialog extends JPanel implements ListSelectionListener {
    private JList list;
    private DefaultListModel listModel;

    private static final String addString = "Add";
    private static final String removeString = "Remove";
    private JButton removeButton;
    private JTextField repoLocation;

    public RepoListDialog(String[] repoList) {
        super(new BorderLayout());
        this.setBorder(BorderFactory.createTitledBorder("List of Remote Repositories"));
        listModel = new DefaultListModel();
        for(String s : repoList) {
        	listModel.addElement(s);
        }

        //Create the list and put it in a scroll pane.
        list = new JList(listModel);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setSelectedIndex(0);
        list.addListSelectionListener(this);
        list.setVisibleRowCount(5);
        JScrollPane listScrollPane = new JScrollPane(list);

        JButton addButton = new JButton(addString);
        AddListener addListener = new AddListener(addButton);
        addButton.setActionCommand(addString);
        addButton.addActionListener(addListener);
        addButton.setEnabled(false);

        removeButton = new JButton(removeString);
        removeButton.setActionCommand(removeString);
        removeButton.addActionListener(new RemoveListener());

        repoLocation = new JTextField(10);
        repoLocation.addActionListener(addListener);
        repoLocation.getDocument().addDocumentListener(addListener);
        
        //Create a panel that uses BoxLayout.
        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new BoxLayout(buttonPane,
                                           BoxLayout.LINE_AXIS));
        buttonPane.add(removeButton);
        buttonPane.add(Box.createHorizontalStrut(5));
        buttonPane.add(new JSeparator(SwingConstants.VERTICAL));
        buttonPane.add(Box.createHorizontalStrut(5));
        buttonPane.add(repoLocation);
        buttonPane.add(addButton);
        buttonPane.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));

        add(listScrollPane, BorderLayout.CENTER);
        add(buttonPane, BorderLayout.PAGE_END);
    }

    public String[] getListData() {
    	List<String> data = new ArrayList<String>();
    	Enumeration<?> iter = listModel.elements();
    	while(iter.hasMoreElements()) {
    		data.add(iter.nextElement().toString());
    	}
    	return data.toArray(new String[data.size()]);
    }
    
    class RemoveListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            //This method can be called only if
            //there's a valid selection
            //so go ahead and remove whatever's selected.
            int index = list.getSelectedIndex();
            listModel.remove(index);

            int size = listModel.getSize();

            if (size == 0) { //Nobody's left, disable firing.
                removeButton.setEnabled(false);

            } else { //Select an index.
                if (index == listModel.getSize()) {
                    //removed item in last position
                    index--;
                }

                list.setSelectedIndex(index);
                list.ensureIndexIsVisible(index);
            }
        }
    }

    //This listener is shared by the text field and the hire button.
    class AddListener implements ActionListener, DocumentListener {
        private boolean alreadyEnabled = false;
        private JButton button;

        public AddListener(JButton button) {
            this.button = button;
        }

        //Required by ActionListener.
        public void actionPerformed(ActionEvent e) {
            String name = repoLocation.getText().trim();

            //User didn't type in a unique name...
            if (CommonUtil.isEmpty(name) || alreadyInList(name)) {
                SwingUtil.handleException("Please choose a non-empty name not already in the list");
                repoLocation.requestFocusInWindow();
                repoLocation.selectAll();
                return;
            }

            int index = list.getSelectedIndex(); //get selected index
            if (index == -1) { //no selection, so insert at beginning
                index = 0;
            } else {           //add after the selected item
                index++;
            }

            listModel.insertElementAt(name, index);
            //If we just wanted to add to the end, we'd do this:
            //listModel.addElement(repoLocation.getText());

            //Reset the text field.
            repoLocation.requestFocusInWindow();
            repoLocation.setText("");

            //Select the new item and make it visible.
            list.setSelectedIndex(index);
            list.ensureIndexIsVisible(index);
        }

        //This method tests for string equality. You could certainly
        //get more sophisticated about the algorithm.  For example,
        //you might want to ignore white space and capitalization.
        protected boolean alreadyInList(String name) {
        	String[] data = getListData();
        	for(String s : data) {
        		if(s.equalsIgnoreCase(name)) {
        			return true;
        		}
        	}
        	return false;
        }

        //Required by DocumentListener.
        public void insertUpdate(DocumentEvent e) {
        	if (!handleEmptyTextField(e)) {
                enableButton();
            }
        }

        //Required by DocumentListener.
        public void removeUpdate(DocumentEvent e) {
        	if (!handleEmptyTextField(e)) {
                enableButton();
            }
        }

        //Required by DocumentListener.
        public void changedUpdate(DocumentEvent e) {
            if (!handleEmptyTextField(e)) {
                enableButton();
            }
        }

        private void enableButton() {
            if (!alreadyEnabled) {
                button.setEnabled(true);
            }
        }

        private boolean handleEmptyTextField(DocumentEvent e) {
            if (e.getDocument().getLength() <= 0) {
                button.setEnabled(false);
                alreadyEnabled = false;
                return true;
            }
            return false;
        }
    }

    //This method is required by ListSelectionListener.
    public void valueChanged(ListSelectionEvent e) {
        if (e.getValueIsAdjusting() == false) {

            if (list.getSelectedIndex() == -1) {
            //No selection, disable fire button.
                removeButton.setEnabled(false);

            } else {
            //Selection, enable the fire button.
                removeButton.setEnabled(true);
            }
        }
    }
}
