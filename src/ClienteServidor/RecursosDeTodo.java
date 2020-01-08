package ClienteServidor;

import java.io.File;
import java.lang.management.ManagementFactory;
import org.hyperic.sigar.CpuInfo;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;

public class RecursosDeTodo {

    //Propiedades a extraer
    static long discoDuro = new File("/").getTotalSpace();
    static long ram = ((com.sun.management.OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean()).getTotalPhysicalMemorySize();
    static String sistema = System.getProperty("os.name");
    static int cores = Runtime.getRuntime().availableProcessors();
    //static String procesador = System.getenv("PROCESSOR_IDENTIFIER");

    static Sigar sigar = new Sigar();
    static CpuInfo [] infos;
    
    public long ObtenenerDiscoDuro() {
        discoDuro /= 1048576;
        return discoDuro;
    }

    public long ObtenerRam() {
        ram /= 1048576;
        return ram;
    }

    public String ObtenerSistema() {
        return sistema;
    }

    public int ObtenerCores() {
        return cores;
    }

    public String ObtenerCPU() throws SigarException {
        infos = sigar.getCpuInfoList();
        CpuInfo info = infos[2];
        return  info.getVendor();
    }
}
