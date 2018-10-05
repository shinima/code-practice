class TopVotedCandidate {
  /**
   * @param {number[]} persons
   * @param {number[]} times
   */
  constructor(persons, times) {
    this.array = []

    const voteMap = new Map()
    let winningVote = 0
    let winningPerson = -1
    for (let i = 0; i < persons.length; i++) {
      const person = persons[i]
      const time = times[i]
      const oldVote = voteMap.has(person) ? voteMap.get(person) : 0
      const newVote = oldVote + 1
      voteMap.set(person, newVote)
      if (newVote >= winningVote) {
        winningVote = newVote
        if (winningPerson === -1 || this.array[this.array.length - 1].person !== person) {
          winningPerson = person
          this.array.push({ time, person })
        }
      }
    }
  }

  /**
   * @param {number} t
   * @return {number}
   */
  q(t) {
    if (t < this.array[0].time) {
      return -1
    }
    let low = 0
    let high = this.array.length - 1
    while (low < high) {
      const mid = Math.ceil((low + high) / 2)
      const item = this.array[mid]
      if (item.time <= t) {
        low = mid
      } else {
        high = mid - 1
      }
    }
    return this.array[low].person
  }
}
