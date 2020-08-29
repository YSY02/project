import java.util.ArrayList;
import java.util.List;

public class BinaryTree {
    //内部类
    class Node{
        char val;
        private Node left=null;
        private Node right=null;
        private Node parents;
        Node(char x){
            val=x;
        }
    }
    Node root=null;


    @Override
    public String toString() {
        return super.toString();
    }

    private static int count=0;
    //前序遍历  根左右
    public void preOrderTraversal(Node root){
        if(root==null){
            return;
        }
        System.out.println(root);
        inOrderTraversal(root.left);
        inOrderTraversal(root.right);
    }

    //中序遍历 左根右
    public static void inOrderTraversal(Node root){
        if(root==null){
            return;
        }
        inOrderTraversal(root.left);
        System.out.println(root);
        inOrderTraversal(root.right);
    }

    //后序遍历  左右根
    public static void postOrderTraversal(Node root) {
        if (root == null) {
            return;
        }
        inOrderTraversal(root.left);
        inOrderTraversal(root.right);
        System.out.println(root);
    }

    public static int getHeight(Node root){
        if(root==null){
            return 0;
        }
        int left=getHeight(root.left);
        int right=getHeight(root.right);
        return Math.max(left,right)+1;
    }

    public static Node find(Node root,int val) {
        if (root == null) {
            return null;
        }
        if (root.val == val) {
            return root;
        }

        Node n = find(root.left, val);
        if (n != null) {
            return n;
        }

        Node m = find(root.right, val);
        if (m != null) {
            return m;
        }
        return null;

        //if (root.left.val != val) {
        //  find(root.left,val);
        //find(root.right,val);
        // }

        // while(root.val!=val&&root.left.val!=val&&root.right.val!=val)
    }

    public static boolean find1(Node root,int val){
        if(root.val==val) {
            return true;
        }
        if(root==null){
            return false;
        }
        if(find1(root.left,val)){
            return true;
        }
        else if (find1(root.right,val)){
            return true;
        }
        return false;
    }

    public boolean isSameTree(Node p,Node q){
        if(p==null&&q==null){
            return true;
        }
        if(p==null){
          return false;
        }
        if(q==null){
            return false;
        }
        return isSameTree(p.right,q.right)&&;
    }

    }

    public boolean isMirrorTree(Node p,Node q){

    }

    public boolean isSymmetric(Node root){

    }


    public List<Character> postOrderTraversal1(Node root){
        if(root==null) {
            return new ArrayList<>();
        }
        List<Character>result=new ArrayList<>();
        List<Character>left=postOrderTraversal1(root.left);
        List<Character>right=postOrderTraversal1(root.right);
        result.addAll(left);
        result.addAll(right);
        result.add(root);
    }

    public boolean isSubtree(Node s,Node t){
        if(isSameTree(s,t)){
            return true;
        }
        if(isSameTree(s.left,t)){
            return true;
        }
        if(isSameTree(s.right,t)){
            return true;

        }
    public static void main(String[] args) {

    }

}