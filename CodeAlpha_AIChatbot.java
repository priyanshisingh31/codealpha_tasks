import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class AIChatbot extends JFrame {

    private JTextArea chatArea;
    private JTextField inputField;
    private JButton sendButton;
    private Map<String, String> faq;

    public AIChatbot() {
        setTitle("Java AI Chatbot");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        trainBot();

        chatArea = new JTextArea();
        chatArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(chatArea);

        inputField = new JTextField();
        sendButton = new JButton("Send");

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(inputField, BorderLayout.CENTER);
        panel.add(sendButton, BorderLayout.EAST);

        add(scrollPane, BorderLayout.CENTER);
        add(panel, BorderLayout.SOUTH);

        sendButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                sendMessage();
            }
        });

        inputField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                sendMessage();
            }
        });

        setVisible(true);
    }

    private void trainBot() {
        faq = new HashMap<String, String>();
        faq.put("hi", "Hello!");
        faq.put("hello", "Hi there!");
        faq.put("bye", "Goodbye!");
        faq.put("what is java", "Java is an object-oriented programming language.");
    }

    private String getResponse(String input) {
        input = input.toLowerCase();
        if (faq.containsKey(input)) {
            return faq.get(input);
        }
        return "Sorry, I didn't understand.";
    }

    private void sendMessage() {
        String text = inputField.getText();
        chatArea.append("You: " + text + "\n");
        chatArea.append("Bot: " + getResponse(text) + "\n\n");
        inputField.setText("");
    }

    public static void main(String[] args) {
        new AIChatbot();
    }
}
