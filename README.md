# bop
## Introduction
Bop is a super fun esoteric programming language in which code is "written" by pressing an input according to predetermined rhythms.
## Design Principles
- Keyboards are large, unwieldy, and wholly unnecessary for programming in Bop; A single key should suffice.
- Theoretically, by decreasing the time interval, programs in Bop can be created from scratch in a matter of seconds.
- It should be fun to program in Bop!
## Language Concepts
Bop is a stack-based programming language, as everything revolves around a central stack that is initiallly empty. Programs in Bop are created by first running the corresponding Java program (Bop.java), which opens a window in which your inputs will be entered. Accepted inputs are as follows:
- Spacebar

That's it.

By pressing Spacebar repeatedly at various time intervals, commands are issued and interpreted into generic stack-based programming commands like `push`, `pop`, `add`, as well as control flow commands like `while`. Specific commands and how to issue them are detailed in the next section.
## Syntax Elements
Commands are issued by pressing a sequence of intermitten spaces and pauses, which will be represented as `Space` and `Pause` respectively below.

By default, the time interval between commands is 1 second. For example, the command `Space` `Pause` `Space` could be issued by pressing Space on second 1, pausing on second 2, and pressing Space again on second 3.
### Commands
- **Push**: `Space` `Space` `Space` `Space` `Pause` (This will trigger the push command, which will then wait for a sequence of consecutive Spaces to determine what number to push to the stack. The number of consecutive Spaces is equal to the value of the number pushed to the stack)
- **Pop**: `Space` `Space` `Space` `Space` `Space` (This will pop the top item off the stack)
- **Add**: `Space` `Space` `Space` `Pause` `Pause` (This will pop the top two items off the stack, add them, and push the result back onto the stack)
- **Subtract**: `Space` `Pause` `Pause` `Pause` `Pause` (This will pop the top two items off the stack, calculate second-top - top, and push the result back onto the stack)
- **Multiply**: `Space` `Space` `Pause` `Pause` `Pause` (This will pop the top two items off the stack, multiply them, and push the result back onto the stack)
- **Divide**: `Space` `Pause` `Space` `Pause` `Pause` (This will pop the top two items off the stack, calculate second-top / top, and push the result back onto the stack)
- **Mod**: `Space` `Pause` `Pause` `Space` `Pause` (This will pop the top two items off the stack, calculate second-top % top, and push the result back onto the stack)
- **Not**: `Space` `Space` `Space` `Pause` `Space` (This will replace the top item of the stack with 0 if it is non-zero, and 1 if it is zero)
- **Greater**: `Space` `Pause` `Pause` `Pause` `Space` (This will pop the top two items off the stack, push a 1 onto the stack if second-top > top and pushes a 0 otherwise)
- **Duplicate**: `Space` `Pause` `Pause` `Space` `Space` (This will push another copy of the top item of the stack onto the stack)
- **Roll**: `Space` `Pause` `Space` `Space` `Space` (This will pop the top two items off the stack and rolls the remaining stack entries to a depth equal to the second-top item, by a *nonnegative* number of rolls equal to the top item)
- **Input**: `Space` `Pause` `Space` `Pause` `Space` (This will read a value from the user and push it onto the stack)
- **Output**: `Space` `Pause` `Space` `Space` `Pause` (This will pop and output the top value of the stack)
- **Start while**: `Space` `Space` `Pause` `Pause` `Space` (This will signify the beginnning of a while loop that executes as long as the top value of the stack is not 0)
- **End while**: `Space` `Space` `Pause` `Space` `Pause` (This will signify the end of the innermost while loop that is currently being executed)
- **End programming/Begin execution**: `Space` `Space` `Pause` `Space` `Space` (This will end the program, allowing for no more commands to be issued and instead running the program that has been built so far; at this point, other inputs may be issued by the user, like numbers)
