function simple_assembler(program) {
  const registers = {}
  let i = 0
  while (i < program.length) {
    const instrument = program[i]
    if (instrument.startsWith('mov')) {
      const [_mov, reg, int] = instrument.split(' ')
      if (isNaN(int)) {
        registers[reg] = registers[int]
      } else {
        registers[reg] = Number(int)
      }
    } else if (instrument.startsWith('inc')) {
      const [_inc, reg] = instrument.split(' ')
      registers[reg]++
    } else if (instrument.startsWith('dec')) {
      const [_dec, reg] = instrument.split(' ')
      registers[reg]--
    } else if (instrument.startsWith('jnz')) {
      const [_jnz, condReg, offset] = instrument.split(' ')
      if (registers[condReg] !== 0) {
        i += Number(offset)
        continue
      }
    } else {
      throw new Error('invalid instrument')
    }
    i++
  }
  return registers
}
