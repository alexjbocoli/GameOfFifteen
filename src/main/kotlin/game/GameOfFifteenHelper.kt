package game

/*
 * This function should return the parity of the permutation.
 * true - the permutation is even
 * false - the permutation is odd
 * https://en.wikipedia.org/wiki/Parity_of_a_permutation

 * If the game of fifteen is started with the wrong parity, you can't get the correct result
 *   (numbers sorted in the right order, empty cell at last).
 * Thus the initial permutation should be correct.
 */
fun isEven(permutation: List<Int>): Boolean {
    val inversionList = ArrayList<Pair<Int, Int>>()
    for (i in 0..permutation.size) {
        val startIndexToCompare = i + 1
        for (j in startIndexToCompare until permutation.size) {
            if (permutation[i] > permutation[j]) inversionList.add(permutation[i] to permutation[j])
        }
    }
    if (inversionList.size % 2 == 0) return true
    return false
}