
package lab5;

import java.util.AbstractList;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

public class MyLinkedList<T> extends AbstractList<T> {
    Node head, tail;
    int size;

    protected class Node {
        T data;
        Node next;
        Node prev;

        protected Node() {
            data = null;
            next = null;
            prev = null;
        }
    }

    public MyLinkedList() {
        head = new Node();
        tail = new Node();
        head.next = tail;
        tail.prev = head;
        size = 0;
    }

    private Node getNth(int index) {
        if (index > size) {
            throw new IndexOutOfBoundsException("index > size -> getNth");
        }

        Node node = head;
        for (int i = -1; i < size; i++) {
            if (i == index) {
                break;
            }
            node = node.next;
        }
        return node;
    }

    private boolean removeNode(Node n) {
        n.prev.next = n.next;
        n.next.prev = n.prev;
        size--;
        return true;
    }

    public boolean add(T data) {
        add(size, data);
        return true;
    }

    public void add(int index, T data) {
        if (data == null) {
            throw new NullPointerException("cannot add null to the linked list!");
        } else if (index > size || index < 0) {
            throw new IndexOutOfBoundsException("indez err size -> add");
        }

        Node node = getNth(index).prev;
        Node dataNode = new Node();
        dataNode.data = data;
        dataNode.next = node.next;
        node.next.prev = dataNode;
        node.next = dataNode;
        dataNode.prev = node;
        size++;
        modCount++;
    }

    public T set(int index, T data) {
        if (data == null) {
            throw new NullPointerException("cannot set node to null -> set");
        } else if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("index > size -> set");
        }
        Node node = getNth(index);
        T ndata = node.data;
        node.data = data;
        return ndata;

    }

    public T remove(int index) {
        if (index >= size) {
            throw new IndexOutOfBoundsException("indez > size -> remove");
        }
        Node node = getNth(index);
        T ndata = node.data;
        this.removeNode(node);
        modCount++;
        return ndata;

    }

    public void clear() {
        head.next = tail;
        tail.prev = head;
        size = 0;
        modCount++;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public T get(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException();
        }
        return getNth(index).data;
    }

    @Override
    public int size() {
        // TODO Auto-generated method stub
        return this.size;
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        // TODO Auto-generated method stub
        for (T i : c) {
            add(i);
        }

        return true;
    }

    @Override
    public ListIterator<T> listIterator() {
        return new MyLinkedListIterator();
    }

    @Override
    public Iterator<T> iterator() {
        return new MyLinkedListIterator();
    }

    class MyLinkedListIterator implements ListIterator<T> {

        // Instance variables here
        private Node prev;
        private Node next;
        private int index;
        private int modIter;

        public MyLinkedListIterator() {
            prev = head;
            next = head.next;
            index = 0;
            modIter = modCount;
        }

        public boolean hasNext() {
            // Your code here
            if (modIter != modCount) {
                throw new ConcurrentModificationException("List modified!");
            } else if (this.next.data == null) {
                return false;
            }
            return true;
        }

        @Override
        public T next() {
            // TODO Auto-generated method stub
            if (modIter != modCount) {
                throw new ConcurrentModificationException("List modified!");
            } else if (!hasNext()) {
                throw new NoSuchElementException("no next element!");
            }
            this.prev = this.next;
            this.next = this.next.next;
            index++;
            return this.prev.data;
        }

        @Override
        public boolean hasPrevious() {
            // TODO Auto-generated method stub
            if (modIter != modCount) {
                throw new ConcurrentModificationException("List modified!");
            } else if (this.prev.data == null) {
                return false;
            }
            return true;
        }

        @Override
        public T previous() {
            // TODO Auto-generated method stub
            if (modIter != modCount) {
                throw new ConcurrentModificationException("List modified!");
            } else if (!hasPrevious()) {
                throw new NoSuchElementException("no next element!");
            }
            this.next = this.prev;
            this.prev = this.prev.prev;
            index--;
            return this.next.data;
        }

        @Override
        public int nextIndex() {
            // TODO Auto-generated method stub
            return this.index;
        }

        @Override
        public int previousIndex() {
            // TODO Auto-generated method stub
            return this.index - 1;
        }

        @Override
        public void remove() {
            // TODO Auto-generated method stub
            throw new NoSuchMethodError("remove - not implemented");
        }

        @Override
        public void set(T e) {
            // TODO Auto-generated method stub
            throw new NoSuchMethodError("set - not implemented");

        }

        @Override
        public void add(T e) {
            // TODO Auto-generated method stub
            throw new NoSuchMethodError("set - not implemented");

        }

        // More methods, etc.
    }

}
