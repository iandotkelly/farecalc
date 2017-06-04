
# Tech Challenge Solution

## Ian Kelly

This SBT project is a solution to the tech challenge given to me on Friday June 2.

## Solution Approach

I chose to do this with a recursive algorithm that takes each coin value in
turn and recursively evaluates the remaining subsets in-turn for each case, i.e.
where the coin is included or excluded from the solution.

The algorithm keeps a running total of the sum of selected coins so far,
and the remaining total that the subsets need to sum to.  If left as is, this
would take O(2^N) time to complete. In order to significantly reduce the
search space, branches of the solution tree are pruned.

I can perform this pruning by ordering the coins by increasing value in the array
(and also discarding coins that are already over the target fare value).
This ordering of the coins allows me to:

- Stop recursing down a branch where the next coin exceeds the target fare

- Stop recursing down a branch where the sum of the coins remaining will not
meet the target fare.  I pre-calculate the 'remaining' value so I don't have
to calculate this every time.

The pros of this solution are:

- It's relatively easy to understand
- It will be better than O(2^N) time
- Storage requirements are small, a one-dimensional array to store the coin values, selected state and pre-calculated remaining values
- Stack usage is proportional to the number of coins, for most applications for fare calculation this shouldn't be a problem

The cons of this solution are:

- Time to a solution is unpredictable compared to other techniques
- If the algorithm was applied to data with very many more items in the array, the size of the stack might become a concern.

### Alternatives

There is an alternative solution using a dynamic programming technique, but
this requires a two dimensional array (of size "available coins" times
"the total fare").  I chose not to do this because I was already developing the recursive approach, and this solution has storage considerations.

If time or storage considerations were critical for this application since its
a relatively easy implementation you could implement both and benchmark their
performance.

### Implementation

I have not coded in Scala before so, as advised in the exercise notes, I
developed this as an SBT project and used Scalatest for unit testing.  

The core of the application is the FareCalc class.  The constructor of which
creates an ordered array of Coin objects, discarding coins that cannot form
part of the solution.  The subsetSum recrusive function performs the search for
a solution and returns true if a solution has been found.

There are a couple of utility functions for conversion between currency and
integers and the Main method has some basic command line processing.

## Running

Unzip the solution zip file.  From within the created farecalc folder, run
the SBT console. SBT version 0.13.13 was used to create the application.

```sh
$ sbt
```

You can run the solution from the SBT console, using a command of the form:

```sh
> run "(coins)" (fare)`
```

You can see an example of this running below.

```sh
> run "6G 13G 21G 23G 24G" 57G
[info] Running exercise.calbiaCli 6G 13G 21 23 24 57
Looking for a combinations of coins 6G, 13G, 21G, 23G, 24G to pay fare of 57G
Usable combination is 13G, 21G, 23G
[success] Total time: 0 s, completed Jun 4, 2017 1:35:41 PM
```

## Tests

Tests can be run from the SBT console:

```
> test
[info] UtilsSpec:
[info] The utils object
[info]   when coinValues function
[info]     when passed an empty string
[info]     - should return an empty Seq
[info]     when passed a string of just numbers
[info]     - should return a List of integers
[info]     when passed a string of numbers with invalid currency suffix
[info]     - should return an empty List
[info]     when passed valid Glaavo string values
[info]     - should return a List of integers
[info]   when glaavoString function
[info]     when passed an empty List
[info]     - should return an empty string
[info]     when passed a single value in a List
[info]     - should return a single Glaavo value string
[info]     when passed multiple values in a List
[info]     - should return a single Glaavo value string
[info]     when passed a single integer
[info]     - should return a single Glaavo value string
[info] FarecalcSpec:
[info] The FareCalc constructor
[info]   when passed negative total
[info]   - should throw IllegalArgumentException
[info]   when passed an empty coin sequence
[info]   - should throw IllegalArgumentException
[info]   when passed any negative coin values
[info]   - should throw IllegalArgumentException
[info] The FareCalc.findCombination method
[info]   when given no coin smaller than the target
[info]   - should not find a solution and return null
[info]   when Given 1G, 1G, 3G, 9G, 9G and a target of 11G
[info]   - should find solution 1G, 1G, 9G
[info]   when Given 9G, 9G, 3G, 1G, 1G and a target of 11G
[info]   - should find same solution 1G, 1G, 9G
[info]   when Given 1G, 1G, 3G, 9G, 9G and a target of 15G
[info]   - should not find a solution
[info]   when Given 1G 4G 6G and a target of 7G
[info]   - should find same solution 1G, 6G
[info]   when Given 1G 4G 6G and a target of 8G
[info]   - should not find a solution
[info]   when Given 6G 13G 13G 21G 23G 24G and a target of 57G
[info]   - should find a solution of 13G, 21G, 23G
[info] Run completed in 173 milliseconds.
[info] Total number of tests run: 18
[info] Suites: completed 2, aborted 0
[info] Tests: succeeded 18, failed 0, canceled 0, ignored 0, pending 0
[info] All tests passed.
[success] Total time: 6 s, completed Jun 4, 2017 2:11:09 PM
```
