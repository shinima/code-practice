const MORSE_CODES = [
  '.-',
  '-...',
  '-.-.',
  '-..',
  '.',
  '..-.',
  '--.',
  '....',
  '..',
  '.---',
  '-.-',
  '.-..',
  '--',
  '-.',
  '---',
  '.--.',
  '--.-',
  '.-.',
  '...',
  '-',
  '..-',
  '...-',
  '.--',
  '-..-',
  '-.--',
  '--..',
]

const CHAR_CODE_A = 'a'.charCodeAt(0)

function getMorseCode(ch) {
  return MORSE_CODES[ch.charCodeAt(0) - CHAR_CODE_A]
}

/**
 * @param {string[]} words
 * @return {number}
 */
function uniqueMorseRepresentations(words) {
  return new Set(
    words.map(word =>
      Array.from(word)
        .map(getMorseCode)
        .join(''),
    ),
  ).size
}

// console.log(uniqueMorseRepresentations(['gin', 'zen', 'gig', 'msg']))
