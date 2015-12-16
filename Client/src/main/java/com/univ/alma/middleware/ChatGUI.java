package com.univ.alma.middleware;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.Naming;
import java.util.Vector;

/**
 * Created by imadhy on 16/12/15.
 */
public class ChatGUI extends JFrame {
    private JPanel chatPanel;
    private JButton connectButton;
    private JTextField nnField;
    private JTextField serverField;
    private JLabel nnLabel;
    private JLabel serverLabel;
    private JLabel sujetLabel;
    private JComboBox catList;
    private JFrame frame;

    public static void main(String[] args) {
        ChatGUI chatgui = new ChatGUI();
    }

    public ChatGUI () {
        super("YouChat Connect to Server");
        setContentPane(chatPanel);
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        connectButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){ doConnect();   }  });
        serverField.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){ doConnect();   }  });
    }

    /*

    JTextArea tx;
        JTextField tf,ip, name;
        JButton connect;
        JList lst;

     */

    private ChatClient client;
    private ServerInterface server;

    public void doConnect() {

        if (nnField.getText().length() < 2) {
            JOptionPane.showMessageDialog(frame, "You need to type a name.");
            return;
        }
        if (serverField.getText().length() < 2) {
            JOptionPane.showMessageDialog(frame, "You need to type an IP.");
            return;
        }
        try {
            client=new ChatClient(nnField.getText());
            Youchat youChatFrame = new Youchat();
            youChatFrame.setVisible(true);
            client.setGUI(youChatFrame);
            server=(ServerInterface) Naming.lookup("rmi://"+serverField.getText()+"/youchat");
            server.login(client);
            youChatFrame.updateUsers(server.getConnected(), client, server, nnField.getText());
        } catch(Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame, "ERROR finding server");
        }
    }
}
