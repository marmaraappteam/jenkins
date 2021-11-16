package deneme;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;



import javax.swing.JRadioButton;
import javax.swing.Box;
import javax.swing.DefaultListModel;

import java.awt.FlowLayout;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.awt.BorderLayout;
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class deneme {

    private JFrame frame;
    private JPasswordField passwordField;
    
    final int AKIS_V12 = 1, AKIS_V228 = 2, AKIS_V252 = 3; 
    
    long selectedSlotId = -1;
    int selectedCardVersion = 0;
    String selectedPin = "";
    boolean symtests = false;
    boolean rsatests = false;
    boolean ecctests = false;
    boolean keygen = false;
    boolean rsa2816 = false;
    boolean publicKeyErisimTesti = false;
    int vendorcheck = 0;


    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    deneme window = new deneme();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    
    private class CustomOutputStream extends OutputStream
    //private class CustomOutputStream extends OutputStream implements Runnable
    {
        JTextArea textArea;
        Boolean update;
        
        public CustomOutputStream(JTextArea textArea)
        {
            this.textArea = textArea;
            update = false;
//            Thread thread = new Thread(this);
//            thread.start();
        }
        
        @Override
        public void write(int b) throws IOException
        {
            textArea.append(String.valueOf((char) b));
            textArea.setCaretPosition(textArea.getDocument().getLength());
//            textArea.update(textArea.getGraphics());
            
//            synchronized(update)
//            {
//                update = true;
//            }
            //textArea.update(textArea.getGraphics());
           
        }
//
//        @Override
//        public void run() {
//            while(true)
//            {
//                synchronized(update)
//                {
//                   if(update)
//                   {
//                       textArea.setCaretPosition(textArea.getDocument().getLength());
//                       textArea.update(textArea.getGraphics());
//                       update = false;
//                   }
//                }
//            }
//        }
    }

    /**
     * Create the application.
     */
    public deneme() {
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 702, 664);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);
        
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setViewportBorder(new TitledBorder(null, "Slots", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        scrollPane.setBounds(10, 11, 666, 188);
        frame.getContentPane().add(scrollPane);
        
        JList slotList = new JList();
        scrollPane.setViewportView(slotList);
        
        JPanel panel = new JPanel();
        panel.setBorder(new TitledBorder(null, "Options", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        panel.setBounds(10, 261, 666, 56);
        frame.getContentPane().add(panel);
        panel.setLayout(new BorderLayout(0, 0));
        JPanel panel2 = new JPanel();
        panel2.setBorder(new TitledBorder(null, "ECC Anahtar Üretme", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        panel2.setBounds(10, 327, 666, 56);
        frame.getContentPane().add(panel2);
        panel2.setLayout(new BorderLayout(0, 0));
        
        Box horizontalBox = Box.createHorizontalBox();
        panel.add(horizontalBox);
        
        Box horizontalBox2 = Box.createHorizontalBox();
        panel2.add(horizontalBox2);
        
        JRadioButton symmetricRadio = new JRadioButton("Symmetric Tests");
        horizontalBox.add(symmetricRadio);
        
        JRadioButton rsaRadio = new JRadioButton("RSA Tests");
        horizontalBox.add(rsaRadio);
        
        JRadioButton eccRadio = new JRadioButton("ECC Tests");
        horizontalBox.add(eccRadio);
        
        JRadioButton keygenRadio = new JRadioButton("Generate Keypair");
        horizontalBox.add(keygenRadio);

        JRadioButton rsa2816Radio = new JRadioButton("Rsa 2816");
        horizontalBox.add(rsa2816Radio);
        
        JRadioButton publicKeyRadio = new JRadioButton("Create ECC");	
        horizontalBox2.add(publicKeyRadio);
        
        JRadioButton vendordefinedornot = new JRadioButton("Vendor Defined or Not");	
        vendordefinedornot.setVisible(false);
        horizontalBox2.add(vendordefinedornot);
        
        JPanel panel_1 = new JPanel();
        panel_1.setBorder(new TitledBorder(null, "Akis Version:", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        panel_1.setBounds(298, 205, 378, 51);          
        frame.getContentPane().add(panel_1);
        panel_1.setLayout(new BorderLayout(0, 0));
        
        JComboBox versionCombo = new JComboBox();
        versionCombo.setModel(new DefaultComboBoxModel(new String[] {"Akis v1.2", "Akis v2.2.8", "Akis v2.5.2", "Akis v2.6"}));
        panel_1.add(versionCombo);
        publicKeyRadio.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(publicKeyRadio.isSelected()) {
					vendordefinedornot.setVisible(true);
				}
				else {
					vendordefinedornot.setVisible(false);
					vendordefinedornot.setSelected(false);
				}
			}
		});
        JButton testButton = new JButton("Start Test");
        testButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                Thread t = new Thread(new Runnable() {
                    public void run() {

                       
                        long startTime = System.currentTimeMillis();
                        testButton.setEnabled(false);
                       

                            // get options

                            selectedSlotId = slotList.getSelectedIndex() + 1;
                            selectedCardVersion = versionCombo.getSelectedIndex() + 1;
                            selectedPin = new String(passwordField.getPassword());
                            symtests = symmetricRadio.isSelected();
                            rsatests = rsaRadio.isSelected();
                            ecctests = eccRadio.isSelected();
                            keygen = keygenRadio.isSelected();
                            rsa2816 = rsa2816Radio.isSelected();
                            publicKeyErisimTesti = publicKeyRadio.isSelected();
                            
                           
                            
                          

                      


                        
                        long endTime = System.currentTimeMillis();
                        testButton.setEnabled(true);
                        
                        System.out.println("Time: " + (endTime - startTime) + " msecs");

                    }
                    
                    

                });
                t.start();
            }
        });

        testButton.setBounds(10, 394, 333, 51);
        frame.getContentPane().add(testButton);
        
        // initialize pkcs11
        
        JButton getbutton = new JButton("Get Data");
        getbutton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
                Thread t = new Thread(new Runnable() {
                    public void run() {
                    	
                    	
                    	
                         
                         /* try {
							token.getMechanismList();
						} catch (PKCS11Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} */
                         //for getdatasdo
                         /*   V25Test v25test = new V25Test(token.sessionHandle);
                         System.out.println("writeRSA2048Key");
                         token.writeRSA2048Key();
                         System.out.println("Rsa2048DecryptPKCS");
                          try {
							v25test.Rsa2048DecryptPKCS();
						} catch (PKCS11Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}*/
                       
                        
                        
                    }
                    
                });
                t.start();
                    }
        });
        getbutton.setBounds(343, 394, 333, 51);
        frame.getContentPane().add(getbutton);
        
        
        


        DefaultListModel listModel = new DefaultListModel();
        

         
         JScrollPane scrollPane_1 = new JScrollPane();
         scrollPane_1.setBounds(10, 394, 666, 221);
         frame.getContentPane().add(scrollPane_1);
         
         JTextArea textArea = new JTextArea();
         scrollPane_1.setViewportView(textArea);
         
         // replace stdout to text area 
         
         PrintStream printStream = new PrintStream(new CustomOutputStream(textArea));
         
         JPanel panel_2 = new JPanel();
         panel_2.setBorder(new TitledBorder(null, "PIN:", TitledBorder.LEADING, TitledBorder.TOP, null, null));
         panel_2.setBounds(10, 205, 278, 51);
         frame.getContentPane().add(panel_2);
         panel_2.setLayout(new BorderLayout(0, 0));
         
         passwordField = new JPasswordField();
         panel_2.add(passwordField);
         System.setOut(printStream);
         System.setErr(printStream);

    }
    
    
    

   
}
