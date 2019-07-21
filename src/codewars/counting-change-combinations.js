function countChange(money, coins) {
  coins.sort((x, y) => y - x)

  return dfs(0, money)

  function dfs(i, remain) {
    // console.log(i, remain)
    if (remain === 0) {
      return 1
    }
    if (remain < 0 || i === coins.length) {
      return 0
    }
    // 使用 money[i]
    const option1 = dfs(i, remain - coins[i])
    // 不使用 money[i]
    const option2 = dfs(i + 1, remain)
    return option1 + option2
  }
}

// console.log(countChange(4, [1, 2]))
// console.log(countChange(10, [5, 2, 3]))
// console.log(countChange(11, [5, 7]))
