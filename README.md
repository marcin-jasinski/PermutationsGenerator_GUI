# PermutationsGenerator_GUI

A simple permutations generator using a recursive algorithm. 
Due to UI Thread protection in JavaFX, GUI freezes while the computations are performed. 
For N < 6 it's not really noticeable, but for larger values it's a problem. 
This should be solved by using Platform.runLater(), but the algorithm is recursive, so a simpler, non-recursive algorithm should be implemented.