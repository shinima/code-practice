function diamond(n) {
  if (n < 0 || n % 2 === 0) {
    return null
  }
  const lines = []
  for (let lineNumber = 1; lineNumber <= n; lineNumber++) {
    const asteriskCount = Math.min(2 * lineNumber - 1, 2 * (n - lineNumber) + 1)
    const blankCount = (n - asteriskCount) / 2
    lines.push(' '.repeat(blankCount) + '*'.repeat(asteriskCount) + '\n')
  }

  return lines.join('')
}
