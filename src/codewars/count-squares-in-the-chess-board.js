function matrix(M, N) {
  const result = []
  for (let i = 0; i < M; i++) {
    result.push(new Array(N).fill(0))
  }
  return result
}

/** @param chessBoard {number[][]} */
function count(chessBoard) {
  const M = chessBoard.length
  const N = chessBoard[0].length

  const hacc = matrix(M, N)
  const vacc = matrix(M, N)
  const dp = matrix(M, N)

  for (let row = 0; row < M; row++) {
    hacc[row][0] = chessBoard[row][0]
    for (let col = 1; col < N; col++) {
      hacc[row][col] = chessBoard[row][col] === 1 ? hacc[row][col - 1] + 1 : 0
    }
  }

  for (let col = 0; col < N; col++) {
    vacc[0][col] = chessBoard[0][col]
    for (let row = 1; row < M; row++) {
      vacc[row][col] = chessBoard[row][col] === 1 ? vacc[row - 1][col] + 1 : 0
    }
  }

  for (let row = 0; row < M; row++) {
    for (let col = 0; col < N; col++) {
      const topLeftSqr = row > 0 && col > 0 ? dp[row - 1][col - 1] : 0
      if (chessBoard[row][col] === 1) {
        dp[row][col] = Math.min(topLeftSqr + 1, hacc[row][col], vacc[row][col])
      } else {
        dp[row][col] = 0
      }
    }
  }

  const maxSquareSize = Math.min(M, N)
  const sizeCountArray = new Array(maxSquareSize + 1).fill(0)
  for (let row = 0; row < M; row++) {
    for (let col = 0; col < N; col++) {
      const size = dp[row][col]
      if (size >= 2) {
        sizeCountArray[size]++
      }
    }
  }
  const result = {}
  let accumulatedSquareCount = 0
  for (let size = maxSquareSize; size >= 2; size--) {
    accumulatedSquareCount += sizeCountArray[size]
    if (accumulatedSquareCount > 0) {
      result[size] = accumulatedSquareCount
    }
  }

  return result
}
