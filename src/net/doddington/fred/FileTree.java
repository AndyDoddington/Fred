package net.doddington.fred;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.sound.midi.SysexMessage;
import javax.swing.tree.TreeNode;
import java.io.*;
import java.util.Enumeration;

class FileTreeNode implements TreeNode {
    private static final Logger logger = LogManager.getLogger(Main.class);

    private final File file;

    public FileTreeNode(String fname) {
        this(new File(fname));
        logger.debug(String.format("Created FileTreeNode for file named '%s'", fname));
    }

    public FileTreeNode(File f) {
        logger.debug(String.format("Created FileTreeNode for file '%s'", f.getName()));
        file = f;
    }

    @Override
    public String toString() {
        return file.getName();
    }

    @Override
    public TreeNode getChildAt(int childIndex) {
        var children = this.file.listFiles();

        logger.debug(String.format("getChildAt on '%s' for child number %d returned '%s'",
                     this.file.getName(), childIndex, children[childIndex]));

        return new FileTreeNode(children[childIndex]);
    }

    @Override
    public int getChildCount() {
        var children = this.file.listFiles();

        // if node has no children then list() returns null
        int count = (children == null) ? 0 : children.length;

        logger.debug(String.format("getChildCount for '%s' returned %d", this.file.getName(), count));

        return count;
    }

    /**
     * Returns the parent directory of the current file or directory.
     * @return
     */
    @Override
    public TreeNode getParent() {
        logger.debug(String.format("Parent of '%s' is '%s'", file.getName(), file.getParent()));

        return new FileTreeNode(file.getParent());
    }

    @Override
    public int getIndex(TreeNode node) {
        var children = this.file.listFiles();

        for (int i=0; i < children.length; i++) {
            // TODO: sort this
            if (node == children[i]) {
                logger.debug(String.format("getIndex of '%s' in '%s' returns %d", node, file.getName(), i));
                return i;
            }
        }
        return 0;
    }

    /**
     * Indicates whether the current node allows children -i.e. whether it is a directory.
     * @return
     */
    @Override
    public boolean getAllowsChildren() {
        logger.debug(String.format("File '%s' does%s allow children", file.getName(), (file.isDirectory() ? "" : " not")));
        return file.isDirectory();
    }

    /**
     * Tests whether the current node is a leaf node. In the context of a file hierarchy, this will
     * be the case iff the node is a simple file (i.e. rather than being a directory).
     * @return
     */
    @Override
    public boolean isLeaf() {
        boolean is_leaf = file.isFile();
        logger.debug(String.format("File '%s' is%s a leaf", file.getName(), (is_leaf ? "" : " not")));
        return is_leaf;
    }

    @Override
    public Enumeration<? extends TreeNode> children() {
        String[] kids = file.list();
        return new ChildEnumeration(kids);
    }

    /**
     * Returns file size.
     * @return file size in bytes.
     */
    public Object getFileSize() {
        var s = file.length();

        logger.debug(String.format("File '%s' contains %d bytes", file.getName(), s));

        return s;
    }

    public boolean canBeDeleted() {
        return true;
    }

    public boolean canHaveChildrenAdded() {
        return file.isDirectory();
    }

    public void addDummyChild() {
        try {
            File.createTempFile("prefix", ".tmp", file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void typeContents() throws IOException {
        System.out.println("Type the contents of " + file.getName());

        FileReader fis = new FileReader(file);

        BufferedReader br = new BufferedReader(fis);

        // read the first line, to prime the pump
        String line = br.readLine();

        while (line != null && isValid(line)) {
            System.out.println(line);
            line = br.readLine();
        }

        if (line != null) {
            System.out.println("File contained control chars!");
        }

        System.out.println("-----------EOF-------------");
    }

    /**
     * Checks for any control chasarters in string
     * @param str The string to be tested
     * @return true iff the string does *not* contain any control characters
     */
    private boolean isValid(String str) {
        for (int i =0; i < str.length(); i++) {
            if (Character.isISOControl(str.charAt(i))) {
                return false;
            }
        }

        return true;
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
