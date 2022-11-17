public class RedBlackTree {
    public RedBlackTree.Node root;//root node
    public RedBlackTree(){

        super();
        root = null;

    }

    public RedBlackTree.Node getRoot(){
        return root;
    }
    // node creating subclass
    class Node
    {
        int data;
        RedBlackTree.Node left;
        RedBlackTree.Node right;
        char colour;
        RedBlackTree.Node parent;

        Node(int data)
        {
            super();
            this.data = data; // only including data. not key
            this.left = null; // left subtree
            this.right = null; // right subtree
            this.colour = 'R'; // colour . either 'R' or 'B'
            this.parent = null; // required at time of rechecking.
        }
    }
    // this function performs left rotation
    private void rotateLeft(Node node) {
        Node parent = node.parent;
        Node rightChild = node.right;

        node.right = rightChild.left;
        if (rightChild.left != null) {
            rightChild.left.parent = node;
        }

        rightChild.left = node;
        node.parent = rightChild;

        if (parent == null) {
            root = rightChild;
        } else if (parent.left == node) {
            parent.left = rightChild;
        } else if (parent.right == node) {
            parent.right = rightChild;
        } else {
            throw new IllegalStateException("Node is not a child of its parent");
        }

        if (rightChild != null) {
            rightChild.parent = parent;
        }
    }
    //this function performs right rotation
    private void rotateRight(Node node) {
        Node parent = node.parent;
        Node leftChild = node.left;

        node.left = leftChild.right;
        if (leftChild.right != null) {
            leftChild.right.parent = node;
        }

        leftChild.right = node;
        node.parent = leftChild;


        if (parent == null) {
            root = leftChild;
        } else if (parent.left == node) {
            parent.left = leftChild;
        } else if (parent.right == node) {
            parent.right = leftChild;
        } else {
            throw new IllegalStateException("Node is not a child of its parent");
        }

        if (leftChild != null) {
            leftChild.parent = parent;
        }
    }



    // these are some flags.
    // Respective rotations are performed during traceback.
    // rotations are done if flags are true.
    boolean ll = false;
    boolean rr = false;
    boolean lr = false;
    boolean rl = false;

    public void insert (int key) {
        Node node = root;
        Node parent = null;
        /*if(this.root==null)
        {
            this.root = new RedBlackTree.Node(key);
            this.root.colour = 'B';
        }
        else {*/

        while (node != null) {
            parent = node;
            if (key < node.data) {
                node = node.left;
            } else if (key > node.data) {
                node = node.right;
            } else {
                throw new IllegalArgumentException("already contains a node with the key" + key);
            }
        }
        Node newNode = new Node(key);
        newNode.colour = 'R';
        if (parent == null) {
            root = newNode;
        } else if (key < parent.data){
            parent.left = newNode;
        }else {
            parent.right = newNode;
        }
        newNode.parent = parent;
        insertHelp(newNode);



    }
    public Node getSibling(Node parent){
        Node parentSibling;
        Node grandparent = parent.parent;
        if(grandparent.left == parent) {
            return grandparent.right;
        }else if(grandparent.right == parent) {
            return grandparent.left;
        }else {
            throw new IllegalStateException("parent is not grandparent's child");
        }
    }

    public void insertHelp(Node node){
        Node parent = node.parent;

        /*RedBlackTree.Node parentSibling;
        if(grandparent.left == parent)
            parentSibling = grandparent.right;
        else if(grandparent.right == parent)
            parentSibling = grandparent.left;
        else
            throw new IllegalStateException("parent is not grandparent's child");*/

        if (parent == null) { // root'a ulasilmis
            return;
        }
        if (parent.colour == 'B') {
            return;
        }
        Node grandparent = parent.parent;
        if(grandparent == null) { //parent root olmus oluyor bu sebeple parent black olmali
            parent.colour = 'B';
            return;
        }
        Node parentSibling = getSibling(parent);
        if (parentSibling != null && parentSibling.colour == 'R'){
            parent.colour = 'B';
            grandparent.colour = 'R';
            parentSibling.colour = 'B';
            insertHelp(grandparent);
        }
        else if(parent == grandparent.left){
            if (node == parent.right){
                rotateLeft(parent);
                parent = node;
            }

            rotateRight(grandparent);
            parent.colour = 'B';
            grandparent.colour = 'R';
        }
        else {
            if (node == parent.left){
                rotateRight(parent);
                parent = node;
            }

            rotateLeft(grandparent);
            parent.colour = 'B';
            grandparent.colour = 'R';
        }


    }
    // helper function for insertion. Actually this function performs all tasks in single pass only.
    /*RedBlackTree.Node insertHelp(RedBlackTree.Node root, int data) {
        insertionNode(data);

    }*/

    // function to insert data into tree.
    /*public void insert(int data)
    {
        if(this.root==null)
        {
            this.root = new RedBlackTree.Node(data);
            this.root.colour = 'B';
        }
        else
            insertionNode(data);
            //this.root = insertionNode(data);
    }*/
    // helper function to print inorder traversal
    void inorderTraversal(RedBlackTree.Node node)
    {
        if(node!=null)
        {
            inorderTraversal(node.left);
            System.out.printf("%d ", node.data);
            inorderTraversal(node.right);
        }
    }

    // helper function to print the tree.

    public static void main(String[] args)
    {

        RedBlackTree t = new RedBlackTree();
        int[] arr = {1,4,6,3,5,7,8,2,9};
        for(int i=0;i<9;i++)
        {
            t.insert(arr[i]);
            System.out.println();
            t.inorderTraversal(t.getRoot());
        }

    }
}
