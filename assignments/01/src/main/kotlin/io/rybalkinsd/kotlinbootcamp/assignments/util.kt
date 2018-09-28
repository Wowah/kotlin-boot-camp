package io.rybalkinsd.kotlinbootcamp.assignments

/**
 * Returns the greatest of `int` values.
 *
 * @param values an argument. !! Assume values.length > 0. !!
 * @return the largest of values.
 */
fun max(values: List<Int>): Int{
    var max = values.first()
    if (values.isNotEmpty()) {
        values.forEach{
            if (it > max) {
                max = it
            }
        }
        return max;
    }
    return 0
}

/**
 * Returns the sum of all `int` values.
 *
 * @param values an argument. Assume values.length > 0.
 * @return the sum of all values.
 */
fun sum(values: List<Int>): Long {
    var sum = 0L
    if (values.isNotEmpty()) {
        values.forEach { sum += it }
        return sum
    }
    return 0L
}