import java.util.*;
public class Solution {
    private static class Node{
        private char val;
        private Node left=null;
        private Node right=null;
        private Node(char val){
            this.val=val;
        }
    }
    private static class RV{
        private Node root;
        private int used;
        private RV(Node root,int used){
            this.root=root;
            this.used=used;
        }

    }
    private static RV buildTree(char[]preorder){

    }
    private static void preorderTrsversal(Node root){
        if(root==null){
            preorderTrsversal(root.left);
            System.out.println(root.val+" ");
            preorderTrsversal(root.right);
        }
    }
    public static void main(String[] args) {

    }
}
