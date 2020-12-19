# countdown-solver
Solving Countdown numeric puzzle

British game show "Countdown" has this puzzle where you are given target, a 3 digit integer, and 6 numbers betwen 1 and 100 inclusive, there is some restrictins on these but they are not imporant. The goal is to produce the target, or close as you can to it, using numbers provided and only alowed operations are +, -, *, / and parentheses. 

Fro example target = 298, nums = 75,50,4,6,1,4 posible solutions: 
6*(1+50)-4-4  
75*4-(6-4)
etc..


This solution uses brute force, creating all subsets and its complement sets and combining to create all possible combinations.



