// TODO 还有一点小问题

const letters = 'ABCDEFGHJKLMNOPQRSTUVWXYZ' // A-Z except I, 25 letters
const letterToCol = new Map(Array.from(letters).map((letter, index) => [letter, index]))
const handicapPositions = {
  size9: ['7G', '3C', '3G', '7C', '5E'],
  size13: ['10K', '4D', '4K', '10D', '7G', '7D', '7K', '10G', '4G'],
  size19: ['16Q', '4D', '4Q', '16D', '10K', '10D', '10Q', '16K', '4K'],
}

function isRival(p1, p2) {
  return (p1 === 'x' && p2 === 'o') || (p1 === 'o' && p2 === 'x')
}

function getPiece(turn) {
  if (turn === 'black') {
    return 'x'
  } else {
    return 'o'
  }
}

function getRivalPiece(turn) {
  if (turn === 'white') {
    return 'o'
  } else {
    return 'x'
  }
}

function assert(condition, message) {
  if (!condition) {
    throw new Error(message)
  }
}

function getCouldPlaceHandicap(height, width) {
  return height === width && (height === 9 || height === 13 || height === 19)
}

function parsePosition(position, height) {
  assert(position.length === 2 || position.length === 3)
  if (position.length === 3) {
    const col = letterToCol.get(position[2])
    const row = height - Number(position.slice(0, 2))
    return { row, col }
  } else {
    // position.length === 2
    const col = letterToCol.get(position[1])
    const row = height - Number(position[0])
    return { row, col }
  }
}

function initBoard(height, width) {
  const board = []
  for (let row = 0; row < height; row++) {
    board.push(new Array(width).fill('.'))
  }
  return board
}

class Go {
  constructor(height, width = height) {
    assert(height >= 1 && height <= 25, 'Height should be in range [1, 25]')
    assert(width >= 1 && width <= 25, 'Width should be in range [1, 25]')
    this.height = height
    this.width = width
    this.turn = 'black' // black | white
    this.board = initBoard(height, width)

    this._couldPlaceHandicap = getCouldPlaceHandicap(height, width)
    this._lastPosition = { white: null, black: null }
    this._history = []
  }

  print() {
    const axisYWidth = String(this.width).length
    console.log('='.repeat(60))
    console.log(' '.repeat(axisYWidth + 1) + Array.from(letters.slice(0, this.width)).join(' '))
    for (let row = 0; row < this.height; row++) {
      const n = this.height - row
      console.log([String(n).padStart(axisYWidth)].concat(this.board[row]).join(' '))
    }
  }

  get size() {
    return { height: this.height, width: this.width }
  }

  rollback(steps = 1) {
    for (let i = 0; i < steps; i++) {
      this._rollbackOneStep()
    }
  }

  _rollbackOneStep() {
    const record = this._history.pop()
    assert(record != null, 'Empty history')
    if (record.type === 'move') {
      this._toggleTurn()
      const { row, col } = parsePosition(record.position, this.height)
      this.board[row][col] = '.'
      for (const capture of record.captures) {
        const { row, col } = this._getRowCol(capture)
        this.board[row][col] = getRivalPiece(record.turn)
      }
    } else if (record.type === 'pass') {
      this._toggleTurn()
    } else {
      assert(false, 'Invalid history record')
    }
    this._lastPosition = record.lastPosition
    this._couldPlaceHandicap = record.couldPlaceHandicap
  }

  handicapStones(number) {
    assert(this._couldPlaceHandicap)
    let positions
    if (this.height === 9) {
      positions = handicapPositions.size9
    } else if (this.height === 13) {
      positions = handicapPositions.size13
    } else {
      positions = handicapPositions.size19
    }
    assert(number >= 1 && number <= positions.length)

    positions.slice(0, number).forEach(position => {
      const { row, col } = parsePosition(position, this.height)
      this.board[row][col] = 'x'
    })
    this._couldPlaceHandicap = false
    this.turn = 'white'
  }

  getPosition(position) {
    const { row, col } = parsePosition(position, this.height)
    return this.board[row][col]
  }

  move(...positions) {
    for (const position of positions) {
      this._moveOne(position)
    }
  }

  _getT(row, col) {
    return row * this.width + col
  }

  _getRowCol(t) {
    const row = Math.floor(t / this.width)
    const col = t % this.width
    return { row, col }
  }

