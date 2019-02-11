// todo TLE
function compareRange(r1, r2) {
  if (r1.start === r2.start) {
    return r1.end - r2.end
  } else {
    return r1.start - r2.start
  }
}

function RangeManager() {
  const ranges = []

  function binarySearchInsertIndex(range) {
    if (ranges.length === 0 || compareRange(range, ranges[ranges.length - 1]) > 0) {
      return ranges.length
    }
    let low = 0
    let high = ranges.length - 1
    while (low <= high) {
      const middle = Math.floor((low + high) / 2)
      if (compareRange(ranges[middle], range) > 0) {
        high = middle
      } else {
        low = middle + 1
      }
    }
    return low
  }

  function binarySearchDeleteIndex(range) {
    let low = 0
    let high = ranges.length - 1
    while (low <= high) {
      const middle = Math.floor((low + high) / 2)
      const cmp = compareRange(ranges[middle], range)
      if (cmp < 0) {
        low = middle + 1
      } else if (cmp > 0) {
        high = middle - 1
      } else {
        return middle
      }
    }
    throw new Error(`Cannot find range(${range.start}, ${range.end})`)
  }

  return {
    ranges,
    addRange(range) {
      ranges.splice(binarySearchInsertIndex(range), 0, range)
    },
    deleteRange(range) {
      ranges.splice(binarySearchDeleteIndex(range), 1)
    },
    getLength() {
      if (ranges.length === 0) {
        return 0
      }
      let lastEnd = -Infinity
      let sum = 0
      for (const { start, end } of ranges) {
        const nonIntersectionStart = Math.max(lastEnd, start)
        const nonIntersectionEnd = Math.max(lastEnd, end)
        sum += nonIntersectionEnd - nonIntersectionStart
        lastEnd = Math.max(end, lastEnd)
      }
      return sum
    },
  }
}

/**
 *
 * @param {number[][]} recs
 * @returns {number}
 */
function calculate(recs) {
  const intervals = []
  for (const [x0, y0, x1, y1] of recs) {
    intervals.push(
      { type: 'enter', x: x0, start: y0, end: y1 },
      { type: 'exit', x: x1, start: y0, end: y1 },
    )
  }
  intervals.sort((in1, in2) => in1.x - in2.x)
  const manager = RangeManager()
  let area = 0
  let lastX = 0
  for (const interval of intervals) {
    area += manager.getLength() * (interval.x - lastX)
    lastX = interval.x
    if (interval.type === 'enter') {
      manager.addRange(interval)
    } else {
      manager.deleteRange(interval)
    }
  }
  return area
}
