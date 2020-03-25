package toymodels;

import processing.core.*;
import java.util.ArrayList;
import java.io.*;

public class ToyMarkov {

	private PApplet p;
	private String input;
	private int order;
	private int nGramLength;
	private String[] storeNGram;
	private ArrayList<NGram> NGramTable;

	public ToyMarkov(PApplet setP) {
		p = setP;
	}

	// LOAD STRING
	public void loadSentence(String setInput, int setOrder) {
		input = setInput;
		order = setOrder;
		nGramLength = (input.length() - order) + 1;
		storeNGram = new String[nGramLength];
		NGramTable = new ArrayList<NGram>();
		build();
	}

	// LOAD STRING ARRAY
	public void loadText(String[] setInputMatrix, String separator, int setOrder) {
		input = p.join(setInputMatrix, separator);
		order = setOrder;
		nGramLength = (input.length() - order) + 1;
		storeNGram = new String[nGramLength];
		NGramTable = new ArrayList<NGram>();
		build();
	}

	// LOAD STRING MATRIX
	public void loadTexts(String[][] setInputStack, String separator,
			int setOrder) {
		input = "";
		for (int i = 0; i <= setInputStack.length - 1; i++) {
			input += p.join(setInputStack[i], separator);
		}
		order = setOrder;
		nGramLength = (input.length() - order) + 1;
		storeNGram = new String[nGramLength];
		NGramTable = new ArrayList<NGram>();
		build();
	}

	// LOAD STRING MATRIX / MIXER
	public void loadTexts(String[][] setInputStack, float[] proportions,
			String separator, int setOrder) {
		boolean oneSum = false;
		boolean equalLength = false;
		float sum = 0;
		for (int i = 0; i <= proportions.length - 1; i++) {
			sum += proportions[i];
		}
		if (p.round(sum) == 1)
			oneSum = true;
		if (setInputStack.length == proportions.length)
			equalLength = true;
		if (oneSum && equalLength) {
			input = "";
			for (int i = 0; i <= proportions.length - 1; i++) {
				int mult = p.round(proportions[i] * 10);
				for (int j = 0; j <= mult - 1; j++) {
					input += p.join(setInputStack[i], separator);
				}
			}
			order = setOrder;
			nGramLength = (input.length() - order) + 1;
			storeNGram = new String[nGramLength];
			NGramTable = new ArrayList<NGram>();
			build();
		}
	}

	public void loadImg(PImage inputImage, int setOrder) {
		inputImage.loadPixels();
		String[] hexSequence = new String[inputImage.height * inputImage.width];
		for (int i = 0; i <= inputImage.pixels.length - 1; i++) {
			hexSequence[i] = p.hex(inputImage.pixels[i]);
		}
		input = p.join(hexSequence, "");
		order = setOrder * 8;
		nGramLength = hexSequence.length - order;
		storeNGram = new String[nGramLength];
		NGramTable = new ArrayList<NGram>();
		buildImg();
	}

	public void loadModel(String fileName) {
		String nextChar = "";
		fileName = fileName + ".csv";
		String[] chart = p.loadStrings(fileName);
		NGramTable = new ArrayList<NGram>();
		for (int i = 0; i <= chart.length - 1; i++) {
			String thisRow[] = p.split(chart[i], ',');
			for (int j = 0; j <= thisRow.length - 1; j++) {
				if (j == 0) {
					nextChar = thisRow[1].charAt(0) + "";
					NGramTable.add(new NGram(p, thisRow[0], nextChar));
				}
				if (j > 1) {
					NGram thisGram = (NGram) NGramTable.get(i);
					nextChar = thisRow[j].charAt(0) + "";
					thisGram.addNextPossibility(nextChar);
				}
			}
		}
	}

	private void build() {
		String nextChar = "";
		for (int i = 0; i <= storeNGram.length - 1; i++) {
			storeNGram[i] = input.substring(i, i + order);
		}
		nextChar = input.charAt(order) + "";
		NGramTable.add(new NGram(p, storeNGram[0], nextChar));
		for (int i = 1; i <= storeNGram.length - 1; i++) {
			boolean alreadyExists = false;
			for (int j = 0; j <= NGramTable.size() - 1; j++) {
				NGram thisGram = (NGram) NGramTable.get(j);
				if (thisGram.getGram().equals(storeNGram[i])) {
					alreadyExists = true;
					if (i != storeNGram.length - 1) {
						nextChar = input.charAt(i + order) + "";
						thisGram.addNextPossibility(nextChar);
					}
				}
			}
			if (alreadyExists == false) {
				if (i != storeNGram.length - 1) {
					nextChar = input.charAt(i + order) + "";
					NGramTable.add(new NGram(p, storeNGram[i], nextChar));
				}
				if (i == storeNGram.length - 1) {
					nextChar = input.charAt(0) + "";
					NGramTable.add(new NGram(p, storeNGram[i], nextChar));
				}
			}
		}
	}

