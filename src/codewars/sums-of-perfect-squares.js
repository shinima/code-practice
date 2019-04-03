function sumOfSquares(n) {
  const map = new Map()
  map.set(0, 0)

  const sqrt = Math.floor(Math.sqrt(n))

  while (true) {
    for (let x = sqrt; x >= 1; x--) {
      const sq = x ** 2
      for (let start = 0; start <= n - sq; start++) {
        const end = start + sq
        if (!map.has(start)) {
          continue
        }
        if (map.has(end)) {
          map.set(end, Math.min(map.get(end), map.get(start) + 1))
        } else {
          map.set(end, map.get(start) + 1)
        }
      }
    }

    if (map.has(n)) {
      return map.get(n)
    }
  }
}
