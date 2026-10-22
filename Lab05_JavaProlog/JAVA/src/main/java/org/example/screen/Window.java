package org.example.screen;

import org.example.Connection;
import org.example.screen.components.Buttons;
import org.example.screen.components.PanelResult;
import org.example.screen.components.TextField;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Window extends JFrame {
    private final TextField entrada = new TextField("Ej: x^2 + 5*x^2");
    private final PanelResult resultado = new PanelResult();
    private final JLabel estado = etiqueta(" ");

    public Window(Connection motor) {
        super("Cálculo simbólico en Prolog");
        entrada.addActionListener(e -> ejecutar(motor, "d", "Derivada"));
        JPanel contenido = new JPanel();
        contenido.setLayout(new BoxLayout(contenido, BoxLayout.Y_AXIS));
        contenido.setOpaque(false);
        JLabel titulo = etiqueta("Cálculo simbólico");
        titulo.setFont(Theme.sans(Font.BOLD, 24));
        titulo.setForeground(Theme.TEXT);
        agregar(contenido, titulo, 4);
        agregar(contenido, etiqueta("Derivadas, integrales, sumas y restas con reglas en Prolog"), 22);
        agregar(contenido, etiqueta("Función f(x)"), 6);
        agregar(contenido, entrada, 8);
        agregar(contenido, crearEjemplos(), 18);
        agregar(contenido, crearBotones(motor), 22);
        resultado.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));
        agregar(contenido, resultado, 10);
        agregar(contenido, crearPie(), 0);
        JPanel raiz = new JPanel(new BorderLayout());
        raiz.setBackground(Theme.BG);
        raiz.setBorder(BorderFactory.createEmptyBorder(24, 28, 24, 28));
        raiz.add(contenido);
        setContentPane(raiz);
        setMinimumSize(new Dimension(660, 500));
        setSize(700, 520);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    private JPanel crearEjemplos() {
        JPanel panel = fila();
        panel.add(etiqueta("Ejemplos:"));
        for (String ejemplo : new String[]{"x", "log(x)", "3*x^2", "x^2+5*x^2", "7*x^3-3*x^3"}) {
            Buttons boton = new Buttons(ejemplo, Theme.CARD, Theme.TEXT, 10, 6, 11);
            boton.addActionListener(e -> {
                entrada.setText(ejemplo);
                entrada.requestFocusInWindow();
            });
            panel.add(boton);
        }
        return panel;
    }

    private JPanel crearBotones(Connection motor) {
        JPanel panel = fila();
        String[] nombres = {"Derivar", "Integrar", "Sumar", "Restar"};
        String[] predicados = {"d", "s", "sumar", "restar"};
        String[] titulos = {"Derivada", "Integral", "Suma", "Resta"};
        Color[] colores = {Theme.BLUE, Theme.GREEN, Theme.AMBER, Theme.RED};
        for (int i = 0; i < nombres.length; i++) {
            String predicado = predicados[i];
            String titulo = titulos[i];
            Buttons boton = new Buttons(nombres[i], colores[i], Theme.BG, 24, 11, 14);
            boton.addActionListener(e -> ejecutar(motor, predicado, titulo));
            panel.add(boton);
        }
        return panel;
    }

    private JPanel crearPie() {
        Buttons copiar = new Buttons("Copiar", Theme.CARD, Theme.TEXT, 16, 7, 12);
        copiar.addActionListener(e -> {
            if (!resultado.getTexto().isEmpty()) {
                Toolkit.getDefaultToolkit().getSystemClipboard()
                        .setContents(new StringSelection(resultado.getTexto()), null);
                mostrarEstado("Copiado al portapapeles", Theme.BLUE);
            }
        });
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);
        panel.add(estado, BorderLayout.WEST);
        panel.add(copiar, BorderLayout.EAST);
        return panel;
    }

    private void ejecutar(Connection motor, String predicado, String titulo) {
        resultado.setTitulo(titulo + " de f(x)");
        resultado.setTexto("");
        if (entrada.getText().isBlank()) {
            mostrarEstado("Escribí una expresión primero.", Theme.AMBER);
            return;
        }
        try {
            String texto = motor.consultar(predicado, entrada.getText().trim());
            if (texto.startsWith("Sin solución")) {
                mostrarEstado(predicado.equals("sumar") ? "Usá una suma algebraica, por ejemplo: x + 2*x."
                        : predicado.equals("restar") ? "Usá una resta algebraica, por ejemplo: 5*x - 2*x."
                        : texto, Theme.AMBER);
            } else {
                resultado.setTexto(texto + (predicado.equals("s") ? " + C" : ""));
                mostrarEstado("Listo", Theme.GREEN);
            }
        } catch (Exception ex) {
            mostrarEstado("Expresión inválida. Usá x, *, ^ y paréntesis.", Theme.RED);
        }
    }

    private void mostrarEstado(String texto, Color color) {
        estado.setText(texto);
        estado.setForeground(color);
    }

    private static JPanel fila() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        panel.setOpaque(false);
        return panel;
    }

    private static JLabel etiqueta(String texto) {
        JLabel label = new JLabel(texto);
        label.setFont(Theme.sans(Font.PLAIN, 12));
        label.setForeground(Theme.MUTED);
        return label;
    }

    private static void agregar(JPanel panel, JComponent componente, int espacio) {
        componente.setAlignmentX(Component.LEFT_ALIGNMENT);
        if (!(componente instanceof PanelResult)) {
            componente.setMaximumSize(new Dimension(Integer.MAX_VALUE, componente.getPreferredSize().height));
        }
        panel.add(componente);
        if (espacio > 0) panel.add(Box.createVerticalStrut(espacio));
    }
}
