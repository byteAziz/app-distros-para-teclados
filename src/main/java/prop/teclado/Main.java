package prop.teclado;

import prop.teclado.presentation.controllers.CtrPresentation;

public class Main {
    private static CtrPresentation cp;

    public static void main(String[] args) {
        cp = new CtrPresentation();
        cp.init();
        cp.runVistaPrincipal();
    }
}