  _bfs(row, col, piece) {
    const land = new Set()
    const liberties = new Set()
    const borders = new Set()
    const boundaries = new Set()

    let front = new Set()
    front.add(this._getT(row, col))
    while (front.size > 0) {
      const nextFront = new Set()
      for (const t of front) {
        land.add(t)
        const { row, col } = this._getRowCol(t)

        if (col > 0) {
          const leftPiece = this.board[row][col - 1]
          const leftT = t - 1
          if (leftPiece === piece) {
            if (!land.has(leftT) && !front.has(leftT)) {
              nextFront.add(leftT)
            }
          } else if (leftPiece === '.') {
            liberties.add(leftT)
          } else {
            boundaries.add(leftT)
          }
        } else {
          borders.add({ side: 'left', row })
        }

        if (col < this.width - 1) {
          const rightPiece = this.board[row][col + 1]
          const rightT = t + 1
          if (rightPiece === piece) {
            if (!land.has(rightT) && !front.has(rightT)) {
              nextFront.add(rightT)
            }
          } else if (rightPiece === '.') {
            liberties.add(rightT)
          } else {
            boundaries.add(rightT)
          }
        } else {
          borders.add({ side: 'right', row })
        }

        if (row > 0) {
          const upPiece = this.board[row - 1][col]
          const upT = t - this.width
          if (upPiece === piece) {
            if (!land.has(upT) && !front.has(upT)) {
              nextFront.add(upT)
            }
          } else if (upPiece === '.') {
            liberties.add(upT)
          } else {
            boundaries.add(upT)
          }
        } else {
          borders.add({ side: 'up', col })
        }

        if (row < this.height - 1) {
          const downPiece = this.board[row + 1][col]
          const downT = t + this.width
          if (downPiece === piece) {
            if (!land.has(downPiece) && !front.has(downPiece)) {
              nextFront.add(downT)
            }
          } else if (downPiece === '.') {
            liberties.add(downT)
          } else {
            boundaries.add(downT)
          }
        } else {
          borders.add({ side: 'down', col })
        }
      }
      front = nextFront
    }

    return { land, liberties, boundaries, borders }
  }

  _isSuicide(row, col, turn) {
    const { liberties } = this._bfs(row, col, getPiece(turn))
    return liberties.size === 0
  }

  _findCaptures(row, col, lastPiece) {
    const t = this._getT(row, col)
    const captures = new Set()
    const addToCaptures = set => {
      for (const item of set) {
        captures.add(item)
      }
    }

    if (col > 0) {
      const leftPiece = this.board[row][col - 1]
      if (isRival(leftPiece, lastPiece)) {
        const { liberties, land } = this._bfs(row, col - 1, leftPiece)
        assert(liberties.size >= 1)
        if (liberties.size === 1) {
          assert(liberties.has(t), `Only liberty must be at { row=${row}, col=${col} }`)
          addToCaptures(land)
        }
      }
    }
    if (col < this.width - 1) {
      const rightPiece = this.board[row][col + 1]
      if (isRival(rightPiece, lastPiece)) {
        const { liberties, land } = this._bfs(row, col + 1, rightPiece)
        assert(liberties.size >= 1)
        if (liberties.size === 1) {
          assert(liberties.has(t), `Only liberty must be at { row=${row}, col=${col} }`)
          addToCaptures(land)
        }
      }
    }
    if (row > 0) {
      const upPiece = this.board[row - 1][col]
      if (isRival(upPiece, lastPiece)) {
        const { liberties, land } = this._bfs(row - 1, col, upPiece)
        assert(liberties.size >= 1)
        if (liberties.size === 1) {
          assert(liberties.has(t), `Only liberty must be at { row=${row}, col=${col} }`)
          addToCaptures(land)
        }
      }
    }
    if (row < this.height - 1) {
      const upPiece = this.board[row + 1][col]
      if (isRival(upPiece, lastPiece)) {
        const { liberties, land } = this._bfs(row + 1, col, upPiece)
        assert(liberties.size >= 1)
        if (liberties.size === 1) {
          assert(liberties.has(t), `Only liberty must be at { row=${row}, col=${col} }`)
          addToCaptures(land)
        }
      }
    }

    return captures
  }

  _executeCaptures(captures) {
    for (const t of captures) {
      const { row, col } = this._getRowCol(t)
      this.board[row][col] = '.'
    }
  }

  _moveOne(position) {
    const { row, col } = parsePosition(position, this.height)
    assert(0 <= row && row < this.height)
    assert(0 <= col && col < this.width)
    assert(this.board[row][col] === '.')

    const piece = getPiece(this.turn)
    const captures = this._findCaptures(row, col, piece)
    assert(captures.size > 0 || !this._isSuicide(row, col, this.turn))
    assert(position !== this._lastPosition[this.turn], 'Not satisfy KO rule')

    this._history.push({
      type: 'move',
      turn: this.turn,
      captures,
      position,
      lastPosition: { ...this._lastPosition },
      couldPlaceHandicap: this._couldPlaceHandicap,
    })

    this._couldPlaceHandicap = false
    this.board[row][col] = piece
    this._lastPosition[this.turn] = position
    this._executeCaptures(captures)
    this._toggleTurn()
  }

  _toggleTurn() {
    if (this.turn === 'black') {
      this.turn = 'white'
    } else {
      this.turn = 'black'
    }
  }

  reset() {
    this.turn = 'black'
    this._history = []
    this._lastPosition = { black: null, white: null }
    this.board = initBoard(this.height, this.width)
    this._couldPlaceHandicap = getCouldPlaceHandicap(this.height, this.width)
  }

  pass() {
    this._history.push({
      type: 'pass',
      turn: this.turn,
      lastPosition: { ...this._lastPosition },
      couldPlaceHandicap: this._couldPlaceHandicap,
    })
    this._couldPlaceHandicap = false
    this._toggleTurn()
  }
}
