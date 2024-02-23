"use strict";


const cnst = x => () => x

const one = cnst(1)
const two = cnst(2)

const cnsts = {
    'one': one,
    'two': two
}


const variablePosition = {
    'x': 0,
    'y': 1,
    'z': 2
}
const variable = name => (...vars) => {
    const position = variablePosition[name]
    return vars[position]
}


const operation = func => (...operands) => (...vars) => func(...operands.map(operand => operand(...vars)))

const add = operation((a, b) => a + b)
const subtract = operation((a, b) => a - b)
const multiply = operation((a, b) => a * b)
const divide = operation((a, b) => a / b)

const negate = operation(x => -x)
const sin = operation(Math.sin)
const cos = operation(Math.cos)

const operations = {
    '+': [add, 2],
    '-': [subtract, 2],
    '*': [multiply, 2],
    '/': [divide, 2],
    'negate': [negate, 1],
    'sin': [sin, 1],
    'cos': [cos, 1]
}

const parse = expr => {
    const stack = []
    expr.split(' ').filter(x => x !== '').forEach(
        operand => stack.push(
            (() => {
                if (operand in cnsts) {
                    return cnsts[operand]
                }
                if (operand in variablePosition) {
                    return variable(operand)
                }
                if (operand in operations) {
                    const operation = operations[operand]
                    return operation[0](...stack.splice(stack.length - operation[1]))
                }
                return cnst(Number(operand))
            })()
        )
    )
    return stack.pop()
}

const test = parse("x x * 2 x * - 1 +")
for (let i = 0; i <= 10; i++) {
    println(test(i, 0, 0))
}