function countBits(n) {
  let bitCount = 0
  while (n > 0) {
    bitCount += n % 2
    n >>= 2
  }
  return bitCount
}
