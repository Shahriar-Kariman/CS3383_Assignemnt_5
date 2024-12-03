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

I guess the first step should be to have an algorithm to calculate the cost of for a particular $i$ and $j$.

```py
def calculate_cost(X, i, j):
  avrage = sum(X[i:j])/(j-i)
  cost = sum(abs(x-avrage) for x in X[i:j])
```

I know how the brute force calcualtion should look like.

```py
def bruteForce_clustering(X):
  n = len(X)
  min_cost = float('inf')
  best_split = None
  for i in range(1,n):
    cost_1 = calculate_cost(X, 0, i-1)
    cost_2 = calculate_cost(X, i, n-1)
    total_cost = cost_1 + cost_2
    if total_cost<min_cost:
      min_cost = total_cost
      best_split = i
  return {'split': best_split, 'cost': min_cost}
```

Now that is not backtracking so I just need to make it recursive I think that would solve my problem.

```py
def clustering(X):
  n = len(X)
  min_cost = float('inf')
  best_split = None
  def helper(index):
    if index==n:
      return
    cost_1 = calculate_cost(X, 0, index-1)
    cost_2 = calculate_cost(X, index, n-1)
    total_cost = cost_1 + cost_2
    if total_cost<min_cost:
      min_cost = total_cost
      best_split = index
    helper(index+1)
  return {'split': best_split, 'cost': min_cost}
```

Now I know for sure that that is a recursive algorithm but I dont think its a backtracking algorithm but I have spent a lot of time trying to figure out a way to prune the space but I cant think of any. I mean so what if adding an index increases the cost I have no proof that adding another one won't reduce the cost so I cant prune base of the current state of the search.

Honestly I give up if my answer is not a backtracking algorithm just don't give me the marks for this question I don't care.

And by the way since I calculate the cost for every possible index and the cost calculation happens in $\Theta(n)$ then the total running time is $\Theta(n^2)$.

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

Well since this algorithm checks every possible substring ($2^n$) and on each case it chacks if it is a palindrom which happns in $\Theta(n)$ time. The total run tiem should be $\Theta(n \times 2^n)$.
