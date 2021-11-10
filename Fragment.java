/*
 * Copyright 2021 Marc Liberatore.
 */

package sequencer;

public class Fragment {

	private String input;

	/**
	 * Creates a new Fragment based upon a String representing a sequence of
	 * nucleotides, containing only the uppercase characters G, C, A and T.
	 * 
	 * @param nucleotides
	 * @throws IllegalArgumentException if invalid characters are in the sequence of
	 *                                  nucleotides
	 */
	public Fragment(String nucleotides) throws IllegalArgumentException {
		boolean check = false;
		for (int a = 0; a < nucleotides.length(); a++) {
			if (nucleotides.charAt(a) == 'A' || nucleotides.charAt(a) == 'C' || nucleotides.charAt(a) == 'G'
					|| nucleotides.charAt(a) == 'T') {
				check = true;
			} else {
				check = false;
			}
			if (check == false) {
				throw new IllegalArgumentException("Illegal Characters");
			}
		}
		input = nucleotides;
	}

	/**
	 * Returns the length of this fragment.
	 * 
	 * @return the length of this fragment
	 */
	public int length() {
		return input.length();
	}

	/**
	 * Returns a String representation of this fragment, exactly as was passed to
	 * the constructor.
	 * 
	 * @return a String representation of this fragment
	 */
	@Override
	public String toString() {
		return input;
	}

	/**
	 * Return true if and only if this fragment contains the same sequence of
	 * nucleotides as another sequence.
	 */
	@Override
	public boolean equals(Object o) {
		boolean check = false;
		if (o == null) {
			check = false;
		}

		if (!(o instanceof Fragment)) {
			check = false;
		}

		if ((o.toString()).length() == input.length()) {
			for (int a = 0; a < input.length(); a++) {
				if (input.charAt(a) == (o.toString()).charAt(a)) {
					check = true;
				} else {
					check = false;
				}
			}
		} else {
			check = false;
		}

		// Don't unconditionally return false; check that
		// the relevant instances variables in this and f
		// are semantically equal
		return check;
	}

	/**
	 * Returns the number of nucleotides of overlap between the end of this fragment
	 * and the start of another fragment, f.
	 * 
	 * The largest overlap is found, for example, CAA and AAG have an overlap of 2,
	 * not 1.
	 * 
	 * @param f the other fragment
	 * @return the number of nucleotides of overlap
	 */
	public int calculateOverlap(Fragment f) {
		int numOverlap = 0;
		String input2 = f.toString();
		int fragLength = 0;
		if (input.length() < input2.length()) {
			fragLength = input.length();
		} else {
			fragLength = input2.length();
		}
		for (int a = 0; a <= fragLength; a++) {
			String fragPiece = f.toString().substring(0, a);
			if (input.endsWith(fragPiece)) {
				numOverlap = a;
			}
		}
		return numOverlap;
	}

	/**
	 * Returns a new fragment based upon merging this fragment with another fragment
	 * f.
	 * 
	 * This fragment will be on the left, the other fragment will be on the right;
	 * the fragments will be overlapped as much as possible during the merge.
	 * 
	 * @param f the other fragment
	 * @return a new fragment based upon merging this fragment with another fragment
	 */
	public Fragment mergedWith(Fragment f) {
		int finalLength = this.calculateOverlap(f);
		Fragment last = new Fragment(this.input + f.input.substring(finalLength));
		return last;
	}
}
