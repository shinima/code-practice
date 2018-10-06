/**
 * @param {number[][]} grid
 * @return {number}
 */
function maxIncreaseKeepingSkyline(grid) {
  const M = grid.length
  const N = grid[0].length
  const horizontal = grid.map(row => Math.max(...row))
  const vertical = transpose(grid).map(col => Math.max(...col))
  let result = 0
  for (let row = 0; row < M; row++) {
    for (let col = 0; col < N; col++) {
      const allowed = Math.min(horizontal[row], vertical[col])
      result += Math.max(0, allowed - grid[row][col])
    }
  }

  return result
}

function transpose(matrix) {
  const result = []
  for (let colIndex = 0; colIndex < matrix[0].length; colIndex++) {
    result.push(matrix.map(row => row[colIndex]))
  }
  return result
}

// console.log(maxIncreaseKeepingSkyline([[3, 0, 8, 4], [2, 4, 5, 7], [9, 2, 6, 3], [0, 3, 1, 0]]))
