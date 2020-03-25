package toymodels;

import processing.core.*;
import java.util.Random;

public class ToyTzara {

	private PApplet p;

	private String text[];
	private String[] vowels = { "a", "e", "i", "o", "u", "A", "E", "I", "O",
			"U", " " };
	private String[] consonants = { "b", "c", "d", "f", "g", "h", "j", "k",
			"l", "m", "n", "p", "q", "r", "s", "t", "v", "x", "w", "y", "z",
			"B", "C", "D", "F", "G", "H", "J", "K", "L", "M", "N", "P", "Q",
			"R", "S", "T", "V", "X", "W", "Y", "Z", " " };

	public ToyTzara(PApplet setP) {
		p = setP;
	}

	public void loadSentence(String input) {
		text = new String[1];
		text[0] = input;
	}

	public void loadText(String[] input) {
		text = input;
	}

	public void loadTexts(String[][] input) {
		String singleString = "";
		for (int i = 0; i <= input.length - 1; i++) {
			singleString += p.join(input[i], "|x|cut|x|");
		}
		text = p.split(singleString, "|x|cut|x|");
	}

	public String cutUp() {
		String thisCut = text[p.round(p.random(text.length - 1))];
		return thisCut;
	}

	// Fisher-Yates Shuffle
	public String[] mixUp() {

		String[] mixup = text;
		Random rng = new Random();
		for (int i = text.length; i > 1; i--) {
			int j = rng.nextInt(i);
			String tmp = mixup[j];
			mixup[j] = mixup[i - 1];
			mixup[i - 1] = tmp;
		}
		return mixup;
	}

	public String[] vowels() {
		String[] justVowelsTxt = new String[text.length];
		for (int i = 0; i <= text.length - 1; i++) {
			justVowelsTxt[i] = "";
			for (int j = 0; j <= text[i].length() - 1; j++) {
				String thisChar = text[i].charAt(j) + "";
				for (int k = 0; k <= vowels.length - 1; k++) {
					if (thisChar.equals(vowels[k])) {
						justVowelsTxt[i] += thisChar;
					}
				}
			}
		}
		return justVowelsTxt;
	}

	public String[] consonants() {
		String[] justConsonantsTxt = new String[text.length];
		for (int i = 0; i <= text.length - 1; i++) {
			justConsonantsTxt[i] = "";
			for (int j = 0; j <= text[i].length() - 1; j++) {
				String thisChar = text[i].charAt(j) + "";
				for (int k = 0; k <= consonants.length - 1; k++) {
					if (thisChar.equals(consonants[k])) {
						justConsonantsTxt[i] += thisChar;
					}
				}
			}
		}
		return justConsonantsTxt;
	}

	public String[] select(String[] sel) {
		String[] selection = p.append(sel, " ");
		String[] justSelectedTxt = new String[text.length];
		for (int i = 0; i <= text.length - 1; i++) {
			justSelectedTxt[i] = "";
			for (int j = 0; j <= text[i].length() - 1; j++) {
				String thisChar = text[i].charAt(j) + "";
				for (int k = 0; k <= selection.length - 1; k++) {
					if (thisChar.equals(selection[k])) {
						justSelectedTxt[i] += thisChar;
					}
				}
			}
		}
		return justSelectedTxt;
	}

	public String[] mirror() {
		String[] mirrored = new String[text.length];
		for (int i = 0; i <= text.length - 1; i++) {
			String[] chars = new String[text[text.length - 1 - i].length()];
			mirrored[i] = "";
			for (int j = 0; j <= chars.length - 1; j++) {
				chars[j] = text[text.length - 1 - i].charAt(text[text.length
						- 1 - i].length()
						- 1 - j)
						+ "";
				mirrored[i] += chars[j];
			}
		}
		return mirrored;
	}

}
