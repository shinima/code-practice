// # obstacle
// <blank> slippery floor
// x non-slippery floor
// S start (slippery)
// E end (non-slippery)

function IceMazeSolver(map) {
  map = map.split('\n')
  const ROWS = map.length
  const COLS = map[0].length
  /** @type Array<{ moveCount: number, distance: number, from: number, direction: string }> */
  const table = new Array(ROWS * COLS).fill(null)
  let S
  let E
  for (let row = 0; row < ROWS; row++) {
    for (let col = 0; col < COLS; col++) {
      if (map[row][col] === 'S') {
        S = row * COLS + col
      } else if (map[row][col] === 'E') {
        E = row * COLS + col
      }
    }
  }
  table[S] = { moveCount: 0, distance: 0, from: -1, direction: null }
  const frontier = new Set([S])

  while (frontier.size > 0) {
    // 在 frontier 中找到 moveCount/distance 最小的 start
    let start = -1
    for (const pos of frontier) {
      if (start === -1 || compare(table[pos], table[start]) < 0) {
        start = pos
      }
    }
    frontier.delete(start)

    if (table[E] && compare(table[start], table[E]) >= 0) {
      break
    }

    const startInfo = table[start]
    for (const slide of [slideDown, slideUp, slideLeft, slideRight]) {
      const sliding = slide(start)
      const stop = sliding.stop
      // 如果没有运动，或是回到了 from，则跳过
      if (sliding.distance === 0 || stop === startInfo.from) {
        continue
      }
      const candidateStopInfo = {
        moveCount: startInfo.moveCount + 1,
        distance: startInfo.distance + sliding.distance,
        from: start,
        direction: sliding.direction,
      }

      if (table[stop] == null || compare(candidateStopInfo, table[stop]) < 0) {
        table[stop] = candidateStopInfo
        frontier.add(stop)
      }
    }
  }

  // 无法到达 E
  if (table[E] == null) {
    return null
  }

  const directions = []
  let pos = E
  while (pos !== S) {
    directions.unshift(table[pos].direction)
    pos = table[pos].from
  }
  return directions

  function compare(info1, info2) {
    const countDelta = info1.moveCount - info2.moveCount
    if (countDelta === 0) {
      return info1.distance - info2.distance
    } else {
      return countDelta
    }
  }

  function slideDown(start) {
    const startRow = Math.floor(start / COLS)
    const startCol = start % COLS
    let row = startRow
    let shouldStop = false
    while (row < ROWS - 1 && !shouldStop) {
      const mapItem = map[row + 1][startCol]
      if (mapItem === '#') {
        break
      } else if (mapItem === ' ' || mapItem === 'S') {
        row++
      } else {
        // x or E
        row++
        shouldStop = true
      }
    }

    return {
      direction: 'd',
      distance: row - startRow,
      stop: row * COLS + startCol,
    }
  }

  function slideUp(start) {
    const startRow = Math.floor(start / COLS)
    const startCol = start % COLS
    let row = startRow
    let shouldStop = false
    while (row > 0 && !shouldStop) {
      const mapItem = map[row - 1][startCol]
      if (mapItem === '#') {
        break
      } else if (mapItem === ' ' || mapItem === 'S') {
        row--
      } else {
        // x or E
        row--
        shouldStop = true
      }
    }

    return {
      direction: 'u',
      distance: startRow - row,
      stop: row * COLS + startCol,
    }
  }

  function slideLeft(start) {
    const startRow = Math.floor(start / COLS)
    const startCol = start % COLS
    let col = startCol
    let shouldStop = false
    while (col > 0 && !shouldStop) {
      const mapItem = map[startRow][col - 1]
      if (mapItem === '#') {
        break
      } else if (mapItem === ' ' || mapItem === 'S') {
        col--
      } else {
        // x or E
        col--
        shouldStop = true
      }
    }

    return {
      direction: 'l',
      distance: startCol - col,
      stop: startRow * COLS + col,
    }
  }

  function slideRight(start) {
    const startRow = Math.floor(start / COLS)
    const startCol = start % COLS
    let col = startCol
    let shouldStop = false
    while (col < COLS - 1 && !shouldStop) {
      const mapItem = map[startRow][col + 1]
      if (mapItem === '#') {
        break
      } else if (mapItem === ' ' || mapItem === 'S') {
        col++
      } else {
        // x or E
        col++
        shouldStop = true
      }
    }

    return {
      direction: 'r',
      distance: col - startCol,
      stop: startRow * COLS + col,
    }
  }
}
