const lowercaseLetters = 'abcdefghijklmnopqrstuvwxyz'

function findMissingLetter(array) {
  const isLowercase = lowercaseLetters.includes(array[0])
  const letters = isLowercase ? lowercaseLetters : lowercaseLetters.toUpperCase()
  const startIndex = letters.indexOf(array[0])
  for (let i = 1; i < array.length; i++) {
    if (array[i] !== letters[startIndex + i]) {
      return letters[startIndex + i]
    }
  }
}
