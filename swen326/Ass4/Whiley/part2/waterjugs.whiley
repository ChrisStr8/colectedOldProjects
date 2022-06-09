function pourSmall2Large(int smallJug, int largeJug) ->
(int smallJugAfter, int largeJugAfter)
// The small jug holds between 0 and 3 litres (before)
requires smallJug >= 0 && smallJug <= 3
// The large jug holds between 0 and 5 litres (before)
requires largeJug >= 0 && largeJug <= 5
// The small jug holds between 0 and 3 litres (after)
ensures smallJugAfter >= 0 && smallJugAfter <= 3
// The large jug holds between 0 and 5 litres (after)
ensures largeJugAfter >= 0 && largeJugAfter <= 5
// The amount in both jugs is unchanged by this function
ensures smallJugAfter + largeJugAfter == smallJug + largeJug
// Afterwards, either the small jug is empty or the large jug is full
ensures smallJugAfter == 0 || largeJugAfter == 5:
    if smallJug + largeJug <= 5:
        // indicates we’re emptying the small jug
        largeJug = largeJug + smallJug
        smallJug = 0
    else:
        // indicates we’re filling up the large jug
        smallJug = smallJug - (5 - largeJug)
        largeJug = 5
    return smallJug, largeJug

