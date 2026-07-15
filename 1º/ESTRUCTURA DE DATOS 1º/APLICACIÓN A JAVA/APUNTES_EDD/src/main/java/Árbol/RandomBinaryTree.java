/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Árbol;

/**
 *
 * @author Hugo
 */
import java.util.Random;

// Implementación de un árbol binario ALEATORIO enlazado en memoria dinámica. Implementa la interfaz Binary tree.
// T es cualquier clase (incluidas las que se cree) pero no un tipo de dato primitivo (int, float, ...)
public class RandomBinaryTree<T> {

    private T value;
    private RandomBinaryTree<T> left;
    private RandomBinaryTree<T> right;

    Random randomGenerator;

    public RandomBinaryTree(T elem) {
        this.value = elem;
        this.left = this.right = null;

        this.randomGenerator = new Random(System.currentTimeMillis());
    }

    /**
     * Randomly inserts the new element on the left or the right child.
     *
     * @param elem The value for the Root new child.
     */
    public void randomInsert(T elem) {
        boolean insertLeft = randomGenerator.nextBoolean();
        if (insertLeft) {
            if (left == null) {
                left = new RandomBinaryTree(elem);
            } else {
                left.randomInsert(elem);
            }
        } else {
            if (right == null) {
                right = new RandomBinaryTree(elem);
            } else {
                right.randomInsert(elem);
            }
        }
    }

    /**
     * Recursively removes the children of this Tree.
     */
    public void clearChildren() {
        if (this.right != null) {
            this.right.clearChildren();
            this.right = null;
        }

        if (this.left != null) {
            this.left.clearChildren();
            this.left = null;
        }
    }

    /**
     * Returns whether this Tree contains elem.
     *
     * @param elem The element to find.
     * @return Whether Tree list contains elem.
     */
    public boolean contains(T elem) {
        boolean found = value.equals(elem);

        if (!found && left != null) {
            found = left.contains(elem);
        }

        if (!found && right != null) {
            found = right.contains(elem);
        }

        return found;
    }
    
    /**
    * Returns the left children of the Root node.
    *
    * @return The left children of the Root node.
    */
    public RandomBinaryTree<T> getLeftChild() {
        return left;
    }

    /**
    * Returns the right children of the Root node.
    *
    * @return The right children of the Root node.
    */
    public RandomBinaryTree<T> getRightChild() {
        return right;
    }

    /**
    * Returns the element at the Root node.
    *
    * @return The element a the Root node.
    */
    public T getRoot() {
        return value;
    }

    /**
    * Returns true if this Tree is a leaf (has no children).
    *
    * @return Whether the Tree is a leaf.
    */
    public boolean isLeaf() {
        return (left == null) && (right == null);
    }

    /**
    * Serializes the values at the nodes of the Tree in the same order they are
    * found while traversing the data structure in inorder.
    *
    * @return A String containing the Serialization of the Tree.
    */
    public String printInOrder() {
        String text = "";

        if (left != null) {
            text += left.printInOrder() + " ";
        }

        text += value.toString();

        if (right != null) {
            text += " " + right.printInOrder();
        }

        return text;
    }

    /**
    * Serializes the values at the nodes of the Tree in the same order they are
    * found while traversing the data structure in postorder.
    *
    * @return A String containing the Serialization of the Tree.
    */
    public String printPostOrder() {
        String text = "";

        if (left != null) {
            text += left.printPostOrder() + " ";
        }

        if (right != null) {
            text += right.printPostOrder() + " ";
        }

        text += value.toString();

        return text;
    }

    /**
    * Serializes the values at the nodes of the Tree in the same order they are
    * found while traversing the data structure in preorder.
    *
    * @return A String containing the Serialization of the Tree.
    */
    public String printPreOrder() {
        String text = "";

        text += value.toString();

        if (left != null) {
            text += " " + left.printPreOrder();
        }

        if (right != null) {
            text += " " + right.printPreOrder();
        }

        return text;
    }

}

