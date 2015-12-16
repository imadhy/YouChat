package com.univ.alma.middleware;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

    public Youchat () {
        super("YouChat");
        setContentPane(youChatPanel);
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

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
        client = chClient;
        server = SI;
        nickname = nn;
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
