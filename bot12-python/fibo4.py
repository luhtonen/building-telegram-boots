def fib():
    a, b = 0, 1
    while True:
        a, b = b, a + b
        yield a


for index, fibonacci_number in zip(range(20), fib()):
    print('{i:3}: {f:3}'.format(i=index, f=fibonacci_number))
