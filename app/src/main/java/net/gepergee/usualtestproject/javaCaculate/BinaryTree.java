package net.gepergee.usualtestproject.javaCaculate;

import java.util.Stack;

/**
 * @author petergee
 * @date 2018/9/6
 */
public class BinaryTree {
    private TreeNode rootNode;

    public BinaryTree() {
        rootNode = new TreeNode(1, "A");
    }

    public void createTreeNode() {
        TreeNode nodeB = new TreeNode(2, "B");
        TreeNode nodeC = new TreeNode(3, "C");
        TreeNode nodeD = new TreeNode(4, "D");
        TreeNode nodeE = new TreeNode(5, "E");
        TreeNode nodeF = new TreeNode(6, "F");
        rootNode.leftNode = nodeB;
        rootNode.rightNode = nodeC;
        nodeB.leftNode = nodeD;
        nodeB.rightNode = nodeE;
        nodeC.rightNode = nodeF;
    }

    /**
     * 前序遍历
     */
    public void preOrder(TreeNode node) {
        if (node == null) {
            return;
        } else {
            System.out.println("index is" + node.index + "         data is " + node.data);
            preOrder((TreeNode) node.leftNode);
            preOrder((TreeNode) node.rightNode);
        }
    }

    /**
     * 中序排列
     *
     * @param node
     */
    public void midOrder(TreeNode node) {
        if (node == null) {
            return;
        } else {
            midOrder((TreeNode) node.leftNode);
            System.out.println("index is" + node.getIndex() + "         data is " + node.getData());
            midOrder((TreeNode) node.rightNode);
        }
    }


    /**
     * 后序排列
     *
     * @param node
     */
    public void postOrder(TreeNode node) {
        if (node == null) {
            return;
        } else {
            postOrder((TreeNode) node.leftNode);
            postOrder((TreeNode) node.rightNode);
            System.out.println("index is" + node.index + "         data is " + node.data);
        }
    }

    /**
     * 使用栈实现中序
     */
    public void stackOrder(TreeNode node) {
        Stack<TreeNode> stack = new Stack<>();
        stack.push(node);
        while (!stack.isEmpty()) {
            TreeNode n = stack.pop();
            if (n.rightNode != null) {
                stack.push((TreeNode) n.rightNode);
            }
            if (n.leftNode != null) {
                stack.push((TreeNode) n.leftNode);
            }
            System.out.println("data is " + n.getData());



        }

    }

    public class TreeNode<T> {
        private T index;
        private String data;
        private T leftNode;
        private T rightNode;

        public TreeNode(T index, String data) {
            this.index = index;
            this.data = data;
            this.leftNode = null;
            this.rightNode = null;
        }

        public String getData() {
            return data;
        }

        public T getIndex() {
            return index;
        }
    }

    public static void main(String[] args) {
        BinaryTree binaryTree = new BinaryTree();
        binaryTree.createTreeNode();
        System.out.println("————————————————————————————前序排列————————————————————————————————————");
        binaryTree.preOrder(binaryTree.rootNode);
        System.out.println("————————————————————————————中序排列————————————————————————————————————");
        binaryTree.midOrder(binaryTree.rootNode);
        System.out.println("————————————————————————————后序排列————————————————————————————————————");
        binaryTree.postOrder(binaryTree.rootNode);
        System.out.println("---------------------栈前序排序-------------------");
        binaryTree.stackOrder(binaryTree.rootNode);
    }

}
