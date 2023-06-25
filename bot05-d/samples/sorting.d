import std.stdio;
import std.algorithm;

// to run execute following command:
// dmd -run samples/sorting.d
void main() {
    int[] arr = [4, 9, 7];
    writefln("%s\n", sort(arr));
}
