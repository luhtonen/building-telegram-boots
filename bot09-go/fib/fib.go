package main

import (
	"fmt"
	"os"
	"strconv"
)

func fibonacci(n int) int {
	if n <= 1 {
		return n
	}
	return fibonacci(n-1) + fibonacci(n-2)
}

func main() {
	i, _ := strconv.Atoi(os.Args[1])
	fmt.Println(fibonacci(i))
}
