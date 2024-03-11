let rec fibonacci n =
    match n with
    | 0 -> 0
    | 1 -> 1
    | _ -> fibonacci (n - 1) + fibonacci (n - 2)

let printFibonacciSequence count =
    printfn ""
    for i = 1 to count do
        printfn "%d" (fibonacci i)

printfn "How long would you like the sequence to be?"
let input = System.Console.ReadLine()
let userInput = int input
printFibonacciSequence userInput