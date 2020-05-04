package net.doddington.fred;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * A simple calculator - playing with Java Swing GUI.
 */
public class Main {
    private static final Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
//        try {
//            UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        } catch (InstantiationException e) {
//            e.printStackTrace();
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        } catch (UnsupportedLookAndFeelException e) {
//            e.printStackTrace();
//        }

        TreeNode root = new FileTreeNode("/");

        JTree tree = new JTree(root);

        JFrame frame = new JFrame("Explorer");

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        tree.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                maybeShowPopup(e);
            }

            public void mouseReleased(MouseEvent e) {
                maybeShowPopup(e);
            }

            private void maybeShowPopup(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    // the mouse event must be inside a JTree
                    JTree tree = (JTree) e.getComponent();

                    // find out which row we are at in the tree (-1 if not inside the tree)
                    int row = tree.getRowForLocation(e.getX(), e.getY());

                    // ignore if we are outsidde the tree area
                    if (row != -1) {
                        // find the path of the tree node which we want to use
                        TreePath path = tree.getPathForLocation(e.getX(), e.getY());

                        // then use the path to find the actual tree node
                        FileTreeNode leaf = (FileTreeNode)path.getLastPathComponent();

                        // the nature of the popup depends on the type of tree node (directory etc)
                        TreePopup popup = new TreePopup(leaf);
                        popup.show(e.getComponent(), e.getX(), e.getY());
                    }
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(tree);

        scrollPane.setPreferredSize(new Dimension(400, 600));

        frame.add(scrollPane);
        frame.pack();

        frame.setLocation(500, 500);
        frame.setVisible(true);
    }
}
