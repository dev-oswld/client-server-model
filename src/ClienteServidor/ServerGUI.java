package ClienteServidor;

import com.sun.management.OperatingSystemMXBean;
import java.awt.Desktop;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import org.hyperic.sigar.SigarException;

public class ServerGUI extends javax.swing.JFrame implements Runnable {

    ServerSocket server;
    static Socket cli;
    static int RANK, RANKTEMP, RANK_COPY;
    static boolean ESTADO;
    static InetAddress ip;
    static String returnMessage;

    static RecursosDeTodo objeto = new RecursosDeTodo();
    static String yoServer = null;
    static List<String> lista_fila, lista_G, lista_R;
    static DefaultTableModel model;
    static int contador = 1;

    static OperatingSystemMXBean bean; //Objeto de la obtencion de RAM
    static Future<Double> ramcitaLlamda; //Objeto del HILO
    static String ramActulizada;
    ServerGUI nuevo;

    public ServerGUI() {
        initComponents();
        try {
            this.server = new ServerSocket(1717); //Con el mismo puerto
        } catch (IOException ex) {
            System.out.println("Error en el constructor" + ex.getMessage());
        }

        Thread hilo = new Thread(this);
        hilo.start();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        bindingGroup = new org.jdesktop.beansbinding.BindingGroup();

        jSeparator1 = new javax.swing.JSeparator();
        TituloClientes = new javax.swing.JLabel();
        btn_Salir = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        TablaGeneral = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        textogrande = new javax.swing.JTextArea();
        txt_serverpuntos = new javax.swing.JLabel();
        Titulo2 = new javax.swing.JLabel();
        OtroTitulo1 = new javax.swing.JLabel();
        txt_serverpuntosfinal = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("Servidor"), this, org.jdesktop.beansbinding.BeanProperty.create("title"));
        bindingGroup.addBinding(binding);

        TituloClientes.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        TituloClientes.setForeground(new java.awt.Color(51, 0, 204));
        TituloClientes.setText("Clientes activos");

