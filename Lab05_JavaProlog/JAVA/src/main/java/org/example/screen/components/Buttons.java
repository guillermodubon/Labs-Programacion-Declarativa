package org.example.screen.components;

import org.example.screen.Theme;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JButton;
import javax.swing.border.EmptyBorder;

public class Buttons extends JButton {
    private final Color colorBase;

    public Buttons(String texto, Color fondo, Color textoColor, int padH, int padV, int fuente) {
        super(texto);
        colorBase = fondo;
        setFont(Theme.sans(Font.BOLD, fuente));
        setForeground(textoColor);
        setFocusPainted(false);
        setBorderPainted(false);
        setContentAreaFilled(false);
        setOpaque(false);
        setRolloverEnabled(true);
        setBorder(new EmptyBorder(padV, padH, padV, padH));
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(getModel().isPressed() ? colorBase.darker()
                : getModel().isRollover() ? colorBase.brighter() : colorBase);
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 16, 16);
        g2.dispose();
        super.paintComponent(g);
    }
}
