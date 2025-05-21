package com.biblioteca.controller;

import com.biblioteca.dao.LibroDAO;
import com.biblioteca.model.Libro;
import com.biblioteca.view.LibroView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class LibroController {
    private LibroView view;
    private LibroDAO libroDAO;

    public LibroController(LibroView view) {
        this.view = view;
        this.libroDAO = new LibroDAO();

        cargarLibros();
        configurarListeners();
    }

    private void cargarLibros() {
        List<Libro> libros = libroDAO.obtenerTodosLibros();
        view.mostrarLibros(libros);
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
            Libro nuevoLibro = view.mostrarFormularioLibro(null);
            if (nuevoLibro != null) {
                libroDAO.agregarLibro(nuevoLibro);
                cargarLibros();
                view.mostrarMensaje("Libro agregado con éxito");
            }
        }
    }

    class EditarListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Libro libroSeleccionado = view.obtenerLibroSeleccionado();
            if (libroSeleccionado != null) {
                Libro libroEditado = view.mostrarFormularioLibro(libroSeleccionado);
                if (libroEditado != null) {
                    libroDAO.actualizarLibro(libroEditado);
                    cargarLibros();
                    view.mostrarMensaje("Libro actualizado con éxito");
                }
            } else {
                view.mostrarMensaje("Por favor, seleccione un libro para editar");
            }
        }
    }

    class EliminarListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Libro libroSeleccionado = view.obtenerLibroSeleccionado();
            if (libroSeleccionado != null) {
                if (view.confirmarEliminacion()) {
                    libroDAO.eliminarLibro(libroSeleccionado.getId());
                    cargarLibros();
                    view.mostrarMensaje("Libro eliminado con éxito");
                }
            } else {
                view.mostrarMensaje("Por favor, seleccione un libro para eliminar");
            }
        }
    }

    class ActualizarListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            cargarLibros();
            view.mostrarMensaje("Lista de libros actualizada");
        }
    }

    class BuscarListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String titulo = view.getTextoBuscarTitulo();
            String autor = view.getTextoBuscarAutor();
            String genero = view.getTextoBuscarGenero();

            List<Libro> libros = libroDAO.buscarCombinado(titulo, autor, genero);
            view.mostrarLibros(libros);

            if (libros.isEmpty()) {
                view.mostrarMensaje("No se encontraron libros con los criterios de búsqueda");
            }
        }
    }

    class ResetListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            view.limpiarCamposBusqueda();
            cargarLibros();
        }
    }
}