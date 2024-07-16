package prop.teclado.domain.classes;

import java.util.List;
import java.util.PriorityQueue;
import java.util.ArrayList;
import java.util.Comparator;

//  Clase que maneja el algoritmo principal para generar un teclado optimo.
//  Autor: Tahir Muhammad Aziz


    // ------------------------------------------- QAP ------------------------------------------ //
    //                                                                                            //
    // Sean:                                                                                      //
    //                                                                                            //
    // D_XiXj como la distancia entre dos teclas del teclado, Xi y Xj.                            //
    // F_i_j como la frecuencia de aparicion del simbolo i junto al simbolo j en el lenguaje.     //
    //                                                                                            //
    // Buscamos que:                                                                              //
    // el sumatorio de D_XiXj * F_ij sea minimo para todas las parejas de simbolos i y j.         //
    //                                                                                            //
    // ------------------------------------------------------------------------------------------ //


public class AlgoritmoDisposicion {

    // ----------------------------------------- ATRIBUTOS -----------------------------------------

    private Teclado teclado;
    private List<Character> simbolos;

    final private int NUM_OBJETOS;      // numero de instalaciones y ubicaciones

    private int[][] matrizDistancias;   // matriz de distancias entre teclas D_XiXj
    private int[][] matrizFrecuencias;  // matriz de frecuencias de aparicion de simbolos F_ij

    private int mejorCota;              // guarda la mejor cota encontrada hasta el momento, si ya ha termiando el algoritmo, es la mejor solucion
    private int[] mejorSolucion;        // guarda la jesima ubicacion que le corresponde al iesimo simbolo, es decir mejorSolucion[iesimoSimbolo] = jesimaUbicacion
    private int[] podas;                // guarda el numero de podas que se han hecho en cada nivel del arbol de soluciones

    public enum TipoAlgoritmo {
        QAP
    }

    // --------------------------------------- CONSTRUCTORAS ---------------------------------------

    public AlgoritmoDisposicion(Teclado teclado, TipoAlgoritmo tipo) {
        this.teclado = teclado;
        simbolos = teclado.getLenguaje().getAlfabeto().getSimbolos();
        
        NUM_OBJETOS = simbolos.size();

        mejorCota = Integer.MAX_VALUE;
        mejorSolucion = new int[NUM_OBJETOS];
        podas = new int[NUM_OBJETOS+1];

        switch (tipo) {
            case QAP:
                optimizarTecladoMedianteQAP();
                break;
        
            default:
                throw new IllegalArgumentException("Tipo de algoritmo no soportado");
        }
    }

    // ***************************************** FUNCIONES *****************************************

    // ----------------------------------------- funciones -----------------------------------------
    //                            -------------- Matrices ---------------

    private int calcularDistanciaEntreDosUbicaciones(int iesimaUbicacion, int jesimaUbicacion) {
        if (iesimaUbicacion == jesimaUbicacion)
            return 0;

        // como son valores bajos, es necesario tener una precision notable para poder distinguir entre ubicaciones directas y diagonales: 
        // se multiplican los valores por 100, de esta forma no se usan decimales y el rendimiento no se ve afectado

        double distancia = teclado.getDistanciaEntreDosTeclas(iesimaUbicacion, jesimaUbicacion);
        return (int)(distancia*100);
    }
 
    private void calcularMatrizDistancias() {
        matrizDistancias = new int[NUM_OBJETOS][NUM_OBJETOS];

        for (int i = 0; i < NUM_OBJETOS; ++i) {
            for (int j = 0; j < NUM_OBJETOS; ++j) {
                    // Se aprovecha que la matriz es simetrica para no calcular las distancias dos veces
                if (j >= i)
                    matrizDistancias[i][j] = calcularDistanciaEntreDosUbicaciones(i, j);
                else 
                    matrizDistancias[i][j] = matrizDistancias[j][i];
            }
        }
    }
    
    private void calcularMatrizFrecuencias() {
        matrizFrecuencias = new int[NUM_OBJETOS][NUM_OBJETOS];
        matrizFrecuencias = teclado.getLenguaje().calcularFrecuenciaParDeSimbolosPalabra();
    } 

    // ----------------------------------------- funciones -----------------------------------------
    //                            ------- Cota de Gilmore-Lawler --------
    
