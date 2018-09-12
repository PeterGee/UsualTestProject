package net.gepergee.usualtestproject.javaCaculate;

import java.util.ArrayList;

/**
 * 生成二叉树
 * @author petergee
 * @date 2018/9/7
 */
public class BinaryTreeTwo {
    TreeNode root;

    public BinaryTreeTwo() {
        root = new TreeNode("A");
    }

    public TreeNode methodCreateBinaryTree(int size, ArrayList<String> list) {
        if (list.size() == 0) {
            return null;
        }
        String s = list.get(0);
        TreeNode tree ;
        int index = size - list.size();
        if (s.equals("#")) {
            tree = null;
            list.remove(0);
            return tree;
        }
        tree = new TreeNode(s);
        if (index == 0) {
            // 创建根结点
           root = tree;
        }
        list.remove(0);
        // 创建左结点
        tree.leftNode = methodCreateBinaryTree(++index, list);
        // 创建右结点
        tree.rightNode = methodCreateBinaryTree(++index, list);
        return tree;
    }

    /**
     * 前序遍历
     *
     * @param treeNode
     */
    public void methodPreOrder(TreeNode treeNode) {
        if (treeNode == null) {
            return;
        }
        System.out.println("data:  " + treeNode.getData());
        methodPreOrder(treeNode.leftNode);
        methodPreOrder(treeNode.rightNode);
    }

    /**
     * 二叉树
     */
    public class TreeNode {
        private String data;
        private TreeNode leftNode;
        private TreeNode rightNode;

        public TreeNode(String data) {
            this.data = data;
        }

        public String getData() {
            return data;
        }

        public void setData(String data) {
            this.data = data;
        }
    }

    public static void main(String[] args) {
        BinaryTreeTwo treeTwo = new BinaryTreeTwo();
        String[] arr = new String[]{"A", "B", "D", "#", "C", "#", "E"};
        ArrayList<String> list=new ArrayList<>();
        for (String s: arr){
            list.add(s);
        }
        TreeNode node = treeTwo.methodCreateBinaryTree(list.size(), list);
        treeTwo.methodPreOrder(node);

    }
}
