type Money is {
    int dollars,    // number of dollar coins
    int fiftyCents  // number of fifty cent pieces
}

function giveChange(int fiveDollarNotes, Money cost) -> (Money r)
// Money provided for payment must be greater than cost of item
requires fiveDollarNotes * 500 >= cost.dollars * 100 + cost.fiftyCents * 50
// Amount of change returned must give total after cost
ensures (r.dollars + cost.dollars) * 100 +
        (r.fiftyCents + cost.fiftyCents) * 50 == fiveDollarNotes * 500:
    //
    int dollars = (fiveDollarNotes * 5) - cost.dollars
    int rem = 0
    int q = 0
    q, rem = divide_unsigned(cost.fiftyCents, 2)
    dollars = dollars - q
    int cents = 0
    if rem != 0:
        dollars = dollars - 1
        cents = 1
    
    Money m = {
        dollars: dollars,
        fiftyCents: cents
    }
    return m
    
function divide_unsigned(int n, int d) -> (int q, int r):
    q = 0
    r = n
    while r >= d:
        q = q + 1
        r = r - 1
    return q, r
 .
