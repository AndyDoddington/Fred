package net.doddington.fred;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class TreePopup extends JPopupMenu {
    public TreePopup() {
        JMenuItem delete = new JMenuItem("Delete");
        JMenuItem add = new JMenuItem("Add");
//        delete.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent ae) {
//                System.out.println("Delete child");
//            }
//        });
//        add.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent ae) {
//                System.out.println("Add child");
//            }
//        });
        add(delete);
        add(new JSeparator());
        add(add);
    }
}
