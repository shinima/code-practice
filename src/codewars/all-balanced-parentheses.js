function balancedParens(n) {
  const result = []
  const prefix = []

  dfs(0, 0)

  return result

  function dfs(openCount, closeCount) {
    if (closeCount === n) {
      result.push(prefix.join(''))
    }
    if (openCount < n) {
      prefix.push('(')
      dfs(openCount + 1, closeCount)
      prefix.pop()
    }
    if (closeCount < openCount) {
      prefix.push(')')
      dfs(openCount, closeCount + 1)
      prefix.pop()
    }
  }
}
