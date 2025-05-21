package com.biblioteca.controller;

import com.biblioteca.view.BibliotecaView;
import com.biblioteca.view.LibroView;
import com.biblioteca.view.DvdView;
import com.biblioteca.view.RevistaView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BibliotecaController {
    private BibliotecaView view;

    public BibliotecaController(BibliotecaView view) {
        this.view = view;

        // Configurar listeners
        view.setLibrosListener(new LibrosListener());
        view.setDvdsListener(new DvdsListener());
        view.setRevistasListener(new RevistasListener());
    }

    class LibrosListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            LibroView libroView = new LibroView();
            LibroController libroController = new LibroController(libroView);
            libroView.mostrar();
        }
    }

    class DvdsListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            DvdView dvdView = new DvdView();
            DvdController dvdController = new DvdController(dvdView);
            dvdView.mostrar();
        }
    }

    class RevistasListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            RevistaView revistaView = new RevistaView();
            RevistaController revistaController = new RevistaController(revistaView);
            revistaView.mostrar();
        }
    }
}