    // La cota nos aproxima el coste minimo que se puede obtener a partir de la solucion parcial pasada por parametro
    private int calcularCotaGilmoreLawler(int numObjetosColocados, boolean[] iesimoSimboloColocado, boolean[] iesimaUbicacionOcupada, int[] jesimaUbicacionDeIesimoSimbolo) {
        int primerTermino = calcularPrimerTermino(iesimoSimboloColocado, jesimaUbicacionDeIesimoSimbolo);
        
            // nos ahorramos las llamadas para el segundo termino si ya se han colocado todos los simbolos
        if (numObjetosColocados == NUM_OBJETOS)
            return primerTermino;
        
        int segundoTermino = calcularSegundoTermino(numObjetosColocados, iesimoSimboloColocado, iesimaUbicacionOcupada, jesimaUbicacionDeIesimoSimbolo);

        return primerTermino + segundoTermino;
    }

    // PRIMER TERMINO: hace referencia al coste del trafico entre los simbolos (teclas) ya colocados en ubicaciones validas
    private int calcularPrimerTermino(boolean[] iesimoSimboloColocado, int[] jesimaUbicacionDeIesimoSimbolo) {
        int primerTermino = 0;

        for (int simbI = 0; simbI < NUM_OBJETOS; ++simbI) {
            if (iesimoSimboloColocado[simbI]) {

                for (int simbJ = simbI + 1; simbJ < NUM_OBJETOS; ++simbJ) {
                    if (iesimoSimboloColocado[simbJ]) {     // simbI y simbJ son simbolos colocados
                            // Como la matriz de distancias esta en iesimos, se obtinen estos a partir de los puntos
                        int ubiI = jesimaUbicacionDeIesimoSimbolo[simbI];
                        int ubiJ = jesimaUbicacionDeIesimoSimbolo[simbJ];

                        primerTermino += matrizFrecuencias[simbI][simbJ] * matrizDistancias[ubiI][ubiJ];
                    }
                }
            }
        }

        return primerTermino;
    }

    // SEGUNDO TERMINO: intenta aproximar el coste minimo que se puede llegar a obtener con las intalaciones aun no emplazadas
    private int calcularSegundoTermino(int numObjetosColocados, boolean[] iesimoSimboloColocado, boolean[] iesimaUbicacionOcupada, int[] jesimaUbicacionDeIesimoSimbolo) {
            // Para evitar sumar C1 y C2, en el calculo de C2 se pasa C1 como parametro y este se modifica 
        int[][] C1 = calcularSegundoTerminoC1(numObjetosColocados, iesimoSimboloColocado, iesimaUbicacionOcupada, jesimaUbicacionDeIesimoSimbolo);
        int[][] C1C2 = calcularSegundoTerminoC2(numObjetosColocados, iesimoSimboloColocado, iesimaUbicacionOcupada, C1);
        
            // se hace una copia de C1C2 para poder usar el algoritmo hungaro
        int[][] copiaC1C2 = new int[C1C2.length][C1C2.length];
        for (int i = 0; i < C1C2.length; ++i) {
            for (int j = 0; j < C1C2.length; ++j) {
                copiaC1C2[i][j] = C1C2[i][j];
            }
        }
        
            // se usa el algoritmo hungaro para encontrar la asignacion optima y con ello calcular el coste
        Hungarian hungarian = new Hungarian(copiaC1C2);
        int[][] asignacion = hungarian.encontrarAsignacionOptima();
        int coste = 0;

        for (int i = 0; i < asignacion.length; ++i) {
            coste += C1C2[asignacion[i][1]][i];
        }

        return coste;        
    }

