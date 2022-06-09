package tinyboycov.tests;

import java.io.IOException;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class Part1_Tests {
	/**
	 * You can change this number as you see fit. Remember, 8Mhz means 8_000_000
	 * cycles per second. Hence, 8_000 cycles in one ms.
	 */
	public static final int PULSE_LENGTH = (250 * 8_000);

	@Test
	public void test_01_fader() throws IOException {
		// NOTE: the fader example doesn't respond to input...
		String inputSequence = "__";
		TestUtils.checkManualCoverage("fader.hex", inputSequence, PULSE_LENGTH);
	}

	@Test
	public void test_02_blocks() throws IOException {
		String inputSequence = "L";
		TestUtils.checkManualCoverage("blocks.hex", inputSequence, PULSE_LENGTH);
	}

	@Test
	public void test_03_blocks_2() throws IOException {
		String inputSequence = "L";
		TestUtils.checkManualCoverage("blocks_2.hex", inputSequence, PULSE_LENGTH);
	}

	@Test
	public void test_04_sokoban() throws IOException {
		String inputSequence = "LL";
		TestUtils.checkManualCoverage("sokoban.hex", inputSequence, PULSE_LENGTH);
	}

	@Test
	public void test_05_snake() throws IOException {
		String inputSequence = "LL";
		TestUtils.checkManualCoverage("snake.hex", inputSequence, PULSE_LENGTH);
	}

	@Test
	public void test_06_tetris() throws IOException {
		// NOTE: an example input sequence --- you'll want something better.
		String inputSequence = "LLUURRDD";
		TestUtils.checkManualCoverage("tetris.hex", inputSequence, PULSE_LENGTH);
	}
}
