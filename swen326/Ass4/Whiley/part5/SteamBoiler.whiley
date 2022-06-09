type nat is (int n) where n >= 0

type State is {
    // Water level
    nat level,
    // State of pumps (true = on)
    bool[] pumps
}

function InitialState(nat n) -> (State after)
// Eactly n pumps created
ensures |after.pumps| == n
// All pumps initially off
ensures all {k in 0..n | after.pumps[k] == false}
// Boiler initially empty
ensures after.level == 0:
    //
    return {level: 0, pumps: [false; n]}

// All pumps are unchanged between before/after state 
property pumpsUnchanged(State before, State after)
// Number of pumps preserved
where before.pumps == after.pumps
// All pumps remain unchanged
where all {k in 0..|before.pumps| | before.pumps[k] == after.pumps[k]}

// All pumps except ith are unchanged between before/after state 
property pumpsUnchangedExcept(State before, State after, int i)
// Number of pumps preserved
where before.pumps == after.pumps
// All pumps remain unchanged except ith
where all {k in 0..i | before.pumps[k] == after.pumps[k]} &&
before.pumps[i] != after.pumps[i] &&
all {k in i+1..|before.pumps| | before.pumps[k] == after.pumps[k]}

// Pump open transition occurs when a pump was closed before and is
// open after.  All other pumps and the water level remain unchanged.
function PumpOpen(State before, int i) -> (State after)
// Valid pump required
requires i >= 0 && i < |before.pumps|
// Pump must have been closed
requires before.pumps[i] == false
// Pump must now be open
ensures after.pumps[i] == true
// All other pumps unchanged
ensures all {k in 0..i | before.pumps[k] == after.pumps[k]} &&
all {k in i+1..|before.pumps| | before.pumps[k] == after.pumps[k]}:
    //    
    before.pumps[i] = true
    return before 

// Pump closed transition occurs when a pump was open before and is
// closed after.  All other pumps and the water level remain unchanged.
function PumpClosed(State before, int i) -> (State after)
// Valid pump required
requires i >= 0 && i < |before.pumps|
// Pump must have been open
requires before.pumps[i] == true
// Pump must now be closed
ensures after.pumps[i] == false
// All other pumps unchanged
ensures all {k in 0..i | before.pumps[k] == after.pumps[k]} &&
all {k in i+1..|before.pumps| | before.pumps[k] == after.pumps[k]}:
    //
    before.pumps[i] = false
    return before    

// Water in transition occurs when water is pumped into boiler.  All
// pumps remain unchanged, whilst the water level increases.
function WaterIn(State before) -> (State after)
// Requires some pump to be open
requires some {k in 0..|before.pumps| | before.pumps[k] == true}
// All pump states unchanged
ensures pumpsUnchanged(before, after)
// Water level increases
ensures before.level < after.level:
    //
    
    before.level = before.level + 1
    return before

// Steam out transition occurs when steam leaves boiler.  All
// pumps remain unchanged, whilst the water level decreases.
function SteamOut(State before) -> (State after)
// Need some water to remove
requires before.level > 0
// All pump states unchanged
ensures pumpsUnchanged(before, after)
// Water level decreases
ensures before.level > after.level:
    //
    before.level = before.level - 1
    return before