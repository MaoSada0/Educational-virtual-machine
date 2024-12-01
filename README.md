# Assembler and interpreter for a learning virtual machine

To use this
```
java jar <path to jar file> <path to txt file with instructions> <path to bin file> <path to yaml file>
```

## Example

- input file

``` txt
LOAD_CONST 400
LOAD_CONST 300
LOAD_CONST 200
LOAD_CONST 100
LOAD_CONST 50
LOAD_CONST 10
POP_CNT 1
POP_CNT 4
POP_CNT 22
POP_CNT 2342
POP_CNT 42
```

- log file

``` yaml
'Step[1] | Stack: ': '{400}'
'Step[1] | Memory: ': '{}'
'Step[2] | Stack: ': '{400, 300}'
'Step[2] | Memory: ': '{}'
'Step[3] | Stack: ': '{400, 300, 200}'
'Step[3] | Memory: ': '{}'
'Step[4] | Stack: ': '{400, 300, 200, 100}'
'Step[4] | Memory: ': '{}'
'Step[5] | Stack: ': '{400, 300, 200, 100, 50}'
'Step[5] | Memory: ': '{}'
'Step[6] | Stack: ': '{400, 300, 200, 100, 50, 10}'
'Step[6] | Memory: ': '{}'
'Step[7] | Stack: ': '{400, 300, 200, 100, 50}'
'Step[7] | Memory: ': '{memory[1]: 10}'
'Step[8] | Stack: ': '{400, 300, 200, 100}'
'Step[8] | Memory: ': '{memory[1]: 10, memory[4]: 50}'
'Step[9] | Stack: ': '{400, 300, 200}'
'Step[9] | Memory: ': '{memory[1]: 10, memory[4]: 50, memory[22]: 100}'
'Step[10] | Stack: ': '{400, 300}'
'Step[10] | Memory: ': '{memory[1]: 10, memory[4]: 50, memory[22]: 100, memory[2342]:
  200}'
'Step[11] | Stack: ': '{400}'
'Step[11] | Memory: ': '{memory[1]: 10, memory[4]: 50, memory[22]: 100, memory[42]:
  300, memory[2342]: 200}'

```
