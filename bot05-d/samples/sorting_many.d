import std.stdio;
import std.algorithm;
import std.range;

// to run execute following command:
// dmd -run samples/sorting_many.d
void main() {
    int[] arr1 = [4, 9, 7];
    int[] arr2 = [5, 2, 1, 10];
    int[] arr3 = [6, 8, 3];
    sort(chain(arr1, arr2, arr3));
    writefln("%s\n%s\n%s\n", arr1, arr2, arr3);
}
