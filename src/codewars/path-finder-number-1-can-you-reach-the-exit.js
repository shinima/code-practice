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
  while (frontier.size > 0) {
    // printMaze()
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
  }
  // printMaze()
  return conquer.has(N * N - 1)

  function printMaze() {
    for (let row = 0; row < N; row++) {
      const line = []
      for (let col = 0; col < N; col++) {
        let t = row * N + col
        if (get(t) === 'W') {
          line.push('W')
        } else if (conquer.has(t)) {
          line.push('x')
        } else {
          line.push('.')
        }
      }
      console.log(line.join(''))
    }
    console.log()
  }
}

function localTest() {
  const maze1 = ['......', '......', '......', '......', '.....W', '....W.']
  console.log(pathFinder(maze1))

  const maze2 = ['......', '......', '......', '......', '.....W', '....W.']
  console.log(pathFinder(maze2))
}
