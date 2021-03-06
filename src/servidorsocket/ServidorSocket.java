/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidorsocket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Gustavo Vinicius
 */
public class ServidorSocket extends javax.swing.JFrame {

    private Socket socket;
    private DataInputStream input;
    private DataOutputStream output;
    private ServerSocket serverSocket;

    /**
     * Creates new form ServidorSocket
     */
    public ServidorSocket() {
        initComponents();
        botaoEnviarHoraServidor.setEnabled(false);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        botaoIniciarServidor = new javax.swing.JButton();
        botaoEnviarHoraServidor = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        textArea = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        botaoIniciarServidor.setText("Iniciar servidor");
        botaoIniciarServidor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoIniciarServidorActionPerformed(evt);
            }
        });

        botaoEnviarHoraServidor.setText("Enviar hora do servidor");
        botaoEnviarHoraServidor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoEnviarHoraServidorActionPerformed(evt);
            }
        });

        textArea.setColumns(20);
        textArea.setRows(5);
        jScrollPane1.setViewportView(textArea);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 333, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(botaoIniciarServidor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(29, 29, 29)
                        .addComponent(botaoEnviarHoraServidor, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(38, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(botaoIniciarServidor)
                    .addComponent(botaoEnviarHoraServidor))
                .addGap(29, 29, 29)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(39, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void botaoIniciarServidorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoIniciarServidorActionPerformed
        textArea.append("Servidor>>> iniciado com sucesso!\n");
        textArea.append("Servidor>>> aguardando nova conexao.\n");
        try {
            serverSocket = new ServerSocket(8081);
        } catch (IOException ex) {
            Logger.getLogger(ServidorSocket.class.getName()).log(Level.SEVERE, null, ex);
        }

        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    socket = serverSocket.accept();
                    textArea.append("Servidor>>> cliente conectado: " + socket.getInetAddress().getHostName() + "\n");
                    botaoEnviarHoraServidor.setEnabled(true);
                    input = new DataInputStream(socket.getInputStream());
                    output = new DataOutputStream(socket.getOutputStream());

                    while (true) {
                        if (input.available() > 0) {
                            String horarioCliente = (String) input.readUTF();
                            textArea.append("Cliente>>> " + horarioCliente + "\n");
                        }
                    }

                } catch (IOException ex) {
                    Logger.getLogger(ServidorSocket.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }).start();

    }//GEN-LAST:event_botaoIniciarServidorActionPerformed

    private void botaoEnviarHoraServidorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoEnviarHoraServidorActionPerformed
        new Thread(new Runnable() {
            @Override
            public void run() {
                String horarioServidor = recupereHorario(new Date(System.currentTimeMillis()));
                textArea.append("Servidor>>> " + horarioServidor + "\n");
                try {
                    output.writeUTF(horarioServidor);
                } catch (IOException ex) {
                    Logger.getLogger(ServidorSocket.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }).start();
    }//GEN-LAST:event_botaoEnviarHoraServidorActionPerformed

    private String recupereHorario(Date horarioNoCliente) {
        int hours = horarioNoCliente.getHours();
        int minutes = horarioNoCliente.getMinutes();
        int seconds = horarioNoCliente.getSeconds();
        String horaFormatada = hours < 10 ? "0" + hours : String.valueOf(hours);
        String minutoFormatado = minutes < 10 ? "0" + minutes : String.valueOf(minutes);
        String segundoFormatado = seconds < 10 ? "0" + seconds : String.valueOf(seconds);
        return horaFormatada + ":" + minutoFormatado + ":" + segundoFormatado;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ServidorSocket.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ServidorSocket.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ServidorSocket.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ServidorSocket.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ServidorSocket().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botaoEnviarHoraServidor;
    private javax.swing.JButton botaoIniciarServidor;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea textArea;
    // End of variables declaration//GEN-END:variables
}
