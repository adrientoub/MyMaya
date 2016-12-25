# Lexical

## Keywords

    'if', 'then', 'else', 'while', 'loop', 'times', do', 'end', 'function' and 'var'

## Symbols

    '.', '+', '-', '\*', '/', '==', '!=', '<', '<=', '>', '>=' and ':='

## White characters

Space and tabulations are the only white space characters supported.

## End-of-line

End of lines are \n.

## Comments

Like Shell comments, start with a `#` until the end of the line.

## Identifiers

Identifiers can contain any UTF-8 character. They should not contain any Keyword or Symbol.

## Numbers

There are integers and doubles, doubles always contain a point. Numbers can be positive or negative.
Doing operations mixing both integers and doubles will end up with a double.

## Strings

A string constant is a possibly empty series of characters enclosed between double quotes.

## Grammar symbols

```
newline       = /* the Unicode code point U+000A */ .
unicode_char  = /* an arbitrary Unicode code point except newline */ .

DIGIT       ::= [0-9]
SIGN        ::= '+' | '-' | ''
INTEGER     ::= SIGN DIGIT { DIGIT }
DOUBLE      ::= SIGN DIGIT { DIGIT } . { DIGIT }
BOOLEAN     ::= 'true' | 'false'
QUOTED_WORD ::= '"' { unicode_char } '"'
WORD        ::= unicode_char { unicode_char }
```

# Grammar

## LL Grammar

```
input: command '\n'
      | command EOF
      | '\n'
      | EOF

command: function_call
      | rule_for
      | rule_while
      | rule_loop
      | rule_if
      | funcdec
      | vardec

function_call: WORD (parameters)+

vardec: 'var' WORD ':=' parameter

parameter: WORD
         | QUOTED_WORD
         | INTEGER
         | DOUBLE
         | BOOLEAN

value: INTEGER
     | DOUBLE
     | '-' value
     | value '+' value
     | value '-' value
     | value '*' value
     | value '%' value
     | value '/' value
     | '(' value ')'

boolean: BOOLEAN
       | boolean '||' boolean
       | boolean '&&' boolean
       | boolean '==' boolean
       | boolean '!=' boolean
       | '!' boolean
       | '(' boolean ')'
       | value '==' value
       | value '!=' value
       | value '>=' value
       | value '>' value
       | value '<' value
       | value '<=' value

funcdec: 'function' (WORD)+ ('\n')+ command_list 'end'

rule_while: 'while' boolean do_group

rule_loop: 'loop' value 'times' ('\n')+ command_list 'end'

rule_if: 'if' boolean 'then' ('\n')+ command_list [else_clause] 'end'

else_clause: 'else' ('\n')+ command_list

do_group: 'do' ('\n')+ command_list 'end'

command_list: ((command ('\n')+)*
```

## Scope

There is no scoping in the language. Any declared variable can be accessed from any scope.

## Function

To declare a function use the following syntax
```
function my_function parameter1 parameter2 ... parametern
  # code
end
```
To call a function use the following syntax
```
my_function parameter1 parameter2
```
Parameters are either variables or literals.

## Loop

Allows to do some code `n` times.

```
loop n times
  # code
end
```

## Condition

```
if cond then
  # code
else
  # code
end
```

## While

```
while cond do
  # code
end
```

## Variables

```
var my_var := 0
var my_var := 3
var my_var := my_var + 1
var my_var := my_var - 1

var my_str := "Hello"
var my_str := "Toto"
```

## Primitives

Primitives are built-in functions. Their body is given by the runtime.
In these functions:
- obj_name is a String containing the name of the object.
- r, g, b are integers between 0 and 255.
- x, y, z are doubles.

Translate allows to move an object.
```
translate obj_name x y z
```

Rotate allows to rotate an object around an axis.
```
rotate obj_name x y z
```

Scale allows to resize an object.
```
scale obj_name ratio
```

Remove allows to remove an object.
```
remove obj_name
```

Rename allows to rename an object.
```
rename obj_name new_obj_name
```

Color change allows to edit an object color.
```
color_change obj_name r g b
```

Exec allows to launch a script with the name path
```
exec path
```

Save allows to save into the file with the name path
```
save path
```

Save next allows to save into the file with the previous path +1
```
save_next
```

To add a new object to the scene use the following Primitives:
If an object with the same name already exists, prints a warning.
```
[sphere|point_light|directional_light] obj_name x y z r g b
```
