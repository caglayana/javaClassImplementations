package ArrayList;

import java.util.Arrays;

public class ArrayList<Generic> {
        private int size = 0;
        private Object[] elements;
        
        
        public ArrayList() {
            elements = new Object[100];
        }

        public void add(Generic e) {
                if (size == elements.length) {
                        ensureCapa();
                }
                elements[size++] = e;
        }


        private void ensureCapa() {
                int newSize = elements.length * 2;
                elements = Arrays.copyOf(elements, newSize);
        }

        public Generic get(int i) {
                if (i >= size || i <0) {
                        throw new IndexOutOfBoundsException("Index: " + i + ", Size " + this.size );
                }
                return (Generic) elements[i];
        }
        
        public Generic set(int index, Generic element) {
            Generic oldValue = (Generic) elements[index];
            elements[index] = element;
            return oldValue;
        }
        
        public boolean isEmpty() {
            if(size == 0) {
                return false;
            }
            return true;
        }
        
        public int size() {
            return this.size;
        }
}
