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
import javax.xml.xpath.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Vector;

/**
 * Created by imadhy on 16/12/15.
 */
public class Youchat extends JFrame {
    private JPanel youChatPanel;
    private JTextArea chatArea;
    private JTextField textToSend;
    private JButton sendButton;
    private JList userList;
    private JFrame frame;
    private ChatClient client;
    private ServerInterface server;
    private String nickname;
    private String topic;
    final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

    public Youchat (String topic) {
        super("YouChat ~ " + topic);
        setContentPane(youChatPanel);
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        this.topic = topic;
        this.loadHistory();

        sendButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){ sendText();   }  });

        textToSend.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){ sendText();   }  });
    }

    public void updateUsers(Vector v, ChatClient chClient, ServerInterface SI, String nn){
        DefaultListModel listModel = new DefaultListModel();
        if(v!=null) for (int i=0;i<v.size();i++){
            try{  String tmp=((ChatClientInterface)v.get(i)).getName();
                listModel.addElement(tmp);
            }catch(Exception e){e.printStackTrace();}
        }
        userList.setModel(listModel);

        this.client = chClient;
        this.server = SI;
        this.nickname = nn;
    }

    public void loadHistory() {
        try {
            final DocumentBuilder builder = factory.newDocumentBuilder();
            final Document document= builder.parse(new File("db.xml"));

            XPath xPath =  XPathFactory.newInstance().newXPath();
            String p = "/Chat/Topic[@name='"+this.topic+"']/message";
            NodeList nodeList = (NodeList) xPath.compile(p).evaluate(document, XPathConstants.NODESET);

            for (int i = 0; i < nodeList.getLength(); i++) {
                final Element message = (Element) nodeList.item(i);
                chatArea.setText(chatArea.getText() + "["+message.getAttribute("username")+"]: " + message.getTextContent() + "\n");
            }
        }
        catch (final ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }
    }

    public void writeMsg(String st) {
        chatArea.setText(chatArea.getText()+"\n" + st);
    }

    public void sendText(){
        String st=textToSend.getText();
        st="["+nickname+"]: "+st;
        textToSend.setText("");

        try{
            server.publish(st);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
