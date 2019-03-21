const LETTERS = 'ABCDEFGHIJKLMNOPQRSTUVWXYZ'
const map = new Map(Array.from(LETTERS).map((letter, index) => [letter, index]))

// the combination number for picking b items from a
function C(a, b) {
  let result = 1
  for (let i = 0; i < b; i++) {
    result *= a - i
  }
  for (let i = 0; i < b; i++) {
    result /= b - i
  }
  return result
}

function listPosition(word) {
  let result = 0

  // 将字符串转换为数字数组
  const ts = Array.from(word).map(char => map.get(char))
  // 记录每个字符对应的数字 出现次数
  const counts = new Array(26).fill(0)
  for (const t of ts) {
    counts[t]++
  }

  for (let fixLen = 0; fixLen < word.length; fixLen++) {
    const slice = counts.slice()
    for (let i = 0; i < fixLen; i++) {
      slice[ts[i]]--
    }
    const cntT = ts[fixLen]
    // 在 slice 中寻找比 cntT 更小的 t
    for (let i = 0; i < cntT; i++) {
      if (slice[i] > 0) {
        slice[i]--
        result += anagramCount(slice)
        slice[i]++
      }
    }
  }
  return result + 1

  function anagramCount(counts) {
    let totalCount = 0
    for (const count of counts) {
      totalCount += count
    }
    let result = 1
    for (const count of counts) {
      result *= C(totalCount, count)
      totalCount -= count
    }
    return result
  }
}

// console.log(listPosition('A'))
// console.log(listPosition('BAAA'))
// console.log(listPosition('QUESTION'))
// console.log(listPosition('BOOKKEEPER'))
