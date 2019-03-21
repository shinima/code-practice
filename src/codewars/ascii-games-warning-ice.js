// # obstacle
// <blank> slippery floor
// x non-slippery floor
// S start (slippery)
// E end (non-slippery)

function IceMazeSolver(map) {
  map = map.split('\n')
  const ROWS = map.length
  const COLS = map[0].length
  /** @type Array<{ moveCount: number, distance: number, from: number }> */
  const table = new Array(ROWS * COLS).fill(null)
  let start
  let end
  for (let t = 0; t < ROWS * COLS; t++) {
    // TODO last edit here
  }

  console.log(ROWS, COLS)
}

console.log(IceMazeSolver('\
    x \n\
  #   \n\
   E  \n\
 #    \n\
    # \n\
S    #'))

process.exit(0)

Test.describe('Example test cases', function() {
  var map
  map = '\
    x \n\
  #   \n\
   E  \n\
 #    \n\
    # \n\
S    #'
  console.log(map)
  Test.assertSimilar(IceMazeSolver(map), ['u', 'r', 'd', 'l', 'u', 'r'], 'A simple spiral')
  map = '\
 #    \n\
x   E \n\
      \n\
     S\n\
      \n\
 #    '
  console.log(map)
  Test.assertSimilar(IceMazeSolver(map), ['l', 'u', 'r'], 'Slippery puzzles has one-way routes')
  map = '\
E#    \n\
      \n\
      \n\
      \n\
      \n\
 #   S'
  console.log(map)
  Test.assertSimilar(IceMazeSolver(map), null, 'The end is unreachable')
  map = '\
E#   #\n\
      \n\
#     \n\
  #   \n\
 #    \n\
 S    '
  console.log(map)
  Test.assertSimilar(
    IceMazeSolver(map),
    ['r', 'u', 'l', 'u'],
    'Tiebreak by least number of moves first',
  )
  map = '\
    E \n\
     #\n\
      \n\
# #   \n\
    # \n\
 #  S '
  console.log(map)
  Test.assertSimilar(
    IceMazeSolver(map),
    ['l', 'u', 'r', 'u', 'r'],
    'Then by total distance traversed',
  )
})
