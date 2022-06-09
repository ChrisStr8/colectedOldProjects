package avranalysis.core;

import java.util.HashMap;

import javr.core.AvrDecoder;
import javr.core.AvrInstruction;
import javr.core.AvrInstruction.AbsoluteAddress;
import javr.core.AvrInstruction.RelativeAddress;
import javr.io.HexFile;
import javr.memory.ElasticByteMemory;

public class StackAnalysis {
	/**
	 * Contains the raw bytes of the given firmware image being analysed.
	 */
	private ElasticByteMemory firmware;

	/**
	 * The decoder is used for actually decoding an instruction.
	 */
	private AvrDecoder decoder = new AvrDecoder();

	/**
	 * Records the maximum height seen so far.
	 */
	private int maxHeight;

	/**
	 *
	 * @param hf
	 */
	public StackAnalysis(HexFile hf) {
		// Create firmware memory
		this.firmware = new ElasticByteMemory();
		// Upload image to firmware memory
		hf.uploadTo(firmware);
	}

	/**
	 * Apply the stack analysis to the given firmware image producing a maximum
	 * stack usage (in bytes).
	 *
	 * @return
	 */
	public int apply() {
		// Reset the maximum, height
		this.maxHeight = 0;
		// Traverse instructions starting at beginning
		traverse(0, 0, new HashMap<>());
		// Return the maximum height observed
		return maxHeight;
	}

	/**
	 * Traverse the instruction at a given pc address, assuming the stack has a
	 * given height on entry.
	 *
	 * @param pc
	 *            Program Counter of instruction to traverse
	 * @param currentHeight
	 *            Current height of the stack at this point (in bytes)
	 * @return
	 */
	private int traverse(int pc, int currentHeight, HashMap<Integer, Integer> heightMap) {
		// Check whether current stack height is maximum
		maxHeight = Math.max(maxHeight, currentHeight);
		// Check whether we have terminated or not
		if ((pc * 2) >= firmware.size()) {
			// We've gone over end of instruction sequence, so stop.
			return currentHeight;
		}
		//System.out.println("pc: "+pc);
		HashMap<Integer, Integer> hMap = new HashMap<>(heightMap);
		if (hMap.containsKey(pc)){
			int lastHeight = hMap.get(pc);
			if (lastHeight == currentHeight){
				//System.out.println("Stable Stack Height: "+currentHeight+", pc = " + pc);
				hMap.replace(pc, currentHeight);
				return currentHeight;
			} else if(lastHeight < currentHeight){
				//System.out.println("Unstable Stack Height, pc = " + pc);
				maxHeight = Integer.MAX_VALUE;
				hMap.replace(pc, Integer.MAX_VALUE);
				return Integer.MAX_VALUE;
			}
		}else{
			//System.out.println("seen "+pc+" at height "+currentHeight);
			hMap.put(pc, currentHeight);
		}
		// Process instruction at this address
		AvrInstruction instruction = decodeInstructionAt(pc);
		// Move to the next logical instruction as this is always the starting point.
		int next = pc + instruction.getWidth();
		//
		//System.out.println("currentHeight " + currentHeight);
		//System.out.println("process pc: "+pc+" "+instruction.getOpcode());
		process(instruction, next, currentHeight, hMap);
		//System.out.println(" ");
		return currentHeight;

	}

	/**
	 * Process the effect of a given instruction.
	 *
	 *  @param instruction
	 *            Instruction to process
	 * @param pc
	 *            Program counter of following instruction
	 * @param currentHeight
	 *            Current height of the stack at this point (in bytes)
	 */
	private int process(AvrInstruction instruction, int pc, int currentHeight, HashMap<Integer, Integer> heightMap) {
		switch (instruction.getOpcode()) {
			case BRBC:
			case BRBS:
			case BREQ:
			case BRGE:
			case BRHC:
			case BRHS:
			case BRID:
			case BRIE:
			case BRLO:
			case BRLT:
			case BRMI:
			case BRPL:
			case BRSH:
			case BRTC:
			case BRTS:
			case BRVC:
			case BRVS:
			/*case BRNE:*/{
				RelativeAddress branch = (RelativeAddress) instruction;
				if(branch.k != -1) {
					//System.out.println("	branch 1");
					traverse(pc + branch.k, currentHeight, heightMap);
				}
				//System.out.println("	branch 2");
				traverse(pc, currentHeight, heightMap);
				//
				break;
			}
			case CALL: {
				AbsoluteAddress branch = (AbsoluteAddress) instruction;
				currentHeight += 2;
				int height = traverse(branch.k, currentHeight, heightMap);
				traverse(pc, height, heightMap);
				//
				break;
			}
			case CPSE:
			case SBIC:
			//case SBIS:
			case SBRS:
			case SBRC: {
				//System.out.println("	skip 1");
				traverse(pc + decodeInstructionAt(pc).getWidth(), currentHeight, heightMap);
				//System.out.println("	skip 2");
				traverse(pc, currentHeight, heightMap);
				break;
			}
			case EICALL:{
				currentHeight += 3;
				traverse(pc, currentHeight, heightMap);
				break;
			}
			//case ICALL:
			case RCALL: {
				RelativeAddress branch = (RelativeAddress) instruction;
				currentHeight += 2;
				if(branch.k != -1){
					//System.out.println("	RCall 1");
					int height = traverse(pc + branch.k, currentHeight, heightMap);
					//System.out.println("	RCall 2");
					traverse(pc, height, heightMap);
				}
				//
				break;
			}
			case JMP: {
				AbsoluteAddress branch = (AbsoluteAddress) instruction;
				traverse(branch.k, currentHeight, heightMap);
				//
				break;
			}
			case RJMP: {
				// NOTE: this one is implemented for you.
				RelativeAddress branch = (RelativeAddress) instruction;
				// Check whether infinite loop; if so, terminate.
				if (branch.k != -1) {
					// Explore the branch target
					traverse(pc + branch.k, currentHeight, heightMap);
				}
				//
				break;
			}
			case LDS:{
				traverse(pc +1, currentHeight, heightMap);
			}
			case RET:
			case RETI: {
				currentHeight -= 2;
				break;
			}
			case PUSH: {
				currentHeight++;
				traverse(pc, currentHeight, heightMap);
				//
				break;
			}
			case POP: {
				currentHeight--;
				traverse(pc, currentHeight, heightMap);
				//
				break;
			}
			default:
				// Indicates a standard instruction where control is transferred to the
				// following instruction.
				traverse(pc, currentHeight, heightMap);
			}
			return currentHeight;
	}

	/**
	 * Decode the instruction at a given PC location.
	 *
	 * @param pc
	 * @return
	 */
	private AvrInstruction decodeInstructionAt(int pc) {
		return decoder.decode(firmware, pc);
	}
}
