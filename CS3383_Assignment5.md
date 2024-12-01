# CS3383 - Assignment 5

**Author:** Shahriar Kariman

**Due:** Nov 29th

## Question 1 - (0-1) Knapsack Problem Backtracking Algorithm

### Part A - All Possible Profits

```py
def allProfits(index, objects, current_p):
  if index == len(objects):
    print(current_b)
    return
  allProfits(index+1, objects, current_p)
  allProfits(index+1, objects, current_p + objects[index]['b'])
```

Here is the run time analysis using the master theorm:

$$
\begin{split}
  T(n) =
  \begin{cases}
    \Theta(1) \qquad if\ n<2
    \\
    2 \times T(n-1) + \Theta(1) \qquad if\ n \geq 2
  \end{cases}
\end{split}
$$

That means

$$
\begin{split}
  a=2, \ b=1, \ f_n = \Theta(1)
  \\
  \log_{b}(a) = \log_{1}(2) \rightarrow undefined
  \\

\end{split}
$$

Well since I can't use the master theorm for analysis of the run time I could just say that because we are doing all possible combinations and each item could be considered in or not. Then there must be $2^n$ way to make a set of objects.

And that means my running time is $\Theta(2^n)$.

### Part B - Actual Solution to the Knapsack Problem

```py
def knapsack_bt(index, objects, current_p, current_w, w):
  if index == len(objects):
    return { 'total_profit': current_p, 'total_weight': current_w }
  result_1 = knapsack_bt(index+1, objects, current_p, current_w, w)
  if current_w+objects[index]['w']<=w:
    result_2 = knapsack_bt(index+1, objects, current_p+objects[index]['b'], current_w+objects[index]['w'], w)
  else:
    return result_1
  if result_1['total_profit'] > result_2['total_profit']:
    return result_1
  return result_2
```

I dont really think pruning is impacting the worst case scenario what is $w$ is high enough for all of the objects to fit?

So I think it should be safe to say the run time analysis is still $\Theta(2^n)$.

## Question 2 - Dividing into Subeset

This is alot like DFS.

```py
def divide(numbers):
  n = len(numbers)
  # This is to make the final decision of wether or not
  # a number is selected for a subset
  solution = [False] * n
  # This is to hold the current state of search as
  # I am tervesing state space
  current_state = [False] * n
  # false for set 1 true for set 2
  min_dif = float(inf)
  total_sum = sum(numbers)

  def divide_helper(index, current_sum, num_selected):
    if (
      # base case
      index==n or
      # pruning based on length of sub strings
      num_selected>(n/2) or
      ((n-index)+num_selected)<(index-num_selected)
    ):
      return
    # If I dont select the index
    divide_helper(index+1, current_sum, num_selected)
    # If I do
    num_selected += 1
    current_sum += numbers[index]
    current_state[index] = True
    if num_selected == n/2:
      if abs(total_sum-2*current_sum) < min_dif:
        min_dif = total_sum-2*current_sum
        for i in range(n-1):
          solution[i] = current_state[i]
    else:
      divide_helper(index+1, current_sum, num_selected)
      # reset
      current_state[index] = False

  divide_helper(0, 0, 0)
  set_1 = set_2 = []
  for i in range(n-1):
    set_1.append(numbers[i]) if solution[i] else set_2.append(numbers[i])
  return set_1, set_2
```

Since in the worst case scenario we are still looking at all possible subsets the run time analisys is $\Theta(n)$.

## Question 3

## Question 4 - Planidrome Partitioning

```py
def is_palindrome(s):
  return s == s[::-1]

def palindrome_partition(string):
  results = []

  def backtrack(start, current_partition):
    if start == len(string):
      results.append(current_partition[:])
      return
    for end in range(start, len(string)):
      sub_string = string[start:end+1]
      if is_palindrome(sub_string):
        current_partition.append(sub_string)
        backtrack(end+1, current_partition)
        current_partition.pop()  # Backtrack

  backtrack(0, [])
  return results
```
