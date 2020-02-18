import java.util.Queue;
import java.util.Stack;

//import java.util.*;
public class Tree {
    private static class Node {
        private char val;
        private Node left = null;
        private Node right = null;

        private Node(char val) {
            this.val = val;
        }
    }

    //非递归实现遍历
    public static void preorder(Node root) {

    }

    public static void inorder(Node root) {

    }

    public static void postorder(Node root) {
        Stack<Node> stack = new Stack<>();
        Node cur = root;
        Node last = null;//上一次被完整遍历的结点
        while (cur != null || !stack.empty()) {
            while (cur != null) {
                stack.push(cur);
                cur = cur.left;
            }
            Node top = stack.peek();
            if (top.right == null || top.right == last) {
                System.out.println(top.val);
                stack.pop();
                last = top;
            } else {
                cur = top.right;
            }
        }
    }

    public static void main(String[] args) {
    }
}

