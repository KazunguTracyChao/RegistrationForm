/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mywork.registrationform2;

/**
 *
 * @author USER
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;

public class RegistrationForm2 extends JFrame implements ActionListener {

    private JTextField idField, nameField, contactField;
    private JTextArea addressArea;
    private JRadioButton maleRadioButton, femaleRadioButton;
    private JButton registerButton, exitButton;
    private JTable table;
    private DefaultTableModel model;

    private static final String url = "jdbc:mysql://localhost:3306/record";
    private static final String username = "root";
    private static final String password = "";

    public RegistrationForm2() {
        setTitle("Registration Form");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel formPanel = new JPanel(new GridLayout(6, 2));

        formPanel.add(new JLabel("ID:"));
        idField = new JTextField();
        formPanel.add(idField);

        formPanel.add(new JLabel("Name:"));
        nameField = new JTextField();
        formPanel.add(nameField);

        formPanel.add(new JLabel("Gender:"));
        JPanel genderPanel = new JPanel();
        maleRadioButton = new JRadioButton("Male");
        femaleRadioButton = new JRadioButton("Female");
        ButtonGroup genderGroup = new ButtonGroup();
        genderGroup.add(maleRadioButton);
        genderGroup.add(femaleRadioButton);
        genderPanel.add(maleRadioButton);
        genderPanel.add(femaleRadioButton);
        formPanel.add(genderPanel);

        formPanel.add(new JLabel("Address:"));
        addressArea = new JTextArea();
        formPanel.add(new JScrollPane(addressArea));

        formPanel.add(new JLabel("Contact:"));
        contactField = new JTextField();
        formPanel.add(contactField);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        registerButton = new JButton("Register");
        registerButton.addActionListener(this);
        buttonPanel.add(registerButton);

        exitButton = new JButton("Exit");
        exitButton.addActionListener(this);
        buttonPanel.add(exitButton);

        model = new DefaultTableModel(new String[]{"ID", "Name", "Gender", "Address", "Contact"}, 0);
        table = new JTable(model);

        add(formPanel, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == registerButton) {
            String id = idField.getText();
            String name = nameField.getText();
            String gender = maleRadioButton.isSelected() ? "Male" : "Female";
            String address = addressArea.getText();
            String contact = contactField.getText();

            try {
                Connection connection = DriverManager.getConnection(url, username, password);
                PreparedStatement statement = connection.prepareStatement("INSERT INTO table2 (ID, Name, Gender, Address, Contact) VALUES (?, ?, ?, ?, ?)");
                statement.setString(1, id);
                statement.setString(2, name);
                statement.setString(3, gender);
                statement.setString(4, address);
                statement.setString(5, contact);
                statement.executeUpdate();
                statement.close();
                connection.close();

                model.addRow(new Object[]{id, name, gender, address, contact});
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "An error occurred while registering user.");
            }
        } else if (e.getSource() == exitButton) {
            dispose(); // Close the application
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new RegistrationForm2();
            }
        });
    }
}
