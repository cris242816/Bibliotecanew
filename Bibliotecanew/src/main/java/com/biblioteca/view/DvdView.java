package com.biblioteca.view;

import com.biblioteca.model.Dvd;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

public class DvdView extends JFrame {
    private JTable tablaDvds;
    private DefaultTableModel modeloTabla;
    private JButton btnAgregar;
    private JButton btnEditar;
    private JButton btnEliminar;
    private JButton btnActualizar;
    private JTextField txtBuscarTitulo;
    private JTextField txtBuscarDirector;
    private JTextField txtBuscarGenero;
    private JButton btnBuscar;
    private JButton btnReset;

    public DvdView() {
        setTitle("Gestión de DVDs");
        setSize(900, 650);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Modelo de tabla
        modeloTabla = new DefaultTableModel();
        modeloTabla.addColumn("ID");
        modeloTabla.addColumn("Título");
        modeloTabla.addColumn("Director");
        modeloTabla.addColumn("Género");
        modeloTabla.addColumn("Año");
        modeloTabla.addColumn("Duración (min)");
        modeloTabla.addColumn("Disponible");

        tablaDvds = new JTable(modeloTabla);
        JScrollPane scrollPane = new JScrollPane(tablaDvds);

        // Panel de búsqueda
        JPanel panelBusqueda = new JPanel(new GridLayout(4, 2, 5, 5));
        panelBusqueda.setBorder(BorderFactory.createTitledBorder("Búsqueda"));

        txtBuscarTitulo = new JTextField();
        txtBuscarDirector = new JTextField();
        txtBuscarGenero = new JTextField();

        btnBuscar = new JButton("Buscar");
        btnReset = new JButton("Limpiar");

        panelBusqueda.add(new JLabel("Título:"));
        panelBusqueda.add(txtBuscarTitulo);
        panelBusqueda.add(new JLabel("Director:"));
        panelBusqueda.add(txtBuscarDirector);
        panelBusqueda.add(new JLabel("Género:"));
        panelBusqueda.add(txtBuscarGenero);
        panelBusqueda.add(btnBuscar);
        panelBusqueda.add(btnReset);

        // Botones de CRUD
        JPanel panelBotones = new JPanel();
        btnAgregar = new JButton("Agregar");
        btnEditar = new JButton("Editar");
        btnEliminar = new JButton("Eliminar");
        btnActualizar = new JButton("Actualizar");

        panelBotones.add(btnAgregar);
        panelBotones.add(btnEditar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnActualizar);

        // Diseño principal
        setLayout(new BorderLayout());
        add(panelBusqueda, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);
    }

    public void mostrar() {
        setVisible(true);
    }

    public void mostrarDvds(List<Dvd> dvds) {
        modeloTabla.setRowCount(0);
        for (Dvd dvd : dvds) {
            Object[] fila = {
                    dvd.getId(),
                    dvd.getTitulo(),
                    dvd.getDirector(),
                    dvd.getGenero(),
                    dvd.getAnioPublicacion(),
                    dvd.getDuracionMinutos(),
                    dvd.isDisponible() ? "Sí" : "No"
            };
            modeloTabla.addRow(fila);
        }
    }

    public Dvd obtenerDvdSeleccionado() {
        int filaSeleccionada = tablaDvds.getSelectedRow();
        if (filaSeleccionada == -1) {
            return null;
        }

        Dvd dvd = new Dvd();
        dvd.setId((int) modeloTabla.getValueAt(filaSeleccionada, 0));
        dvd.setTitulo((String) modeloTabla.getValueAt(filaSeleccionada, 1));
        dvd.setDirector((String) modeloTabla.getValueAt(filaSeleccionada, 2));
        dvd.setGenero((String) modeloTabla.getValueAt(filaSeleccionada, 3));
        dvd.setAnioPublicacion((int) modeloTabla.getValueAt(filaSeleccionada, 4));
        dvd.setDuracionMinutos((int) modeloTabla.getValueAt(filaSeleccionada, 5));
        dvd.setDisponible(modeloTabla.getValueAt(filaSeleccionada, 6).equals("Sí"));

        return dvd;
    }

    public String getTextoBuscarTitulo() {
        return txtBuscarTitulo.getText().trim();
    }

    public String getTextoBuscarDirector() {
        return txtBuscarDirector.getText().trim();
    }

    public String getTextoBuscarGenero() {
        return txtBuscarGenero.getText().trim();
    }

    public void limpiarCamposBusqueda() {
        txtBuscarTitulo.setText("");
        txtBuscarDirector.setText("");
        txtBuscarGenero.setText("");
    }

    public void setAgregarListener(ActionListener listener) {
        btnAgregar.addActionListener(listener);
    }

    public void setEditarListener(ActionListener listener) {
        btnEditar.addActionListener(listener);
    }

    public void setEliminarListener(ActionListener listener) {
        btnEliminar.addActionListener(listener);
    }

    public void setActualizarListener(ActionListener listener) {
        btnActualizar.addActionListener(listener);
    }

    public void setBuscarListener(ActionListener listener) {
        btnBuscar.addActionListener(listener);
    }

    public void setResetListener(ActionListener listener) {
        btnReset.addActionListener(listener);
    }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }

    public boolean confirmarEliminacion() {
        int respuesta = JOptionPane.showConfirmDialog(
                this,
                "¿Está seguro de que desea eliminar este DVD?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION
        );
        return respuesta == JOptionPane.YES_OPTION;
    }

    public Dvd mostrarFormularioDvd(Dvd dvdExistente) {
        JTextField txtTitulo = new JTextField();
        JTextField txtDirector = new JTextField();
        JTextField txtGenero = new JTextField();
        JTextField txtAnio = new JTextField();
        JTextField txtDuracion = new JTextField();
        JCheckBox chkDisponible = new JCheckBox("Disponible");

        if (dvdExistente != null) {
            txtTitulo.setText(dvdExistente.getTitulo());
            txtDirector.setText(dvdExistente.getDirector());
            txtGenero.setText(dvdExistente.getGenero());
            txtAnio.setText(String.valueOf(dvdExistente.getAnioPublicacion()));
            txtDuracion.setText(String.valueOf(dvdExistente.getDuracionMinutos()));
            chkDisponible.setSelected(dvdExistente.isDisponible());
        }

        Object[] campos = {
                "Título:", txtTitulo,
                "Director:", txtDirector,
                "Género:", txtGenero,
                "Año de publicación:", txtAnio,
                "Duración (minutos):", txtDuracion,
                chkDisponible
        };

        int resultado = JOptionPane.showConfirmDialog(
                this,
                campos,
                dvdExistente == null ? "Agregar DVD" : "Editar DVD",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        if (resultado == JOptionPane.OK_OPTION) {
            Dvd dvd = dvdExistente != null ? dvdExistente : new Dvd();
            dvd.setTitulo(txtTitulo.getText());
            dvd.setDirector(txtDirector.getText());
            dvd.setGenero(txtGenero.getText());
            dvd.setAnioPublicacion(Integer.parseInt(txtAnio.getText()));
            dvd.setDuracionMinutos(Integer.parseInt(txtDuracion.getText()));
            dvd.setDisponible(chkDisponible.isSelected());
            return dvd;
        }

        return null;
    }
}