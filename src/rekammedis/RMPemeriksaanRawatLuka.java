/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

 /*
 * DlgDataSkriningGiziLanjut.java
 * Kontribusi Haris Rochmatullah RS Bhayangkara Nganjuk
 * Created on 11 November 2020, 20:19:56
 */
package rekammedis;

import fungsi.WarnaTable;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.akses;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javafx.scene.web.WebEngine;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.Timer;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import kepegawaian.DlgCariDokter;
import kepegawaian.DlgCariPetugas;
import laporan.DlgBerkasRawat;

/**
 *
 * @author perpustakaan
 */
public final class RMPemeriksaanRawatLuka extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private Connection koneksi = koneksiDB.condb();
    private sekuel Sequel = new sekuel();
    private validasi Valid = new validasi();
    private PreparedStatement ps;
    private ResultSet rs;
    private int i = 0;
    private DlgCariDokter dokter = new DlgCariDokter(null, false);
    private String dpjp = "";

    /**
     * Creates new form DlgRujuk
     *
     * @param parent
     * @param modal
     */
    public RMPemeriksaanRawatLuka(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocation(8, 1);
        setSize(628, 674);

        tabMode = new DefaultTableModel(null, new Object[]{
            "No.Rawat", "No.R.M.", "Nama Pasien", "Umur", "JK", "Tgl.Lahir", "Tanggal", "Jam",
            "Klasifikasi Luka", "Nyeri Lokal", "Demam", "Kemerahan",
            "Drainase Purulen", "Bengkak Terlokalisir", "Jenis Lokasi",
            "Slought", "Krusta", "Nekrotic", "Bullae", "Granulasi","Pus",
            "Rawat Luka", "Keterangan Rawat Luka", "Kode Dokter", "Nama Dokter"
        }) {
            @Override
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false;
            }
        };
        tbObat.setModel(tabMode);

        //tbObat.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbObat.getBackground()));
        tbObat.setPreferredScrollableViewportSize(new Dimension(500, 500));
        tbObat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        
        //"No.Rawat", "No.R.M.", "Nama Pasien", "Umur", "JK", "Tgl.Lahir", "Tanggal", "Jam", "Klasifikasi Luka", "Nyeri Lokal", "Demam", "Kemerahan",
         //   "Drainase Purulen", "Bengkak Terlokalisir", "Jenis Lokasi",
         //   "Slought", "Krusta", "Nekrotic", "Bullae", "Granulasi","Pus",
          //  "Rawat Luka", "Keterangan Rawat Luka", "Kode Dokter", "Nama Dokter"
        for (i = 0; i < 24; i++) {
            TableColumn column = tbObat.getColumnModel().getColumn(i);
            if (i == 0) {
                column.setPreferredWidth(105);
            } else if (i == 1) {
                column.setPreferredWidth(65);
            } else if (i == 2) {
                column.setPreferredWidth(160);
            } else if (i == 3) {
                column.setPreferredWidth(35);
            } else if (i == 4) {
                column.setPreferredWidth(20);
            } else if (i == 5) {
                column.setPreferredWidth(75);
            } else if (i == 6) {
                column.setPreferredWidth(75);
            } else if (i == 7) {
                column.setPreferredWidth(60);
            } else if (i == 8) {
                column.setPreferredWidth(120);
            } else if (i == 9) {
                column.setPreferredWidth(80);
            } else if (i == 10) {
                column.setPreferredWidth(80);
            } else if (i == 11) {
                column.setPreferredWidth(80);
            } else if (i == 12) {
                column.setPreferredWidth(120);
            } else if (i == 13) {
                column.setPreferredWidth(120);
            } else if (i == 14) {
                column.setPreferredWidth(100);
            } else if (i == 15) {
                column.setPreferredWidth(80);
            } else if (i == 16) {
                column.setPreferredWidth(80);
            } else if (i == 17) {
                column.setPreferredWidth(80);
            } else if (i == 18) {
                column.setPreferredWidth(80);
            } else if (i == 19) {
                column.setPreferredWidth(80);
            } else if (i == 20) {
                column.setPreferredWidth(100);
            } else if (i == 21) {
                column.setPreferredWidth(100); //100
            } else if (i == 22) {
                column.setPreferredWidth(200); //200
            } else if (i == 23) {
                column.setPreferredWidth(105); //105
            } else if (i == 24) {
                column.setPreferredWidth(160); //160
            } 
        }

        tbObat.setDefaultRenderer(Object.class, new WarnaTable());

        TNoRw.setDocument(new batasInput((byte) 17).getKata(TNoRw));
        KdDokter.setDocument(new batasInput((byte) 20).getKata(KdDokter));
        TCari.setDocument(new batasInput((int) 100).getKata(TCari));

        if (koneksiDB.CARICEPAT().equals("aktif")) {
            TCari.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
                @Override
                public void insertUpdate(DocumentEvent e) {
                    if (TCari.getText().length() > 2) {
                        tampil();
                    }
                }

                @Override
                public void removeUpdate(DocumentEvent e) {
                    if (TCari.getText().length() > 2) {
                        tampil();
                    }
                }

                @Override
                public void changedUpdate(DocumentEvent e) {
                    if (TCari.getText().length() > 2) {
                        tampil();
                    }
                }
            });
        }

        dokter.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
            }

            @Override
            public void windowClosing(WindowEvent e) {
            }

            @Override
            public void windowClosed(WindowEvent e) {
                if (dokter.getTable().getSelectedRow() != -1) {
                    KdDokter.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(), 0).toString());
                    NmDokter.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(), 1).toString());
                    KdDokter.requestFocus();
                }
            }

            @Override
            public void windowIconified(WindowEvent e) {
            }

            @Override
            public void windowDeiconified(WindowEvent e) {
            }

            @Override
            public void windowActivated(WindowEvent e) {
            }

            @Override
            public void windowDeactivated(WindowEvent e) {
            }
        });

        ChkInput.setSelected(false);
        isForm();
        jam();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPopupMenu1 = new javax.swing.JPopupMenu();
        MnCatatanKeperawatanRawatInap = new javax.swing.JMenuItem();
        JK = new widget.TextBox();
        Umur = new widget.TextBox();
        internalFrame1 = new widget.InternalFrame();
        Scroll = new widget.ScrollPane();
        tbObat = new widget.Table();
        jPanel3 = new javax.swing.JPanel();
        panelGlass8 = new widget.panelisi();
        BtnSimpan = new widget.Button();
        BtnBatal = new widget.Button();
        BtnHapus = new widget.Button();
        BtnEdit = new widget.Button();
        BtnPrint = new widget.Button();
        jLabel7 = new widget.Label();
        LCount = new widget.Label();
        BtnKeluar = new widget.Button();
        panelGlass9 = new widget.panelisi();
        jLabel19 = new widget.Label();
        DTPCari1 = new widget.Tanggal();
        jLabel21 = new widget.Label();
        DTPCari2 = new widget.Tanggal();
        jLabel6 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        BtnAll = new widget.Button();
        PanelInput = new javax.swing.JPanel();
        FormInput = new widget.PanelBiasa();
        jLabel4 = new widget.Label();
        TNoRw = new widget.TextBox();
        TPasien = new widget.TextBox();
        Tanggal = new widget.Tanggal();
        TNoRM = new widget.TextBox();
        jLabel16 = new widget.Label();
        Jam = new widget.ComboBox();
        Menit = new widget.ComboBox();
        Detik = new widget.ComboBox();
        ChkKejadian = new widget.CekBox();
        jLabel18 = new widget.Label();
        KdDokter = new widget.TextBox();
        NmDokter = new widget.TextBox();
        BtnDokter = new widget.Button();
        jLabel8 = new widget.Label();
        TglLahir = new widget.TextBox();
        jLabel12 = new widget.Label();
        jLabel13 = new widget.Label();
        Luka = new javax.swing.JComboBox<>();
        jLabel14 = new widget.Label();
        jLabel15 = new widget.Label();
        jLabel17 = new widget.Label();
        jLabel23 = new widget.Label();
        jLabel24 = new widget.Label();
        Granulasi = new javax.swing.JComboBox<>();
        RawatLuka = new javax.swing.JComboBox<>();
        Nekrotic = new javax.swing.JComboBox<>();
        Bullae = new javax.swing.JComboBox<>();
        jLabel25 = new widget.Label();
        Krusta = new javax.swing.JComboBox<>();
        jLabel20 = new widget.Label();
        JenisLokasi = new javax.swing.JComboBox<>();
        jLabel22 = new widget.Label();
        jLabel26 = new widget.Label();
        jLabel27 = new widget.Label();
        jLabel28 = new widget.Label();
        jLabel29 = new widget.Label();
        jLabel30 = new widget.Label();
        Bengkak = new javax.swing.JComboBox<>();
        Nyeri = new javax.swing.JComboBox<>();
        Kemerahan = new javax.swing.JComboBox<>();
        Drainase = new javax.swing.JComboBox<>();
        jLabel31 = new widget.Label();
        Demam = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        KetRawat = new javax.swing.JTextArea();
        Slought = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        BtnPrint1 = new widget.Button();
        jLabel32 = new widget.Label();
        Pus = new javax.swing.JComboBox<>();
        ChkInput = new widget.CekBox();

        jPopupMenu1.setName("jPopupMenu1"); // NOI18N

        MnCatatanKeperawatanRawatInap.setBackground(new java.awt.Color(255, 255, 254));
        MnCatatanKeperawatanRawatInap.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnCatatanKeperawatanRawatInap.setForeground(new java.awt.Color(50, 50, 50));
        MnCatatanKeperawatanRawatInap.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnCatatanKeperawatanRawatInap.setText("Formulir Catatan Keperawatan Rawat Inap");
        MnCatatanKeperawatanRawatInap.setName("MnCatatanKeperawatanRawatInap"); // NOI18N
        MnCatatanKeperawatanRawatInap.setPreferredSize(new java.awt.Dimension(260, 26));
        MnCatatanKeperawatanRawatInap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnCatatanKeperawatanRawatInapActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnCatatanKeperawatanRawatInap);

        JK.setHighlighter(null);
        JK.setName("JK"); // NOI18N

        Umur.setHighlighter(null);
        Umur.setName("Umur"); // NOI18N

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Pemeriksaan Rawat Luka ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setFont(new java.awt.Font("Tahoma", 2, 12)); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);
        Scroll.setPreferredSize(new java.awt.Dimension(452, 200));

        tbObat.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbObat.setComponentPopupMenu(jPopupMenu1);
        tbObat.setName("tbObat"); // NOI18N
        tbObat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbObatMouseClicked(evt);
            }
        });
        tbObat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbObatKeyPressed(evt);
            }
        });
        Scroll.setViewportView(tbObat);

        internalFrame1.add(Scroll, java.awt.BorderLayout.CENTER);

        jPanel3.setName("jPanel3"); // NOI18N
        jPanel3.setOpaque(false);
        jPanel3.setPreferredSize(new java.awt.Dimension(44, 100));
        jPanel3.setLayout(new java.awt.BorderLayout(1, 1));

        panelGlass8.setName("panelGlass8"); // NOI18N
        panelGlass8.setPreferredSize(new java.awt.Dimension(44, 44));
        panelGlass8.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        BtnSimpan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/save-16x16.png"))); // NOI18N
        BtnSimpan.setMnemonic('S');
        BtnSimpan.setText("Simpan");
        BtnSimpan.setToolTipText("Alt+S");
        BtnSimpan.setName("BtnSimpan"); // NOI18N
        BtnSimpan.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSimpanActionPerformed(evt);
            }
        });
        BtnSimpan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnSimpanKeyPressed(evt);
            }
        });
        panelGlass8.add(BtnSimpan);

        BtnBatal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Cancel-2-16x16.png"))); // NOI18N
        BtnBatal.setMnemonic('B');
        BtnBatal.setText("Baru");
        BtnBatal.setToolTipText("Alt+B");
        BtnBatal.setName("BtnBatal"); // NOI18N
        BtnBatal.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnBatal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnBatalActionPerformed(evt);
            }
        });
        BtnBatal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnBatalKeyPressed(evt);
            }
        });
        panelGlass8.add(BtnBatal);

        BtnHapus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/stop_f2.png"))); // NOI18N
        BtnHapus.setMnemonic('H');
        BtnHapus.setText("Hapus");
        BtnHapus.setToolTipText("Alt+H");
        BtnHapus.setName("BtnHapus"); // NOI18N
        BtnHapus.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnHapusActionPerformed(evt);
            }
        });
        BtnHapus.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnHapusKeyPressed(evt);
            }
        });
        panelGlass8.add(BtnHapus);

        BtnEdit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/inventaris.png"))); // NOI18N
        BtnEdit.setMnemonic('G');
        BtnEdit.setText("Ganti");
        BtnEdit.setToolTipText("Alt+G");
        BtnEdit.setName("BtnEdit"); // NOI18N
        BtnEdit.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnEditActionPerformed(evt);
            }
        });
        BtnEdit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnEditKeyPressed(evt);
            }
        });
        panelGlass8.add(BtnEdit);

        BtnPrint.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/b_print.png"))); // NOI18N
        BtnPrint.setMnemonic('T');
        BtnPrint.setText("Cetak");
        BtnPrint.setToolTipText("Alt+T");
        BtnPrint.setName("BtnPrint"); // NOI18N
        BtnPrint.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnPrint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPrintActionPerformed(evt);
            }
        });
        BtnPrint.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnPrintKeyPressed(evt);
            }
        });
        panelGlass8.add(BtnPrint);

        jLabel7.setText("Record :");
        jLabel7.setName("jLabel7"); // NOI18N
        jLabel7.setPreferredSize(new java.awt.Dimension(80, 23));
        panelGlass8.add(jLabel7);

        LCount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount.setText("0");
        LCount.setName("LCount"); // NOI18N
        LCount.setPreferredSize(new java.awt.Dimension(70, 23));
        panelGlass8.add(LCount);

        BtnKeluar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/exit.png"))); // NOI18N
        BtnKeluar.setMnemonic('K');
        BtnKeluar.setText("Keluar");
        BtnKeluar.setToolTipText("Alt+K");
        BtnKeluar.setName("BtnKeluar"); // NOI18N
        BtnKeluar.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnKeluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnKeluarActionPerformed(evt);
            }
        });
        BtnKeluar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnKeluarKeyPressed(evt);
            }
        });
        panelGlass8.add(BtnKeluar);

        jPanel3.add(panelGlass8, java.awt.BorderLayout.CENTER);

        panelGlass9.setName("panelGlass9"); // NOI18N
        panelGlass9.setPreferredSize(new java.awt.Dimension(44, 44));
        panelGlass9.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        jLabel19.setText("Tanggal :");
        jLabel19.setName("jLabel19"); // NOI18N
        jLabel19.setPreferredSize(new java.awt.Dimension(60, 23));
        panelGlass9.add(jLabel19);

        DTPCari1.setForeground(new java.awt.Color(50, 70, 50));
        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "29-11-2024" }));
        DTPCari1.setDisplayFormat("dd-MM-yyyy");
        DTPCari1.setName("DTPCari1"); // NOI18N
        DTPCari1.setOpaque(false);
        DTPCari1.setPreferredSize(new java.awt.Dimension(95, 23));
        panelGlass9.add(DTPCari1);

        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel21.setText("s.d.");
        jLabel21.setName("jLabel21"); // NOI18N
        jLabel21.setPreferredSize(new java.awt.Dimension(23, 23));
        panelGlass9.add(jLabel21);

        DTPCari2.setForeground(new java.awt.Color(50, 70, 50));
        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "29-11-2024" }));
        DTPCari2.setDisplayFormat("dd-MM-yyyy");
        DTPCari2.setName("DTPCari2"); // NOI18N
        DTPCari2.setOpaque(false);
        DTPCari2.setPreferredSize(new java.awt.Dimension(95, 23));
        panelGlass9.add(DTPCari2);

        jLabel6.setText("Key Word :");
        jLabel6.setName("jLabel6"); // NOI18N
        jLabel6.setPreferredSize(new java.awt.Dimension(90, 23));
        panelGlass9.add(jLabel6);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(310, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelGlass9.add(TCari);

        BtnCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCari.setMnemonic('3');
        BtnCari.setToolTipText("Alt+3");
        BtnCari.setName("BtnCari"); // NOI18N
        BtnCari.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnCari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCariActionPerformed(evt);
            }
        });
        BtnCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnCariKeyPressed(evt);
            }
        });
        panelGlass9.add(BtnCari);

        BtnAll.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Search-16x16.png"))); // NOI18N
        BtnAll.setMnemonic('M');
        BtnAll.setToolTipText("Alt+M");
        BtnAll.setName("BtnAll"); // NOI18N
        BtnAll.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAllActionPerformed(evt);
            }
        });
        BtnAll.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnAllKeyPressed(evt);
            }
        });
        panelGlass9.add(BtnAll);

        jPanel3.add(panelGlass9, java.awt.BorderLayout.PAGE_START);

        internalFrame1.add(jPanel3, java.awt.BorderLayout.PAGE_END);

        PanelInput.setName("PanelInput"); // NOI18N
        PanelInput.setOpaque(false);
        PanelInput.setPreferredSize(new java.awt.Dimension(192, 400));
        PanelInput.setLayout(new java.awt.BorderLayout(1, 1));

        FormInput.setBackground(new java.awt.Color(250, 255, 245));
        FormInput.setName("FormInput"); // NOI18N
        FormInput.setPreferredSize(new java.awt.Dimension(100, 225));
        FormInput.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                FormInputAncestorAdded(evt);
            }
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });
        FormInput.setLayout(null);

        jLabel4.setText("No.Rawat :");
        jLabel4.setName("jLabel4"); // NOI18N
        FormInput.add(jLabel4);
        jLabel4.setBounds(0, 10, 70, 23);

        TNoRw.setHighlighter(null);
        TNoRw.setName("TNoRw"); // NOI18N
        TNoRw.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNoRwKeyPressed(evt);
            }
        });
        FormInput.add(TNoRw);
        TNoRw.setBounds(74, 10, 136, 23);

        TPasien.setEditable(false);
        TPasien.setHighlighter(null);
        TPasien.setName("TPasien"); // NOI18N
        TPasien.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TPasienKeyPressed(evt);
            }
        });
        FormInput.add(TPasien);
        TPasien.setBounds(326, 10, 295, 23);

        Tanggal.setForeground(new java.awt.Color(50, 70, 50));
        Tanggal.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "29-11-2024" }));
        Tanggal.setDisplayFormat("dd-MM-yyyy");
        Tanggal.setName("Tanggal"); // NOI18N
        Tanggal.setOpaque(false);
        Tanggal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TanggalKeyPressed(evt);
            }
        });
        FormInput.add(Tanggal);
        Tanggal.setBounds(74, 40, 90, 23);

        TNoRM.setEditable(false);
        TNoRM.setHighlighter(null);
        TNoRM.setName("TNoRM"); // NOI18N
        TNoRM.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNoRMKeyPressed(evt);
            }
        });
        FormInput.add(TNoRM);
        TNoRM.setBounds(212, 10, 112, 23);

        jLabel16.setText("Tanggal :");
        jLabel16.setName("jLabel16"); // NOI18N
        jLabel16.setVerifyInputWhenFocusTarget(false);
        FormInput.add(jLabel16);
        jLabel16.setBounds(0, 40, 70, 23);

        Jam.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23" }));
        Jam.setName("Jam"); // NOI18N
        Jam.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                JamKeyPressed(evt);
            }
        });
        FormInput.add(Jam);
        Jam.setBounds(168, 40, 62, 23);

        Menit.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        Menit.setName("Menit"); // NOI18N
        Menit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MenitKeyPressed(evt);
            }
        });
        FormInput.add(Menit);
        Menit.setBounds(233, 40, 62, 23);

        Detik.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        Detik.setName("Detik"); // NOI18N
        Detik.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DetikKeyPressed(evt);
            }
        });
        FormInput.add(Detik);
        Detik.setBounds(298, 40, 62, 23);

        ChkKejadian.setBorder(null);
        ChkKejadian.setSelected(true);
        ChkKejadian.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        ChkKejadian.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ChkKejadian.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ChkKejadian.setName("ChkKejadian"); // NOI18N
        FormInput.add(ChkKejadian);
        ChkKejadian.setBounds(363, 40, 23, 23);

        jLabel18.setText("Dokter :");
        jLabel18.setName("jLabel18"); // NOI18N
        FormInput.add(jLabel18);
        jLabel18.setBounds(390, 40, 70, 23);

        KdDokter.setEditable(false);
        KdDokter.setHighlighter(null);
        KdDokter.setName("KdDokter"); // NOI18N
        KdDokter.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KdDokterKeyPressed(evt);
            }
        });
        FormInput.add(KdDokter);
        KdDokter.setBounds(460, 40, 104, 23);

        NmDokter.setEditable(false);
        NmDokter.setName("NmDokter"); // NOI18N
        FormInput.add(NmDokter);
        NmDokter.setBounds(570, 40, 187, 23);

        BtnDokter.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnDokter.setMnemonic('2');
        BtnDokter.setToolTipText("ALt+2");
        BtnDokter.setName("BtnDokter"); // NOI18N
        BtnDokter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnDokterActionPerformed(evt);
            }
        });
        BtnDokter.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnDokterKeyPressed(evt);
            }
        });
        FormInput.add(BtnDokter);
        BtnDokter.setBounds(761, 40, 28, 23);

        jLabel8.setText("Tgl.Lahir :");
        jLabel8.setName("jLabel8"); // NOI18N
        FormInput.add(jLabel8);
        jLabel8.setBounds(625, 10, 60, 23);

        TglLahir.setHighlighter(null);
        TglLahir.setName("TglLahir"); // NOI18N
        FormInput.add(TglLahir);
        TglLahir.setBounds(689, 10, 100, 23);

        jLabel12.setText("Keterangan :");
        jLabel12.setName("jLabel12"); // NOI18N
        FormInput.add(jLabel12);
        jLabel12.setBounds(230, 270, 70, 23);

        jLabel13.setText("Klasifikasi Luka :");
        jLabel13.setName("jLabel13"); // NOI18N
        FormInput.add(jLabel13);
        jLabel13.setBounds(10, 80, 90, 23);

        Luka.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Bersih", "Kotor", "Bersih Terkontaminasi", "Terkontaminasi" }));
        Luka.setName("Luka"); // NOI18N
        Luka.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LukaActionPerformed(evt);
            }
        });
        FormInput.add(Luka);
        Luka.setBounds(110, 80, 120, 24);

        jLabel14.setText("Luka Dibersihkan dari :");
        jLabel14.setName("jLabel14"); // NOI18N
        FormInput.add(jLabel14);
        jLabel14.setBounds(0, 210, 110, 23);

        jLabel15.setText("Bullae :");
        jLabel15.setName("jLabel15"); // NOI18N
        FormInput.add(jLabel15);
        jLabel15.setBounds(300, 240, 100, 23);

        jLabel17.setText("Nekrotic :");
        jLabel17.setName("jLabel17"); // NOI18N
        FormInput.add(jLabel17);
        jLabel17.setBounds(310, 210, 90, 23);

        jLabel23.setText("Pus :");
        jLabel23.setToolTipText("");
        jLabel23.setName("jLabel23"); // NOI18N
        FormInput.add(jLabel23);
        jLabel23.setBounds(510, 240, 120, 23);

        jLabel24.setText("Slought :");
        jLabel24.setName("jLabel24"); // NOI18N
        FormInput.add(jLabel24);
        jLabel24.setBounds(110, 210, 90, 23);

        Granulasi.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tidak", "Ya" }));
        Granulasi.setName("Granulasi"); // NOI18N
        FormInput.add(Granulasi);
        Granulasi.setBounds(640, 210, 72, 24);

        RawatLuka.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Daryantulle", "SSD", "Lainnya" }));
        RawatLuka.setName("RawatLuka"); // NOI18N
        FormInput.add(RawatLuka);
        RawatLuka.setBounds(110, 270, 110, 24);

        Nekrotic.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tidak", "Ya" }));
        Nekrotic.setName("Nekrotic"); // NOI18N
        FormInput.add(Nekrotic);
        Nekrotic.setBounds(420, 210, 72, 24);

        Bullae.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tidak", "Ya" }));
        Bullae.setName("Bullae"); // NOI18N
        FormInput.add(Bullae);
        Bullae.setBounds(420, 240, 72, 24);

        jLabel25.setText("Krusta :");
        jLabel25.setName("jLabel25"); // NOI18N
        FormInput.add(jLabel25);
        jLabel25.setBounds(110, 240, 90, 23);

        Krusta.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tidak", "Ya" }));
        Krusta.setName("Krusta"); // NOI18N
        FormInput.add(Krusta);
        Krusta.setBounds(220, 240, 72, 24);

        jLabel20.setText("Identifikasi Luka :");
        jLabel20.setName("jLabel20"); // NOI18N
        FormInput.add(jLabel20);
        jLabel20.setBounds(10, 110, 90, 23);

        JenisLokasi.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Superfisial", "Organ", "Fascia" }));
        JenisLokasi.setName("JenisLokasi"); // NOI18N
        FormInput.add(JenisLokasi);
        JenisLokasi.setBounds(110, 170, 140, 24);

        jLabel22.setText("Jenis Lokasi Infeksi :");
        jLabel22.setName("jLabel22"); // NOI18N
        FormInput.add(jLabel22);
        jLabel22.setBounds(10, 170, 100, 23);

        jLabel26.setText("Rawat Luka :");
        jLabel26.setName("jLabel26"); // NOI18N
        FormInput.add(jLabel26);
        jLabel26.setBounds(30, 270, 70, 23);

        jLabel27.setText("Drainase Purulen :");
        jLabel27.setName("jLabel27"); // NOI18N
        FormInput.add(jLabel27);
        jLabel27.setBounds(290, 140, 100, 23);

        jLabel28.setText("Kemerahan :");
        jLabel28.setName("jLabel28"); // NOI18N
        FormInput.add(jLabel28);
        jLabel28.setBounds(300, 110, 90, 23);

        jLabel29.setText("Bengkak Terlokalisir:");
        jLabel29.setName("jLabel29"); // NOI18N
        FormInput.add(jLabel29);
        jLabel29.setBounds(510, 110, 110, 23);

        jLabel30.setText("Nyeri Lokal :");
        jLabel30.setName("jLabel30"); // NOI18N
        FormInput.add(jLabel30);
        jLabel30.setBounds(100, 110, 90, 23);

        Bengkak.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Ya", "Tidak" }));
        Bengkak.setName("Bengkak"); // NOI18N
        FormInput.add(Bengkak);
        Bengkak.setBounds(640, 110, 72, 24);

        Nyeri.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Ya", "Tidak" }));
        Nyeri.setName("Nyeri"); // NOI18N
        FormInput.add(Nyeri);
        Nyeri.setBounds(210, 110, 72, 24);

        Kemerahan.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Ya", "Tidak" }));
        Kemerahan.setName("Kemerahan"); // NOI18N
        FormInput.add(Kemerahan);
        Kemerahan.setBounds(410, 110, 72, 24);

        Drainase.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Ya", "Tidak" }));
        Drainase.setName("Drainase"); // NOI18N
        FormInput.add(Drainase);
        Drainase.setBounds(410, 140, 72, 24);

        jLabel31.setText("Demam :");
        jLabel31.setName("jLabel31"); // NOI18N
        FormInput.add(jLabel31);
        jLabel31.setBounds(100, 140, 90, 23);

        Demam.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Ya", "Tidak" }));
        Demam.setName("Demam"); // NOI18N
        FormInput.add(Demam);
        Demam.setBounds(210, 140, 72, 24);

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        KetRawat.setColumns(20);
        KetRawat.setRows(5);
        KetRawat.setName("KetRawat"); // NOI18N
        jScrollPane1.setViewportView(KetRawat);

        FormInput.add(jScrollPane1);
        jScrollPane1.setBounds(300, 270, 240, 70);

        Slought.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tidak", "Ya" }));
        Slought.setName("Slought"); // NOI18N
        FormInput.add(Slought);
        Slought.setBounds(220, 210, 72, 24);

        jLabel1.setText("SIMPAN TERLEBIH DAHUL DATA INI KEMUDIAN SILAHKAN KE BERKAS DIGITAL UNTUK UPLOAD FOTO RAWAT LUKA");
        jLabel1.setName("jLabel1"); // NOI18N
        FormInput.add(jLabel1);
        jLabel1.setBounds(10, 350, 830, 18);

        BtnPrint1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/b_print.png"))); // NOI18N
        BtnPrint1.setMnemonic('T');
        BtnPrint1.setText("Berkas Digital");
        BtnPrint1.setToolTipText("Alt+T");
        BtnPrint1.setName("BtnPrint1"); // NOI18N
        BtnPrint1.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnPrint1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPrint1ActionPerformed(evt);
            }
        });
        BtnPrint1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnPrint1KeyPressed(evt);
            }
        });
        FormInput.add(BtnPrint1);
        BtnPrint1.setBounds(550, 270, 160, 30);

        jLabel32.setText("Granulasi:");
        jLabel32.setName("jLabel32"); // NOI18N
        FormInput.add(jLabel32);
        jLabel32.setBounds(510, 210, 120, 23);

        Pus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tidak", "Ya" }));
        Pus.setName("Pus"); // NOI18N
        FormInput.add(Pus);
        Pus.setBounds(640, 240, 80, 24);

        PanelInput.add(FormInput, java.awt.BorderLayout.CENTER);

        ChkInput.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/143.png"))); // NOI18N
        ChkInput.setMnemonic('I');
        ChkInput.setText(".: Input Data");
        ChkInput.setToolTipText("Alt+I");
        ChkInput.setBorderPainted(true);
        ChkInput.setBorderPaintedFlat(true);
        ChkInput.setFocusable(false);
        ChkInput.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ChkInput.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ChkInput.setName("ChkInput"); // NOI18N
        ChkInput.setPreferredSize(new java.awt.Dimension(192, 20));
        ChkInput.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/143.png"))); // NOI18N
        ChkInput.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/145.png"))); // NOI18N
        ChkInput.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/145.png"))); // NOI18N
        ChkInput.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChkInputActionPerformed(evt);
            }
        });
        PanelInput.add(ChkInput, java.awt.BorderLayout.PAGE_END);

        internalFrame1.add(PanelInput, java.awt.BorderLayout.PAGE_START);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {
        if (TNoRw.getText().trim().equals("") || TNoRM.getText().trim().equals("") || TPasien.getText().trim().equals("")) {
            Valid.textKosong(TNoRw, "pasien");
        } else if (KdDokter.getText().trim().equals("")) {
            Valid.textKosong(BtnDokter, "Dokter");
        } else {
            if (Sequel.menyimpantf("pemeriksaan_rawat_luka", "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?", "Data", 19, new String[]{
                TNoRw.getText(), Valid.SetTgl(Tanggal.getSelectedItem() + ""), Jam.getSelectedItem() + ":" + Menit.getSelectedItem() + ":" + Detik.getSelectedItem(),
                KdDokter.getText(), Luka.getSelectedItem().toString(), Nyeri.getSelectedItem().toString(), Demam.getSelectedItem().toString(), Kemerahan.getSelectedItem().toString(),
                Drainase.getSelectedItem().toString(), Bengkak.getSelectedItem().toString(), JenisLokasi.getSelectedItem().toString(),
                Slought.getSelectedItem().toString(), Krusta.getSelectedItem().toString(), Nekrotic.getSelectedItem().toString(),
                Bullae.getSelectedItem().toString(), Granulasi.getSelectedItem().toString(),Pus.getSelectedItem().toString(),
                RawatLuka.getSelectedItem().toString(), KetRawat.getText()
            }) == true) {
                tampil();       // Tampilkan data baru
                emptTeks();     // Reset semua input di form
            }
        }
    }


    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnSimpanActionPerformed(null);
        } else {
            //  Valid.pindah(evt,Uraian,BtnBatal);
        }
}//GEN-LAST:event_BtnSimpanKeyPressed

    private void BtnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnBatalActionPerformed
        emptTeks();
        ChkInput.setSelected(true);
        isForm();
}//GEN-LAST:event_BtnBatalActionPerformed

    private void BtnBatalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnBatalKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            emptTeks();
        } else {
            Valid.pindah(evt, BtnSimpan, BtnHapus);
        }
}//GEN-LAST:event_BtnBatalKeyPressed

    private void BtnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnHapusActionPerformed
        if (tbObat.getSelectedRow() > -1) {
            if (akses.getkode().equals("Admin Utama")) {
                hapus();
            } else {
                if (KdDokter.getText().equals(tbObat.getValueAt(tbObat.getSelectedRow(), 23).toString())) {
                    hapus();
                } else {
                    JOptionPane.showMessageDialog(null, "Hanya bisa dihapus oleh petugas yang bersangkutan..!!");
                }
            }
        } else {
            JOptionPane.showMessageDialog(rootPane, "Silahkan anda pilih data terlebih dahulu..!!");
        }
}//GEN-LAST:event_BtnHapusActionPerformed

    private void BtnHapusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnHapusKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnHapusActionPerformed(null);
        } else {
            Valid.pindah(evt, BtnBatal, BtnEdit);
        }
}//GEN-LAST:event_BtnHapusKeyPressed

    private void BtnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnEditActionPerformed
        if (TNoRw.getText().trim().equals("") || TPasien.getText().trim().equals("")) {
            Valid.textKosong(TNoRw, "pasien");
        } else if (KdDokter.getText().trim().equals("") || NmDokter.getText().trim().equals("")) {
            Valid.textKosong(KdDokter, "Petugas");
        } else {
            if (tbObat.getSelectedRow() > -1) {
                if (akses.getkode().equals("Admin Utama")) {
                    ganti();
                } else {
                    if (KdDokter.getText().equals(tbObat.getValueAt(tbObat.getSelectedRow(),23).toString())) {
                        ganti();
                    } else {
                        JOptionPane.showMessageDialog(null, "Hanya bisa diganti oleh petugas yang bersangkutan..!!");
                    }
                }
            } else {
                JOptionPane.showMessageDialog(rootPane, "Silahkan anda pilih data terlebih dahulu..!!");
            }
        }
}//GEN-LAST:event_BtnEditActionPerformed

    private void BtnEditKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnEditKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnEditActionPerformed(null);
        } else {
            Valid.pindah(evt, BtnHapus, BtnPrint);
        }
}//GEN-LAST:event_BtnEditKeyPressed

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        //petugas.dispose();
        dispose();
}//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnKeluarActionPerformed(null);
        } else {
            Valid.pindah(evt, BtnEdit, TCari);
        }
}//GEN-LAST:event_BtnKeluarKeyPressed

    private void BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrintActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        if (tabMode.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
            BtnBatal.requestFocus();
        } else if (tabMode.getRowCount() != 0) {
            Map<String, Object> param = new HashMap<>();
            param.put("namars", akses.getnamars());
            param.put("alamatrs", akses.getalamatrs());
            param.put("kotars", akses.getkabupatenrs());
            param.put("propinsirs", akses.getpropinsirs());
            param.put("kontakrs", akses.getkontakrs());
            param.put("emailrs", akses.getemailrs());
            param.put("logo", Sequel.cariGambar("select setting.logo from setting"));

            if (TCari.getText().trim().equals("")) {
                Valid.MyReportqry("rptDataCatatanKeperawatanRanap.jasper", "report", "::[ Data Catatan Keperawatan Rawat Inap ]::",
                        "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,reg_periksa.umurdaftar,reg_periksa.sttsumur,"
                        + "pasien.jk,pasien.tgl_lahir,catatan_keperawatan_ranap.tanggal,catatan_keperawatan_ranap.jam,catatan_keperawatan_ranap.uraian,"
                        + "catatan_keperawatan_ranap.nip,petugas.nama from catatan_keperawatan_ranap inner join reg_periksa on catatan_keperawatan_ranap.no_rawat=reg_periksa.no_rawat "
                        + "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "
                        + "inner join petugas on catatan_keperawatan_ranap.nip=petugas.nip where "
                        + "catatan_keperawatan_ranap.tanggal between '" + Valid.SetTgl(DTPCari1.getSelectedItem() + "") + "' and '" + Valid.SetTgl(DTPCari2.getSelectedItem() + "") + "' order by catatan_keperawatan_ranap.tanggal", param);
            } else {
                Valid.MyReportqry("rptDataCatatanKeperawatanRanap.jasper", "report", "::[ Data Catatan Keperawatan Rawat Inap ]::",
                        "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,reg_periksa.umurdaftar,reg_periksa.sttsumur,"
                        + "pasien.jk,pasien.tgl_lahir,catatan_keperawatan_ranap.tanggal,catatan_keperawatan_ranap.jam,catatan_keperawatan_ranap.uraian,"
                        + "catatan_keperawatan_ranap.nip,petugas.nama from catatan_keperawatan_ranap inner join reg_periksa on catatan_keperawatan_ranap.no_rawat=reg_periksa.no_rawat "
                        + "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "
                        + "inner join petugas on catatan_keperawatan_ranap.nip=petugas.nip where "
                        + "catatan_keperawatan_ranap.tanggal between '" + Valid.SetTgl(DTPCari1.getSelectedItem() + "") + "' and '" + Valid.SetTgl(DTPCari2.getSelectedItem() + "") + "' and "
                        + "(reg_periksa.no_rawat like '%" + TCari.getText().trim() + "%' or pasien.no_rkm_medis like '%" + TCari.getText().trim() + "%' or "
                        + "pasien.nm_pasien like '%" + TCari.getText().trim() + "%' or catatan_keperawatan_ranap.nip like '%" + TCari.getText().trim() + "%' or petugas.nama like '%" + TCari.getText().trim() + "%') "
                        + "order by catatan_keperawatan_ranap.tanggal ", param);
            }
        }
        this.setCursor(Cursor.getDefaultCursor());
}//GEN-LAST:event_BtnPrintActionPerformed

    private void BtnPrintKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPrintKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnPrintActionPerformed(null);
        } else {
            Valid.pindah(evt, BtnEdit, BtnKeluar);
        }
}//GEN-LAST:event_BtnPrintKeyPressed

    private void TCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCariKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            BtnCariActionPerformed(null);
        } else if (evt.getKeyCode() == KeyEvent.VK_PAGE_DOWN) {
            BtnCari.requestFocus();
        } else if (evt.getKeyCode() == KeyEvent.VK_PAGE_UP) {
            BtnKeluar.requestFocus();
        }
}//GEN-LAST:event_TCariKeyPressed

    private void BtnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariActionPerformed
        tampil();
}//GEN-LAST:event_BtnCariActionPerformed

    private void BtnCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCariKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnCariActionPerformed(null);
        } else {
            Valid.pindah(evt, TCari, BtnAll);
        }
}//GEN-LAST:event_BtnCariKeyPressed

    private void BtnAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAllActionPerformed
        TCari.setText("");
        tampil();
}//GEN-LAST:event_BtnAllActionPerformed

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            tampil();
            TCari.setText("");
        } else {
            Valid.pindah(evt, BtnCari, TPasien);
        }
}//GEN-LAST:event_BtnAllKeyPressed

    private void tbObatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbObatMouseClicked
        if (tabMode.getRowCount() != 0) {
            try {
                getData();
            } catch (java.lang.NullPointerException e) {
            }
        }
}//GEN-LAST:event_tbObatMouseClicked

    private void tbObatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbObatKeyPressed
        if (tabMode.getRowCount() != 0) {
            if ((evt.getKeyCode() == KeyEvent.VK_ENTER) || (evt.getKeyCode() == KeyEvent.VK_UP) || (evt.getKeyCode() == KeyEvent.VK_DOWN)) {
                try {
                    getData();
                } catch (java.lang.NullPointerException e) {
                }
            }
        }
}//GEN-LAST:event_tbObatKeyPressed

    private void ChkInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkInputActionPerformed
        isForm();
    }//GEN-LAST:event_ChkInputActionPerformed

    private void MnCatatanKeperawatanRawatInapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnCatatanKeperawatanRawatInapActionPerformed

    }//GEN-LAST:event_MnCatatanKeperawatanRawatInapActionPerformed

    private void LukaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LukaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_LukaActionPerformed

    private void BtnDokterKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnDokterKeyPressed
        //        Valid.pindah(evt,Detik,Uraian);
    }//GEN-LAST:event_BtnDokterKeyPressed

    private void BtnDokterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnDokterActionPerformed
        dokter.isCek();
        dokter.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
        dokter.setLocationRelativeTo(internalFrame1);
        dokter.setAlwaysOnTop(false);
        dokter.setVisible(true);
    }//GEN-LAST:event_BtnDokterActionPerformed

    private void KdDokterKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KdDokterKeyPressed

    }//GEN-LAST:event_KdDokterKeyPressed

    private void DetikKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DetikKeyPressed
        Valid.pindah(evt, Menit, BtnDokter);
    }//GEN-LAST:event_DetikKeyPressed

    private void MenitKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_MenitKeyPressed
        Valid.pindah(evt, Jam, Detik);
    }//GEN-LAST:event_MenitKeyPressed

    private void JamKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JamKeyPressed
        Valid.pindah(evt, Tanggal, Menit);
    }//GEN-LAST:event_JamKeyPressed

    private void TNoRMKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TNoRMKeyPressed
        // Valid.pindah(evt, TNm, BtnSimpan);
    }//GEN-LAST:event_TNoRMKeyPressed

    private void TanggalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TanggalKeyPressed
        Valid.pindah(evt, TCari, Jam);
    }//GEN-LAST:event_TanggalKeyPressed

    private void TPasienKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TPasienKeyPressed
        Valid.pindah(evt, TCari, BtnSimpan);
    }//GEN-LAST:event_TPasienKeyPressed

    private void TNoRwKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TNoRwKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_PAGE_DOWN) {
            isRawat();
        } else {
            Valid.pindah(evt, TCari, Tanggal);
        }
    }//GEN-LAST:event_TNoRwKeyPressed

    private void BtnPrint1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrint1ActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            DlgBerkasRawat berkas=new DlgBerkasRawat(null,true);
            berkas.setJudul("::[ Berkas Digital Perawatan ]::","berkasrawat/pages");
            try {
                if(akses.gethapus_berkas_digital_perawatan()==true){
                    berkas.loadURL("http://"+koneksiDB.HOSTHYBRIDWEB()+":"+koneksiDB.PORTWEB()+"/"+koneksiDB.HYBRIDWEB()+"/"+"berkasrawat/login2.php?act=login&usere="+koneksiDB.USERHYBRIDWEB()+"&passwordte="+koneksiDB.PASHYBRIDWEB()+"&no_rawat="+TNoRw.getText());
                }else{
                    berkas.loadURL("http://"+koneksiDB.HOSTHYBRIDWEB()+":"+koneksiDB.PORTWEB()+"/"+koneksiDB.HYBRIDWEB()+"/"+"berkasrawat/login2nonhapus.php?act=login&usere="+koneksiDB.USERHYBRIDWEB()+"&passwordte="+koneksiDB.PASHYBRIDWEB()+"&no_rawat="+TNoRw.getText());
                }   
            } catch (Exception ex) {
                System.out.println("Notifikasi : "+ex);
            }

            berkas.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            berkas.setLocationRelativeTo(internalFrame1);
            berkas.setVisible(true);
            this.setCursor(Cursor.getDefaultCursor());

    }//GEN-LAST:event_BtnPrint1ActionPerformed

    private void BtnPrint1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPrint1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnPrint1KeyPressed

    private void FormInputAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_FormInputAncestorAdded
        // TODO add your handling code here:
    }//GEN-LAST:event_FormInputAncestorAdded

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            RMPemeriksaanRawatLuka dialog = new RMPemeriksaanRawatLuka(new javax.swing.JFrame(), true);
            dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosing(java.awt.event.WindowEvent e) {
                    System.exit(0);
                }
            });
            dialog.setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> Bengkak;
    private widget.Button BtnAll;
    private widget.Button BtnBatal;
    private widget.Button BtnCari;
    private widget.Button BtnDokter;
    private widget.Button BtnEdit;
    private widget.Button BtnHapus;
    private widget.Button BtnKeluar;
    private widget.Button BtnPrint;
    private widget.Button BtnPrint1;
    private widget.Button BtnSimpan;
    private javax.swing.JComboBox<String> Bullae;
    private widget.CekBox ChkInput;
    private widget.CekBox ChkKejadian;
    private widget.Tanggal DTPCari1;
    private widget.Tanggal DTPCari2;
    private javax.swing.JComboBox<String> Demam;
    private widget.ComboBox Detik;
    private javax.swing.JComboBox<String> Drainase;
    private widget.PanelBiasa FormInput;
    private javax.swing.JComboBox<String> Granulasi;
    private widget.TextBox JK;
    private widget.ComboBox Jam;
    private javax.swing.JComboBox<String> JenisLokasi;
    private widget.TextBox KdDokter;
    private javax.swing.JComboBox<String> Kemerahan;
    private javax.swing.JTextArea KetRawat;
    private javax.swing.JComboBox<String> Krusta;
    private widget.Label LCount;
    private javax.swing.JComboBox<String> Luka;
    private widget.ComboBox Menit;
    private javax.swing.JMenuItem MnCatatanKeperawatanRawatInap;
    private javax.swing.JComboBox<String> Nekrotic;
    private widget.TextBox NmDokter;
    private javax.swing.JComboBox<String> Nyeri;
    private javax.swing.JPanel PanelInput;
    private javax.swing.JComboBox<String> Pus;
    private javax.swing.JComboBox<String> RawatLuka;
    private widget.ScrollPane Scroll;
    private javax.swing.JComboBox<String> Slought;
    private widget.TextBox TCari;
    private widget.TextBox TNoRM;
    private widget.TextBox TNoRw;
    private widget.TextBox TPasien;
    private widget.Tanggal Tanggal;
    private widget.TextBox TglLahir;
    private widget.TextBox Umur;
    private widget.InternalFrame internalFrame1;
    private javax.swing.JLabel jLabel1;
    private widget.Label jLabel12;
    private widget.Label jLabel13;
    private widget.Label jLabel14;
    private widget.Label jLabel15;
    private widget.Label jLabel16;
    private widget.Label jLabel17;
    private widget.Label jLabel18;
    private widget.Label jLabel19;
    private widget.Label jLabel20;
    private widget.Label jLabel21;
    private widget.Label jLabel22;
    private widget.Label jLabel23;
    private widget.Label jLabel24;
    private widget.Label jLabel25;
    private widget.Label jLabel26;
    private widget.Label jLabel27;
    private widget.Label jLabel28;
    private widget.Label jLabel29;
    private widget.Label jLabel30;
    private widget.Label jLabel31;
    private widget.Label jLabel32;
    private widget.Label jLabel4;
    private widget.Label jLabel6;
    private widget.Label jLabel7;
    private widget.Label jLabel8;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JScrollPane jScrollPane1;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelGlass9;
    private widget.Table tbObat;
    // End of variables declaration//GEN-END:variables

    public void tampil() {
        Valid.tabelKosong(tabMode);
        try {
            if (TCari.getText().toString().trim().equals("")) {
                ps = koneksi.prepareStatement(
                        "select reg_periksa.no_rawat, pasien.no_rkm_medis, pasien.nm_pasien, reg_periksa.umurdaftar, reg_periksa.sttsumur, "
                        + "pasien.jk, pasien.tgl_lahir, pemeriksaan_rawat_luka.tanggal, pemeriksaan_rawat_luka.jam, "
                        + "pemeriksaan_rawat_luka.klasifikasi_luka, pemeriksaan_rawat_luka.nyeri_lokal, pemeriksaan_rawat_luka.demam, "
                        + "pemeriksaan_rawat_luka.kemerahan, pemeriksaan_rawat_luka.drainase_purulen, pemeriksaan_rawat_luka.bengkak_terlokalisir, "
                        + "pemeriksaan_rawat_luka.jenis_lokasi, pemeriksaan_rawat_luka.slought, pemeriksaan_rawat_luka.krusta, "
                        + "pemeriksaan_rawat_luka.nekrotic, pemeriksaan_rawat_luka.bullae, pemeriksaan_rawat_luka.granulasi, pemeriksaan_rawat_luka.pus, "
                        + "pemeriksaan_rawat_luka.rawat_luka, pemeriksaan_rawat_luka.ket_rawat_luka, dokter.kd_dokter, dokter.nm_dokter "
                        + "from pemeriksaan_rawat_luka "
                        + "inner join reg_periksa on pemeriksaan_rawat_luka.no_rawat = reg_periksa.no_rawat "
                        + "inner join pasien on reg_periksa.no_rkm_medis = pasien.no_rkm_medis "
                        + "inner join dokter on pemeriksaan_rawat_luka.kd_dokter = dokter.kd_dokter "
                        + "where pemeriksaan_rawat_luka.tanggal between ? and ? order by pemeriksaan_rawat_luka.tanggal"
                );
            } else {
                ps = koneksi.prepareStatement(
                        "select reg_periksa.no_rawat, pasien.no_rkm_medis, pasien.nm_pasien, reg_periksa.umurdaftar, reg_periksa.sttsumur, "
                        + "pasien.jk, pasien.tgl_lahir, pemeriksaan_rawat_luka.tanggal, pemeriksaan_rawat_luka.jam, "
                        + "pemeriksaan_rawat_luka.klasifikasi_luka, pemeriksaan_rawat_luka.nyeri_lokal, pemeriksaan_rawat_luka.demam, "
                        + "pemeriksaan_rawat_luka.kemerahan, pemeriksaan_rawat_luka.drainase_purulen, pemeriksaan_rawat_luka.bengkak_terlokalisir, "
                        + "pemeriksaan_rawat_luka.jenis_lokasi, pemeriksaan_rawat_luka.slought, pemeriksaan_rawat_luka.krusta, "
                        + "pemeriksaan_rawat_luka.nekrotic, pemeriksaan_rawat_luka.bullae, pemeriksaan_rawat_luka.granulasi, pemeriksaan_rawat_luka.pus, "
                        + "pemeriksaan_rawat_luka.rawat_luka, pemeriksaan_rawat_luka.ket_rawat_luka, dokter.kd_dokter, dokter.nm_dokter "
                        + "from pemeriksaan_rawat_luka "
                        + "inner join reg_periksa on pemeriksaan_rawat_luka.no_rawat = reg_periksa.no_rawat "
                        + "inner join pasien on reg_periksa.no_rkm_medis = pasien.no_rkm_medis "
                        + "inner join dokter on pemeriksaan_rawat_luka.kd_dokter = dokter.kd_dokter "
                        + "where pemeriksaan_rawat_luka.tanggal between ? and ? and "
                        + "(reg_periksa.no_rawat like ? or pasien.no_rkm_medis like ? or pasien.nm_pasien like ? or dokter.nm_dokter like ?) "
                        + "order by pemeriksaan_rawat_luka.tanggal"
                );
            }

            try {
                if (TCari.getText().toString().trim().equals("")) {
                    ps.setString(1, Valid.SetTgl(DTPCari1.getSelectedItem() + "") + " 00:00:00");
                    ps.setString(2, Valid.SetTgl(DTPCari2.getSelectedItem() + "") + " 23:59:59");
                } else {
                    ps.setString(1, Valid.SetTgl(DTPCari1.getSelectedItem() + "") + " 00:00:00");
                    ps.setString(2, Valid.SetTgl(DTPCari2.getSelectedItem() + "") + " 23:59:59");
                    ps.setString(3, "%" + TCari.getText() + "%");
                    ps.setString(4, "%" + TCari.getText() + "%");
                    ps.setString(5, "%" + TCari.getText() + "%");
                    ps.setString(6, "%" + TCari.getText() + "%");
                }

                rs = ps.executeQuery();
                while (rs.next()) {
                    tabMode.addRow(new String[]{
                        rs.getString("no_rawat"), rs.getString("no_rkm_medis"), rs.getString("nm_pasien"),
                        rs.getString("umurdaftar") + " " + rs.getString("sttsumur"), rs.getString("jk"), rs.getString("tgl_lahir"),
                        rs.getString("tanggal"), rs.getString("jam"), rs.getString("klasifikasi_luka"), rs.getString("nyeri_lokal"),
                        rs.getString("demam"), rs.getString("kemerahan"), rs.getString("drainase_purulen"), rs.getString("bengkak_terlokalisir"),
                        rs.getString("jenis_lokasi"), rs.getString("slought"), rs.getString("krusta"), rs.getString("nekrotic"),
                        rs.getString("bullae"), rs.getString("granulasi"), rs.getString("pus"), rs.getString("rawat_luka"), rs.getString("ket_rawat_luka"),
                        rs.getString("kd_dokter"), rs.getString("nm_dokter")
                    });
                }
            } catch (Exception e) {
                System.out.println("Notif : " + e);
            } finally {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
            }
        } catch (Exception e) {
            System.out.println("Notifikasi : " + e);
        }
        LCount.setText("" + tabMode.getRowCount());
    }

    public void emptTeks() {
        // Reset semua elemen form ke nilai awal atau kosong
        Luka.setSelectedIndex(0);   // Reset Klasifikasi Luka
        Nyeri.setSelectedIndex(0);  // Reset Nyeri Lokal
        Demam.setSelectedIndex(0);  // Reset Demam
        Kemerahan.setSelectedIndex(0);  // Reset Kemerahan
        Drainase.setSelectedIndex(0);  // Reset Drainase Purulen
        Bengkak.setSelectedIndex(0);   // Reset Bengkak Terlokalisir
        JenisLokasi.setSelectedIndex(0);  // Reset Jenis Lokasi
        Slought.setSelectedIndex(0);  // Reset Slought
        Krusta.setSelectedIndex(0);   // Reset Kista
        Nekrotic.setSelectedIndex(0); // Reset Nekotik
        Bullae.setSelectedIndex(0);  // Reset Bulae
        Granulasi.setSelectedIndex(0); // Reset Granulasi
        Pus.setSelectedIndex(0);
        RawatLuka.setSelectedIndex(0); // Reset Rawat Luka
        KetRawat.setText("");          // Kosongkan keterangan rawat luka
        Tanggal.setDate(new Date());   // Reset tanggal ke hari ini
        Luka.requestFocus();           // Fokus kembali ke elemen Luka

    }

    private void getData() {
        if (tbObat.getSelectedRow() != -1) { // Cek apakah ada baris yang dipilih
            TNoRw.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 0).toString()); // Nomor Rawat
            TNoRM.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 1).toString()); // Nomor Rekam Medis
            TPasien.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 2).toString()); // Nama Pasien
            Umur.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 3).toString()); // Umur Pasien
            JK.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 4).toString()); // Jenis Kelamin
            TglLahir.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 5).toString()); // Tanggal Lahir
            Valid.SetTgl(Tanggal, tbObat.getValueAt(tbObat.getSelectedRow(), 6).toString()); // Tanggal Perawatan
            Valid.SetTgl(Tanggal, tbObat.getValueAt(tbObat.getSelectedRow(), 7).toString()); // Jam Perawatan
            Luka.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(), 8).toString()); // Klasifikasi Luka
            Nyeri.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(), 9).toString()); // Nyeri Lokal
            Demam.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(), 10).toString()); // Demam
            Kemerahan.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(), 11).toString()); // Kemerahan
            Drainase.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(), 12).toString()); // Drainase Purulen
            Bengkak.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(), 13).toString()); // Bengkak Terlokalisir
            JenisLokasi.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(), 14).toString()); // Jenis Lokasi
            Slought.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(), 15).toString()); // Slought
            Krusta.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(), 16).toString()); // Krusta
            Nekrotic.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(), 17).toString()); // Nekrotic
            Bullae.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(), 18).toString()); // Bullae
            Granulasi.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(), 19).toString()); // Granulasi
            Pus.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(), 20).toString());
            RawatLuka.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(), 21).toString()); // Rawat Luka
            KetRawat.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 22).toString()); // Keterangan Rawat Luka
            KdDokter.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 23).toString()); // Kode Dokter
            NmDokter.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 24).toString()); // Nama Dokter
        }
    }

    private void isRawat() {
        try {
            ps = koneksi.prepareStatement(
                    "select reg_periksa.no_rkm_medis,pasien.nm_pasien,pasien.jk,pasien.tgl_lahir,reg_periksa.tgl_registrasi,reg_periksa.umurdaftar,"
                    + "reg_periksa.sttsumur from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis where reg_periksa.no_rawat=?");
            try {
                ps.setString(1, TNoRw.getText());
                rs = ps.executeQuery();
                if (rs.next()) {
                    TNoRM.setText(rs.getString("no_rkm_medis"));
                    DTPCari1.setDate(rs.getDate("tgl_registrasi"));
                    TPasien.setText(rs.getString("nm_pasien"));
                    JK.setText(rs.getString("jk"));
                    Umur.setText(rs.getString("umurdaftar") + " " + rs.getString("sttsumur"));
                    TglLahir.setText(rs.getString("tgl_lahir"));
                }
            } catch (Exception e) {
                System.out.println("Notif : " + e);
            } finally {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
            }
        } catch (Exception e) {
            System.out.println("Notif : " + e);
        }
    }

    public void setNoRm(String norwt, Date tgl2) {
        TNoRw.setText(norwt);
        TCari.setText(norwt);
        DTPCari2.setDate(tgl2);
        isRawat();
        ChkInput.setSelected(true);
        isForm();
    }

    private void isForm() {
        if (ChkInput.isSelected() == true) {
            ChkInput.setVisible(false);
            PanelInput.setPreferredSize(new Dimension(WIDTH, 400));
            FormInput.setVisible(true);
            ChkInput.setVisible(true);
        } else if (ChkInput.isSelected() == false) {
            ChkInput.setVisible(false);
            PanelInput.setPreferredSize(new Dimension(WIDTH, 20));
            FormInput.setVisible(false);
            ChkInput.setVisible(true);
        }
    }

    public void isCek() {
        BtnSimpan.setEnabled(akses.gettindakan_ralan());
        BtnHapus.setEnabled(akses.gettindakan_ralan());
        BtnEdit.setEnabled(akses.gettindakan_ralan());
        BtnPrint.setEnabled(akses.gettindakan_ralan());
        if (akses.getjml2() >= 1) {
            KdDokter.setEditable(false);
            BtnDokter.setEnabled(false);
            KdDokter.setText(akses.getkode());
            NmDokter.setText(dokter.tampil3(KdDokter.getText()));
            if (NmDokter.getText().equals("")) {
                KdDokter.setText("");
                JOptionPane.showMessageDialog(null, "User login bukan Dokter...!!");
            }
        }
    }

    private void jam() {
        ActionListener taskPerformer = new ActionListener() {
            private int nilai_jam;
            private int nilai_menit;
            private int nilai_detik;

            public void actionPerformed(ActionEvent e) {
                String nol_jam = "";
                String nol_menit = "";
                String nol_detik = "";

                Date now = Calendar.getInstance().getTime();

                // Mengambil nilaj JAM, MENIT, dan DETIK Sekarang
                if (ChkKejadian.isSelected() == true) {
                    nilai_jam = now.getHours();
                    nilai_menit = now.getMinutes();
                    nilai_detik = now.getSeconds();
                } else if (ChkKejadian.isSelected() == false) {
                    nilai_jam = Jam.getSelectedIndex();
                    nilai_menit = Menit.getSelectedIndex();
                    nilai_detik = Detik.getSelectedIndex();
                }

                // Jika nilai JAM lebih kecil dari 10 (hanya 1 digit)
                if (nilai_jam <= 9) {
                    // Tambahkan "0" didepannya
                    nol_jam = "0";
                }
                // Jika nilai MENIT lebih kecil dari 10 (hanya 1 digit)
                if (nilai_menit <= 9) {
                    // Tambahkan "0" didepannya
                    nol_menit = "0";
                }
                // Jika nilai DETIK lebih kecil dari 10 (hanya 1 digit)
                if (nilai_detik <= 9) {
                    // Tambahkan "0" didepannya
                    nol_detik = "0";
                }
                // Membuat String JAM, MENIT, DETIK
                String jam = nol_jam + Integer.toString(nilai_jam);
                String menit = nol_menit + Integer.toString(nilai_menit);
                String detik = nol_detik + Integer.toString(nilai_detik);
                // Menampilkan pada Layar
                //tampil_jam.setText("  " + jam + " : " + menit + " : " + detik + "  ");
                Jam.setSelectedItem(jam);
                Menit.setSelectedItem(menit);
                Detik.setSelectedItem(detik);
            }
        };
        // Timer
        new Timer(1000, taskPerformer).start();
    }

    
    //"No.Rawat", "No.R.M.", "Nama Pasien", "Umur", "JK", "Tgl.Lahir", "Tanggal", "Jam",
      //      "Klasifikasi Luka", "Nyeri Lokal", "Demam", "Kemerahan",
        //    "Drainase Purulen", "Bengkak Terlokalisir", "Jenis Lokasi",
         //   "Slought", "Krusta", "Nekrotic", "Bullae", "Granulasi","Pus",
          //  "Rawat Luka", "Keterangan Rawat Luka", "Kode Dokter", "Nama Dokter"
    private void ganti() {
        if (Sequel.mengedittf(
                "pemeriksaan_rawat_luka",
                "tanggal=? and jam=? and no_rawat=?",
                "no_rawat=?,tanggal=?,jam=?,kd_dokter=?,klasifikasi_luka=?,nyeri_lokal=?,demam=?,kemerahan=?,drainase_purulen=?,bengkak_terlokalisir=?,jenis_lokasi=?,slought=?,krusta=?,nekrotic=?,bullae=?,granulasi=?,pus=?,rawat_luka=?,ket_rawat_luka=?",
                22, // Jumlah parameter yang harus dikirimkan
                new String[]{
                    TNoRw.getText(), // no_rawat baru
                    Valid.SetTgl(Tanggal.getSelectedItem() + ""), // tanggal baru
                    Jam.getSelectedItem() + ":" + Menit.getSelectedItem() + ":" + Detik.getSelectedItem(), // jam baru
                    KdDokter.getText(), // kd_dokter baru
                    Luka.getSelectedItem().toString(), // klasifikasi_luka
                    Nyeri.getSelectedItem().toString(), // nyeri_lokal
                    Demam.getSelectedItem().toString(), // demam
                    Kemerahan.getSelectedItem().toString(), // kemerahan
                    Drainase.getSelectedItem().toString(), // drainase_purulen
                    Bengkak.getSelectedItem().toString(), // bengkak_terlokalisir
                    JenisLokasi.getSelectedItem().toString(), // jenis_lokasi
                    Slought.getSelectedItem().toString(), // slought
                    Krusta.getSelectedItem().toString(), // krusta
                    Nekrotic.getSelectedItem().toString(), // nekrotic
                    Bullae.getSelectedItem().toString(), // bullae
                    Granulasi.getSelectedItem().toString(), // granulasi
                    Pus.getSelectedItem().toString(),
                    RawatLuka.getSelectedItem().toString(), // rawat_luka
                    KetRawat.getText(), // ket_rawat_luka
                    tbObat.getValueAt(tbObat.getSelectedRow(), 6).toString(), // tanggal lama
                    tbObat.getValueAt(tbObat.getSelectedRow(), 7).toString(), // jam lama
                    tbObat.getValueAt(tbObat.getSelectedRow(), 0).toString() // no_rawat lama
                }) == true) {

            // Perbarui tabel dengan data baru
            tbObat.setValueAt(TNoRw.getText(), tbObat.getSelectedRow(), 0);
            tbObat.setValueAt(TNoRM.getText(), tbObat.getSelectedRow(), 1);
            tbObat.setValueAt(TPasien.getText(), tbObat.getSelectedRow(), 2);
            tbObat.setValueAt(Valid.SetTgl(Tanggal.getSelectedItem() + ""), tbObat.getSelectedRow(), 6);
            tbObat.setValueAt(Jam.getSelectedItem() + ":" + Menit.getSelectedItem() + ":" + Detik.getSelectedItem(), tbObat.getSelectedRow(), 7);
            tbObat.setValueAt(Luka.getSelectedItem().toString(), tbObat.getSelectedRow(), 8);
            tbObat.setValueAt(Nyeri.getSelectedItem().toString(), tbObat.getSelectedRow(), 9);
            tbObat.setValueAt(Demam.getSelectedItem().toString(), tbObat.getSelectedRow(), 10);
            tbObat.setValueAt(Kemerahan.getSelectedItem().toString(), tbObat.getSelectedRow(), 11);
            tbObat.setValueAt(Drainase.getSelectedItem().toString(), tbObat.getSelectedRow(), 12);
            tbObat.setValueAt(Bengkak.getSelectedItem().toString(), tbObat.getSelectedRow(), 13);
            tbObat.setValueAt(JenisLokasi.getSelectedItem().toString(), tbObat.getSelectedRow(), 14);
            tbObat.setValueAt(Slought.getSelectedItem().toString(), tbObat.getSelectedRow(), 15);
            tbObat.setValueAt(Krusta.getSelectedItem().toString(), tbObat.getSelectedRow(), 16);
            tbObat.setValueAt(Nekrotic.getSelectedItem().toString(), tbObat.getSelectedRow(), 17);
            tbObat.setValueAt(Bullae.getSelectedItem().toString(), tbObat.getSelectedRow(), 18);
            tbObat.setValueAt(Granulasi.getSelectedItem().toString(), tbObat.getSelectedRow(), 19);
            tbObat.setValueAt(Pus.getSelectedItem().toString(), tbObat.getSelectedRow(), 20);
            tbObat.setValueAt(RawatLuka.getSelectedItem().toString(), tbObat.getSelectedRow(), 21);
            tbObat.setValueAt(KetRawat.getText(), tbObat.getSelectedRow(), 22);
            tbObat.setValueAt(KdDokter.getText(), tbObat.getSelectedRow(), 23);
            tbObat.setValueAt(NmDokter.getText(), tbObat.getSelectedRow(), 24);

            emptTeks(); // Kosongkan input
        }
    }

    private void hapus() {
        if (TNoRw.getText().trim().equals("") || TNoRM.getText().trim().equals("") || TPasien.getText().trim().equals("")) {
            Valid.textKosong(TNoRw, "pasien");
        } else if (KdDokter.getText().trim().equals("")) {
            Valid.textKosong(BtnDokter, "Dokter");
        } else {
            if (Sequel.queryu2tf(
                    "delete from pemeriksaan_rawat_luka where tanggal=? and jam=? and no_rawat=?",
                    3,
                    new String[]{
                        tbObat.getValueAt(tbObat.getSelectedRow(), 6).toString(), // tanggal
                        tbObat.getValueAt(tbObat.getSelectedRow(), 7).toString(), // jam
                        tbObat.getValueAt(tbObat.getSelectedRow(), 0).toString() // no_rawat
                    }) == true) {
                tabMode.removeRow(tbObat.getSelectedRow()); // Hapus baris dari tabel
                LCount.setText("" + tabMode.getRowCount()); // Perbarui jumlah data
                emptTeks(); // Kosongkan form
            } else {
                JOptionPane.showMessageDialog(null, "Gagal menghapus data..!!");
            }
        }
    }

}
