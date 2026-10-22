package org.example.screen.components;

import org.example.screen.Theme;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import javax.swing.BorderFactory;
import javax.swing.JTextField;

public class TextField extends JTextField {
    private final String ayuda;

    public TextField(String ayuda) {
        this.ayuda = ayuda;
        setFont(Theme.mono(Font.PLAIN, 18));
        setBackground(Theme.CARD);
        setForeground(Theme.TEXT);
        setCaretColor(Theme.TEXT);
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Theme.BORDER), BorderFactory.createEmptyBorder(10, 14, 10, 14)));
        addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) { repaint(); }
            @Override
            public void focusLost(FocusEvent e) { repaint(); }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (hasFocus()) {
            g.setColor(Theme.BLUE);
            g.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
        }
        if (getText().isEmpty()) {
            g.setColor(Theme.MUTED);
            g.setFont(getFont());
            int y = (getHeight() + g.getFontMetrics().getAscent() - g.getFontMetrics().getDescent()) / 2;
            g.drawString(ayuda, getInsets().left, y);
        }
    }
}
