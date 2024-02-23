"use strict"


function Const(value) {
    this.value = value;
}
Const.prototype.evaluate = function(...values) {
    return this.value;
};
Const.prototype.toString = function () {
    return String(this.value);
};
Const.prototype.prefix = Const.prototype.toString;


const variablePosition = {x: 0, y: 1, z: 2};
function Variable(name) {
    this.name = name;
    this.position = variablePosition[name]
}
Variable.prototype.evaluate = function (...values) {
    return values[this.position];
};
Variable.prototype.toString = function () {
    return this.name;
};
Variable.prototype.prefix = Variable.prototype.toString;

const operations = {};
function Operation(sign, apply, count) {
    const NewOperation = function (...operands) {
        this.operands = operands;
    };
    NewOperation.prototype = Object.create(Operation.prototype);
    NewOperation.prototype.sign = sign;
    NewOperation.prototype.apply = apply;
    operations[sign] = [NewOperation, count];
    return NewOperation;
}
Operation.prototype.evaluate = function (...values) {
    return this.apply(...this.operands.map(operand => operand.evaluate(...values)));
};
Operation.prototype.toString = function () {
    return this.operands.reduce((string, operand) => string + operand.toString() + ' ', '') + this.sign;
};
Operation.prototype.prefix = function () {
    return '(' + this.operands.reduce((string, operand) => `${string} ${operand.prefix()}`, this.sign) +
        (this.operands.length !== 0 ? '' : ' ') + ')';
};


const Add = new Operation('+', (a, b) => a + b, 2);
const Subtract = new Operation('-', (a, b) => a - b, 2);
const Multiply = new Operation('*', (a, b) => a * b, 2);
const Divide = new Operation('/', (a, b) => a / b, 2);
const Negate = new Operation('negate', a => -a, 1);
const Exp = new Operation('exp', Math.exp, 1);
const Ln = new Operation('ln', Math.log, 1);
const Sum = new Operation('sum', (...args) => args.reduce((sum, x) => sum + x, 0), 'any')
const Avg = new Operation('avg', (...args) => args.reduce((sum, x) => sum + x, 0) / args.length, 'any')


const parse = expr => expr.split(' ').filter(x => x !== '').reduce((stack, operand) => {
    let element;
    if (operand in variablePosition) {
        element = new Variable(operand);
    } else if (operand in operations) {
        const operation = operations[operand];
        element = new operation[0](...stack.splice(stack.length - operation[1]));
    } else {
        element = new Const(Number(operand));
    }
    stack.push(element);
    return stack;
}, []).pop();

function makeError(errorName, messageGenerator) {
    function ParserError(...args) {
        this.message = messageGenerator(...args);
    }
    ParserError.prototype = Object.create(Error.prototype);
    ParserError.prototype.name = errorName;
    ParserError.prototype.constructor = ParserError;
    return ParserError;
}

const UnexpectedTokenError = makeError(
    'UnexpectedTokenError', (pos, found) =>
        `Unexpected token at position ${pos}: expected const or variable, found \'${found}\'`
);
const WrongNumberOfArgumentsError = makeError(
    'WrongNumberOfArgumentsError', (pos, op, expect, found) =>
        `Error at position ${pos}: wrong number of arguments of operation ${op}: expected \'${expect}\', found \'${found}\'`
);
const NoCloseBracketError = makeError(
    'NoCloseBracketError', (pos, found) =>
        `Expected ')', but found \'${found}\' at position ${pos}`
);
const WrongSignError = makeError(
    'WrongSignError', (pos, op) =>
        `Wrong sign at position ${pos}: ${op}`
);
const ExcessiveInfoError = makeError(
    "ExcessiveInfoError", pos => `Error at position ${pos}: end of expression wasn't reached`
);

function parsePrefix(expr) {
    let p = -1;
    let c;
    next();
    function next() {
        ++p;
        if (p < expr.length) {
            c = expr[p];
        } else {
            c = p;
        }
    }
    function test(ch) {
        return c === ch;
    }
    function take(ch) {
        if (c === ch) {
            next();
            return true;
        }
        return false;
    }
    function eof() {
        return c === p;
    }
    function isWS() {
        return /\s/.test(c);
    }
    function skipWS() {
        while (!eof() && isWS()) {
            next();
        }
    }
    function err(SomeError, ...args) {
        throw new SomeError(...args);
    }
    function parseNonWS() {
        let oldP = p;
        while (!eof() && !isWS() && !test('(') && !test(')')) {
            next();
        }
        return expr.substring(oldP, p);
    }
    function parseSign() {
        skipWS();
        let result = parseNonWS();
        if (result in operations) {
            return result;
        } else {
            err(WrongSignError, p, result);
        }
    }
    function parseNonExpression() {
        let result = parseNonWS();
        if (result in variablePosition) {
            return new Variable(result);
        }
        if (result.length === 0 || isNaN(Number(result))) {
            err(UnexpectedTokenError, p, result);
        }
        return new Const(Number(result));
    }
    function parseOperands() {
        let result = [];
        while (true) {
            skipWS();
            if (eof() || test(')')) {
                break;
            }
            result.push(test('(') ? parseExpression() : parseNonExpression());
        }
        return result;
    }
    function parseExpression() {
        skipWS();
        if (take('(')) {
            let sign = parseSign();
            let operation = operations[sign];
            let operands = parseOperands();
            if (operation[1] !== 'any' && operands.length !== operation[1]) {
                err(WrongNumberOfArgumentsError, 1, sign, operation[1], operands.length);
            }
            let result = new operation[0](...operands);
            if (!take(')')) {
                err(NoCloseBracketError, p, eof() ? 'EOF' : c);
            }
            return result;
        }
        return parseNonExpression();
    }
    function parse() {
        let result = parseExpression();
        skipWS();
        if (!eof()) {
            err(ExcessiveInfoError, p);
        }
        return result;
    }
    return parse();
}