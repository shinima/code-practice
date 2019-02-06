function findEvenIndex(arr) {
  if (arr.length === 0) {
    return 0
  }
  let sum = 0
  for (const val of arr) {
    sum += val
  }

  let leftSum = 0
  for (let i = 1; i < arr.length; i++) {
    leftSum += arr[i - 1]
    const rightSum = sum - leftSum - arr[i]
    if (leftSum === rightSum) {
      return i
    }
  }
  return -1
}
