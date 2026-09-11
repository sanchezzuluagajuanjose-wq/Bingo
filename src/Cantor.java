import java.util.Random;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public abstract class Cantor {
    private static final String[] encabezados = {"B", "I", "N", "G", "O"};
    private static final int[][] balotas = new int[15][5];
    private static int totalBalotasSacadas;
    private static final Random r = new Random();

    public static void iniciar() {
        for (int f = 0; f < 15; f++) {
            for (int c = 0; c < 5; c++) {
                balotas[f][c] = 0;
            }
        }
        totalBalotasSacadas = 0;
    }

    public static int sacarBalota() {
        if (totalBalotasSacadas >= 75) return 0;

        int numero;
        do {
            numero = r.nextInt(75) + 1;
        } while (verificarSacada(numero));

        int f = (numero - 1) % 15;
        int c = (numero - 1) / 15;
        balotas[f][c] = numero;
        totalBalotasSacadas++;
        return numero;
    }

    public static void mostrarBalotas(JTable tbl) {
        String[][] m = new String[15][5];
        for (int f = 0; f < 15; f++) {
            for (int c = 0; c < 5; c++) {
                m[f][c] = balotas[f][c] == 0 ? "" : String.valueOf(balotas[f][c]);
            }
        }
        tbl.setModel(new DefaultTableModel(m, encabezados));
    }

    public static boolean verificarSacada(int numero) {
        if (numero < 1 || numero > 75) return false;
        int f = (numero - 1) % 15;
        int c = (numero - 1) / 15;
        return balotas[f][c] != 0;
    }

    public static String[] obtenerEncabezados() {
        return encabezados;
    }

    public static int[][] obtenerBalotas() {
        return balotas;
    }

    public static int obtenerTotalBalotasSacadas() {
        return totalBalotasSacadas;
    }
}
