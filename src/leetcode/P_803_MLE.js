function hitBricks(grid, hits) {
  const M = grid.length
  const N = grid[0].length
  const left = t => (t % N > 0 ? t - 1 : -1)
  const right = t => (t % N < N - 1 ? t + 1 : -1)
  const up = t => (Math.floor(t / N) > 0 ? t - N : -1)
  const down = t => (Math.floor(t / N) < M - 1 ? t + N : -1)
  const directions = [left, right, up, down]

  const result = []

  for (const [hitRow, hitCol] of hits) {
    if (grid[hitRow][hitCol] === 0) {
      result.push(0)
    } else {
      grid[hitRow][hitCol] = 0
      result.push(calculateDropCount(hitRow * N + hitCol))
    }
  }

  return result

  function calculateDropCount(hitT) {
    let totalCount = 0
    for (const dir of directions) {
      const start = dir(hitT)
      if (get(start) === 1) {
        const dropSet = findDropSet(start)
        if (dropSet) {
          totalCount += dropSet.size
          for (const t of dropSet) {
            const row = Math.floor(t / N)
            const col = t % N
            grid[row][col] = 0
          }
        }
      }
    }
    return totalCount
  }

  function findDropSet(start) {
    const set = new Set()
    let frontier = new Set()
    frontier.add(start)
    while (frontier.size > 0) {
      const nextFrontier = new Set()
      for (const t of frontier) {
        if (Math.floor(t / N) === 0) {
          return null
        }
        set.add(t)
        for (const dir of directions) {
          const next = dir(t)
          if (get(next) == 1 && !set.has(next) && !frontier.has(next)) {
            nextFrontier.add(next)
          }
        }
      }
      frontier = nextFrontier
    }
    return set
  }

  function get(t) {
    if (t === -1) {
      return 0
    }
    const row = Math.floor(t / N)
    const col = t % N
    return grid[row][col]
  }
}
