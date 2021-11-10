/*
 * Copyright 2021 Marc Liberatore.
 */

package sequencer;

import java.util.ArrayList;
import java.util.List;

public class Assembler {

	List<Fragment> Frag = new ArrayList<Fragment>();

	/**
	 * Creates a new Assembler containing a list of fragments.
	 * 
	 * The list is copied into this assembler so that the original list will not be
	 * modified by the actions of this assembler.
	 * 
	 * @param fragments
	 */
	public Assembler(List<Fragment> fragments) {
		for (int a = 0; a < fragments.size(); a++) {
			Frag.add(fragments.get(a));
		}
	}

	/**
	 * Returns the current list of fragments this assembler contains.
	 * 
	 * @return the current list of fragments
	 */
	public List<Fragment> getFragments() {
		return Frag;
	}

	/**
	 * Attempts to perform a single assembly, returning true iff an assembly was
	 * performed.
	 * 
	 * This method chooses the best assembly possible, that is, it merges the two
	 * fragments with the largest overlap, breaking ties between merged fragments by
	 * choosing the shorter merged fragment.
	 * 
	 * Merges must have an overlap of at least 1.
	 * 
	 * After merging two fragments into a new fragment, the new fragment is inserted
	 * into the list of fragments in this assembler, and the two original fragments
	 * are removed from the list.
	 * 
	 * @return true iff an assembly was performed
	 */
	public boolean assembleOnce() {
		Fragment temp1 = null;
		Fragment temp2 = null;
		Fragment finalFrag = null;
		int overlapCount = 0;
		int overlapCheck = -1;
		for (Fragment obj1 : Frag) {
			for (Fragment obj2 : Frag) {
				if (obj1 != obj2) {
					overlapCount = obj1.calculateOverlap(obj2);
					if (overlapCount > overlapCheck) {
						overlapCheck = overlapCount;
						temp1 = obj1;
						temp2 = obj2;
					}
					if (overlapCount == overlapCheck) {
						if (temp1.mergedWith(temp2).length() > obj1.mergedWith(obj2).length()) {
							temp1 = obj1;
							temp2 = obj2;
						}
					}
				}
				if (obj1 == null || obj2 == null) {
					return false;
				}
			}
		}
		if (overlapCheck > 0) {
			Frag.remove(temp1);
			Frag.remove(temp2);
			finalFrag = temp1.mergedWith(temp2);
			Frag.add(finalFrag);
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Repeatedly assembles fragments until no more assembly can occur.
	 */
	public void assembleAll() {
		while (assembleOnce() != false) {
			assembleOnce();
		}
	}
}
