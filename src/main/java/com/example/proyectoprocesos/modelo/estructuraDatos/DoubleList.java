package com.example.proyectoprocesos.modelo.estructuraDatos;

import java.util.Iterator;

public class DoubleList<T> implements Iterable<T> {

    private DoubleNode<T> firstNode;
    private DoubleNode<T> lastNode;
    private int size;


    public DoubleList() {
        firstNode = null;
        lastNode = null;
        size = 0;
    }

    public void addStart(T value) {

        DoubleNode<T> newNode = new DoubleNode<>(value);

        if(isEmpty()){
            firstNode = lastNode = newNode;
        }
        else{
            newNode.setNextNode(firstNode);
            firstNode = newNode;
        }
        size++;
    }

    public void addEnd(T value) {

        DoubleNode<T> newNode = new DoubleNode<>(value);

        if(isEmpty()){
            firstNode = lastNode = newNode;
        }
        else{
            newNode.setPreviousNode(lastNode);
            lastNode.setNextNode(newNode);
            lastNode = newNode;
        }
        size++;
    }

    public void add(T value, int index) {

        if(isValidIndex(index)) {

            if(index==0) {
                addStart(value);
            }
            else {
                DoubleNode<T> newNode = new DoubleNode<>(value);
                DoubleNode<T> currentNode = getNode(index);

                newNode.setNextNode(currentNode);
                newNode.setPreviousNode(currentNode.getPreviousNode());
                currentNode.getPreviousNode().setNextNode(newNode);
                currentNode.setPreviousNode(newNode);

                size++;
            }
        }
    }

    public void deleteList() {
        firstNode = lastNode = null;
        size = 0;
    }

    public T getNodeValue(int index) {

        DoubleNode<T> auxNode = null;
        int count = 0;

        if(isValidIndex(index))
        {
            auxNode = firstNode;

            while (count < index) {

                auxNode = auxNode.getNextNode();
                count++;
            }
        }

        if(auxNode != null)
            return auxNode.getValue();
        else
            return null;
    }

    private boolean isValidIndex(int index) {
        if( index >= 0 && index < size ) {
            return true;
        }
        throw new RuntimeException("Indice no valido");
    }

    public boolean isEmpty() {
        return firstNode == null && lastNode == null;
    }

    public void printList() {

        DoubleNode<T> aux = firstNode;

        while(aux != null) {
            System.out.print( aux.getValue()+"\t" );
            aux = aux.getNextNode();
        }

        System.out.println();
    }

    public void printBackward() {

        for(DoubleNode<T> aux = lastNode; aux != null; aux = aux.getPreviousNode()) {
            System.out.print( aux.getValue()+"\t" );
        }
        System.out.println();

    }

    public void removeNode(T value) {

        DoubleNode<T> auxNode = searchNode(value);

        if(auxNode!=null) {

            DoubleNode<T> preNode = auxNode.getPreviousNode();
            DoubleNode<T> nxNode = auxNode.getNextNode();

            if(preNode == null) {
                firstNode = nxNode;
            }else {
                preNode.setNextNode(nxNode);
            }

            if(nxNode == null) {
                lastNode = preNode;
            }else {
                nxNode.setPreviousNode(preNode);
            }

            auxNode=null;
            size--;

            return;
        }

        throw new RuntimeException("El valor no existe");
    }

    public void removeFirst() {

        if( !isEmpty() ) {
            DoubleNode<T> auxNode = firstNode;
            firstNode = auxNode.getNextNode();

            if(firstNode == null) {
                lastNode = null;
            }
            else{
                firstNode.setPreviousNode(null);
            }

            size--;
            return;
        }

        throw new RuntimeException("Lista vacia");
    }


    public void removeLast() {

        if( !isEmpty() ) {
            DoubleNode<T> prev = getNode(size-2);
            lastNode = prev;

            if(lastNode==null) {
                firstNode=null;
            }else {
                prev.setNextNode(null);
            }

            size--;
            return;
        }

        throw new RuntimeException("Lista vacia");
    }

    private DoubleNode<T> getNode(int index) {

        if( index >= 0 && index < size) {

            DoubleNode<T> auxNode = firstNode;

            for (int i = 0; i < index; i++) {
                auxNode = auxNode.getNextNode();
            }

            return auxNode;
        }

        return null;
    }

    private DoubleNode<T> searchNode(T value){

        DoubleNode<T> aux = firstNode;

        while(aux != null) {
            if(aux.getValue().equals(value)) {
                return aux;
            }
            aux = aux.getNextNode();
        }

        return null;
    }

    public void modifyNode(int index, T newNode) {

        if( isValidIndex(index) ) {
            DoubleNode<T> auxNode = getNode(index);
            auxNode.setValue(newNode);
        }

    }

    public int getNodePosition(T value) {

        int i = 0;

        for( DoubleNode<T> aux = firstNode ; aux != null ; aux = aux.getNextNode() ) {
            if( aux.getValue().equals(value) ) {
                return i;
            }
            i++;
        }

        return -1;
    }

    public T getValueByIndex(int index) {

        if( isValidIndex(index) ) {
            DoubleNode<T> n = getNode(index);

            if( n != null ) {
                return n.getValue();
            }
        }

        return null;
    }


    @Override
    public Iterator<T> iterator() {

        return new IteratorDoubleList (firstNode);
    }

    protected class IteratorDoubleList implements Iterator<T>{

        private DoubleNode<T> Node;
        private int position;

        public IteratorDoubleList(DoubleNode<T> Node) {
            this.Node = Node;
            this.position = 0;
        }

        @Override
        public boolean hasNext() {
            return Node != null;
        }

        @Override
        public T next() {
            T value = Node.getValue();
            Node = Node.getNextNode();
            position++;
            return value;
        }

        public boolean hasPrevious() {
            return Node != null;
        }

        public T previous() {
            T aux = Node.getValue();
            Node = Node.getPreviousNode();
            position--;
            return aux;
        }

        public int nextIndex() {
            return position;
        }

        public int previousIndex() {
            return position-1;
        }

        public void remove() {
            if(Node != null) {
                removeNode(Node.getValue());
            }
        }

        public void set(T e) {
            if(Node != null) {
                Node.setValue(e);
            }
        }

        public void add(T e) {
            addEnd(e);
        }

        public int getPosition() {
            return position;
        }

    }

    public DoubleNode<T> getFirstNode() {
        return firstNode;
    }

    public void setFirstNode(DoubleNode<T> firstNode) {
        this.firstNode = firstNode;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public DoubleNode<T> getLastNode() {
        return lastNode;
    }

    public void setLastNode(DoubleNode<T> lastNode) {
        this.lastNode = lastNode;
    }

}