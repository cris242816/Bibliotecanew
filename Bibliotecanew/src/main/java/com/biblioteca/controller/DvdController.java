package com.biblioteca.controller;

import com.biblioteca.dao.DvdDAO;
import com.biblioteca.model.Dvd;
import com.biblioteca.view.DvdView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class DvdController {
    private DvdView view;
    private DvdDAO dvdDAO;

    public DvdController(DvdView view) {
        this.view = view;
        this.dvdDAO = new DvdDAO();

        cargarDvds();
        configurarListeners();
    }

    private void cargarDvds() {
        List<Dvd> dvds = dvdDAO.obtenerTodosDvds();
        view.mostrarDvds(dvds);
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
            Dvd nuevoDvd = view.mostrarFormularioDvd(null);
            if (nuevoDvd != null) {
                dvdDAO.agregarDvd(nuevoDvd);
                cargarDvds();
                view.mostrarMensaje("DVD agregado con éxito");
            }
        }
    }

    class EditarListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Dvd dvdSeleccionado = view.obtenerDvdSeleccionado();
            if (dvdSeleccionado != null) {
                Dvd dvdEditado = view.mostrarFormularioDvd(dvdSeleccionado);
                if (dvdEditado != null) {
                    dvdDAO.actualizarDvd(dvdEditado);
                    cargarDvds();
                    view.mostrarMensaje("DVD actualizado con éxito");
                }
            } else {
                view.mostrarMensaje("Por favor, seleccione un DVD para editar");
            }
        }
    }

    class EliminarListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Dvd dvdSeleccionado = view.obtenerDvdSeleccionado();
            if (dvdSeleccionado != null) {
                if (view.confirmarEliminacion()) {
                    dvdDAO.eliminarDvd(dvdSeleccionado.getId());
                    cargarDvds();
                    view.mostrarMensaje("DVD eliminado con éxito");
                }
            } else {
                view.mostrarMensaje("Por favor, seleccione un DVD para eliminar");
            }
        }
    }

    class ActualizarListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            cargarDvds();
            view.mostrarMensaje("Lista de DVDs actualizada");
        }
    }

    class BuscarListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String titulo = view.getTextoBuscarTitulo();
            String director = view.getTextoBuscarDirector();
            String genero = view.getTextoBuscarGenero();

            List<Dvd> dvds = dvdDAO.buscarCombinado(titulo, director, genero);
            view.mostrarDvds(dvds);

            if (dvds.isEmpty()) {
                view.mostrarMensaje("No se encontraron DVDs con los criterios de búsqueda");
            }
        }
    }

    class ResetListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            view.limpiarCamposBusqueda();
            cargarDvds();
        }
    }
}