        // C1: aproxima el coste de emplazar los simbolos (o teclas) que quedan por colocar en ubicaciones libres con los simbolos ya colocados
        //      El elemento (i, j) de C1 representa el coste de colocar el iesimo simbolo (tecla) no emplazado en la 
        //      jesima ubicacion libre del teclado, respecto a los simbolos (teclas) ya emplazados.
    private int[][] calcularSegundoTerminoC1(int numObjetosColocados, boolean[] iesimoSimboloColocado, boolean[] iesimaUbicacionOcupada, int[] jesimaUbicacionDeIesimoSimbolo) {
        int numObjetosRestantes = NUM_OBJETOS - numObjetosColocados;
        int[][] C1 = new int[numObjetosRestantes][numObjetosRestantes];

        int indiceProximoSimboloLibre = 0;                  // indice del proximo simbolo no colocado 
        for (int i = 0; i < NUM_OBJETOS; ++i) {              
            if (!iesimoSimboloColocado[i]) {                    // i "entra" en el cuerpo del for cuando el iesimo simbolo no esta colocado  
                
                int indiceProximaUbicacionLibre = 0;        // indice de la proxima ubicacion libre
                for (int j = 0; j < NUM_OBJETOS; ++j) {         
                    if (!iesimaUbicacionOcupada[j]) {           // j "entra" en el cuerpo del for cuando la jesima ubicacion no esta ocupada
                        
                            // se calcula el coste como un sumatorio del producto directo entre frecuencias y distancias
                        int sumaCosteIesimoSimboloLibreEnJesimaUbicacionLibre = 0;

                        for (int k = 0; k < NUM_OBJETOS; ++k) {     // k entra en el cuerpo del for cuando el kesimo simbolo esta colocado
                            if (iesimoSimboloColocado[k]) {
                                int iesimaUbicacionQueOcupaK = jesimaUbicacionDeIesimoSimbolo[k];
                                sumaCosteIesimoSimboloLibreEnJesimaUbicacionLibre += matrizFrecuencias[i][k] * matrizDistancias[j][iesimaUbicacionQueOcupaK];
                            }
                        }

                        C1[indiceProximoSimboloLibre][indiceProximaUbicacionLibre] = sumaCosteIesimoSimboloLibreEnJesimaUbicacionLibre;
                        
                        ++indiceProximaUbicacionLibre;
                    }
                }

                ++indiceProximoSimboloLibre;
            }
        }

        return C1;
    }

        // C2: aproxima el coste del trafico entre las instalaciones aun no emplazadas dependiendo de las ubicaciones
        //      El elemento (i, j) de C2 representa el coste de colocar el iesimo simbolo (tecla) no emplazado en la
        //      jesima ubicacion vacia del teclado, respecto a los simbolos (teclas) que quedan por colocar.
    private int[][] calcularSegundoTerminoC2(int numObjetosColocados, boolean[] iesimoSimboloColocado, boolean[] iesimaUbicacionOcupada, int[][] C1) {
        int numObjetosRestantes = NUM_OBJETOS - numObjetosColocados;
        int[][] C1C2 = C1;

        ArrayList<ArrayList<Integer>> traficos = new ArrayList<ArrayList<Integer>>(numObjetosRestantes - 1);      // la matriz de traficos desde la iesima instalacion al resto de las no emplazadas
        ArrayList<ArrayList<Integer>> distancias = new ArrayList<ArrayList<Integer>>(numObjetosRestantes - 1);    // la matriz de distancias desde la jesima ubicacion al resto de las no ucupadas

            // Se rellena la matriz de traficos restantes
        for (int i = 0; i < NUM_OBJETOS; ++i) {
            if (!iesimoSimboloColocado[i]) {

                ArrayList<Integer> traficosDeIesimo = new ArrayList<>(numObjetosRestantes-1);
                for (int j = 0; j < NUM_OBJETOS; ++j) {
                    if (!iesimoSimboloColocado[j] && i != j)
                        traficosDeIesimo.add(matrizFrecuencias[i][j]);
                }
                    // oredanamos los traficos de MENOR A MAYOR de la iesima instalacion
                traficosDeIesimo.sort(Comparator.naturalOrder());
                    // se guarda el vector en la matriz
                traficos.add(traficosDeIesimo);
            }
        }

            // Se rellena la matriz de distancias restantes
        for (int i = 0; i < NUM_OBJETOS; ++i) {
            if (!iesimaUbicacionOcupada[i]) {
                ArrayList<Integer> distanciasDeIesimo = new ArrayList<>(numObjetosRestantes-1);
                for (int j = 0; j < NUM_OBJETOS; ++j) {
                    if (!iesimaUbicacionOcupada[j] && i != j)
                        distanciasDeIesimo.add(matrizDistancias[i][j]);
                }
                    // oredanamos las distancias de MAYOR A MENOR de la iesima ubicacion
                distanciasDeIesimo.sort(Comparator.reverseOrder());
                    // se guarda el vector en la matriz
                distancias.add(distanciasDeIesimo);
            }
        }

        int indiceProximoSimboloLibre = 0;                  // indice del proximo simbolo no colocado
        for (int i = 0; i < NUM_OBJETOS; ++i) {
            if (!iesimoSimboloColocado[i]) {                    // i "entra" en el cuerpo del for cuando el iesimo simbolo no esta colocado
                
                int indiceProximaUbicacionLibre = 0;        // indice de la proxima ubicacion libre
                for (int j = 0; j < NUM_OBJETOS; ++j) {     
                    if (!iesimaUbicacionOcupada[j]) {           // j "entra" en el cuerpo del for cuando la jesima ubicacion no esta ocupada

                            // se calcula el coste como el sumatorio del producto escalar del iesimo vector de traficos y el jesimo del de distancias
                        int sumaCosteIesimoSimboloLibreEnJesimaUbicacionLibre = 0;

                        for (int k = 0; k < numObjetosRestantes - 1; ++k) {
                            sumaCosteIesimoSimboloLibreEnJesimaUbicacionLibre += traficos.get(indiceProximoSimboloLibre).get(k) * distancias.get(indiceProximaUbicacionLibre).get(k);
                        }

                        C1C2[indiceProximoSimboloLibre][indiceProximaUbicacionLibre] += sumaCosteIesimoSimboloLibreEnJesimaUbicacionLibre;
                        
                        ++indiceProximaUbicacionLibre;
                    }
                }

                ++indiceProximoSimboloLibre;
            }
        }

        return C1C2;
    }

