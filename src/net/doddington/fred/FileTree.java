package net.doddington.fred;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.tree.TreeNode;
import java.io.File;
import java.util.Enumeration;

class FileTreeNode implements TreeNode {
    private static final Logger logger = LogManager.getLogger(Main.class);

    private final File file;

    public FileTreeNode(String fname) {
        logger.debug(String.format("Created FileTreeNode for '%s'", fname));
        file = new File(fname);
    }

    @Override
    public String toString() {
        return file.getName();
    }

    @Override
    public TreeNode getChildAt(int childIndex) {
        String[] children = this.file.list();

        logger.debug(String.format("getChildAt on '%s' for child number %d returned '%s'",
                     this.file.getName(), childIndex, children[childIndex]));

        return new FileTreeNode(children[childIndex]);
    }

    @Override
    public int getChildCount() {
        String[] children = this.file.list();

        // if node has no children then list() returns null
        int count = (children == null) ? 0 : children.length;

        logger.debug(String.format("getChildCount for '%s' returned %d", this.file.getName(), count));

        return count;
    }

    @Override
    public TreeNode getParent() {
        logger.debug(String.format("Parent of '%s' is '%s'", file.getName(), file.getParent()));

        return new FileTreeNode(file.getParent());
    }

    @Override
    public int getIndex(TreeNode node) {
        String[] children = this.file.list();

        for (int i=0; i < children.length; i++) {
            if (node.toString() == children[i]) {
                logger.debug(String.format("getIndex of '%s' in '%s' returns %d", node, file.getName(), i));
                return i;
            }
        }
        return 0;
    }

    @Override
    public boolean getAllowsChildren() {
        logger.debug(String.format("File '%s' does%s allow children", file.getName(), (file.isDirectory() ? "" : " not")));
        return file.isDirectory();
    }

    /**
     * Tests whether the current node is a leaf node. In the context of a file hierarchy, this will
     * be the case iff the node is a simple file (i.e. rather than being a director).
     * @return
     */
    @Override
    public boolean isLeaf() {
        logger.debug(String.format("File '%s' is%s a leaf", file.getName(), (file.isFile() ? "" : " not")));
        return file.isFile();
    }

    @Override
    public Enumeration<? extends TreeNode> children() {
        return new ChildEnumeration(file.list());
    }

    private class ChildEnumeration implements Enumeration<TreeNode> {
        private int index;
        private String [] name_list;

        public ChildEnumeration(String[] list) {
            name_list = list;
            index = 0;
        }

        @Override
        public boolean hasMoreElements() {
            return name_list != null && index < name_list.length;
        }

        @Override
        public TreeNode nextElement() {
            return new FileTreeNode(name_list[index++]);
        }
    }
}