        btn_Salir.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        btn_Salir.setText("Salir");
        btn_Salir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_SalirActionPerformed(evt);
            }
        });

        TablaGeneral.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nombre", "IP", "", "", "", "", "", "Estado"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        TablaGeneral.setColumnSelectionAllowed(true);
        TablaGeneral.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(TablaGeneral);
        TablaGeneral.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

        textogrande.setColumns(20);
        textogrande.setRows(5);
        jScrollPane2.setViewportView(textogrande);

        txt_serverpuntos.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txt_serverpuntos.setText("0.0");

        Titulo2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        Titulo2.setForeground(new java.awt.Color(51, 0, 204));
        Titulo2.setText("Monitoreo de recursos");

        OtroTitulo1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        OtroTitulo1.setText("Calificacion del server:");

        txt_serverpuntosfinal.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txt_serverpuntosfinal.setText("0.0");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jScrollPane2)
                        .addComponent(TituloClientes, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 749, Short.MAX_VALUE)
                        .addComponent(btn_Salir, javax.swing.GroupLayout.Alignment.LEADING))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(OtroTitulo1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txt_serverpuntos)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_serverpuntosfinal))
                    .addComponent(Titulo2))
                .addContainerGap(91, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addComponent(Titulo2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(OtroTitulo1)
                    .addComponent(txt_serverpuntos)
                    .addComponent(txt_serverpuntosfinal))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21)
                .addComponent(TituloClientes)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btn_Salir)
                .addContainerGap(16, Short.MAX_VALUE))
        );

        bindingGroup.bind();

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_SalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_SalirActionPerformed
        try {
            server.close();
            JOptionPane.showMessageDialog(null, "El Servidor sera cerrado", "Aviso Importante", JOptionPane.WARNING_MESSAGE);
            System.out.println("Todo llega a su fin :)");
            System.exit(0);

        } catch (IOException ex) {
            System.out.println("Error en cerrar conexion " + ex.getMessage());
        }
    }//GEN-LAST:event_btn_SalirActionPerformed

    public static void main(String args[]) throws UnknownHostException {
        Llamada();

        System.out.println("Estoy en el lado del Servidor");
        ip = InetAddress.getLocalHost();
        System.out.println("IP de aqui: " + ip);
        JOptionPane.showMessageDialog(null, "Tengo la IP: " + ip);

        //Este sirve para la RAM dinamica
        bean = (com.sun.management.OperatingSystemMXBean) ManagementFactory
                .getOperatingSystemMXBean();
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel OtroTitulo1;
    private javax.swing.JTable TablaGeneral;
    private javax.swing.JLabel Titulo2;
    private javax.swing.JLabel TituloClientes;
    private javax.swing.JButton btn_Salir;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTextArea textogrande;
    private javax.swing.JLabel txt_serverpuntos;
    private javax.swing.JLabel txt_serverpuntosfinal;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables

    @Override
    public void run() { //Creamos un hilo para aceptar las entradas

        try {
            while (true) { //Ciclo infinito para recibir datos constantemente
                cli = server.accept();
                DataInputStream flujo = new DataInputStream(cli.getInputStream());
                String msg = flujo.readUTF(); //Aqui recibo mis propiedades del PC

                FilaLlenado(msg);

                String url = "https://www.youtube.com/watch?v=kRPMGp7n9Eg";
                Desktop.getDesktop().browse(java.net.URI.create(url));

                Peticion();
                cli.close();
            }
        } catch (IOException ex) {
            System.out.println("Estado de conexion: " + ex.getMessage());
        } catch (SigarException ex) {
            System.out.println("Con error por Sigar");
            Logger.getLogger(ServerGUI.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException | ExecutionException ex) {
            Logger.getLogger(ServerGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void Peticion() throws IOException {
        //Enviando respuesta al cliente.
        returnMessage = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(Calendar.getInstance().getTime());
        OutputStream os = cli.getOutputStream();
        OutputStreamWriter osw = new OutputStreamWriter(os);
        BufferedWriter bw = new BufferedWriter(osw);
        bw.write(returnMessage);
        System.out.println("Operacion realizada con el cliente " + returnMessage);
        bw.flush();
    }

    public static void Llamada() {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ServerGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(() -> {
            new ServerGUI().setVisible(true);
        });
    }

    public void FilaLlenado(String msg) throws SigarException, InterruptedException, ExecutionException {

        while (contador == 1) { //Fila del Servidor
            yoServer = "CPU: " + objeto.ObtenerCPU() + ",Nucleos: " + objeto.ObtenerCores() + ",RAM: " + objeto.ObtenerRam() + "GB,DD: " + objeto.ObtenenerDiscoDuro() + "GB,OS: " + objeto.ObtenerSistema();
            String filaX = "" + cli.getInetAddress().getHostName() + "," + cli.getInetAddress().getHostAddress() + "," + yoServer;
            String filaInicio[] = filaX.split(",");
            //lista_R = Arrays.asList(filaInicio); //System.out.println("Size: " + lista_R.size()); //Es 7
            model = (DefaultTableModel) TablaGeneral.getModel();
            model.addRow(filaInicio);
            RankeoGeneral(yoServer);
            String temp = "/ " + RANK;
            txt_serverpuntosfinal.setText(temp); //System.out.println("Calif " + RANK);
            contador++;
        }

        //Aqui empiezo con un HILO
        ExecutorService servicio = Executors.newFixedThreadPool(1);
        ramcitaLlamda = servicio.submit(new HiloRankeo());
        ramActulizada = "RAM: " + String.format("%.2f", ramcitaLlamda.get());
        //System.out.println("Mira lo nuevo " + ramActulizada);
        RankeoDeRam(ramActulizada);
        txt_serverpuntos.setText("" + RANK_COPY);

        if (RANK_COPY <= 0) { //TimeUnit.SECONDS.sleep(5);
            JOptionPane.showMessageDialog(null, "Ahora cambiare de modo", "Aviso de Switheo", JOptionPane.WARNING_MESSAGE);
            nuevo.dispose();
            
            guicliente clienteY = new guicliente();
            clienteY.CrearInterfaz();
            
            //clienteX.dispose();
            //guiserver ServerX = new guiserver();
            //ServerX.CrearInterfazServer();
            // Switch
            //ServerGUI nuevo = new ServerGUI();
            //nuevo.setVisible(true);
        }

        String fila = "" + cli.getInetAddress().getHostName() + "," + cli.getInetAddress().getHostAddress() + "," + msg + "," + RANK + "";
        String filacortada[] = fila.split(",");
        lista_fila = Arrays.asList(filacortada);
        //System.out.println("Size: " + lista_fila.size());
        if (lista_fila.size() == 9) {
            model = (DefaultTableModel) TablaGeneral.getModel();
            model.addRow(filacortada);

            RankeoGeneral(msg); //Lamo a mi algoritmo de RANKEO
            textogrande.append("Nombre: " + cli.getInetAddress().getHostName() + "\nCalificación: " + RANK + ".\n\n");

        } else if (lista_fila.size() == 4) {
            textogrande.append("Nombre: " + cli.getInetAddress().getHostName() + "\nEstado: " + msg + ".\n\n");
        }
    }

    public static void RankeoGeneral(String msg) {
        Pattern cpu1 = Pattern.compile("CPU: AMD"); //¿A quien busco?
        Pattern cpu2 = Pattern.compile("CPU: INTEL");
        Matcher checkCPU1 = cpu1.matcher(msg);
        Matcher checkCPU2 = cpu2.matcher(msg);

        Pattern nucleosmin = Pattern.compile("\\b(Nucleos\\:\\ )[0-4]");
        Pattern nucleosmax = Pattern.compile("\\b(Nucleos\\:\\ )[5-]");
        Matcher checkCores1 = nucleosmin.matcher(msg);
        Matcher checkCores2 = nucleosmax.matcher(msg);

        Pattern ramMin = Pattern.compile("\\b(RAM\\:\\ )[0-2]");
        Pattern ramMid = Pattern.compile("\\b(RAM\\:\\ )[3-4]");
        Pattern ramMid1 = Pattern.compile("\\b(RAM\\:\\ )[5-6]");
        Pattern ramMid2 = Pattern.compile("\\b(RAM\\:\\ )[7-8]");
        Pattern ramMax = Pattern.compile("\\b(RAM\\:\\ )[9]");

        Matcher checkRAM1 = ramMin.matcher(msg);
        Matcher checkRAM2 = ramMid.matcher(msg);
        Matcher checkRAM4 = ramMid1.matcher(msg);
        Matcher checkRAM5 = ramMid2.matcher(msg);
        Matcher checkRAM3 = ramMax.matcher(msg);

        Pattern ddMin = Pattern.compile("\\b(DD\\:\\ )[0-4]");
        Pattern ddMid = Pattern.compile("\\b(DD\\:\\ )[5-8]");
        Pattern ddMax = Pattern.compile("\\b(DD\\:\\ )[9-]");
        Matcher checkDD1 = ddMin.matcher(msg);
        Matcher checkDD2 = ddMid.matcher(msg);
        Matcher checkDD3 = ddMax.matcher(msg);

        Pattern osMin = Pattern.compile("\\b(OS\\: Windows\\ )[7]");
        Pattern osMid = Pattern.compile("\\b(OS\\: Windows\\ )[8]");
        Pattern osMax = Pattern.compile("\\b(OS\\: Windows\\ )[10]");
        Pattern osLL = Pattern.compile("\\b(OS\\: Linux)");
        Matcher checkosLL = osLL.matcher(msg);
        Matcher checkOS1 = osMin.matcher(msg);
        Matcher checkOS2 = osMid.matcher(msg);
        Matcher checkOS3 = osMax.matcher(msg);

        RANK = 0; //Rankeo
        if (checkCPU1.find()) {
            RANK += 50;
        } else if (checkCPU2.find()) {
            RANK += 100;
        }

        if (checkCores1.find()) {
            RANK += 100;
        } else if (checkCores2.find()) {
            RANK += 200;
        }

        if (checkRAM1.find()) {
            RANK += 50;
        } else if (checkRAM2.find()) {
            RANK += 100;
        } else if (checkRAM3.find()) {
            RANK += 150;
        } else if (checkRAM4.find()) {
            RANK += 120;
        } else if (checkRAM5.find()) {
            RANK += 130;
        }

        if (checkDD1.find()) {
            RANK += 30;
        } else if (checkDD2.find()) {
            RANK += 60;
        } else if (checkDD3.find()) {
            RANK += 90;
        }

        if (checkOS1.find()) {
            RANK += 70;
        } else if (checkOS2.find()) {
            RANK -= 80;
        } else if (checkOS3.find()) {
            RANK += 100;
        } else if (checkosLL.find()) {
            RANK += 110;
        }
    }

    public void RankeoDeRam(String msg) {
        Pattern ramcita0 = Pattern.compile("\\b(RAM\\:\\ )-?[10-25]");
        Pattern ramcita1 = Pattern.compile("\\b(RAM\\:\\ )-?[30-45]");
        Pattern ramcita2 = Pattern.compile("\\b(RAM\\:\\ )-?[40-55]");
        Pattern ramcita3 = Pattern.compile("\\b(RAM\\:\\ )-?[60-75]");
        Pattern ramcita4 = Pattern.compile("\\b(RAM\\:\\ )-?[80-95]");

        Matcher verRamcita0 = ramcita0.matcher(msg);
        Matcher verRamcita1 = ramcita1.matcher(msg);
        Matcher verRamcita2 = ramcita2.matcher(msg);
        Matcher verRamcita3 = ramcita3.matcher(msg);
        Matcher verRamcita4 = ramcita4.matcher(msg);

        //RANK_COPY = RANK;
        if (verRamcita0.find()) {
            RANKTEMP -= 50;
        } else if (verRamcita1.find()) {
            RANKTEMP -= 80;
        } else if (verRamcita2.find()) {
            RANKTEMP -= 110;
        } else if (verRamcita3.find()) {
            RANKTEMP -= 140;
        } else if (verRamcita4.find()) {
            RANKTEMP -= 170;
        }
        RANK_COPY = RANK + RANKTEMP;
    }
}
