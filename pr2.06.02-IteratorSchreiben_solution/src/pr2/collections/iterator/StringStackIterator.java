package pr2.collections.iterator;

import java.util.Iterator;

public class StringStackIterator implements Iterator<String> {
    private String[] stack;
    private int pos;

    public StringStackIterator(String[] stack, int pos) {
        this.stack = stack;
        this.pos = pos;
    }

    public boolean hasNext() {
        return this.pos > 0;
    }

    public String next() {
        return this.stack[--this.pos];
    }
}
