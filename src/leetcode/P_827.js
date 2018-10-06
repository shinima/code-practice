const add = (a, b) => a + b
/**
 * @param {number[][]} grid
 * @return {number}
 */
function largestIsland(grid) {
  const M = grid.length
  const N = grid[0].length
  const visited = []
  for (let row = 0; row < M; row++) {
    visited.push(new Array(N).fill(false))
  }
  const infos = []
  for (let row = 0; row < M; row++) {
    for (let col = 0; col < N; col++) {
      if (grid[row][col] === 1 && !visited[row][col]) {
        infos.push(getInfo(row, col))
      }
    }
  }
  const islandSizes = infos.map(({ island }) => island.size)
  const connections = new Map()

  for (let index = 0; index < infos.length; index++) {
    const contour = infos[index].contour
    for (const t of contour) {
      if (!connections.has(t)) {
        connections.set(t, [])
      }
      connections.get(t).push(index)
    }
  }
  if (connections.size === 0) {
    // 全为 0 或 全为 1
    return islandSizes.length > 0 ? N * M : 1
  }

  let maxSize = 0
  for (const arr of connections.values()) {
    const size = 1 + arr.map(index => islandSizes[index]).reduce(add)
    maxSize = Math.max(maxSize, size)
  }
  return maxSize

  function getInfo(startRow, startCol) {
    const island = new Set()
    const contour = new Set()

    dfs(startRow, startCol)

    return { island, contour }

    function dfs(row, col) {
      if (row < 0 || row >= M || col < 0 || col >= N) {
        return
      }
      const t = row * N + col
      if (grid[row][col] === 0) {
        contour.add(t)
        return
      }
      if (!visited[row][col]) {
        visited[row][col] = true
        island.add(t)
        dfs(row, col - 1)
        dfs(row, col + 1)
        dfs(row - 1, col)
        dfs(row + 1, col)
      }
    }
  }
}

// console.log(largestIsland([[1, 1], [1, 0]]))
// console.log(largestIsland([[1, 0], [0, 1]]))
