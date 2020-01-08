package ClienteServidor;

import static ClienteServidor.ServerGUI.bean;
import java.util.concurrent.Callable;

public class HiloRankeo implements Callable<Double> {

    static double ramcita;

    @Override //Metodo RUN
    public Double call() throws Exception {
        ramcita = bean.getSystemCpuLoad() * 1048576;

       try { //Duermo y luego vuelvo
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            System.out.println("Tengo un error en Hilo Rankeo con " + e);
        }
        return ramcita;
    }
}
