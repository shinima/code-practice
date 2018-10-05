const UNKNOWN = 0
const SAFE = 1
const UNSAFE = 2

/**
 * @param {number[][]} graph
 * @return {number[]}
 */
function eventualSafeNodes(graph) {
  const status = new Array(graph.length).fill(UNKNOWN)

  for (let i = 0; i < status.length; i++) {
    if (status[i] !== UNKNOWN) {
      continue
    }

    const prefix = new Set()
    const visited = new Set()
    if (dfs(i, prefix, visited)) {
      for (const j of prefix) {
        status[j] = UNSAFE
      }
    } else {
      for (const j of visited) {
        status[j] = SAFE
      }
    }
  }

  const result = []
  for (let i = 0; i < status.length; i++) {
    if (status[i] === SAFE) {
      result.push(i)
    }
  }
  return result

  function dfs(i, prefix, visited) {
    if (prefix.has(i) || status[i] === UNSAFE) {
      return true
    }
    if (status[i] === SAFE) {
      return false
    }
    visited.add(i)
    prefix.add(i)
    for (const j of graph[i]) {
      if (dfs(j, prefix, visited)) {
        return true
      }
    }
    prefix.delete(i)
    return false
  }
}

