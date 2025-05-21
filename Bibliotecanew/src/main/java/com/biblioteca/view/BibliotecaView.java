package com.biblioteca.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BibliotecaView extends JFrame {
    private JButton btnLibros;
    private JButton btnDvds;
    private JButton btnRevistas;

    public BibliotecaView() {
        setTitle("Sistema de Gestión de Biblioteca");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(3, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        btnLibros = new JButton("Gestión de Libros");
        btnDvds = new JButton("Gestión de DVDs");
        btnRevistas = new JButton("Gestión de Revistas");

        panel.add(btnLibros);
        panel.add(btnDvds);
        panel.add(btnRevistas);

        add(panel);
    }

    public void setLibrosListener(ActionListener listener) {
        btnLibros.addActionListener(listener);
    }

    public void setDvdsListener(ActionListener listener) {
        btnDvds.addActionListener(listener);
    }

    public void setRevistasListener(ActionListener listener) {
        btnRevistas.addActionListener(listener);
    }

    public void mostrar() {
        setVisible(true);
    }
}