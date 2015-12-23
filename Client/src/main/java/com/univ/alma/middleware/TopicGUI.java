package com.univ.alma.middleware;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.swing.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

/**
 * Created by imadhy on 23/12/15.
 */
public class TopicGUI extends JFrame {
    private JPanel topicPanel;
    private JLabel topicName;
    private JTextField sujetField;
    private JButton ajouterButton;
    private JFrame frame;
    final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

    public TopicGUI() {
        super("Ajouter un nouveau sujet");
        setContentPane(topicPanel);
        pack();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);

        ajouterButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){ addTopic();
                setVisible(false);
                dispose();}  });
    }


    public void addTopic() {
        try {
            final DocumentBuilder builder = factory.newDocumentBuilder();
            final Document document= builder.parse(new File("db.xml"));

            XPath xPath =  XPathFactory.newInstance().newXPath();
            String p = "/Chat";
            NodeList ChatTag = (NodeList) xPath.compile(p).evaluate(document, XPathConstants.NODESET);
            final Element Chat = (Element) ChatTag.item(0);

            Element newTopic = document.createElement("Topic");
            newTopic.setAttribute("name", sujetField.getText());

            Chat.appendChild(newTopic);
            DOMSource source = new DOMSource(document);

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            StreamResult result = new StreamResult("db.xml");
            transformer.transform(source, result);

            JOptionPane.showMessageDialog(frame, "Vous avez ajoutée le sujet '" + sujetField.getText() + "' avec succès !");
        }
        catch (final ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }
}
