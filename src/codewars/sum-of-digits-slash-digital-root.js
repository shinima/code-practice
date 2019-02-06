const add = (a,b) => a + b

function digitalSum(n) {
  return Array.from(String(n)).map(Number).reduce(add, 0)
}

function digital_root(n) {
  while (n >= 10) {
    n = digitalSum(n)
  }
  return n
}
