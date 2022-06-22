package ru.serdyuk.simplebot_2;

import javax.swing.*;
import java.awt.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.regex.*;

/**
 * @author Serdyuk S.B.
 */
public class ChatUDP extends JFrame {
    private JTextArea textAreaMain;
    private JTextField textFieldMessage;

    private static final String FRM_TITLE = "Chat Bot 2";
    private static final int FRM_LOC_X = 100;
    private static final int FRM_LOC_Y = 100;
    private static final int FRM_WIDTH = 400;
    private static final int FRM_HEIGHT = 400;

    private final int PORT = 9876;
    private final String IP_BROADCAST = "172.16.0.217";


    private class thdReceiver extends Thread {
        @Override
        public void start() {
            super.start();
            try {
                customize();
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
        private void customize() throws Exception {
            DatagramSocket receiverSocked = new DatagramSocket(PORT);
            Pattern regex = Pattern.compile("[\u0020-\uFFFF]");

            while (true) {
                byte[] receiveData = new byte[1024];
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                receiverSocked.receive(receivePacket);
                InetAddress IPAddress = receivePacket.getAddress();
                int port = receivePacket.getPort();
                String sentence = new String(receivePacket.getData());
                Matcher matcher = regex.matcher(sentence);

                textAreaMain.append(IPAddress.toString() + ":" + port + ": ");
                while (matcher.find())
                    textAreaMain.append(sentence.substring(matcher.start(), matcher.end()));
                textAreaMain.append("\r\n");
                textAreaMain.setCaretPosition(textAreaMain.getText().length());
            }
        }
    }



    private void btnSendHandler() throws Exception {
        //открыть сокет, сформировать пакет и отправить его
        DatagramSocket sendSocket = new DatagramSocket();
        InetAddress IPAddress = InetAddress.getByName(IP_BROADCAST);
        byte[] sendData;
        String sentence = textFieldMessage.getText();
        textFieldMessage.setText("");
        sendData = sentence.getBytes("UTF-8");
        DatagramPacket sendPacked = new DatagramPacket(sendData, sendData.length, IPAddress, PORT);
        sendSocket.send(sendPacked);
    }

    private void framDraw(JFrame frame) {
        textFieldMessage = new JTextField();
        textAreaMain = new JTextArea(FRM_HEIGHT / 19, 50 );
        JScrollPane spMane = new JScrollPane(textAreaMain);
        spMane.setLocation(0,0);
        textAreaMain.setLineWrap(true);
        textAreaMain.setEditable(false);

        JButton btnSend = new JButton();

        btnSend.setText("Send");
        btnSend.setToolTipText("Broadcast a message");
        btnSend.addActionListener(e -> {
            try {
                btnSendHandler();

            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setTitle(FRM_TITLE);
        frame.setLocation(FRM_LOC_X, FRM_LOC_Y);
        frame.setSize(FRM_WIDTH, FRM_HEIGHT);
        frame.setResizable(false);
        frame.getContentPane().add(BorderLayout.NORTH, spMane);
        frame.getContentPane().add(BorderLayout.CENTER, textFieldMessage);
        frame.getContentPane().add(BorderLayout.EAST, btnSend);
        frame.setVisible(true);
    }

    private void initThread() {
        framDraw(new ChatUDP());
        new thdReceiver().start();
    }
    public static void main(String[] args) {
        new ChatUDP().initThread();
    }
}
