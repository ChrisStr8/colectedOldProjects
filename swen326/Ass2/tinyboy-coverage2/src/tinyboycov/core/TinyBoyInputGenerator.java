package tinyboycov.core;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.Random;

import javr.util.ArrayBitList;
import javr.util.BitList;
import tinyboy.core.ControlPad;
import tinyboy.core.TinyBoyInputSequence;
import tinyboy.util.AutomatedTester;

/**
 * The TinyBoy Input Generator is responsible for generating and refining inputs
 * to try and ensure that sufficient branch coverage is obtained.
 *
 * @author David J. Pearce
 *
 */
public class TinyBoyInputGenerator implements AutomatedTester.InputGenerator<TinyBoyInputSequence> {
	/**
	 * Determines the number of pulses in a given input sequence. Increasing this
	 * value means there are more pulses, but then more possible combinations.
	 */
	private static final int PULSE_COUNT = 10;
	/**
	 * Determine the pulse length. This determines the absolute length (in cycles)
	 * of an input sequence. The cap will be 1 second, so don't forget that.
	 */
	private static final int PULSE_LENGTH = (100 * 8_000); // 100ms
	/**
	 * Use random number generation with fixed seed for deterministic behaviour. You
	 * can use a proper seed if you prefer. This may work better.
	 */
	private Random random = new Random(274837);
	/**
	 * The current list of candidate roots.
	 */
	private Pair last = null;

	@Override
	public TinyBoyInputSequence generate() {
		TinyBoyInputSequence inputs;
		if (last == null) {
			inputs = new TinyBoyInputSequence(PULSE_COUNT,PULSE_LENGTH);
			// Using a D6 means 2 / 6 probability of null pulse.
			inputs = randomlyMutate(inputs, inputs.size(), 6);
		} else {
			inputs = selectAndMutateRoot();
		}
		return inputs;
	}

	@Override
	public void record(TinyBoyInputSequence input, BitSet output) {
		// NOTE: this is an opportunity to record what output was obtained from a given
		// input. This allows us to discard generated inputs which do not in some way
		// improve our situation.
		if(last == null) {
			// FIXME: should only replace last one if this one is better. See "subsumedBy".
			last = new Pair(input,output);
		}
	}

	/**
	 * Check whether a given input sequence is completely subsumed by another.
	 *
	 * @param lhs
	 *            The one which may be subsumed.
	 * @param rhs
	 *            The one which may be subsuming.
	 * @return
	 */
	public boolean subsumedBy(BitSet lhs, BitSet rhs) {
		for (int i = lhs.nextSetBit(0); i >= 0; i = lhs.nextSetBit(i + 1)) {
			if (!rhs.get(i)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * This selects a root to mutate and does so.
	 *
	 * @return
	 */
	private TinyBoyInputSequence selectAndMutateRoot() {
		// FIXME: we only have one recorded pair, which rather limits the processing of
		// this algorithm

		// NOTE: randomly mutating only 1 pulse makes this relatively unaggressive.
		// Likewise, using a d6 means the probability of a null is 2/6.
		return randomlyMutate(last.input,1,6);
	}

	/**
	 * Randomly mutate a given input sequence. This will mutate exactly
	 * <code>n</code> input values randomly. Varying the value of <code>n</code>
	 * will change how aggressive the mutation is. The incoming list will not be
	 * affected and a completely new (mutated) sequence will be created.
	 *
	 * @param root
	 *            The input sequence to mutate.
	 * @param n
	 *            The number of pulses to mutate.
	 * @param m
	 *            The sides of the dice to roll for each mutation.  This determines the likelihood of a button versus null.
	 * @return
	 */
	private TinyBoyInputSequence randomlyMutate(TinyBoyInputSequence root, int n, int m) {
		TinyBoyInputSequence nRoot = new TinyBoyInputSequence(root);
		final int size = PULSE_COUNT;
		for (int i = 0; i != size; ++i) {
			int index = random.nextInt(size - i);
			if (index < n) {
				// Perform a mutation
				ControlPad.Button b = getRandomButton(m);
				nRoot.setPulse(i, b);
				n = n - 1;
			}
		}
		return nRoot;
	}

	/**
	 * Get a random control pad button, or null (to indicate no button should be
	 * pushed). The <code>m</code> parameter indicates the total size of the
	 * dice. That is, we have number of buttons + m gives the sides of the dice.
	 * If a number comes up which is not a control pad button, then just return
	 * null.  Hence, we can control the probability of a button versus null.
	 *
	 * @param m
	 * @return
	 */
	private ControlPad.Button getRandomButton(int m) {
		int numButtons = ControlPad.Button.values().length;
		int roll = random.nextInt(m);
		if (roll >= numButtons) {
			// We've rolled into a gap.
			return null;
		} else {
			// We've rolled into a button.
			return ControlPad.Button.values()[roll];
		}
	}

	private final static class Pair {
		public final TinyBoyInputSequence input;
		public final BitSet output;

		public Pair(TinyBoyInputSequence input, BitSet output) {
			this.input = input;
			this.output = output;
		}
	}
}
