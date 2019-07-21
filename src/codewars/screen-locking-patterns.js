// 0 1 2
// 3 4 5
// 6 7 8
const cuts = ['012', '345', '678', '048', '246', '036', '147', '258']
const cutMap = new Map()
for (let i = 0; i < 9; i++) {
  cutMap.set(i, new Map())
}
for (const [s_start, s_middle, s_end] of cuts) {
  const start = Number(s_start)
  const middle = Number(s_middle)
  const end = Number(s_end)
  cutMap.get(start).set(end, middle)
  cutMap.get(end).set(start, middle)
}

function countPatternsFrom(firstDot, length) {
  const set = new Set()
  const startDot = firstDot.charCodeAt(0) - 'A'.charCodeAt(0)
  set.add(startDot)
  return countPattern(startDot)

  function countPattern(lastDot) {
    if (set.size === length) {
      return 1
    }
    let result = 0
    for (let next = 0; next < 9; next++) {
      if (!set.has(next)) {
        const map = cutMap.get(lastDot)
        if (map.has(next) && !set.has(map.get(next))) {
          continue
        }
        set.add(next)
        result += countPattern(next)
        set.delete(next)
      }
    }
    return result
  }
}
