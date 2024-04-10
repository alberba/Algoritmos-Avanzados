package Controlador.TreeSort;

public class TreeNode {
    double data;
    TreeNode left, right;

    public TreeNode(double data) {
        this.data = data;
        left = right = null;
    }

    public void setRight(TreeNode right) {
        this.right = right;
    }

    public void setLeft(TreeNode left) {
        this.left = left;
    }

    public TreeNode getLeft() {
        return left;
    }

    public TreeNode getRight() {
        return right;
    }

    public double getData() {
        return data;
    }
}
