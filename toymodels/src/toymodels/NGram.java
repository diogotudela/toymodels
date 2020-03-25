package toymodels;

import processing.core.*;
import java.util.ArrayList;
import java.io.*;

public class NGram {

	PApplet p;

	private String thisNGram;
	private ArrayList<String> possibilities;

	public NGram(PApplet setP, String setNGram, String setFirstPossibility) {
		p = setP;
		thisNGram = setNGram;
		possibilities = new ArrayList<String>();
		possibilities.add("" + setFirstPossibility);
	}

	public String getGram() {
		return thisNGram;
	}

	public int getOcurences() {
		return possibilities.size();
	}

	// TXT
	public void addNextPossibility(char nextPossibility) {
		possibilities.add("" + nextPossibility);
	}

	// IMG
	public void addNextPossibility(String nextPossibility) {
		possibilities.add("" + nextPossibility);
	}

	public String getPossibleNextGram() {
		String thisPossibility = (String) possibilities.get(p.round(p
				.random(possibilities.size() - 1)));
		return thisPossibility;
	}

	public void printPossibilities() {
		for (int i = 0; i <= possibilities.size() - 1; i++) {
			String thisNGram = (String) possibilities.get(i);
			p.print(" [" + i + "] " + thisNGram);
		}
	}

	public void exportPossibilities(PrintWriter file) {
		for (int i = 0; i <= possibilities.size() - 1; i++) {
			String thisNGram = (String) possibilities.get(i);
			if (i == possibilities.size() - 1)
				file.print(thisNGram);
			else
				file.print(thisNGram + ",");
		}
	}

}
