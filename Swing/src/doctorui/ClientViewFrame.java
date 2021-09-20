/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doctorui;

import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author roshanantonyarunkumar
 */
public class ClientViewFrame extends javax.swing.JFrame {

    /**
     * Creates new form ClientDataFrame
     */
    Client client1;
    Client client2;
    Client client3;
    Client client4;
    int doctor_id;
    int k = 0;
    String username;

    ArrayList<Integer> ids = new ArrayList<Integer>();

    public ClientViewFrame(int doctor_id, String username) {
        initComponents();
        this.doctor_id = doctor_id;
        this.username = username;
        String jStr = getJSON(doctor_id);
        try {
            setArrayList(jStr);
        } catch (JSONException ex) {
            Logger.getLogger(ClientViewFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
       updateClients();

    }
    
   

    public void setArrayList(String jStr) throws JSONException {
        JSONArray jsonarray = new JSONArray(jStr);
        for (int i = 0; i < jsonarray.length(); i++) {
            JSONObject jsonobject = jsonarray.getJSONObject(i);
            ids.add(jsonobject.getInt("id"));

        }
    }

    public String getJSON(int doctor_id) {

        try {
            String doc_id = Integer.toString(doctor_id);

            String link = "http://vrvision.a2hosted.com/getdoctorclients.php";
            String data = URLEncoder.encode("doc_id", "UTF-8") + "="
                    + URLEncoder.encode(doc_id, "UTF-8");

            URL url = new URL(link);

            URLConnection conn = url.openConnection();

            conn.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());

            wr.write(data);
            wr.flush();

            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            StringBuilder sb = new StringBuilder();
            String line = null;

            // Read Server Response
            while ((line = reader.readLine()) != null) {
                sb.append(line);
                break;
            }
            return sb.toString();
        } catch (Exception e) {
            return new String("Exception: " + e.getMessage());
        }
    }

    public void updateClients() {
        //checks if the smallest value of k is greater than the size of the arraylist
        if ((k + 1) <= ids.size()) {
            /*chain of if-else checks how many clients are remaining to be shown
            turns of unused buttons, and sets ids to all buttons which will be used,
            depending on how many clients are remaining*/
            if ((k / 4) == (ids.size() / 4)) {
                if (ids.size() % 4 == 0) {
                    client1 = new Client(ids.get(k));
                    client2 = new Client(ids.get(k + 1));
                    client3 = new Client(ids.get(k + 2));
                    client4 = new Client(ids.get(k + 3));

                    ClientLabel1.setText(client1.getName());
                    ClientLabel2.setText(client2.getName());
                    ClientLabel3.setText(client3.getName());
                    ClientLabel4.setText(client4.getName());
                } else if (ids.size() % 4 == 3) {
                    client1 = new Client(ids.get(k));
                    client2 = new Client(ids.get(k + 1));
                    client3 = new Client(ids.get(k + 2));

                    ClientLabel1.setText(client1.getName());
                    ClientLabel2.setText(client2.getName());
                    ClientLabel3.setText(client3.getName());

                    ClientLabel4.setVisible(false);
                    UserOpen4.setVisible(false);

                } else if (ids.size() % 4 == 2) {
                    client1 = new Client(ids.get(k));
                    client2 = new Client(ids.get(k + 1));

                    ClientLabel1.setText(client1.getName());
                    ClientLabel2.setText(client2.getName());

                    ClientLabel3.setVisible(false);
                    ClientLabel4.setVisible(false);
                    UserOpen3.setVisible(false);
                    UserOpen4.setVisible(false);

                } else if (ids.size() % 4 == 1) {
                    client1 = new Client(ids.get(k));

                    ClientLabel1.setText(client1.getName());

                    ClientLabel2.setVisible(false);
                    ClientLabel3.setVisible(false);
                    ClientLabel4.setVisible(false);
                    UserOpen2.setVisible(false);
                    UserOpen3.setVisible(false);
                    UserOpen4.setVisible(false);
                }
            } else {
                client1 = new Client(ids.get(k));
                client2 = new Client(ids.get(k + 1));
                client3 = new Client(ids.get(k + 2));
                client4 = new Client(ids.get(k + 3));

                ClientLabel1.setText(client1.getName());
                ClientLabel2.setText(client2.getName());
                ClientLabel3.setText(client3.getName());
                ClientLabel4.setText(client4.getName());
            }

        }

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        kGradientPanel1 = new keeptoo.KGradientPanel();
        ClientPanel2 = new javax.swing.JPanel();
        ClientLabel2 = new javax.swing.JLabel();
        ClientPanel1 = new javax.swing.JPanel();
        ClientLabel1 = new javax.swing.JLabel();
        ClientPanel3 = new javax.swing.JPanel();
        ClientLabel3 = new javax.swing.JLabel();
        ClientPanel4 = new javax.swing.JPanel();
        ClientLabel4 = new javax.swing.JLabel();
        UserOpen1 = new javax.swing.JButton();
        UserOpen4 = new javax.swing.JButton();
        UserOpen3 = new javax.swing.JButton();
        UserOpen2 = new javax.swing.JButton();
        Back = new javax.swing.JButton();
        Next = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(1000, 562));
        setSize(new java.awt.Dimension(1000, 562));

        kGradientPanel1.setMinimumSize(new java.awt.Dimension(1000, 562));

        ClientPanel2.setBackground(new java.awt.Color(255, 255, 255));

        ClientLabel2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        ClientLabel2.setForeground(new java.awt.Color(12, 91, 160));
        ClientLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout ClientPanel2Layout = new javax.swing.GroupLayout(ClientPanel2);
        ClientPanel2.setLayout(ClientPanel2Layout);
        ClientPanel2Layout.setHorizontalGroup(
            ClientPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(ClientLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        ClientPanel2Layout.setVerticalGroup(
            ClientPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(ClientLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        ClientPanel1.setBackground(new java.awt.Color(255, 255, 255));

        ClientLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        ClientLabel1.setForeground(new java.awt.Color(12, 91, 160));
        ClientLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout ClientPanel1Layout = new javax.swing.GroupLayout(ClientPanel1);
        ClientPanel1.setLayout(ClientPanel1Layout);
        ClientPanel1Layout.setHorizontalGroup(
            ClientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(ClientLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 215, Short.MAX_VALUE)
        );
        ClientPanel1Layout.setVerticalGroup(
            ClientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(ClientLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 81, Short.MAX_VALUE)
        );

        ClientPanel3.setBackground(new java.awt.Color(255, 255, 255));

        ClientLabel3.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        ClientLabel3.setForeground(new java.awt.Color(12, 91, 160));
        ClientLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout ClientPanel3Layout = new javax.swing.GroupLayout(ClientPanel3);
        ClientPanel3.setLayout(ClientPanel3Layout);
        ClientPanel3Layout.setHorizontalGroup(
            ClientPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(ClientLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        ClientPanel3Layout.setVerticalGroup(
            ClientPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(ClientLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 69, Short.MAX_VALUE)
        );

        ClientPanel4.setBackground(new java.awt.Color(255, 255, 255));

        ClientLabel4.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        ClientLabel4.setForeground(new java.awt.Color(12, 91, 160));
        ClientLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout ClientPanel4Layout = new javax.swing.GroupLayout(ClientPanel4);
        ClientPanel4.setLayout(ClientPanel4Layout);
        ClientPanel4Layout.setHorizontalGroup(
            ClientPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(ClientLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 203, Short.MAX_VALUE)
        );
        ClientPanel4Layout.setVerticalGroup(
            ClientPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(ClientLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        UserOpen1.setText("Open");
        UserOpen1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                UserOpen1ActionPerformed(evt);
            }
        });

        UserOpen4.setText("Open");
        UserOpen4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                UserOpen4ActionPerformed(evt);
            }
        });

        UserOpen3.setText("Open");
        UserOpen3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                UserOpen3ActionPerformed(evt);
            }
        });

