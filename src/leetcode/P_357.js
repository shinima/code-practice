const facts = []

GenerateFacts: {
  let product = 1
  let n = 0
  while (n <= 10) {
    facts.push(product)
    n++
    product *= n
  }
}

function P(m, n) {
  return facts[n] / facts[n - m]
}

/**
 * @param {number} n
 * @return {number}
 */
function countNumbersWithUniqueDigits(n) {
  if (n === 0) {
    return 1
  }
  let result = 0
  for (let i = 1; i <= n; i++) {
    result += P(i, 10)
    if (i > 1) {
      // 去掉 0 开头的数字
      result -= P(i - 1, 9)
    }
  }
  return result
}
