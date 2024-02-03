import java.util.Scanner;


import static com.diogonunes.jcolor.Ansi.colorize;
import static com.diogonunes.jcolor.Attribute.*;


public class Juego {
    private static final String JUGADOR_UNO = "J1";
    private static final String JUGADOR_DOS = "J2";
    private static final String CASILLA_LIBRE = colorize(" L ",GREEN_TEXT());
    private static final String NEGRAS = colorize(" N ",BLACK_BACK());
    private static final String BLANCAS =colorize(" B ",WHITE_BACK());
    private static final char D_ARRIBAIZQUIERDA = 'Q';
    private static final char D_ARRIBADERECHA = 'E';
    private static final char D_ABAJOIZQUIERDA = 'Z';
    private static final char D_ABAJODERECHA = 'C';
    private static final char ABANDONAR = 'X';
    private static String[][] tableroDamas;


    public static void main(String[] args) {
        inicializarJuego();
        mostrarMenu();
        jugar();
    }


    private static void inicializarJuego() {
        tableroDamas = inicializarTablero(8, 8);
        colocarFichasEnTablero();
    }


    private static void colocarFichasEnTablero() {
        int numFichas = 12;


        for (int fila = 0; fila < tableroDamas.length; fila++) {
            for (int columna = 0; columna < tableroDamas[0].length; columna++) {
                if ((fila + columna) % 2 == 0) {
                    tableroDamas[fila][columna] = CASILLA_LIBRE;
                } else {
                    tableroDamas[fila][columna] = NEGRAS;
                }
            }
        }


        for (int fila = 3; fila <= 4; fila++) {
            for (int columna = 0; columna < tableroDamas[0].length; columna++) {
                tableroDamas[fila][columna] = CASILLA_LIBRE;
            }
        }


        for (int fila = 0; fila < tableroDamas.length; fila++) {
            for (int columna = 0; columna < tableroDamas[0].length; columna++) {
                if ((fila + columna) % 2 == 0 || fila >= 3 && fila <= 4) {
                    continue;
                }


                if (fila < tableroDamas.length / 2 && numFichas > 0) {
                    tableroDamas[fila][columna] = BLANCAS;
                    numFichas--;
                }
            }
        }
    }


    private static String[][] inicializarTablero(int filas, int columnas) {
        String[][] tablero = new String[filas][columnas];
        for (int fila = 0; fila < filas; fila++) {
            for (int columna = 0; columna < columnas; columna++) {
                tablero[fila][columna] = CASILLA_LIBRE;
            }
        }
        return tablero;
    }


    private static void mostrarMenu() {
        System.out.println("_________________________________________________________________________________________________");
        System.out.println(colorize("|         TRABAJO REALIZADO POR:    JAVIER MARTINEZ    ANDREI NEDEA    MIRIAM LIZALDE            |", GREEN_TEXT()));
        System.out.println("-------------------------------------------------------------------------------------------------");
        System.out.println("_________________________________________________________________________________________________");
        System.out.println(colorize("|                                     ¡Bienvenido al juego!                                       |", GREEN_BACK()));
        System.out.println("-------------------------------------------------------------------------------------------------");
        System.out.println("|                       Reglas:                                                                  |");
        System.out.println("| -El JUGADOR UNO (J1) juega con las fichas BLANCAS                                              |");
        System.out.println("| -El JUGADOR DOS (J2) juega con las fichas NEGRAS                                               |");
        System.out.println("| -Si el jugador quiere abandonar la partida debe escribir la letra: X                           |");
        System.out.println("| -Los jugadores deben seguir las indicaciones del juego, eligiendo una fila y columna.          |");
        System.out.println("| -Los jugadores deben introducir la dirección: Q, E, Z, C                                       |");
        System.out.println("| -Q: ARRIBA IZQUIERDA, E: ARRIBA DERECHA, Z: ABAJO IZQUIERDA, C: ABAJO DERECHA                  |");
        System.out.println("| -El objetivo es eliminar la máxima cantidad de fichas del oponente.                            |");
        System.out.println("| -El juego continúa hasta que un jugador pierde todas sus fichas o decide abandonar la partida. |");
        System.out.println("--------------------------------------------------------------------------------------------------");
    }


