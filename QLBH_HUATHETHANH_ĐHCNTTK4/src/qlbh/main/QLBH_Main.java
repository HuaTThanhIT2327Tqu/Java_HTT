package qlbh.main;  // ← PHẢI CÓ DÒNG NÀY

import javax.swing.SwingUtilities;
import qlbh.view.LoginForm; 

public class QLBH_Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new LoginForm().setVisible(true);
            }
        });
    }
}