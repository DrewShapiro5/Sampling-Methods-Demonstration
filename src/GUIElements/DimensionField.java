package GUIElements;

import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.PlainDocument;


public class DimensionField extends JTextField {
    private int LIMIT = 5;

    public DimensionField(String initialString, int limit)
    {
        super(initialString);
        this.setText(initialString);
        this.LIMIT = limit;
    }

    @Override
    protected Document createDefaultModel()
    {
        return new LimitDocument();
    }

    private class LimitDocument extends PlainDocument
    {

        @Override
        public void insertString(int offset, String  str, AttributeSet a) throws BadLocationException
        {
            if (str == null) return;
            if ((getLength() + str.length()) <= LIMIT && str.matches("[0-9]+")) super.insertString(offset, str, a);
        }
    }
}