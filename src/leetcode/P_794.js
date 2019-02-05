/* 玩家获胜时，其可能的落子的情况
   棋盘位置的编号如下：
   012
   345
   678
*/
const combinations = ['012', '345', '678', '036', '147', '258', '048', '246'].map(str =>
  Array.from(str).map(Number),
)

function validTicTacToe(board_str) {
  const board = Array.from(board_str.join(''))
  const totalStep = board.filter(x => x !== ' ').length
  const s1 = new Set() // 记录模拟过程中 X 的位置
  const s2 = new Set() // 记录模拟过程中 O 的位置

  return backtrackPlayer1(0, 0)

  function backtrackPlayer1(p1Step, p2Step) {
    if (p1Step + p2Step === totalStep) {
      return true
    }
    if (isP2Win()) {
      return false
    }
    // P1 寻找下一个下 X 的位置
    for (let i = 0; i < 9; i++) {
      if (board[i] === 'X' && !s1.has(i)) {
        s1.add(i)
        if (backtrackPlayer2(p1Step + 1, p2Step)) {
          return true
        }
        s1.delete(i)
      }
    }
    return false
  }

  function backtrackPlayer2(p1Step, p2Step) {
    if (p1Step + p2Step === totalStep) {
      return true
    }
    if (isP1Win()) {
      return false
    }
    // P2 寻找下一个下 O 的位置
    for (let i = 0; i < 9; i++) {
      if (board[i] === 'O' && !s2.has(i)) {
        s2.add(i)
        if (backtrackPlayer1(p1Step, p2Step + 1)) {
          return true
        }
        s2.delete(i)
      }
    }
    return false
  }

  function isP1Win() {
    return combinations.some(combination => combination.every(x => s1.has(x)))
  }

  function isP2Win() {
    return combinations.some(combination => combination.every(x => s2.has(x)))
  }
}

// console.log(validTicTacToe(["O  ","   ","   "])) // false
// console.log(validTicTacToe(["XOX", " X ", "   "])) // false
// console.log(validTicTacToe(["XXX", "   ", "OOO"])) // false
// console.log(validTicTacToe(["XOX", "O O", "XOX"])) // true
