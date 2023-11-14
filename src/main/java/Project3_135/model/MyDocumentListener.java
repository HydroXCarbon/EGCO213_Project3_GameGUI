package Project3_135.model;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;

public class MyDocumentListener implements DocumentListener {
    @Override
    public void insertUpdate(DocumentEvent e) {
        handleTextChange(e);
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        handleTextChange(e);
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        handleTextChange(e);
    }

    private void handleTextChange(DocumentEvent e) {
        // The DocumentEvent object contains information about the text change
        Document document = e.getDocument();
        try {
            String text = document.getText(0, document.getLength());
            System.out.println("Text changed: " + text);
        } catch (javax.swing.text.BadLocationException ex) {
            ex.printStackTrace();
        }
    }
}
