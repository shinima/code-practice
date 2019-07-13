// TODO WA
// https://www.codewars.com/kata/path-finder-number-3-the-alpinist/train/javascript

function pathFinder(area) {
  area = area.split('\n').filter(Boolean)
  const N = area.length

  const directions = [
    t => (t >= N ? t - N : -1),
    t => (t < (N - 1) * N ? t + N : -1),
    t => (t % N > 0 ? t - 1 : -1),
    t => (t % N < N - 1 ? t + 1 : -1),
  ]
  const get = t => {
    const row = Math.floor(t / N)
    const col = Math.floor(t % N)
    return area[row][col]
  }

  const heap = [{ from: 0, t: 0, distance: 0 }]

  // { [t]: { from, distance } }
  const table = new Map()

  while (heap.length > 0) {
    const { t, from, distance } = popFromHeap()
    table.set(t, { from, distance })
    // console.log(`${from} -> ${t}  distance: ${distance}`)

    for (const direction of directions) {
      const next = direction(t)
      if (next === -1 || table.has(next)) {
        continue
      }
      const delta = Math.abs(get(t) - get(next))
      addToHeap({ from: t, t: next, distance: distance + delta })
    }
  }
  // console.log(table)
  return table.get(N * N - 1).distance

  function popFromHeap() {
    console.assert(heap.length > 0)
    let idx = 0
    for (let i = 1; i < heap.length; i++) {
      if (heap[i].distance < heap[idx].distance) {
        idx = i
      }
    }
    const [item] = heap.splice(idx, 1)
    return item
  }

  function addToHeap(adding) {
    for (const item of heap) {
      if (item.t === adding.t && adding.distance < item.distance) {
        item.from = adding
        item.distance = adding.distance
        return
      }
    }
    heap.push(adding)
  }
}

// console.log(`
// 1895
// 2943
// 1059
// 2046`)
//
// console.log(
//   pathFinder(`
// 1895
// 2943
// 1059
// 2046`),
// )
