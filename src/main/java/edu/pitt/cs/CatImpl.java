package edu.pitt.cs;

public class CatImpl implements Cat {

	// TODO: Fill in with member variables

	private int id;
	private String name;
	private boolean rented = false;
	


	public CatImpl(int id, String name) {
		// TODO: Fill in
		this.id = id;
		this.name = name;
	}

	public void rentCat() {
		// TODO: Fill in
		rented = true;
	}

	public void returnCat() {
		// TODO: Fill in
		rented = false;

	}

	public void renameCat(String str) {
		// TODO: Fill in
		name = str;
	}

	public String getName() {
		// TODO: Fill in
		return name;
	}

	public int getId() {
		// TODO: Fill in
		return id;
	}

	public boolean getRented() {
		// TODO: Fill in
		return rented;
	}

	public String toString() {
		// TODO: Fill in
		String tostr = "ID "+id +". "+name;
		return tostr;
	}

}