    // ----------------------------------------- funciones -----------------------------------------
    //                            ---------- Branch and Bound -----------

    private void branchAndBoundv2(SolucionParcial solucionParcial) {
        int cota = solucionParcial.getCota();
        int numObjetosColocados = solucionParcial.getNumObjetosColocados();
        boolean[] iesimoSimboloColocado = solucionParcial.getIesimoSimboloColocado();
        boolean[] iesimaUbicacionOcupada = solucionParcial.getIesimaUbicacionOcupada();
        int[] jesimaUbicacionDeIesimoSimbolo = solucionParcial.getSolution();

        if (numObjetosColocados == NUM_OBJETOS) {
            System.out.println("ACTUALIZANDO MEJOR COTA A " + cota);

            mejorCota = cota;
            mejorSolucion = jesimaUbicacionDeIesimoSimbolo;
        }

        // se crea una cola de prioridad de SolucionParcial, ordenada por coste:
        PriorityQueue<SolucionParcial> pq = new PriorityQueue<SolucionParcial>(new Comparator<SolucionParcial>() {
            @Override
            public int compare(SolucionParcial o1, SolucionParcial o2) {
                return o1.getCota() - o2.getCota();
            }
        });

        // se rellena la cola de prioridad con las soluciones parciales
        for (int i = 0; i < NUM_OBJETOS; ++i) {
            if (!iesimoSimboloColocado[i]) {
                iesimoSimboloColocado[i] = true;

                for (int j = 0; j < NUM_OBJETOS; ++j) {
                    if (!iesimaUbicacionOcupada[j]) {
                        iesimaUbicacionOcupada[j] = true;

                        jesimaUbicacionDeIesimoSimbolo[i] = j;
                    
                        int cotaHijo = calcularCotaGilmoreLawler(numObjetosColocados+1, iesimoSimboloColocado, iesimaUbicacionOcupada, jesimaUbicacionDeIesimoSimbolo);
                        pq.add(new SolucionParcial(cotaHijo, numObjetosColocados+1, iesimoSimboloColocado.clone(), iesimaUbicacionOcupada.clone(), jesimaUbicacionDeIesimoSimbolo.clone()));

                        iesimaUbicacionOcupada[j] = false;
                    }
                }

                iesimoSimboloColocado[i] = false;
            }
        }

        // se van sacando las soluciones parciales de la cola de prioridad y se llama recursivamente a la funcion con ellas

        while (!pq.isEmpty()) {
            SolucionParcial top = pq.poll();
            if (top.getCota() < mejorCota && mejorCota == Integer.MAX_VALUE)
            {
                System.out.println("Explorando la COTA " + top.getCota() + " con " + top.getNumObjetosColocados()
                        + " objetos colocados y la mejor es " + mejorCota);
                branchAndBoundv2(top);
            }

            else
                ++podas[top.getNumObjetosColocados()];
        }
    }

