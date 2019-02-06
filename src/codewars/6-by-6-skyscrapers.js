function solvePuzzle(clues) {
  const matrix = []
  const rowUsed = []
  const colUsed = []
  for (let i = 0; i < 6; i++) {
    matrix.push(new Array(6).fill(0)) // 0 表示未知
    rowUsed.push(new Array(7).fill(false))
    colUsed.push(new Array(7).fill(false))
  }

  for (let t = 0; t < 6; t++) {
    if (clues[t] === 1) {
      place(0, t, 6)
    }
    if (clues[t] === 6) {
      for (let row = 0; row < 6; row++) {
        place(row, t, row + 1)
      }
    }

    if (clues[6 + t] === 1) {
      place(t, 5, 6)
    }
    if (clues[6 + t] === 6) {
      for (let col = 5; col >= 0; col--) {
        place(t, col, 6 - col)
      }
    }

    if (clues[12 + t] === 1) {
      place(5, 5 - t, 6)
    }
    if (clues[12 + t] === 6) {
      for (let row = 5; row >= 0; row--) {
        place(row, 5 - t, 6 - row)
      }
    }

    if (clues[18 + t] === 1) {
      place(5 - t, 0, 6)
    }
    if (clues[18 + t] === 6) {
      for (let col = 0; col < 6; col++) {
        place(5 - t, col, 1 + col)
      }
    }
  }

  function place(row, col, height) {
    matrix[row][col] = height
    rowUsed[row][height] = true
    colUsed[col][height] = true
  }
  function unplace(row, col, height) {
    matrix[row][col] = 0
    rowUsed[row][height] = false
    colUsed[col][height] = false
  }

  dfs(0)

  return matrix

  function checkClues(row, col) {
    const toBottom = matrix.map(line => line[col])
    const toTop = toBottom.slice().reverse()
    const toRight = matrix[row]
    const toLeft = toRight.slice().reverse()
    return (
      check(toBottom, clues[col], row) &&
      check(toTop, clues[17 - col]) &&
      check(toRight, clues[23 - row]) &&
      check(toLeft, clues[6 + row])
    )
  }

  function calculatePotentialCount(used, maxHeight) {
    let result = 0
    for (let h = 1; h <= 6; h++) {
      if (!used[h] && h > maxHeight) {
        result++
      }
    }
    return result
  }

  function check(heights, clue) {
    if (clue === 0) {
      return true
    }
    const used = new Array(7).fill(false)
    let count = 0
    let maxHeight = 0
    for (const height of heights) {
      if (height === 0) {
        return count + calculatePotentialCount(used, maxHeight) >= clue
      }
      used[height] = true
      if (height > maxHeight) {
        count++
        maxHeight = height
      }
      if (count > clue) {
        return false
      }
    }
    return count === clue
  }

  function dfs(t) {
    if (t === 36) {
      return true
    }
    const row = Math.floor(t / 6)
    const col = t % 6
    if (matrix[row][col] !== 0) {
      return dfs(t + 1)
    }
    for (let candidate = 1; candidate <= 6; candidate++) {
      if (!rowUsed[row][candidate] && !colUsed[col][candidate]) {
        place(row, col, candidate)
        if (checkClues(row, col) && dfs(t + 1)) {
          return true
        }
        unplace(row, col, candidate)
      }
    }
    return false
  }
}
