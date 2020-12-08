
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class CentroComercial extends JFrame {

    JPanel panelArriba, panelCentro, panelAbajo;

    JTable table;
    DefaultTableModel dtm;
    JScrollPane scrollTable;

    JTextField idTF;
    JTextField tipoDocTF;
    JTextField identificacionTF;
    JTextField nombresTF;
    JTextField residenteTF;
    JTextField motivoTF;
    JTextField fechaTF;

    JLabel idL;
    JLabel tipoDocL;
    JLabel identificacionL;
    JLabel nombresL;
    JLabel residenteL;
    JLabel motivoL;
    JLabel fechaL;

    JButton addButton;
    JButton saveButton;
    JButton removeButton;
    JButton cleanButton;

    String columnas[], datos[][];
    Calendario calendario;

    public CentroComercial() {
        super("Centro comercial");
        this.setLayout(new GridLayout(3, 1));

        initComponents();
        addComponents();
        lauchFrame();
    }

    public final void initComponents() {
        columnas = new String[]{"Id", "Tipo documento", "Identificacion", "Nombres", "Visita a", "Motivo", "Fecha"};
        datos = new String[][]{};

        calendario = new Calendario();

        panelArriba = new JPanel();
        panelCentro = new JPanel(new GridLayout(7, 2));
        panelAbajo = new JPanel(new GridLayout(1, 5));

        idTF = new JTextField();
        idTF.enable(false);
        tipoDocTF = new JTextField();
        identificacionTF = new JTextField();
        nombresTF = new JTextField();
        residenteTF = new JTextField();
        motivoTF = new JTextField();
        fechaTF = new JTextField();
        fechaTF.enable(false);

        idL = new JLabel("ID:");
        tipoDocL = new JLabel("Tipo de documento:");
        identificacionL = new JLabel("Identificación:");
        nombresL = new JLabel("Nombres:");
        residenteL = new JLabel("Visita a:");
        motivoL = new JLabel("Motivo:");
        fechaL = new JLabel("Fecha:");

        addButton = new JButton("Agregar");
        removeButton = new JButton("Eliminar");
        saveButton = new JButton("Guardar");
        cleanButton = new JButton("Limpiar");

        //Add table to controls
        dtm = new DefaultTableModel(columnas, 0);
        table = new JTable(dtm);
        scrollTable = new JScrollPane(table);

        try {
            ResultSet rs = visitante.getvisitante();
            ResultSetMetaData metaDatos = rs.getMetaData();
            int cols = metaDatos.getColumnCount();

            Object[] etiquetas = new Object[cols];
            for (int i = 0; i < cols; i++) {
                etiquetas[i] = metaDatos.getColumnLabel(i + 1);
            }

            dtm.setColumnIdentifiers(etiquetas);
            table.setAutoCreateRowSorter(true);
            table.setShowGrid(true);
            table.setPreferredScrollableViewportSize(new Dimension(580, 100));
        
            while (rs.next()) {
                Object[] fila = new Object[cols];
                for (int i = 0; i < cols; i++) {
                    fila[i] = rs.getObject(i + 1);
                }
                dtm.addRow(fila);
            }

        } catch (SQLException e) {
            System.err.println(e.getLocalizedMessage());
        }

        table.addKeyListener(new KeyAdapter() {
            public void keyAdapter(KeyEvent e) {
                int renglon = table.getSelectedRow();
                mostrarRenglonDeTabla(renglon);
            }

            @Override
            public void keyPressed(KeyEvent e) {
                int renglon = table.getSelectedRow();
                mostrarRenglonDeTabla(renglon);
            }

            public void keyRelased(KeyEvent e) {
                int renglon = table.getSelectedRow();
                mostrarRenglonDeTabla(renglon);
            }
        });

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int renglon = table.getSelectedRow();
                mostrarRenglonDeTabla(renglon);
            }
        });

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addRow();
            }
        });

        //Process the remove button press
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeRow();
            }
        });

        //Process the save button press
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editRow();
            }
        });

        //Process the save button press
        cleanButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limpiarCampos();
            }
        });
    }

    public final void addComponents() {
        panelArriba.add(scrollTable, BorderLayout.NORTH);

        panelCentro.add(idL);
        panelCentro.add(idTF);
        panelCentro.add(tipoDocL);
        panelCentro.add(tipoDocTF);
        panelCentro.add(identificacionL);
        panelCentro.add(identificacionTF);
        panelCentro.add(nombresL);
        panelCentro.add(nombresTF);
        panelCentro.add(residenteL);
        panelCentro.add(residenteTF);
        panelCentro.add(motivoL);
        panelCentro.add(motivoTF);
        panelCentro.add(fechaL);
        panelCentro.add(fechaTF);

        panelAbajo.add(addButton);
        panelAbajo.add(removeButton);
        panelAbajo.add(saveButton);
        panelAbajo.add(cleanButton);

        add(panelArriba, BorderLayout.NORTH);
        add(panelCentro, BorderLayout.CENTER);
        add(panelAbajo, BorderLayout.SOUTH);
    }

    public final void lauchFrame() {

        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(600, 400);
        this.setResizable(true);
        this.setLocationRelativeTo(null);
    }

    public void mostrarRenglonDeTabla(int renglon) {
        Object value = table.getValueAt(renglon, 1);
        if (value != null) {
            idTF.setText(table.getValueAt(renglon, 0).toString());
            tipoDocTF.setText(table.getValueAt(renglon, 1).toString());
            identificacionTF.setText(table.getValueAt(renglon, 2).toString());
            nombresTF.setText(table.getValueAt(renglon, 3).toString());
            fechaTF.setText(table.getValueAt(renglon, 4).toString());
            residenteTF.setText(table.getValueAt(renglon, 5).toString());
            motivoTF.setText(table.getValueAt(renglon, 6).toString());
        } else {
            System.err.println("Favor de seleccionar un renglón válido");
        }
    }

    public void agregarEnTabla(int temp) {
        Object renglon[] = {String.valueOf(temp),
            tipoDocTF.getText(),
            identificacionTF.getText(),
            nombresTF.getText(),
            calendario.getFecha(),
            residenteTF.getText(),
            motivoTF.getText()
        };

        dtm.addRow(renglon);
    }

    public void borrarEnTabla(int renglon) {
        dtm.removeRow(renglon);
    }

    public void actualizarTabla(Integer id, String tipoDoc, String identificacion, String nombres,
            String residente, String motivo, String fecha) {
        int renglones = dtm.getRowCount();
        for (int i = 0; i < renglones; i++) {

            if (dtm.getValueAt(i, 0).equals(id)) {
                table.setValueAt(tipoDoc, i, 1);
                table.setValueAt(identificacion, i, 2);
                table.setValueAt(nombres, i, 3);
                table.setValueAt(fecha, i, 4);
                table.setValueAt(residente, i, 5);
                table.setValueAt(motivo, i, 6);

                break;
            }
        }
    }

    public void limpiarCampos() {
        idTF.setText("");
        tipoDocTF.setText("");
        identificacionTF.setText("");
        nombresTF.setText("");
        residenteTF.setText("");
        motivoTF.setText("");
        fechaTF.setText("");
    }

    private void addRow() {
        try {
            visitante p = new visitante();
            int newID = p.obtenerID() + 1;

            p.setID(newID);
            p.setTipoDocumento(tipoDocTF.getText());
            p.setIdentificacion(identificacionTF.getText());
            p.setNombres(nombresTF.getText());
            p.setFecha(calendario.getFecha());
            p.setResidente(residenteTF.getText());
            p.setMotivo(motivoTF.getText());

            agregarEnTabla(newID);
            p.agregar();
            limpiarCampos();

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error", "Datos incorrectos", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void editRow() {
        int id = Integer.parseInt(idTF.getText());

        visitante p = new visitante();
        p.setTipoDocumento(tipoDocTF.getText());
        p.setIdentificacion(identificacionTF.getText());
        p.setNombres(nombresTF.getText());
        p.setFecha(calendario.getFecha());
        p.setResidente(residenteTF.getText());
        p.setMotivo(motivoTF.getText());

        p.editar(id);
        actualizarTabla(id, tipoDocTF.getText(), identificacionTF.getText(), nombresTF.getText(),
                calendario.getFecha(), residenteTF.getText(), motivoTF.getText());
        limpiarCampos();
    }

    private void removeRow() {
        int selectedRow = table.getSelectedRow();
        
        if (selectedRow >= 0) {
            visitante p = new visitante();
            p.eliminar(Integer.valueOf(idTF.getText()));
            borrarEnTabla(selectedRow);
            limpiarCampos();

            JOptionPane.showMessageDialog(null, "Registro eliminado exitosamente", "Eliminar", JOptionPane.INFORMATION_MESSAGE);
        } else {
            System.out.println("No se selecciono un registro para eliminar");
        }
    }

    public static void main(String[] args) {
        CentroComercial cc = new CentroComercial();
    }
}
