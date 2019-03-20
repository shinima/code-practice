function countNull(arr) {
  let count = 0
  for (const item of arr) {
    if (item == null) {
      count++
    }
  }
  return count
}

function sum(arr) {
  let result = 0
  for (const item of arr) {
    if (item != null) {
      result += item
    }
  }
  return result
}

function range(start, end) {
  const result = []
  for (let i = start; i < end; i++) {
    result.push(i)
  }
  return result
}

const conflict = { type: 'conflict' }

function completeMatrix(matrix) {
  let totalNullCount = sum(matrix.map(countNull))

  let lineSum = null // 一行 或 一列的和，null 表示未知

  run: while (true) {
    for (let row = 0; row < 3; row++) {
      for (let col = 0; col < 3; col++) {
        const result = resolve(row, col)
        if (result.type === 'conflict') {
          return null
        } else if (result.type === 'resolve') {
          matrix[result.row][result.col] = result.x
          totalNullCount--
          continue run
        } else if (result.type === 'line-sum') {
          lineSum = result.lineSum
          continue run
        } else {
          if (result.type !== 'empty') {
            throw new Error('')
          }
        }
      }
    }
    // result.type === 'empty' for every position
    return totalNullCount === 0 ? matrix : null
  }

  function resolve(row, col) {
    const known = matrix[row][col] != null
    const hline = matrix[row]
    const vline = matrix.map(rowNums => rowNums[col])
    const hNullCount = countNull(hline) - (known ? 0 : 1)
    const vNullCount = countNull(vline) - (known ? 0 : 1)

    if (hNullCount === 0 && vNullCount === 1) {
      const lackCol = col
      const lackRow = range(0, 3).find(r => r !== row && vline[r] == null)
      const x = sum(hline) - sum(vline)
      if (x < 0) {
        return conflict
      } else {
        return { type: 'resolve', row: lackRow, col: lackCol, x }
      }
    } else if (hNullCount === 1 && vNullCount === 0) {
      const lackRow = row
      const lackCol = range(0, 3).find(c => c !== col && hline[c] == null)
      const x = sum(vline) - sum(hline)
      if (x < 0) {
        return conflict
      } else {
        return { type: 'resolve', row: lackRow, col: lackCol, x }
      }
    } else if (hNullCount === 0 && vNullCount === 0) {
      if (sum(hline) !== sum(vline)) {
        return conflict
      }
      if (lineSum == null && known) {
        return { type: 'line-sum', lineSum: sum(hline) }
      }
      if (lineSum != null && known) {
        if (lineSum !== sum(hline)) {
          return conflict
        }
      }
    }

    return { type: 'empty' }
  }
}

// prettier-ignore
const matrix1 = [
  [10, null, 3],
  [4, null, 16],
  [null, 12, null]
]
console.log(completeMatrix(matrix1))
// answer=[
// [10, 8, 3],
// [ 4, 1,16],
// [ 7,12, 2]
// ]

// prettier-ignore
const matrix2 = [
  [10,   null, 8   ],
  [null, 11,   6   ],
  [null, 7 ,   null]
]
console.log(completeMatrix(matrix2)) // null

// prettier-ignore
const matrix3 = [
  [null, 17, 4],
  [null, 3, null],
  [null, 1, 18]
]
console.log(completeMatrix(matrix3)) // null
