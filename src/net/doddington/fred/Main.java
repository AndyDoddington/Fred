package net.doddington.fred;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import javax.swing.tree.TreeNode;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * A simple calculator - playing with Java Swing GUI.
 */
public class Main {
    private static final Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
//        try {
//            UIManager.setLookAndFeel("javax.swing.plaf.metal.");
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        } catch (InstantiationException e) {
//            e.printStackTrace();
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        } catch (UnsupportedLookAndFeelException e) {
//            e.printStackTrace();
//        }

        TreeNode dummy = new FileTreeNode("/");

        int a = dummy.getChildCount();
        System.out.println(a);

        TreeNode b = dummy.getChildAt(3);
        System.out.println(b);

        JFrame frame = new JFrame("Fred");

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Define the panel to hold the buttons
        JPanel panel = new JPanel();

        GridBagLayout layout = new GridBagLayout();
        panel.setLayout(layout);

        GridBagConstraints gbc = new GridBagConstraints();
    
        JTree tree = new JTree(dummy);
        panel.add(tree, gbc);

        frame.add(panel);
        frame.pack();

        frame.setLocation(500, 500);
        frame.setVisible(true);
    }
}
class TreePopup extends JPopupMenu {
    public TreePopup(JTree tree) {
        JMenuItem delete = new JMenuItem("Delete");
        JMenuItem add = new JMenuItem("Add");
        delete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                System.out.println("Delete child");
            }
        });
        add.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                System.out.println("Add child");
            }
        });
        add(delete);
        add(new JSeparator());
        add(add);
    }
}
