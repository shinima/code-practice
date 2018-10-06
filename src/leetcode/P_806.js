const LINE_WIDTH = 100

/**
 * @param {number[]} widths
 * @param {string} S
 * @return {number[]}
 */
function numberOfLines(widths, S) {
  let line = 1
  let cursor = 0
  for (const ch of S) {
    if (cursor + getCharWidth(ch) <= LINE_WIDTH) {
      cursor += getCharWidth(ch)
    } else {
      line++
      cursor = getCharWidth(ch)
    }
  }

  return [line, cursor]

  function getCharWidth(ch) {
    return widths[ch.charCodeAt(0) - 'a'.charCodeAt(0)]
  }
}
