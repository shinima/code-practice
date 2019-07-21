function solution(tiles) {
  return Array.from('123456789')
    .filter(t => {
      return count(tiles, t) <= 3 && canWin(merge(tiles, t), false)
    })
    .join('')

  function canWin(tiles, paired) {
    if (tiles.length === 0) {
      return paired
    }
    if (tiles.length < 2) {
      return false
    }
    if (tiles.length === 2) {
      return !paired && tiles[0] === tiles[1]
    }
    // tiles.length >= 3
    const [t1, t2, t3] = tiles
    if (t1 === t2) {
      // try pair
      if (!paired && canWin(tiles.substring(2), true)) {
        return true
      }
      // try triple
      if (t2 === t3 && canWin(tiles.substring(3), paired)) {
        return true
      }
    }
    if (t1 <= '7') {
      const n1 = next(t1)
      const n2 = next(n1)
      if (tiles.includes(n1) && tiles.includes(n2)) {
        const nextTiles = removeOne(tiles, new Set([t1, n1, n2]))
        if (canWin(nextTiles, paired)) {
          return true
        }
      }
    }
    return false
  }

  function removeOne(tiles, removeSet) {
    const tileArray = Array.from(tiles)
    const resist = []
    for (let i = 0; i < tileArray.length; i++) {
      if (removeSet.has(tileArray[i])) {
        resist.push(false)
        removeSet.delete(tileArray[i])
      } else {
        resist.push(true)
      }
    }

    return tileArray.filter((t, i) => resist[i]).join('')
  }

  function next(t) {
    return String(Number(t) + 1)
  }

  function merge(tiles, t) {
    for (let i = 0; i < tiles.length; i++) {
      // 字典序比较
      if (t <= tiles[i]) {
        return tiles.substring(0, i) + t + tiles.substring(i)
      }
    }
    return tiles + t
  }

  function count(tiles, t) {
    let n = 0
    for (const tile of tiles) {
      if (tile === t) {
        n++
      }
    }
    return n
  }
}

console.log(solution('1233334578999'))