        UserOpen2.setText("Open");
        UserOpen2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                UserOpen2ActionPerformed(evt);
            }
        });

        Back.setText("Back");
        Back.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BackActionPerformed(evt);
            }
        });

        Next.setText("Next");
        Next.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NextActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout kGradientPanel1Layout = new javax.swing.GroupLayout(kGradientPanel1);
        kGradientPanel1.setLayout(kGradientPanel1Layout);
        kGradientPanel1Layout.setHorizontalGroup(
            kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(kGradientPanel1Layout.createSequentialGroup()
                .addGap(64, 64, 64)
                .addGroup(kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(UserOpen1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(ClientPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(ClientPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(UserOpen3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 215, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 448, Short.MAX_VALUE)
                .addGroup(kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(ClientPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(ClientPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(UserOpen4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(UserOpen2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(70, 70, 70))
            .addGroup(kGradientPanel1Layout.createSequentialGroup()
                .addComponent(Back)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(Next))
        );
        kGradientPanel1Layout.setVerticalGroup(
            kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(kGradientPanel1Layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addGroup(kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(ClientPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(ClientPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(UserOpen1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(UserOpen2, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 215, Short.MAX_VALUE)
                .addGroup(kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(ClientPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(ClientPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(UserOpen4, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(UserOpen3, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(kGradientPanel1Layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(Back)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, kGradientPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Next))))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(kGradientPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(kGradientPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void UserOpen1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_UserOpen1ActionPerformed
        this.setVisible(false);
        ClientDataFrame cdf = new ClientDataFrame(client1, username);
        cdf.setVisible(true);
    }//GEN-LAST:event_UserOpen1ActionPerformed

    private void UserOpen4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_UserOpen4ActionPerformed
        this.setVisible(false);
        ClientDataFrame cdf = new ClientDataFrame(client4, username);
        cdf.setVisible(true);
    }//GEN-LAST:event_UserOpen4ActionPerformed

    private void UserOpen3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_UserOpen3ActionPerformed
        this.setVisible(false);
        ClientDataFrame cdf = new ClientDataFrame(client3, username);
        cdf.setVisible(true);
    }//GEN-LAST:event_UserOpen3ActionPerformed

    private void UserOpen2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_UserOpen2ActionPerformed
        this.setVisible(false);
        ClientDataFrame cdf = new ClientDataFrame(client2, username);
        cdf.setVisible(true);    }//GEN-LAST:event_UserOpen2ActionPerformed

    private void BackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BackActionPerformed
        ClientLabel1.setVisible(true);
        ClientLabel2.setVisible(true);
        ClientLabel3.setVisible(true);
        ClientLabel4.setVisible(true);
        UserOpen1.setVisible(true);
        UserOpen2.setVisible(true);
        UserOpen3.setVisible(true);
        UserOpen4.setVisible(true);
        
        if (k > 0) {
            k = k - 4;
            updateClients();
        } else{
              MainInterface mi = new MainInterface(username);
              mi.setVisible(true);
        }


    }//GEN-LAST:event_BackActionPerformed

    private void NextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NextActionPerformed
        k = k + 4;
        updateClients();
    }//GEN-LAST:event_NextActionPerformed

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
            java.util.logging.Logger.getLogger(ClientViewFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ClientViewFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ClientViewFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ClientViewFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Back;
    private javax.swing.JLabel ClientLabel1;
    private javax.swing.JLabel ClientLabel2;
    private javax.swing.JLabel ClientLabel3;
    private javax.swing.JLabel ClientLabel4;
    private javax.swing.JPanel ClientPanel1;
    private javax.swing.JPanel ClientPanel2;
    private javax.swing.JPanel ClientPanel3;
    private javax.swing.JPanel ClientPanel4;
    private javax.swing.JButton Next;
    private javax.swing.JButton UserOpen1;
    private javax.swing.JButton UserOpen2;
    private javax.swing.JButton UserOpen3;
    private javax.swing.JButton UserOpen4;
    private keeptoo.KGradientPanel kGradientPanel1;
    // End of variables declaration//GEN-END:variables
}
