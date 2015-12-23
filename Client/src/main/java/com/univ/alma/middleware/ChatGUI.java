package com.univ.alma.middleware;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.swing.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
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
    final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

    public static void main(String[] args) {
        
        ChatGUI chatgui = new ChatGUI();
    }

    public ChatGUI () {
        super("YouChat Connect to Server");
        setContentPane(chatPanel);
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        try {
            final DocumentBuilder builder = factory.newDocumentBuilder();
            final Document document= builder.parse(new File("db.xml"));
            final Element racine = document.getDocumentElement();
            final NodeList racineNoeuds = racine.getChildNodes();
            final int nbRacineNoeuds = racineNoeuds.getLength();

            for (int i = 0; i<nbRacineNoeuds; i++) {
                if(racineNoeuds.item(i).getNodeType() == Node.ELEMENT_NODE) {
                    final Element topic = (Element) racineNoeuds.item(i);
                    catList.addItem(topic.getAttribute("name"));
                }
            }
        }
        catch (final ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        connectButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){ doConnect();   }  });
        serverField.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){ doConnect();   }  });
    }

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
            Youchat youChatFrame = new Youchat(catList.getSelectedItem().toString());
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
