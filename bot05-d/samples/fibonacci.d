auto fib(const int n) {
    import std.bigint;

    if (n == 0) return BigInt(0);
    if (n == 1) return BigInt(1);

    BigInt next;
    for (BigInt i = 0, j = 1, count = 1; count < n; ++count) {
        next = i + j;
        i = j;
        j = next;
    }
    return next;
}

// to run execute following command:
// dmd -run samples/fibonacci.d
void main() {
    import std.stdio;
    writeln(fib(100_000));
}
