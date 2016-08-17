import java.math.BigInteger;

/*********************************************************************
 * Purpose/Description: A Program that demonstrates the author's ability to
 * write a clean and efficient algorithm, and then execute it in Java. The
 * program contains two methods, one which implements an O(n) algorithm for
 * searching a sorted Matrix, and another that calculates the nth
 * Fibonacci number in O(logN) complexity.
 * Author’s Panther ID: 4221685
 * Certification: I hereby certify that this work is my own and none of it is
 * the work of any other person.
 ********************************************************************/
public class AlgorithimTester {

	/**
	 * Method should determine if a given integer x is contained in a given
	 * Matrix A
	 * 
	 * @param x:
	 *            an integer to be searched for in Matrix A.
	 * @param A:
	 *            An NxN Matrix containing non-negative integers. The rows
	 *            decrease sequentially from left to right, and the columns
	 *            decrease sequentially from top to bottom. Essentially a
	 *            "sorted" matrix.
	 * @return true if x is an element of A, false if it is not, and number of
	 * 			indeces checked. 
	 * @algorithm:
	 * 	Step 1: Begin at first index of Matrix. (Set i=0, j=0.)
	 * 		--> If x > element at a(0)[0], x is not an element of A. Output False.
	 * 		--> Else if x = element at a(0)[0], Output True. 
	 * 		--> Else, go to step 2.
	 * 	Step 2: Go diagonal, down 1 right 1 index. (Increment i and j)
	 * 		--> If i>N (N = dimension of A) Output False. Reached end of Matrix. 
	 * 		--> Else if x = element at a(i)[j], Output true.
	 * 		--> Else if x > element at a(i)[j], go to Step 3. (If x is an element of A, it 
	 *  			will now be located between, ie : a(i)[j] < x < a(i-1)[j-1])
	 *  	--> Else repeat Step 2.
	 *  Step 3: Check remaining items in row for x, going backwards. 
	 *  (Define integer k such that k=j before beginning step 3). 
	 *  	--> If every item in row has been checked, go to step 4. (if k=0)
	 *  	--> Else if there are unchecked items in the row (k!=0), move to the left
	 *  		one index (decrement k, k=k-1) 
	 *  	--> If x = element at a(i)[k], Output True.
	 *  	--> Else if x < element at a(i)[k], Output False.
	 *  	--> Else repeat Step 3. 
	 *  Step 4: Go the the previous row, and begin checking indexes from the right most,
	 *  then moving left (Reassign k to k=n. Check remaining items of i-1 where k > j-1)
	 *  	--> If k=j-1, or if x < element at a(i)[k], Output False. (x is not an element of A.)
	 *  	--> Else if x = element at a(i)[k] Output True.
	 *  	--> Else decrement k, or move left one unit (k=k-1). 
	 */
	public static String isContainedIn(int x, int[][] A) {
		// Trivial Cases False:
		// Matrix does not contain negative integers, so if x < 0
		// it will not be contained in A
		if (x < 0)
			return "false, checked 0 indeces" ;

		// Instance Variables
		int row = 0;
		int column = 0;
		int k = 0;			// temp variable
		int n = A.length-1; // dimensions of Matrix
		int i = 1;			// index counter for checking efficiency

		// ***** Begin Actual Algorithm ******

		// STEP 1
		if (x > A[row][column])
			return "false, checked " + i + " indeces";
		if (x == A[row][column])
			return "true checked " + i + " indeces";

		// STEP 2
		while (row < n) 
		{
			row++;
			column++;
			i++;
			if (x == A[row][column])
				return "true, checked " + i + " indeces";
			if (x > A[row][column]) 
			{
				// STEP 3
				k = column;
				while (k > 0) {
					i++;
					k--;
					if (x == A[row][k])
						return "true, checked " + i + " indeces";
					if (x < A[row][k])
						return "false, checked " + i + " indeces";
				}
				// STEP 4
				k = n;
				while (k > column - 1) {
					i++;
					if (x == A[row][k])
						return  "true, checked " + i + " indeces";;
					if (x < A[row][k])
						return "false, checked " + i + " indeces";
					k--;
				}
				return "false, checked " + i + " indeces";
			}
		}
		return "false, checked " + i + " indeces";
	}
	/**
	 * A sub-linear Time Complexity function for calculating Fibonacci
	 * Utilizes Fast-Doubling Algorithm, derived from Matrix Exponentiation Proof
	 * [ 1 1 ]       [ F(n+1)     F(n)   ]
 	 * [ 1 0 ] ^ n = [ F(n)       F(n-1) ]
	 * @Algorithim
	 * 			Assuming formula for Matrix Exponentiation above is true,
	 * 			If we know F(k) and F(k+1), then we can find
				F(2k) = F(k)[2F(k+1) – F(k)]
				F(2k+1) = F(k+1)^2 + F(k)^2
				Complexity: O(logN)
				Justification: Let us assume that an algorithm of O(N) complexity,
				for generating the nth Fibbonacci number, needs to "run" n times,
				or calculate each fibbonacci number up to the nth. An Algorithm of O(logN)
				complexity would only need to "run" a fraction of n times, or generate a 
				fraction of n fibbonacci in order to calculate the nth Fibonacci number.
				This algorithm only needs to iterate a fraction of N times, 
				making it O(logN) complexity. 
				Disclaimer: Absolutely found this algorithm online (clearly).
	 * @param n:
	 * 			 Sequence of Fibbonacci  
	 * @return
	 * 			The Nth Fibonacci number. 
	 */
	public static BigInteger fastFib(int n) {
		// We have to use BigInt as numbers quickly get very large, and long
		// will not cut it, especially for accuracy 
		if (n<=0)
			return BigInteger.ZERO;
		// Break up Algorithm into two methods, 1 with partial recursion
		// + solving algorithm, and one to handle returning answer. Answer method
		// will make a call to solving method. 
		// Use an Array since we cannot use Tuples
		// Array Answer contains 2 parts of fib formula, f(n) and f(n+1)
		BigInteger[] answer = fib(n-1);
		return answer[1];
	}
	// Method that does the actual heavy lifting
	// Da real MVP
	public static BigInteger[] fib(int n) {
		//Two = 2
		 BigInteger TWO = BigInteger.valueOf(2l);
		if (n <= 0)
		{
			// Arrived at Base Case
			BigInteger[] base= {BigInteger.ZERO,BigInteger.ONE};
			return base;
		}
		// Recursive call utilizing the fast doubling algorithm
		// is implemented below. Each Tuple stores fib value for 
		// either n+1 or n to carry on to next recursive call,
		// and of course be manipulated according to algorithm
		BigInteger[] arr = fib(n/2);
		BigInteger a = arr[0];
		BigInteger b = arr[1];
		BigInteger c = a.multiply((TWO.multiply(b).subtract(a)));   
		BigInteger d = (b.multiply(b)).add(a.multiply(a));
		if (n % 2 == 0)
		{
			arr[0] = c;
			arr[1] = d;
			return arr;
		}
		else
			arr[0] = d;
			arr[1] = c.add(d);
			return arr;
	}

