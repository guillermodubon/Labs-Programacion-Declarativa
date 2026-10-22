package org.example.screen.components;

import org.example.screen.Theme;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class PanelResult extends JPanel {
    private final JLabel titulo = new JLabel("Resultado");
    private final JTextArea resultado = new JTextArea();

    public PanelResult() {
        super(new BorderLayout(0, 6));
        setOpaque(false);
        titulo.setFont(Theme.sans(Font.BOLD, 12));
        titulo.setForeground(Theme.MUTED);
        resultado.setEditable(false);
        resultado.setLineWrap(true);
        resultado.setWrapStyleWord(true);
        resultado.setFont(Theme.mono(Font.PLAIN, 17));
        resultado.setBackground(Theme.CARD);
        resultado.setForeground(Theme.TEXT);
        resultado.setBorder(BorderFactory.createEmptyBorder(12, 14, 12, 14));
        JScrollPane scroll = new JScrollPane(resultado);
        scroll.setBorder(BorderFactory.createLineBorder(Theme.BORDER));
        scroll.getViewport().setBackground(Theme.CARD);
        scroll.setPreferredSize(new Dimension(0, 120));
        add(titulo, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);
    }

    public void setTitulo(String texto) { titulo.setText(texto); }
    public void setTexto(String texto) { resultado.setText(texto); }
    public String getTexto() { return resultado.getText(); }
}
