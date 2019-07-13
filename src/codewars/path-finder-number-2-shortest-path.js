function pathFinder(maze) {
  maze = maze.split('\n').filter(Boolean)
  const N = maze.length
  const directions = [
    t => (t >= N ? t - N : -1),
    t => (t < (N - 1) * N ? t + N : -1),
    t => (t % N > 0 ? t - 1 : -1),
    t => (t % N < N - 1 ? t + 1 : -1),
  ]
  const get = t => {
    const row = Math.floor(t / N)
    const col = Math.floor(t % N)
    return maze[row][col]
  }

  let frontier = new Set()
  const conquer = new Set()
  frontier.add(0)
  let step = 0
  let target = N * N - 1
  while (frontier.size > 0) {
    const nextFrontier = new Set()
    for (const cell of frontier) {
      conquer.add(cell)
      for (const dir of directions) {
        const next = dir(cell)
        if (next !== -1 && !frontier.has(next) && !conquer.has(next) && get(next) !== 'W') {
          nextFrontier.add(next)
        }
      }
    }
    frontier = nextFrontier
    if (conquer.has(target)) {
      return step
    }
    step++
  }

  return false
}
