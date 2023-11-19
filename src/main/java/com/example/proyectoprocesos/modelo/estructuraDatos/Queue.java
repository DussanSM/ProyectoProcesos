package com.example.proyectoprocesos.modelo.estructuraDatos;

import java.util.Iterator;

public class Queue<T> implements Iterable<T>{
    private Node<T> firstNode, endNode;
    private int size;

    public Queue() {
        firstNode = endNode = null;
        size = 0;
    }

    public void push(T value){
        Node<T> newNode = new Node<>(value);

        if(isEmpty()){
            firstNode = endNode = newNode;
        }else{
            endNode.setNext(newNode);
            endNode = newNode;
        }
        ++size;
    }

    public T pop(){
        if(isEmpty()){
            throw new RuntimeException("La Cola está vacía");
        }
        T value = firstNode.getValue();
        firstNode = firstNode.getNext();
        if(firstNode == null){
            endNode = null;
        }
        --size;
        return value;
    }

    public T end(){
        return endNode.getValue();
    }

    public boolean isEmpty(){
        return firstNode == null;
    }

    public void emptyQueue(){
        firstNode = endNode = null;
        size = 0;
    }

    @Override
    public Queue<T> clone(){
        Queue<T> newQueue = new Queue<>();
        Node<T> auxNode = firstNode;

        while (auxNode != null){
            newQueue.push(auxNode.getValue());
            auxNode = auxNode.getNext();
        }

        return newQueue;
    }

    public Node<T> getFirstNode() {
        return firstNode;
    }

    public Node<T> getEndNode() {
        return endNode;
    }

    public int getSize() {
        return size;
    }

    @Override
    public Iterator<T> iterator() {
        return new IteratorQueue(firstNode);
    }

    public class IteratorQueue implements Iterator<T>{

        private Node<T> Node;
        private int position;

        public IteratorQueue(Node<T> Node) {
            this.Node = Node;
            this.position = 0;
        }
        @Override
        public boolean hasNext() {
            return Node != null;
        }
        @Override
        public T next() {
            Node = Node.getNext();
            position++;
            return pop();
        }
        public void set(T e) {
            if(Node != null) {
                Node.setValue(e);
            }
        }
        public int getPosition() {
            return position;
        }

    }
}
