const NO = 0
const YES = 1
const UNKNOWN = 2

function sum(arr, iteratee) {
  let result = 0
  for (const item of arr) {
    if (iteratee) {
      result += iteratee(item)
    } else {
      result += item
    }
  }
  return result
}

function solve([h_clues, v_clues], width, height) {
  const table = []
  for (let y = 0; y < height; y++) {
    table.push(new Array(width).fill(UNKNOWN))
  }

  let yesCount = 0
  let noCount = 0

  const left = []
  for (const clue of v_clues) {
    const s = sum(clue)
    let min = 0
    let max = width - 1 - (s + clue.length)
    const row = []
    for (let i = 0; i < clue.length; i++) {
      max += 1 + clue[i]
      row.push({ i, count: clue[i], min, max, done: false })
      min += 1 + clue[i]
    }
    left.push(row)
  }

  const top = []
  for (const clue of h_clues) {
    const s = sum(clue)
    let min = 0
    let max = height - 1 - (s + clue.length)
    const column = []
    for (let i = 0; i < clue.length; i++) {
      max += 1 + clue[i]
      column.push({ i, count: clue[i], min, max, done: false })
      min += 1 + clue[i]
    }
    top.push(column)
  }

  let lastYesSetPerLeft = new Set()
  let lastYesSetPerTop = new Set()
  let lastNoSetPerLeft = new Set()
  let lastNoSetPerTop = new Set()

  function markAsNoPerLeft(rowIndex, colIndex) {
    if (table[rowIndex][colIndex] === UNKNOWN) {
      lastNoSetPerLeft.add(pack(rowIndex, colIndex))
      table[rowIndex][colIndex] = NO
      noCount++
    }
  }
  function markAsYesPerLeft(rowIndex, colIndex) {
    if (table[rowIndex][colIndex] === UNKNOWN) {
      lastYesSetPerLeft.add(pack(rowIndex, colIndex))
      table[rowIndex][colIndex] = YES
      yesCount++
    }
  }
  function markAsNoPerTop(rowIndex, colIndex) {
    if (table[rowIndex][colIndex] === UNKNOWN) {
      lastNoSetPerTop.add(pack(rowIndex, colIndex))
      table[rowIndex][colIndex] = NO
      noCount++
    }
  }
  function markAsYesPerTop(rowIndex, colIndex) {
    if (table[rowIndex][colIndex] === UNKNOWN) {
      lastYesSetPerTop.add(pack(rowIndex, colIndex))
      table[rowIndex][colIndex] = YES
      yesCount++
    }
  }

  function testRowConfig(rowIndex, config) {
    const yesCountAtCenter = config.count * 2 - (config.max - config.min + 1)
    if (yesCountAtCenter > 0) {
      const startColIndex = (config.min + config.max + 1 - yesCountAtCenter) / 2
      const endColIndex = (config.min + config.max - 1 + yesCountAtCenter) / 2
      for (let colIndex = startColIndex; colIndex <= endColIndex; colIndex++) {
        markAsYesPerLeft(rowIndex, colIndex)
      }
      if (yesCountAtCenter === config.count) {
        config.done = true
        if (yesCountAtCenter === config.count) {
          config.done = true
          if (startColIndex > 0) {
            markAsNoPerLeft(rowIndex, startColIndex - 1)
          }
          if (endColIndex < height - 1) {
            markAsNoPerLeft(rowIndex, endColIndex + 1)
          }
          if (config.i === 0) {
            for (let colIndex = 0; colIndex < startColIndex; colIndex++) {
              markAsNoPerLeft(rowIndex, colIndex)
            }
          }
          if (config.i === left[rowIndex].length - 1) {
            for (let colIndex = endColIndex + 1; colIndex < width; colIndex++) {
              markAsNoPerLeft(rowIndex, colIndex)
            }
          }
        }
      }
    }
  }

  function testColConfig(colIndex, config) {
    const yesCountAtCenter = config.count * 2 - (config.max - config.min + 1)
    if (yesCountAtCenter > 0) {
      const startRowIndex = (config.min + config.max + 1 - yesCountAtCenter) / 2
      const endRowIndex = (config.min + config.max - 1 + yesCountAtCenter) / 2
      for (let rowIndex = startRowIndex; rowIndex <= endRowIndex; rowIndex++) {
        markAsYesPerTop(rowIndex, colIndex)
      }
      if (yesCountAtCenter === config.count) {
        config.done = true
        if (startRowIndex > 0) {
          markAsNoPerTop(startRowIndex - 1, colIndex)
        }
        if (endRowIndex < height - 1) {
          markAsNoPerTop(endRowIndex + 1, colIndex)
        }
        if (config.i === 0) {
          for (let rowIndex = 0; rowIndex < startRowIndex; rowIndex++) {
            markAsNoPerTop(rowIndex, colIndex)
          }
        }
        if (config.i === top[colIndex].length - 1) {
          for (let rowIndex = endRowIndex + 1; rowIndex < height; rowIndex++) {
            markAsNoPerTop(rowIndex, colIndex)
          }
        }
      }
    }
  }

  while (true) {
    let shouldContinue = false
    for (let rowIndex = 0; rowIndex < height; rowIndex++) {
      for (const config of left[rowIndex]) {
        testRowConfig(rowIndex, config)
      }
    }
    for (let colIndex = 0; colIndex < width; colIndex++) {
      for (const config of top[colIndex]) {
        testColConfig(colIndex, config)
      }
    }
    shouldContinue = shouldContinue || lastYesSetPerTop.size > 0 || lastYesSetPerLeft.size > 0

    if (lastYesSetPerTop.size > 0) {
      const leftScanSet = lastYesSetPerTop
      lastYesSetPerTop = new Set()
      for (const t of leftScanSet) {
        const { rowIndex: targetRowIndex, colIndex: targetColIndex } = unpack(t)
        const row = left[targetRowIndex]
        const maybe = row.filter(({ min, max }) => min <= targetColIndex && targetColIndex <= max)
        if (maybe.length === 1 && !maybe.done) {
          // 恰好落在一个区间上，那么可以缩小该区间的范围
          const config = maybe[0]
          // 向左，增加 min 值
          config.min = Math.max(config.min, targetColIndex - config.count + 1)
          // 向下，减少 max 值
          config.max = Math.min(config.max, targetColIndex + config.count - 1)
          testRowConfig(targetRowIndex, config)
        }
      }
    }
    shouldContinue = shouldContinue || lastYesSetPerTop.size > 0 || lastYesSetPerLeft.size > 0

    if (lastYesSetPerLeft.size > 0) {
      const topScanSet = lastYesSetPerLeft
      lastYesSetPerLeft = new Set()
      for (const t of topScanSet) {
        const { rowIndex: targetRowIndex, colIndex: targetColIndex } = unpack(t)
        const column = top[targetColIndex]
        const maybe = column.filter(
          ({ min, max }) => min <= targetRowIndex && targetRowIndex <= max,
        )
        if (maybe.length === 1 && !maybe.done) {
          // 恰好落在一个区间上，那么可以缩小该区间的范围
          const config = maybe[0]
          // 向上，增加 min 值
          config.min = Math.max(config.min, targetRowIndex - config.count + 1)
          // 向下，减少 max 值
          config.max = Math.min(config.max, targetRowIndex + config.count - 1)
          testColConfig(targetColIndex, config)
        }
      }
    }
    shouldContinue = shouldContinue || lastYesSetPerTop.size > 0 || lastYesSetPerLeft.size > 0

    // TODO LAST EDIT HERE 根据 lastNoSetPerLeft 以及 lastNoSetPerTop 来缩小范围
    // TODO 移除 shouldContinue.
    //   换成 yesCount + noCount < height * width
    console.log('yesCount:', yesCount, 'noCount:', noCount)
    if (!shouldContinue) {
      break
    }
  }

  function pack(rowIndex, colIndex) {
    return rowIndex * width + colIndex
  }
  function unpack(t) {
    return { rowIndex: Math.floor(t / width), colIndex: t % width }
  }

  return table
}

const h_clues = [[1, 1], [4], [1, 1, 1], [3], [1]]

const v_clues = [[1], [2], [3], [2, 1], [4]]
const ans = [[0, 0, 1, 0, 0], [1, 1, 0, 0, 0], [0, 1, 1, 1, 0], [1, 1, 0, 1, 0], [0, 1, 1, 1, 1]]

console.log(solve([h_clues, v_clues], 5, 5))
console.log(ans)
