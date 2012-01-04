package com.ebaytools.util;

/**
 * This is helper class, which help in JComboBox
 * @author Admin
 *
 */
public class Pair<T> {
	private String key;
	private T value;
	
	public Pair(String key, T value) {
		this.key = key;
		this.value = value;
	}
	
	public String getKey() {
		return key;
	}

    public T getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Pair pair = (Pair) o;

        return !(key != null ? !key.equals(pair.key) : pair.key != null) && (value != null ? !value.equals(pair.value) : pair.value != null);
    }

    @Override
    public int hashCode() {
        int result = key != null ? key.hashCode() : 0;
        result = 31 * result + (value != null ? value.hashCode() : 0);
        return result;
    }

    @Override
	public String toString() {
		return key;
	}
}
