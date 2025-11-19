package qlbh;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class QLBH_Main extends JFrame {
	public QLBH_Main() {
	}
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new LoginForm().setVisible(true);
            }
        });
    }
}