package com.biblioteca.view;

import com.biblioteca.model.Libro;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

public class LibroView extends JFrame {
    private JTable tablaLibros;
    private DefaultTableModel modeloTabla;
    private JButton btnAgregar;
    private JButton btnEditar;
    private JButton btnEliminar;
    private JButton btnActualizar;
    private JTextField txtBuscarTitulo;
    private JTextField txtBuscarAutor;
    private JTextField txtBuscarGenero;
    private JButton btnBuscar;
    private JButton btnReset;

    public LibroView() {
        setTitle("Gestión de Libros");
        setSize(900, 650);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Modelo de tabla
        modeloTabla = new DefaultTableModel();
        modeloTabla.addColumn("ID");
        modeloTabla.addColumn("Título");
        modeloTabla.addColumn("Autor");
        modeloTabla.addColumn("Género");
        modeloTabla.addColumn("Año");
        modeloTabla.addColumn("Páginas");
        modeloTabla.addColumn("Disponible");

        tablaLibros = new JTable(modeloTabla);
        JScrollPane scrollPane = new JScrollPane(tablaLibros);

        // Panel de búsqueda
        JPanel panelBusqueda = new JPanel(new GridLayout(4, 2, 5, 5));
        panelBusqueda.setBorder(BorderFactory.createTitledBorder("Búsqueda"));

        txtBuscarTitulo = new JTextField();
        txtBuscarAutor = new JTextField();
        txtBuscarGenero = new JTextField();

        btnBuscar = new JButton("Buscar");
        btnReset = new JButton("Limpiar");

        panelBusqueda.add(new JLabel("Título:"));
        panelBusqueda.add(txtBuscarTitulo);
        panelBusqueda.add(new JLabel("Autor:"));
        panelBusqueda.add(txtBuscarAutor);
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

    public void mostrarLibros(List<Libro> libros) {
        modeloTabla.setRowCount(0);
        for (Libro libro : libros) {
            Object[] fila = {
                    libro.getId(),
                    libro.getTitulo(),
                    libro.getAutor(),
                    libro.getGenero(),
                    libro.getAnioPublicacion(),
                    libro.getPaginas(),
                    libro.isDisponible() ? "Sí" : "No"
            };
            modeloTabla.addRow(fila);
        }
    }

    public Libro obtenerLibroSeleccionado() {
        int filaSeleccionada = tablaLibros.getSelectedRow();
        if (filaSeleccionada == -1) {
            return null;
        }

        Libro libro = new Libro();
        libro.setId((int) modeloTabla.getValueAt(filaSeleccionada, 0));
        libro.setTitulo((String) modeloTabla.getValueAt(filaSeleccionada, 1));
        libro.setAutor((String) modeloTabla.getValueAt(filaSeleccionada, 2));
        libro.setGenero((String) modeloTabla.getValueAt(filaSeleccionada, 3));
        libro.setAnioPublicacion((int) modeloTabla.getValueAt(filaSeleccionada, 4));
        libro.setPaginas((int) modeloTabla.getValueAt(filaSeleccionada, 5));
        libro.setDisponible(modeloTabla.getValueAt(filaSeleccionada, 6).equals("Sí"));

        return libro;
    }

    public String getTextoBuscarTitulo() {
        return txtBuscarTitulo.getText().trim();
    }

    public String getTextoBuscarAutor() {
        return txtBuscarAutor.getText().trim();
    }

    public String getTextoBuscarGenero() {
        return txtBuscarGenero.getText().trim();
    }

    public void limpiarCamposBusqueda() {
        txtBuscarTitulo.setText("");
        txtBuscarAutor.setText("");
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
                "¿Está seguro de que desea eliminar este libro?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION
        );
        return respuesta == JOptionPane.YES_OPTION;
    }

    public Libro mostrarFormularioLibro(Libro libroExistente) {
        JTextField txtTitulo = new JTextField();
        JTextField txtAutor = new JTextField();
        JTextField txtGenero = new JTextField();
        JTextField txtAnio = new JTextField();
        JTextField txtPaginas = new JTextField();
        JCheckBox chkDisponible = new JCheckBox("Disponible");

        if (libroExistente != null) {
            txtTitulo.setText(libroExistente.getTitulo());
            txtAutor.setText(libroExistente.getAutor());
            txtGenero.setText(libroExistente.getGenero());
            txtAnio.setText(String.valueOf(libroExistente.getAnioPublicacion()));
            txtPaginas.setText(String.valueOf(libroExistente.getPaginas()));
            chkDisponible.setSelected(libroExistente.isDisponible());
        }

        Object[] campos = {
                "Título:", txtTitulo,
                "Autor:", txtAutor,
                "Género:", txtGenero,
                "Año de publicación:", txtAnio,
                "Páginas:", txtPaginas,
                chkDisponible
        };

        int resultado = JOptionPane.showConfirmDialog(
                this,
                campos,
                libroExistente == null ? "Agregar Libro" : "Editar Libro",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        if (resultado == JOptionPane.OK_OPTION) {
            Libro libro = libroExistente != null ? libroExistente : new Libro();
            libro.setTitulo(txtTitulo.getText());
            libro.setAutor(txtAutor.getText());
            libro.setGenero(txtGenero.getText());
            libro.setAnioPublicacion(Integer.parseInt(txtAnio.getText()));
            libro.setPaginas(Integer.parseInt(txtPaginas.getText()));
            libro.setDisponible(chkDisponible.isSelected());
            return libro;
        }

        return null;
    }
}