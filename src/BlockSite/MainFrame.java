package BlockSite;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class MainFrame extends JFrame {

    private JTextField textField;
    private DefaultListModel<String> listModel;
    private DefaultListModel<String> initialListModel;
    private JList<String> myList;

    public MainFrame() {
        setTitle("JList Example");

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (!isEqualList(listModel, initialListModel)) {
                    int option = JOptionPane.showConfirmDialog(
                            null,
                            "You have unsaved changes. Are you sure you want to close?",
                            "Unsaved Changes",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.WARNING_MESSAGE);

                    if (option == JOptionPane.YES_OPTION) {
                        dispose(); // Close the frame
                    }
                } else {
                    dispose(); // Close the frame
                }
            }
        });

        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLayout(new BorderLayout());

        // Create the text field
        textField = new JTextField();
        add(textField, BorderLayout.NORTH);

        // Create the Add button
        JButton addButton = new JButton("Add");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String item = textField.getText();
                if (!item.isEmpty()) {
                    listModel.add(0, item);
                    textField.setText("");
                }
            }
        });

        JButton removeButton = new JButton("Remove");
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (myList.getSelectedIndex() != -1) {
                    listModel.removeElementAt(myList.getSelectedIndex());
                }
            }
        });

        // Create the Save button
        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<String> list = new ArrayList<>();

                for (int i = 0; i < listModel.size(); i++) {
                    list.add(listModel.get(i));
                }

                HostsFileUpdater.removeWebsiteFromHostsFile();
                HostsFileUpdater.addWebsiteToHostsFile(list);

                initialListModel.clear();

                for (int i = 0; i < listModel.size(); i++) {
                    initialListModel.addElement(listModel.get(i));
                }

                JOptionPane.showMessageDialog(MainFrame.this, "Data saved successfully!");
            }

        });

        JButton clearButton = new JButton("Clear");
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listModel.clear();
            }
        });

        // Create a panel for the buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        buttonPanel.add(removeButton);
        buttonPanel.add(saveButton);
        buttonPanel.add(clearButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // Create the JList and its model
        listModel = new DefaultListModel<>();
        initialListModel = new DefaultListModel<>();
        myList = new JList<>(listModel);
        JScrollPane scrollPane = new JScrollPane(myList);
        add(scrollPane, BorderLayout.CENTER);

        List<String> list = HostsFileUpdater.getWebsitesFromHostsFile();

        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
                listModel.addElement(list.get(i));
                initialListModel.addElement(list.get(i));
            }
        }

        myList.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_DELETE) {
                    removeButton.doClick();
                }
            }
        });

        textField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    addButton.doClick();
                }
            }
        });

        KeyAdapter keyAdapter = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_S && e.isControlDown()) {
                    System.out.println("Ctrl + S pressed");
                    saveButton.doClick();
                }
            }
        };

        addButton.addKeyListener(keyAdapter);
        removeButton.addKeyListener(keyAdapter);
        saveButton.addKeyListener(keyAdapter);
        clearButton.addKeyListener(keyAdapter);
        myList.addKeyListener(keyAdapter);
        textField.addKeyListener(keyAdapter);
        addKeyListener(keyAdapter);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private static boolean isEqualList(DefaultListModel<String> list1, DefaultListModel<String> list2) {
        if (list1.size() != list2.size()) {
            return false;
        }

        for (int i = 0; i < list1.size(); i++) {
            if (!list1.get(i).equals(list2.get(i))) {
                return false;
            }
        }
        return true;
    }
}
