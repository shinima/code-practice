function formatDuration(seconds) {
  if (seconds === 0) {
    return 'now'
  }
  const minutes = Math.floor(seconds / 60)
  const hours = Math.floor(minutes / 60)
  const days = Math.floor(hours / 24)
  const years = Math.floor(days / 365)

  const array = []
  const s = seconds % 60
  if (s > 0) {
    array.unshift({ unit: 'second', amount: s })
  }
  const m = minutes % 60
  if (m > 0) {
    array.unshift({ unit: 'minute', amount: m })
  }
  const h = hours % 24
  if (h > 0) {
    array.unshift({ unit: 'hour', amount: h })
  }
  const D = days % 365
  if (D > 0) {
    array.unshift({ unit: 'day', amount: D })
  }
  if (years > 0) {
    array.unshift({ unit: 'year', amount: years })
  }

  // console.log(array)
  const buffer = []
  for (let i = 0; i < array.length; i++) {
    const { unit, amount } = array[i]
    if (i > 0) {
      buffer.push(i < array.length - 1 ? ', ' : ' and ')
    }
    buffer.push(amount, ' ', unit, amount > 1 ? 's' : '')
  }
  return buffer.join('')
}
