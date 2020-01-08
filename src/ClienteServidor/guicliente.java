package ClienteServidor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hyperic.sigar.SigarException;

public class guicliente extends JFrame implements ActionListener {

    JButton btnenviar, btnactualizar, btncerrar;

    Socket sockecito;
    boolean estado;
    DataOutputStream envio;
    ObjectOutputStream flujo;

    //Propiedades a extraer
    String resultado, resultadito;
    RecursosDeTodo objeto = new RecursosDeTodo();
    static String IP_PUBLIC;
    guicliente clienteY;

    public static void main(String[] args) throws IOException, InterruptedException {
        System.out.println("Estoy en el lado del Cliente");

        IP_PUBLIC = JOptionPane.showInputDialog("Ingresa la IP a donde esta el servidor:");
        if (IP_PUBLIC.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Se necesita campos completos de la IP, intente de nuevo", "Aviso", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }

        System.out.println("Escribiste: " + IP_PUBLIC);
        guicliente clienteX = new guicliente();
        clienteX.CrearInterfaz();
    }

    @Override
    public void actionPerformed(ActionEvent ae) {//Parte logica de los botones
        try {
            sockecito = new Socket(IP_PUBLIC, 1717);
            estado = sockecito.isConnected();
        } catch (IOException ex) {
            System.out.println("Tengo problemas en conectar con IP y PORT");
            Logger.getLogger(guicliente.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (ae.getSource() == btnenviar) {
            try {

                //CAMBIO IMPORTANTE
                resultado = "CPU: " + objeto.ObtenerCPU() + ",Nucleos: " + objeto.ObtenerCores() + ",RAM: " + objeto.ObtenerRam() + "GB,DD: " + objeto.ObtenenerDiscoDuro() + "GB,OS: " + objeto.ObtenerSistema() + "," + estado;
                //resultado = "" + objeto.ObtenerCPU() + "," + objeto.ObtenerCores() + "," + objeto.ObtenerRam() + "GB," + objeto.ObtenenerDiscoDuro() + "GB," + objeto.ObtenerSistema() + "," + estado;

                //Para enviar datos
                envio = new DataOutputStream(sockecito.getOutputStream());
                envio.writeUTF(resultado);
                btnenviar.setEnabled(false);

            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Servidor desconectado", "Aviso 01", JOptionPane.INFORMATION_MESSAGE);
                System.out.println("Error en cliente con: " + ex);
            } catch (SigarException ex) {
                Logger.getLogger(guicliente.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (ae.getSource() == btnactualizar) {

            try {

                resultado = "haciendo trabajar al servidor";

                envio = new DataOutputStream(sockecito.getOutputStream());
                envio.writeUTF(resultado);

                MensajeEntrante();
            } catch (IOException ex) {
                System.out.println("Error en actualizar");
                JOptionPane.showMessageDialog(null, "Servidor desconectado", "Aviso 02", JOptionPane.INFORMATION_MESSAGE);
                Logger.getLogger(guicliente.class.getName()).log(Level.SEVERE, null, ex);

                clienteY = new guicliente();
                clienteY.dispose();

                ServerGUI nuevo = new ServerGUI();
                nuevo.setVisible(true);

            }

        } else if (ae.getSource() == btncerrar) {
            estado = sockecito.isClosed();
            try {
                //resultado = "Cliente desconectado";
                resultado = "" + objeto.ObtenerCPU() + "," + objeto.ObtenerCores() + "," + objeto.ObtenerRam() + "GB," + objeto.ObtenenerDiscoDuro() + "GB," + objeto.ObtenerSistema() + "," + estado;

                //Para enviar datos
                envio = new DataOutputStream(sockecito.getOutputStream());
                envio.writeUTF(resultado);
                sockecito.close();

            } catch (IOException ex) {
                System.out.println("Error en salida con: " + ex);
            } catch (SigarException ex) {
                Logger.getLogger(guicliente.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.println("Todo llega a su fin :)");
            System.exit(0);
        }
    }

    public void CrearInterfaz() {
        btnenviar = new JButton();
        btnenviar.setText("Conectar");
        btnenviar.setBounds(10, 10, 150, 20);
        btnenviar.addActionListener(this);
        add(btnenviar);

        btnactualizar = new JButton();
        btnactualizar.setText("Peticion al servidor");
        btnactualizar.setBounds(10, 40, 150, 20);
        btnactualizar.addActionListener(this);
        add(btnactualizar);

        btncerrar = new JButton();
        btncerrar.setText("Salir"); //Cambio nuevo
        btncerrar.setBounds(10, 70, 150, 20);
        btncerrar.addActionListener(this);
        add(btncerrar);

        setLayout(null);
        setTitle("Cliente"); //LocalDateTime.now().getMinute()
        setSize(300, 150);
        setVisible(true);
    }

    public void MensajeEntrante() throws IOException {
        InputStream is = sockecito.getInputStream();
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);
        String message = br.readLine();
        JOptionPane.showMessageDialog(null, "Respuesta del servidor exitosa, con fecha de " + message, "Peticiones Cliente/Servidor", JOptionPane.INFORMATION_MESSAGE);
        System.out.println("Operacion exitosa hecha con el Servidor :]");
    }
}
