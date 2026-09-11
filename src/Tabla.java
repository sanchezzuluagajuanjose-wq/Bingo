import java.util.Random;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class Tabla {
    private Random r;
    private int[][] tabla;

    public Tabla(int numero) {
        r = new Random(System.currentTimeMillis() * (numero + 1));
        tabla = new int[5][5];

        for (int c = 0; c < 5; c++) {
            for (int f = 0; f < 5; f++) {
                if (f != 2 || c != 2) {
                    while (tabla[f][c] == 0) {
                        int valor = c * 15 + r.nextInt(15) + 1;
                        if (!verificarNumero(valor, c)) tabla[f][c] = valor;
                    }
                }
            }
        }
    }

    private boolean verificarNumero(int numero, int columna) {
        for (int f = 0; f < 5; f++)
            if (tabla[f][columna] == numero) return true;
        return false;
    }

    public int[][] obtenerTabla() { return tabla; }

    public void mostrar(JTable tbl) {
        String[][] m = new String[5][5];
        for (int f = 0; f < 5; f++) {
            for (int c = 0; c < 5; c++) {
                if (f == 2 && c == 2) m[f][c] = "LIBRE";
                else {
                    m[f][c] = String.valueOf(tabla[f][c]);
                    if (Cantor.verificarSacada(tabla[f][c])) m[f][c] += "*";
                }
            }
        }
        tbl.setModel(new DefaultTableModel(m, Cantor.obtenerEncabezados()));
    }

    public boolean verificarBingo() {
        for (int f = 0; f < 5; f++)
            for (int c = 0; c < 5; c++)
                if ((f != 2 || c != 2) && !Cantor.verificarSacada(tabla[f][c])) return false;
        return true;
    }

    public boolean verificarBinguito() {
        for (int f = 0; f < 5; f++) {
            boolean completa = true;
            for (int c = 0; c < 5; c++)
                if ((f != 2 || c != 2) && !Cantor.verificarSacada(tabla[f][c])) completa = false;
            if (completa) return true;
        }
        for (int c = 0; c < 5; c++) {
            boolean completa = true;
            for (int f = 0; f < 5; f++)
                if ((f != 2 || c != 2) && !Cantor.verificarSacada(tabla[f][c])) completa = false;
            if (completa) return true;
        }
        boolean diagonal = true;
        for (int i = 0; i < 5; i++)
            if (i != 2 && !Cantor.verificarSacada(tabla[i][i])) diagonal = false;
        if (diagonal) return true;
        diagonal = true;
        for (int i = 0; i < 5; i++)
            if (i != 2 && !Cantor.verificarSacada(tabla[i][4 - i])) diagonal = false;
        return diagonal;
    }
}