	private void buildImg() {
		for (int i = 0; i <= storeNGram.length - 1; i++) {
			storeNGram[i] = input.substring(i * 8, (i * 8) + order);
		}
		NGramTable.add(new NGram(p, storeNGram[0], storeNGram[1]));
		for (int i = 1; i <= storeNGram.length - 1; i++) {
			boolean alreadyExists = false;
			for (int j = 0; j <= NGramTable.size() - 1; j++) {
				NGram thisGram = (NGram) NGramTable.get(j);
				if (thisGram.getGram().equals(storeNGram[i])) {
					alreadyExists = true;
					if (i != storeNGram.length - 1) {
						thisGram.addNextPossibility(storeNGram[i + 1]);
					}
				}
			}
			if (alreadyExists == false) {
				if (i != storeNGram.length - 1)
					NGramTable.add(new NGram(p, storeNGram[i],
							storeNGram[i + 1]));
				if (i == storeNGram.length - 1)
					NGramTable.add(new NGram(p, storeNGram[i], storeNGram[0])); // LOOP
																				// Back
			}
		}
	}

	public void printChart() {
		p.println("MARKOV CHAIN");
		for (int i = 0; i <= NGramTable.size() - 1; i++) {
			NGram thisGram = (NGram) NGramTable.get(i);
			p.print(p.nf(i, 4) + " | ");
			p.print("N-Gram: " + thisGram.getGram() + " | ");
			p.print("Ocurences: " + thisGram.getOcurences() + " | ");
			p.print("Possibilities: ");
			thisGram.printPossibilities();
			p.println();
		}
		p.println("\n");
	}

	public void exportModel(String fileName) {
		PrintWriter file;
		file = p.createWriter(fileName + "_" + order + "-Grams table.csv");
		for (int i = 0; i <= NGramTable.size() - 1; i++) {
			NGram thisGram = (NGram) NGramTable.get(i);
			file.print(thisGram.getGram() + ",");
			thisGram.exportPossibilities(file);
			file.println();
		}
		file.flush();
		file.close();
	}

	public int getLength() {
		return NGramTable.size();
	}

	public String getFirstNGram() {
		return storeNGram[0];
	}

	public String getRandomNGram() {
		return storeNGram[p.floor(p.random(storeNGram.length - 1))];
	}

	public String genText(String starter, int maxLimit) {
		String result = starter;
		for (int i = 0; i <= maxLimit - 1; i++) {
			for (int j = 0; j <= NGramTable.size() - 1; j++) {
				NGram thisGram = (NGram) NGramTable.get(j);
				if (thisGram.getGram().equals(starter)) {
					starter += thisGram.getPossibleNextGram();
					starter = starter.substring(starter.length() - order,
							starter.length());
					result += thisGram.getPossibleNextGram();
				}
			}
		}
		return result;
	}

	public PImage genImg(String starter, int w, int h, boolean save) { // <---------
																		// int w
																		// x int
																		// h
		String[] sequence = new String[w * h];
		sequence[0] = starter;
		for (int i = 1; i <= sequence.length - 1; i++) { // <------init=1?
			for (int j = 0; j <= NGramTable.size() - 1; j++) {
				NGram thisGram = (NGram) NGramTable.get(j);
				if (thisGram.getGram().equals(starter)) {
					starter += thisGram.getPossibleNextGram();
					starter = starter.substring(starter.length() - order,
							starter.length());
					sequence[i] = starter;
				}
			}
		}
		int c = 0;
		int slices = p.round(order / 8);
		int[] newPixels = new int[sequence.length * slices];
		for (int i = 0; i <= sequence.length - 1; i++) {
			for (int j = 0; j < slices; j++) {
				newPixels[c] = p.unhex(sequence[i].substring(j * 8, j * 8 + 8));
				c++;
			}
		}
		PImage img = p.createImage(w, h, p.RGB);
		img.loadPixels();
		for (int i = 0; i <= img.pixels.length - 1; i++) {
			if (i <= newPixels.length - 1) {
				img.pixels[i] = newPixels[i];
			} else {
				img.pixels[i] = p.color(0, 0, 0);
			}
		}
		img.updatePixels();
		if (save)
			img.save("toyMarkovExports/toyMarkovImage" + p.year() + "_"
					+ p.month() + "_" + p.day() + "_" + p.hour() + "_"
					+ p.minute() + "_" + p.second());
		return img;
	}

}
