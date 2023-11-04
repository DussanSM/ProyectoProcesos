package com.example.proyectoprocesos.modelo.estructuraDatos;
public class Node<T> {

    private T value;
    private Node<T> next;

    public Node(T value) {
        this.value = value;
    }

    public Node(T value, Node<T> next) {
        super();
        this.value = value;
        this.next = next;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public Node<T> getNext() {
        return next;
    }

    public void setNext(Node<T> next) {
        this.next = next;
    }

}