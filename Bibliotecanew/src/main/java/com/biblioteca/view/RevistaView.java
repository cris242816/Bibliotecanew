package com.biblioteca.view;

import com.biblioteca.model.Revista;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

public class RevistaView extends JFrame {
    private JTable tablaRevistas;
    private DefaultTableModel modeloTabla;
    private JButton btnAgregar;
    private JButton btnEditar;
    private JButton btnEliminar;
    private JButton btnActualizar;
    private JTextField txtBuscarTitulo;
    private JTextField txtBuscarEditor;
    private JTextField txtBuscarGenero;
    private JButton btnBuscar;
    private JButton btnReset;

    public RevistaView() {
        setTitle("Gestión de Revistas");
        setSize(900, 650);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Modelo de tabla
        modeloTabla = new DefaultTableModel();
        modeloTabla.addColumn("ID");
        modeloTabla.addColumn("Título");
        modeloTabla.addColumn("Editor");
        modeloTabla.addColumn("Género");
        modeloTabla.addColumn("Año");
        modeloTabla.addColumn("Número");
        modeloTabla.addColumn("Páginas");
        modeloTabla.addColumn("Disponible");

        tablaRevistas = new JTable(modeloTabla);
        JScrollPane scrollPane = new JScrollPane(tablaRevistas);

        // Panel de búsqueda
        JPanel panelBusqueda = new JPanel(new GridLayout(4, 2, 5, 5));
        panelBusqueda.setBorder(BorderFactory.createTitledBorder("Búsqueda"));

        txtBuscarTitulo = new JTextField();
        txtBuscarEditor = new JTextField();
        txtBuscarGenero = new JTextField();

        btnBuscar = new JButton("Buscar");
        btnReset = new JButton("Limpiar");

        panelBusqueda.add(new JLabel("Título:"));
        panelBusqueda.add(txtBuscarTitulo);
        panelBusqueda.add(new JLabel("Editor:"));
        panelBusqueda.add(txtBuscarEditor);
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

    public void mostrarRevistas(List<Revista> revistas) {
        modeloTabla.setRowCount(0);
        for (Revista revista : revistas) {
            Object[] fila = {
                    revista.getId(),
                    revista.getTitulo(),
                    revista.getEditor(),
                    revista.getGenero(),
                    revista.getAnioPublicacion(),
                    revista.getNumero(),
                    revista.getPaginas(),
                    revista.isDisponible() ? "Sí" : "No"
            };
            modeloTabla.addRow(fila);
        }
    }

    public Revista obtenerRevistaSeleccionada() {
        int filaSeleccionada = tablaRevistas.getSelectedRow();
        if (filaSeleccionada == -1) {
            return null;
        }

        Revista revista = new Revista();
        revista.setId((int) modeloTabla.getValueAt(filaSeleccionada, 0));
        revista.setTitulo((String) modeloTabla.getValueAt(filaSeleccionada, 1));
        revista.setEditor((String) modeloTabla.getValueAt(filaSeleccionada, 2));
        revista.setGenero((String) modeloTabla.getValueAt(filaSeleccionada, 3));
        revista.setAnioPublicacion((int) modeloTabla.getValueAt(filaSeleccionada, 4));
        revista.setNumero((int) modeloTabla.getValueAt(filaSeleccionada, 5));
        revista.setPaginas((int) modeloTabla.getValueAt(filaSeleccionada, 6));
        revista.setDisponible(modeloTabla.getValueAt(filaSeleccionada, 7).equals("Sí"));

        return revista;
    }

    public String getTextoBuscarTitulo() {
        return txtBuscarTitulo.getText().trim();
    }

    public String getTextoBuscarEditor() {
        return txtBuscarEditor.getText().trim();
    }

    public String getTextoBuscarGenero() {
        return txtBuscarGenero.getText().trim();
    }

    public void limpiarCamposBusqueda() {
        txtBuscarTitulo.setText("");
        txtBuscarEditor.setText("");
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
                "¿Está seguro de que desea eliminar esta revista?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION
        );
        return respuesta == JOptionPane.YES_OPTION;
    }

    public Revista mostrarFormularioRevista(Revista revistaExistente) {
        JTextField txtTitulo = new JTextField();
        JTextField txtEditor = new JTextField();
        JTextField txtGenero = new JTextField();
        JTextField txtAnio = new JTextField();
        JTextField txtNumero = new JTextField();
        JTextField txtPaginas = new JTextField();
        JCheckBox chkDisponible = new JCheckBox("Disponible");

        if (revistaExistente != null) {
            txtTitulo.setText(revistaExistente.getTitulo());
            txtEditor.setText(revistaExistente.getEditor());
            txtGenero.setText(revistaExistente.getGenero());
            txtAnio.setText(String.valueOf(revistaExistente.getAnioPublicacion()));
            txtNumero.setText(String.valueOf(revistaExistente.getNumero()));
            txtPaginas.setText(String.valueOf(revistaExistente.getPaginas()));
            chkDisponible.setSelected(revistaExistente.isDisponible());
        }

        Object[] campos = {
                "Título:", txtTitulo,
                "Editor:", txtEditor,
                "Género:", txtGenero,
                "Año de publicación:", txtAnio,
                "Número:", txtNumero,
                "Páginas:", txtPaginas,
                chkDisponible
        };

        int resultado = JOptionPane.showConfirmDialog(
                this,
                campos,
                revistaExistente == null ? "Agregar Revista" : "Editar Revista",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        if (resultado == JOptionPane.OK_OPTION) {
            Revista revista = revistaExistente != null ? revistaExistente : new Revista();
            revista.setTitulo(txtTitulo.getText());
            revista.setEditor(txtEditor.getText());
            revista.setGenero(txtGenero.getText());
            revista.setAnioPublicacion(Integer.parseInt(txtAnio.getText()));
            revista.setNumero(Integer.parseInt(txtNumero.getText()));
            revista.setPaginas(Integer.parseInt(txtPaginas.getText()));
            revista.setDisponible(chkDisponible.isSelected());
            return revista;
        }

        return null;
    }
}