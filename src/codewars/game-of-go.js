const EMPTY_PIECE = '.'
const X_AXIS_LETTERS = 'ABCDEFGHJKLMNOPQRSTUVWXYZ' // A-Z except I, 25 letters
const LETTER_TO_COL = new Map(Array.from(X_AXIS_LETTERS).map((letter, index) => [letter, index]))
const HANDICAP_POSITION_MAP = {
  9: ['7G', '3C', '3G', '7C', '5E'],
  13: ['10K', '4D', '4K', '10D', '7G', '7D', '7K', '10G', '4G'],
  19: ['16Q', '4D', '4Q', '16D', '10K', '10D', '10Q', '16K', '4K'],
}

const errors = {
  invalidPosition(position) {
    return `Invalid position - ${position}.`
  },
  placeOnAnOccupiedPosition() {
    return 'Cannot place stone at an already occupied position.'
  },
}

function isRival(p1, p2) {
  return (p1 === 'x' && p2 === 'o') || (p1 === 'o' && p2 === 'x')
}

function getMyPiece(turn) {
  if (turn === 'black') {
    return 'x'
  } else if (turn === 'white') {
    return 'o'
  } else {
    throw new Error(`Invalid turn - ${turn}`)
  }
}

function getRivalPiece(turn) {
  if (turn === 'white') {
    return 'x'
  } else if (turn === 'black') {
    return 'o'
  } else {
    throw new Error(`Invalid turn - ${turn}`)
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

class Go {
  constructor(height, width = height) {
    assert(height >= 1 && height <= 25, 'Height should be in range [1, 25]')
    assert(width >= 1 && width <= 25, 'Width should be in range [1, 25]')

    this.height = height
    this.width = width
    this.turn = 'black' // black | white

    this._list = new Array(height * width).fill(EMPTY_PIECE)
    this._couldPlaceHandicap = getCouldPlaceHandicap(height, width)
    this._history = []

    const left = position => {
      const { row, col } = this._parseRowCol(position)
      return col > 0 ? this._makePosition(row, col - 1) : null
    }
    const right = position => {
      const { row, col } = this._parseRowCol(position)
      return col < this.width - 1 ? this._makePosition(row, col + 1) : null
    }
    const up = position => {
      const { row, col } = this._parseRowCol(position)
      return row > 0 ? this._makePosition(row - 1, col) : null
    }
    const down = position => {
      const { row, col } = this._parseRowCol(position)
      return row < this.height - 1 ? this._makePosition(row + 1, col) : null
    }
    this._directionFuncs = [left, right, up, down]
  }

  get board() {
    const result = []
    for (let row = 0; row < this.height; row++) {
      const line = []
      for (let col = 0; col < this.width; col++) {
        line.push(this._list[row * this.width + col])
      }
      result.push(line)
    }
    return result
  }

  get size() {
    return { height: this.height, width: this.width }
  }

  _parse(position) {
    const match = position.match(/^(\d\d?)([A-Z])$/)
    if (match == null) {
      return null
    }
    const lineNumber = Number(match[1]) // lineNumber is 1-based
    if (lineNumber < 1 || lineNumber > this.height) {
      return -1
    }
    const colLetter = match[2]
    const col = LETTER_TO_COL.get(colLetter) // col is 0-based
    if (col == null || col < 0 || col > this.width - 1) {
      return -1
    }
    return (this.height - lineNumber) * this.width + col
  }

  _parseRowCol(position) {
    const t = this._parse(position)
    assert(t !== -1, errors.invalidPosition(position))
    return {
      row: Math.floor(t / this.width),
      col: t % this.width,
    }
  }

  _setPosition(position, piece) {
    const t = this._parse(position)
    assert(t !== -1, errors.invalidPosition(position))
    this._list[t] = piece
  }

  rollback(steps = 1) {
    for (let i = 0; i < steps; i++) {
      this._rollbackOneStep()
    }
  }

  _rollbackOneStep() {
    const record = this._history.pop()
    assert(record != null, 'Cannot rollback.')
    if (record.type === 'move') {
      this._toggleTurn()
      this._setPosition(record.position, EMPTY_PIECE)
      const rivalPiece = getRivalPiece(record.turn)
      for (const capture of record.captures) {
        this._setPosition(capture, rivalPiece)
      }
    } else if (record.type === 'pass') {
      this._toggleTurn()
    } else if (record.type === 'place-handicap-stones') {
      record.positions.forEach(position => {
        this._setPosition(position, EMPTY_PIECE)
      })
      this._toggleTurn()
    } else {
      assert(false, 'Invalid history record')
    }
    this._couldPlaceHandicap = record.couldPlaceHandicap
  }

  handicapStones(number) {
    assert(this._couldPlaceHandicap, 'Cannot place handicap stones for now')
    const positions = HANDICAP_POSITION_MAP[this.height]
    assert(positions != null, 'Cannot find handicap positions in HANDICAP_POSITION_MAP')
    assert(
      number >= 1 && number <= positions.length,
      `handicap stone number must be in range [1, ${positions.length}]`,
    )

    this._history.push({
      type: 'place-handicap-stones',
      positions: positions.slice(0, number),
    })

    for (const position of positions.slice(0, number)) {
      this._setPosition(position, getMyPiece(this.turn))
    }
    this._couldPlaceHandicap = false
    this._toggleTurn()
  }

  getPosition(position) {
    const t = this._parse(position)
    assert(t !== -1, errors.invalidPosition(position))
    return this._list[t]
  }

  move(...positions) {
    for (const position of positions) {
      this._moveOne(position)
    }
  }

  _bfs(position, piece) {
    const prevPiece = this.getPosition(position)
    this._setPosition(position, piece)

    const land = new Set()
    const liberties = new Set()

    let front = new Set()
    front.add(position)
    while (front.size > 0) {
      const nextFront = new Set()
      for (const pos of front) {
        land.add(pos)

        for (const directionFn of this._directionFuncs) {
          const neighbor = directionFn(pos)
          if (neighbor == null) {
            continue
          }
          const neighborPiece = this.getPosition(neighbor)
          if (neighborPiece === piece) {
            if (!land.has(neighbor) && !front.has(neighbor)) {
              nextFront.add(neighbor)
            }
          } else if (neighborPiece === EMPTY_PIECE) {
            liberties.add(neighbor)
          }
        }
      }
      front = nextFront
    }

    this._setPosition(position, prevPiece)
    return { land, liberties }
  }

  _isSuicide(position, piece) {
    const { liberties } = this._bfs(position, piece)
    return liberties.size === 0
  }

  _makePosition(row, col) {
    return `${this.height - row}${X_AXIS_LETTERS[col]}`
  }

  _findCaptures(position, lastPiece) {
    const captures = new Set()

    for (const directionFn of this._directionFuncs) {
      const neighbor = directionFn(position)
      if (neighbor == null) {
        continue
      }
      const neighborPiece = this.getPosition(neighbor)
      if (isRival(neighborPiece, lastPiece)) {
        const { liberties, land } = this._bfs(neighbor, neighborPiece)
        assert(liberties.size >= 1, 'liberties.size must greater than or equal to 1')
        if (liberties.size === 1) {
          assert(liberties.has(position), `Only liberty must be at ${position}`)
          for (const item of land) {
            captures.add(item)
          }
        }
      }
    }

    return captures
  }

  _isSatisfyKoRules(position, captures) {
    if (this._history.length > 0) {
      const record = this._history[this._history.length - 1]
      if (
        record.type === 'move' &&
        record.captures.size === 1 &&
        record.captures.has(position) &&
        captures.size === 1 &&
        captures.has(record.position)
      ) {
        return false
      }
    }
    return true
  }

  _moveOne(position) {
    assert(this.getPosition(position) === EMPTY_PIECE, errors.placeOnAnOccupiedPosition())
    // position must be valid here
    const piece = getMyPiece(this.turn)
    const captures = this._findCaptures(position, piece)
    assert(captures.size > 0 || !this._isSuicide(position, piece), 'Suicide is not allowed')
    assert(this._isSatisfyKoRules(position, captures), 'Does not satisfy KO rule')

    this._history.push({
      type: 'move',
      turn: this.turn,
      captures,
      position,
      couldPlaceHandicap: this._couldPlaceHandicap,
    })

    this._couldPlaceHandicap = false
    this._setPosition(position, piece)
    // executing captures
    for (const pos of captures) {
      this._setPosition(pos, EMPTY_PIECE)
    }
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
    this._list = new Array(this.height * this.width).fill(EMPTY_PIECE)
    this._couldPlaceHandicap = getCouldPlaceHandicap(this.height, this.width)
  }

  pass() {
    this._history.push({
      type: 'pass',
      turn: this.turn,
      couldPlaceHandicap: this._couldPlaceHandicap,
    })
    this._couldPlaceHandicap = false
    this._toggleTurn()
  }

  print() {
    const axisYWidth = String(this.width).length
    console.log('='.repeat(60))
    console.log(
      ' '.repeat(axisYWidth + 1) + Array.from(X_AXIS_LETTERS.slice(0, this.width)).join(' '),
    )
    for (let row = 0; row < this.height; row++) {
      const n = this.height - row
      console.log([String(n).padStart(axisYWidth)].concat(this.board[row]).join(' '))
    }
  }
}
