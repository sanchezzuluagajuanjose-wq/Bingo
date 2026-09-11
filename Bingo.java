import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.*;

public class Bingo extends JFrame {
    private JTextField txtTotalTablas;
    private JComboBox<String> cmbTablas;
    private JButton btnIniciar, btnSacarBalota;
    private JTable tblBingo, tblTabla;
    private JLabel lblUltimaBalota;
    private Tabla[] tablas;
    private boolean[] bingoAvisado, binguitoAvisado;

    public Bingo() {
        setTitle("Juego de Bingo");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(720, 650);
        setLocationRelativeTo(null);
        crearInterfaz();
    }

    private void crearInterfaz() {
        JPanel superior = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 8));
        superior.add(new JLabel("Total Tablas:"));
        txtTotalTablas = new JTextField("2", 4);
        superior.add(txtTotalTablas);
        btnIniciar = new JButton("Iniciar");
        superior.add(btnIniciar);
        superior.add(new JLabel("Tabla:"));
        cmbTablas = new JComboBox<>();
        cmbTablas.setPreferredSize(new Dimension(100, 25));
        superior.add(cmbTablas);
        btnSacarBalota = new JButton("Sacar Balota");
        btnSacarBalota.setEnabled(false);
        superior.add(btnSacarBalota);
        add(superior, BorderLayout.NORTH);

        tblBingo = crearTabla();
        tblTabla = crearTabla();
        JPanel centro = new JPanel(new GridLayout(1, 2, 10, 0));
        centro.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        JPanel p1 = new JPanel(new BorderLayout());
        p1.setBorder(BorderFactory.createTitledBorder("Balotas sacadas"));
        p1.add(new JScrollPane(tblBingo));
        JPanel p2 = new JPanel(new BorderLayout());
        p2.setBorder(BorderFactory.createTitledBorder("Tabla del jugador"));
        p2.add(new JScrollPane(tblTabla));
        centro.add(p1); centro.add(p2);
        add(centro, BorderLayout.CENTER);

        lblUltimaBalota = new JLabel("Última balota: -", SwingConstants.CENTER);
        lblUltimaBalota.setFont(new Font("Arial", Font.BOLD, 18));
        add(lblUltimaBalota, BorderLayout.SOUTH);

        btnIniciar.addActionListener(e -> iniciarJuego());
        btnSacarBalota.addActionListener(e -> sacarBalota());
        cmbTablas.addActionListener(e -> mostrarTablaSeleccionada());
    }

    private JTable crearTabla() {
        JTable t = new JTable();
        t.setRowHeight(28);
        t.setFont(new Font("Arial", Font.PLAIN, 13));
        t.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
        t.setRowSelectionAllowed(false);
        return t;
    }

    private void iniciarJuego() {
        int total = 2;
        try {
            total = Integer.parseInt(txtTotalTablas.getText());
            if (total < 1 || total > 50) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            total = 2;
            txtTotalTablas.setText("2");
            JOptionPane.showMessageDialog(this, "Número inválido. Se crearán 2 tablas.");
        }
        tablas = new Tabla[total];
        bingoAvisado = new boolean[total];
        binguitoAvisado = new boolean[total];
        cmbTablas.removeAllItems();
        for (int i = 0; i < total; i++) {
            tablas[i] = new Tabla(i);
            cmbTablas.addItem("Tabla " + (i + 1));
        }
        Cantor.iniciar();
        Cantor.mostrarBalotas(tblBingo);
        btnSacarBalota.setEnabled(true);
        lblUltimaBalota.setText("Última balota: -");
        cmbTablas.setSelectedIndex(0);
        mostrarTablaSeleccionada();
    }

    private void mostrarTablaSeleccionada() {
        if (tablas != null && cmbTablas.getSelectedIndex() >= 0)
            tablas[cmbTablas.getSelectedIndex()].mostrar(tblTabla);
    }

    private void sacarBalota() {
        int numero = Cantor.sacarBalota();
        if (numero == 0) {
            btnSacarBalota.setEnabled(false);
            JOptionPane.showMessageDialog(this, "El juego había terminado.");
            return;
        }
        String letra = numero <= 15 ? "B" : numero <= 30 ? "I" : numero <= 45 ? "N" : numero <= 60 ? "G" : "O";
        lblUltimaBalota.setText("Última balota: " + letra + " - " + numero);
        Cantor.mostrarBalotas(tblBingo);
        mostrarTablaSeleccionada();

        for (int i = 0; i < tablas.length; i++) {
            if (!bingoAvisado[i] && tablas[i].verificarBingo()) {
                bingoAvisado[i] = true;
                JOptionPane.showMessageDialog(this, "¡BINGO para el jugador " + (i + 1) + "!");
            } else if (!binguitoAvisado[i] && tablas[i].verificarBinguito()) {
                binguitoAvisado[i] = true;
                JOptionPane.showMessageDialog(this, "¡BINGUITO para el jugador " + (i + 1) + "!");
            }
        }
        if (Cantor.obtenerTotalBalotasSacadas() == 75) {
            btnSacarBalota.setEnabled(false);
            JOptionPane.showMessageDialog(this, "No hay más balotas para sacar.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Bingo().setVisible(true));
    }
}
