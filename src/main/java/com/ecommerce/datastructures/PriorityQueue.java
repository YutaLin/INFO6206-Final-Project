package com.ecommerce.datastructures;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Custom Priority Queue implementation using a binary heap
 * Time Complexity: O(log n) for enqueue and dequeue, O(1) for peek
 * Space Complexity: O(n)
 * Used for: Featured/recommended products ranking
 */
public class PriorityQueue<T> {
    private static final int DEFAULT_CAPACITY = 16;

    private T[] heap;
    private int size;
    private Comparator<T> comparator;

    @SuppressWarnings("unchecked")
    public PriorityQueue(Comparator<T> comparator) {
        this.heap = (T[]) new Object[DEFAULT_CAPACITY];
        this.size = 0;
        this.comparator = comparator;
    }

    @SuppressWarnings("unchecked")
    public PriorityQueue() {
        this.heap = (T[]) new Object[DEFAULT_CAPACITY];
        this.size = 0;
        this.comparator = null;
    }

    /**
     * Add an element to the priority queue
     * Time Complexity: O(log n)
     */
    public void enqueue(T data) {
        if (size == heap.length) {
            resize();
        }

        heap[size] = data;
        heapifyUp(size);
        size++;
    }

    /**
     * Remove and return the highest priority element
     * Time Complexity: O(log n)
     */
    public T dequeue() {
        if (isEmpty()) {
            throw new IllegalStateException("Priority queue is empty");
        }

        T result = heap[0];
        heap[0] = heap[size - 1];
        heap[size - 1] = null;
        size--;

        if (size > 0) {
            heapifyDown(0);
        }

        return result;
    }

    /**
     * Peek at the highest priority element without removing it
     * Time Complexity: O(1)
     */
    public T peek() {
        if (isEmpty()) {
            throw new IllegalStateException("Priority queue is empty");
        }
        return heap[0];
    }

    /**
     * Bubble up element at given index to maintain heap property
     */
    private void heapifyUp(int index) {
        while (index > 0) {
            int parentIndex = (index - 1) / 2;
            if (compare(heap[index], heap[parentIndex]) >= 0) {
                break;
            }
            swap(index, parentIndex);
            index = parentIndex;
        }
    }

    /**
     * Bubble down element at given index to maintain heap property
     */
    private void heapifyDown(int index) {
        while (true) {
            int leftChild = 2 * index + 1;
            int rightChild = 2 * index + 2;
            int smallest = index;

            if (leftChild < size && compare(heap[leftChild], heap[smallest]) < 0) {
                smallest = leftChild;
            }

            if (rightChild < size && compare(heap[rightChild], heap[smallest]) < 0) {
                smallest = rightChild;
            }

            if (smallest == index) {
                break;
            }

            swap(index, smallest);
            index = smallest;
        }
    }

    /**
     * Compare two elements using comparator or natural ordering
     */
    @SuppressWarnings("unchecked")
    private int compare(T a, T b) {
        if (comparator != null) {
            return comparator.compare(a, b);
        } else {
            return ((Comparable<T>) a).compareTo(b);
        }
    }

    /**
     * Swap two elements in the heap
     */
    private void swap(int i, int j) {
        T temp = heap[i];
        heap[i] = heap[j];
        heap[j] = temp;
    }

    /**
     * Resize the heap array
     */
    @SuppressWarnings("unchecked")
    private void resize() {
        T[] newHeap = (T[]) new Object[heap.length * 2];
        System.arraycopy(heap, 0, newHeap, 0, size);
        heap = newHeap;
    }

    /**
     * Get all elements as a list (in heap order, not sorted)
     */
    public List<T> toList() {
        List<T> list = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            list.add(heap[i]);
        }
        return list;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void clear() {
        for (int i = 0; i < size; i++) {
            heap[i] = null;
        }
        size = 0;
    }
}
