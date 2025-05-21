package com.biblioteca.controller;

import com.biblioteca.dao.RevistaDAO;
import com.biblioteca.model.Revista;
import com.biblioteca.view.RevistaView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class RevistaController {
    private RevistaView view;
    private RevistaDAO revistaDAO;

    public RevistaController(RevistaView view) {
        this.view = view;
        this.revistaDAO = new RevistaDAO();

        cargarRevistas();
        configurarListeners();
    }

    private void cargarRevistas() {
        List<Revista> revistas = revistaDAO.obtenerTodasRevistas();
        view.mostrarRevistas(revistas);
    }

    private void configurarListeners() {
        view.setAgregarListener(new AgregarListener());
        view.setEditarListener(new EditarListener());
        view.setEliminarListener(new EliminarListener());
        view.setActualizarListener(new ActualizarListener());
        view.setBuscarListener(new BuscarListener());
        view.setResetListener(new ResetListener());
    }

    class AgregarListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Revista nuevaRevista = view.mostrarFormularioRevista(null);
            if (nuevaRevista != null) {
                revistaDAO.agregarRevista(nuevaRevista);
                cargarRevistas();
                view.mostrarMensaje("Revista agregada con éxito");
            }
        }
    }

    class EditarListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Revista revistaSeleccionada = view.obtenerRevistaSeleccionada();
            if (revistaSeleccionada != null) {
                Revista revistaEditada = view.mostrarFormularioRevista(revistaSeleccionada);
                if (revistaEditada != null) {
                    revistaDAO.actualizarRevista(revistaEditada);
                    cargarRevistas();
                    view.mostrarMensaje("Revista actualizada con éxito");
                }
            } else {
                view.mostrarMensaje("Por favor, seleccione una revista para editar");
            }
        }
    }

    class EliminarListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Revista revistaSeleccionada = view.obtenerRevistaSeleccionada();
            if (revistaSeleccionada != null) {
                if (view.confirmarEliminacion()) {
                    revistaDAO.eliminarRevista(revistaSeleccionada.getId());
                    cargarRevistas();
                    view.mostrarMensaje("Revista eliminada con éxito");
                }
            } else {
                view.mostrarMensaje("Por favor, seleccione una revista para eliminar");
            }
        }
    }

    class ActualizarListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            cargarRevistas();
            view.mostrarMensaje("Lista de revistas actualizada");
        }
    }

    class BuscarListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String titulo = view.getTextoBuscarTitulo();
            String editor = view.getTextoBuscarEditor();
            String genero = view.getTextoBuscarGenero();

            List<Revista> revistas = revistaDAO.buscarCombinado(titulo, editor, genero);
            view.mostrarRevistas(revistas);

            if (revistas.isEmpty()) {
                view.mostrarMensaje("No se encontraron revistas con los criterios de búsqueda");
            }
        }
    }

    class ResetListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            view.limpiarCamposBusqueda();
            cargarRevistas();
        }
    }
}