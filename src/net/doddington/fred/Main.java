package net.doddington.fred;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

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

        TreeNode dummy = new FileTreeNode("/");

        int a = dummy.getChildCount();
        System.out.println(a);

        TreeNode b = dummy.getChildAt(3);
        System.out.println(b);

        JFrame frame = new JFrame("Explorer");

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JTree tree = new JTree(dummy);
//        {
//            @Override
//            public String getToolTipText(MouseEvent evt) {
//                if (getRowForLocation(evt.getX(), evt.getY()) == -1)
//                    return null;
//                TreePath curPath = getPathForLocation(evt.getX(), evt.getY());
//                FileTreeNode node = (FileTreeNode) curPath.getLastPathComponent();
//
//                if (node != null) {
//                    if (node.isLeaf()) {
//                        return String.format("Node '%s' is a leaf, with size=%d bytes",
//                                node.toString(), node.getFileSize());
//                    } else {
//                        return String.format("Node '%s' is not a leaf and contains %d children",
//                                node.toString(), node.getChildCount());
//                    }
//                } else {
//                    return null;
//                }
//            }
//        };

//        ToolTipManager.sharedInstance().registerComponent(tree);

        tree.addMouseListener(new MouseAdapter() {
                public void mousePressed(MouseEvent e) {
                    maybeShowPopup(e);
                }

                public void mouseReleased(MouseEvent e) {
                    maybeShowPopup(e);
                }

                private void maybeShowPopup(MouseEvent e) {
                    if (e.isPopupTrigger()) {
                        TreePopup popup = new TreePopup();
                        popup.show(e.getComponent(),
                                   e.getX(), e.getY());
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
