package com.biblioteca;

import com.biblioteca.controller.BibliotecaController;
import com.biblioteca.view.BibliotecaView;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        // Configurar el look and feel del sistema operativo
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Crear y mostrar la interfaz gráfica
        BibliotecaView view = new BibliotecaView();
        BibliotecaController controller = new BibliotecaController(view);
        view.mostrar();
    }
}