	// Tester
	public static void main(String[] args) {
/*
 * Student Driver for testing input/output 

		int[][] matrix = { 
				{ 26, 22, 17, 10 },
				{ 19, 16, 12, 7, },
				{ 12, 10, 7, 4 },
				{ 5, 4, 2, 1 } };
		System.out.println("  ====Matrix====" 
				+ "\n {26, 22, 17, 10}" 
				+ "\n  {19, 16, 12, 7}"
				+ "\n  {12, 10, 7, 4}"
				+ "\n   {5, 4, 2, 1}"
				+ "\n  =============="
				+ "\nTesting Values:" 
				+ "\nValue 10 returns " 
				+ isContainedIn(10, matrix) + ". Expected: true."
				+ "\nValue 26 returns " 
				+ isContainedIn(26, matrix) + ". Expected: true."
				+ "\nValue 0 returns " 
				+ isContainedIn(0, matrix) + ". Expected: false."
				+ "\nValue 30 returns " 
				+ isContainedIn(30, matrix) + ". Expected: false."
				+ "\nValue 3 returns " 
				+ isContainedIn(3, matrix) + ". Expected: false."
				+ "\nValue -10 returns " 
				+ isContainedIn(-10, matrix) + ". Expected: False.");
		
		System.out.println(fastFib(250));
		
		 */
	}
}