    // ----------------------------------------- funciones -----------------------------------------
    //                            ----------------- QAP -----------------

    private void optimizarTecladoMedianteQAP() {
        System.out.println("OPTIMIZANDO TECLADO MEDIANTE QAP");
        calcularMatrizDistancias();
        calcularMatrizFrecuencias();

            // numero de simbolos colocados
        int numObjetosColocados = 0;
            // indica si el simbolo i esta colocado
        boolean[] iesimoSimboloColocado = new boolean[NUM_OBJETOS];                         
            // indica si la iesima ubicacion esta ocupada
        boolean[] iesimaUbicacionOcupada = new boolean[NUM_OBJETOS]; 
            // indica la jesima ubicacion que le corresponde al iesimo simbolo, el valor retornado es valido si iesimoSimboloColocado[i] == true
        int[] jesimaUbicacionDeIesimoSimbolo = new int[NUM_OBJETOS];          

            // inicializacion de los arrays de booleanos a false
        for (int i = 0; i < NUM_OBJETOS; ++i) {
            iesimoSimboloColocado[i] = false;
            iesimaUbicacionOcupada[i] = false;
        }

            // se usa el metodo branch and boundv2 para encontrar una solucion optima
        int cotaInicial = calcularCotaGilmoreLawler(numObjetosColocados, iesimoSimboloColocado, iesimaUbicacionOcupada, jesimaUbicacionDeIesimoSimbolo);
        SolucionParcial solucionInicial = new SolucionParcial(cotaInicial, numObjetosColocados, iesimoSimboloColocado, iesimaUbicacionOcupada, jesimaUbicacionDeIesimoSimbolo);
        branchAndBoundv2(solucionInicial);
    }

    // ----------------------------------------- funciones -----------------------------------------
    //                            ----------------- PUB -----------------

    public void asignarMejorSolucion() {
        System.out.println("MEJOR COTA: " + mejorCota);
        System.out.println("NUMERO DE PODAS POR NIVEL: ");
        for (int i = 0; i < podas.length; ++i) {
            System.out.println("NIVEL " + i + ": " + podas[i]);
        }

        teclado.asignarSimbolosEnTeclasMedianteIesimasUbicaciones(simbolos, mejorSolucion);
    }

    // ------------------------------------------ GETTERS ------------------------------------------

    public int[][] getMatrizDistancias() {
        return matrizDistancias;
    }

    public int[][] getMatrizFrecuencias() {
        return matrizFrecuencias;
    }

    public Teclado getTeclado() {
        return teclado;
    }

    // ---------------------------------- CLASE A LO STRUCT DE C++ ---------------------------------

    static public class SolucionParcial{
        private int cota;
        private int numObjetosColocados;
        private boolean[] iesimoSimboloColocado;
        private boolean[] iesimaUbicacionOcupada;
        private int[] jesimaUbicacionDeIesimoSimbolo;

        public SolucionParcial(int cota, int numObjetosColocados, boolean[] iesimoSimboloColocado, boolean[] iesimaUbicacionOcupada, int[] jesimaUbicacionDeIesimoSimbolo){
            this.cota = cota;
            this.numObjetosColocados = numObjetosColocados;
            this.iesimoSimboloColocado = iesimoSimboloColocado;
            this.iesimaUbicacionOcupada = iesimaUbicacionOcupada;
            this.jesimaUbicacionDeIesimoSimbolo = jesimaUbicacionDeIesimoSimbolo;
        }

        public int getCota(){
            return cota;
        }

        public int getNumObjetosColocados(){
            return numObjetosColocados;
        }
        
        public boolean[] getIesimoSimboloColocado(){
            return iesimoSimboloColocado;
        }
        
        public boolean[] getIesimaUbicacionOcupada(){
            return iesimaUbicacionOcupada;
        }
        public int[] getSolution(){
            return jesimaUbicacionDeIesimoSimbolo;
        }
    }
}
