def recursion(n):
  if(n > 0):
    result = recursion(n - 1) * recursion(n-2)
    print(result)
  else:
    result = 1
  return result

print("\n\nRecursion Example Results")
recursion(6)
