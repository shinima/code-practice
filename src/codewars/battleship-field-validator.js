const SIZE = 10

function validateBattlefield(field) {
  const right = (row, col) => col < SIZE - 1 && field[row][col + 1] === 1
  const down = (row, col) => row < SIZE - 1 && field[row + 1][col] === 1
  const rightDown = (row, col) => row < SIZE - 1 && col < SIZE - 1 && field[row + 1][col + 1] === 1
  const leftDown = (row, col) => row < SIZE - 1 && col > 0 && field[row + 1][col - 1] === 1

  let battleships = 0
  let cruisers = 0
  let destroyers = 0
  let submarines = 0

  for (let row = 0; row < SIZE; row++) {
    for (let col = 0; col < SIZE; col++) {
      if (field[row][col] === 1) {
        if (!findShip(row, col)) {
          return false
        }
      }
    }
  }

  return battleships === 1 && cruisers === 2 && destroyers === 3 && submarines === 4

  function findShip(row, col) {
    const horizontal = right(row, col)
    const vertical = down(row, col)

    if (!horizontal && !vertical) {
      if (rightDown(row, col) || leftDown(row, col)) {
        return false
      }
      field[row][col] = 0
      submarines++
      return submarines <= 4
    } else {
      let len = 0
      if (horizontal) {
        while (col + len < SIZE - 1 && field[row][col + len] === 1) {
          len++
          const row2 = row
          const col2 = col + len - 1
          if (rightDown(row2, col2) || leftDown(row2, col2)) {
            return false
          }
          field[row2][col2] = 0
        }
      } else {
        // isVertical === true
        while (row + len < SIZE - 1 && field[row + len][col] === 1) {
          len++
          const row2 = row + len - 1
          const col2 = col
          if (rightDown(row2, col2) || leftDown(row2, col2)) {
            return false
          }
          field[row2][col2] = 0
        }
      }

      if (len === 2) {
        destroyers++
        return destroyers <= 3
      } else if (len === 3) {
        cruisers++
        return cruisers <= 2
      } else if (len === 4) {
        battleships++
        return battleships <= 1
      } else {
        return false
      }
    }
  }
}
