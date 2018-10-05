/**
 * @param {number[]} A
 * @param {number[]} B
 * @return {number}
 */
function minSwap(A, B) {
  let x = 1
  let y = 0
  for (let i = 1; i < A.length; i++) {
    let xx = Infinity
    let yy = Infinity

    // 上次交换，这次交换 || 上次不交换，这次不交换
    if (A[i] > A[i - 1] && B[i] > B[i - 1]) {
      xx = Math.min(xx, x + 1)
      yy = Math.min(yy, y)
    }

    // 上次交换，这次不交换 || 上次不交换，这次交换
    if (A[i] > B[i - 1] && B[i] > A[i - 1]) {
      yy = Math.min(yy, x)
      xx = Math.min(xx, y + 1)
    }

    x = xx
    y = yy
  }

  return Math.min(x, y)
}