    private static boolean jugar() {
        Scanner scanner = new Scanner(System.in);


        while (true) {
            System.out.println(colorize("\nTurno del Jugador " + JUGADOR_UNO + ":", GREEN_TEXT()));
            System.out.println("--------------------------------");
            mostrarTablero();
            if (!moverJugador(JUGADOR_UNO)) {
                System.out.println("¡El jugador " + JUGADOR_UNO + " ha perdido la partida!");
                break;
            }


            System.out.println(colorize("\nTurno del Jugador " + JUGADOR_DOS + ":", GREEN_TEXT()));
            System.out.println("------------------");
            mostrarTablero();
            if (!moverJugador(JUGADOR_DOS)) {
                System.out.println("¡El jugador " + JUGADOR_DOS + " ha perdido la partida!");
                break;
            }
        }
        return false;
    }


    private static boolean moverJugador(String jugador) {
        boolean movimientoValido = false;
        Scanner scanner = new Scanner(System.in);
        while (!movimientoValido) {

            System.out.print("\nElige la fila de la ficha que deseas mover: ");
            int filaOrigen = scanner.nextInt();
            System.out.print("\nElige la columna de la ficha que deseas mover: ");
            int columnaOrigen = scanner.nextInt();

            System.out.print("\nIntroduce la dirección (Q, E, Z, C.  X para abandonar): ");
            char direccion = scanner.next().toUpperCase().charAt(0);

            int filaDestino = calcularFilaDestino(filaOrigen, direccion);
            int columnaDestino = calcularColumnaDestino(columnaOrigen, direccion);


            if (esMovimientoValido(filaOrigen, columnaOrigen, filaDestino, columnaDestino, jugador)) {
                realizarMovimiento(filaOrigen, columnaOrigen, filaDestino, columnaDestino);
                return true;
            } else {
                System.out.println("Movimiento no válido. Inténtalo de nuevo.");
            }
        }
        return false;
    }

    private static int calcularFilaDestino(int filaOrigen, char direccion) {
        switch (direccion) {
            case D_ARRIBAIZQUIERDA:
            case D_ARRIBADERECHA:
                return filaOrigen - 1;
            case D_ABAJOIZQUIERDA:
            case D_ABAJODERECHA:
                return filaOrigen + 1;
            case ABANDONAR:
                System.out.println("Juego terminado");
                System.exit(1);
            default:
                return filaOrigen;
        }

    }

    private static int calcularColumnaDestino(int columnaOrigen, char direccion) {
        switch (direccion) {
            case D_ARRIBAIZQUIERDA:
            case D_ABAJOIZQUIERDA:
                return columnaOrigen - 1;
            case D_ARRIBADERECHA:
            case D_ABAJODERECHA:
                return columnaOrigen + 1;
            default:
                return columnaOrigen;
        }
    }

    private static boolean esMovimientoValido(int filaOrigen, int columnaOrigen, int filaDestino, int columnaDestino, String jugador) {
        if (filaOrigen < 0 || filaOrigen >= tableroDamas.length || columnaOrigen < 0 || columnaOrigen >= tableroDamas[0].length ||
                filaDestino < 0 || filaDestino >= tableroDamas.length || columnaDestino < 0 || columnaDestino >= tableroDamas[0].length) {
            return false;
        }
        return true;
    }
    private static String fichaContraria(String jugador) {
        return (jugador.equals(JUGADOR_UNO)) ? JUGADOR_DOS : JUGADOR_UNO;
    }


    private static void realizarMovimiento(int filaOrigen, int columnaOrigen, int filaDestino, int columnaDestino) {
        if (tableroDamas[filaDestino][columnaDestino].equals(fichaContraria(tableroDamas[filaOrigen][columnaOrigen]))) {
            tableroDamas[filaDestino][columnaDestino] = CASILLA_LIBRE;
            tableroDamas[filaOrigen][columnaOrigen] = BLANCAS;


        } else {
            tableroDamas[filaDestino][columnaDestino] = tableroDamas[filaOrigen][columnaOrigen];
            tableroDamas[filaOrigen][columnaOrigen] = CASILLA_LIBRE;
        }
    }




    private static void mostrarTablero() {
        for (int fila = 0; fila < tableroDamas.length; fila++) {
            for (int columna = 0; columna < tableroDamas[fila].length; columna++) {
                System.out.print(tableroDamas[fila][columna] + " ");
            }
            System.out.println();
        }
    }
}
