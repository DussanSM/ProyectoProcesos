package com.example.proyectoprocesos.modelo.estructuraDatos;

public class DoubleNode<T> {
    private DoubleNode<T> nextNode;
    private DoubleNode<T> previousNode;
    private T value;

    public DoubleNode(T valorNodo) {
        this.value = valorNodo;
    }

    public DoubleNode(T dato, DoubleNode<T> siguiente,DoubleNode<T> anterior) {
        super();
        this.value = dato;
        this.nextNode = siguiente;
        this.previousNode = anterior;
    }

    public DoubleNode<T> getNextNode() {
        return nextNode;
    }


    public void setNextNode(DoubleNode<T> nextNode) {
        this.nextNode = nextNode;
    }


    public T getValue() {
        return value;
    }


    public void setValue(T value) {
        this.value = value;
    }


    public DoubleNode<T> getPreviousNode() {
        return previousNode;
    }


    public void setPreviousNode(DoubleNode<T> previousNode) {
        this.previousNode = previousNode;
    }

}
