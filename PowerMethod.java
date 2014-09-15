import java.util.Random;

public class PowerMethod
{
    public static void main(String []args)
    {
	int dimension = Integer.parseInt(args[0]);
	Random random = new Random();
	double [][]array = new double [dimension][dimension];
	for (int i = 0; i < dimension; i++)
	{
	    for (int j = 0; j < dimension; j++)
	    {
		array[i][j] = random.nextInt(dimension*dimension);
	    }
	}

	Matrix matrix = new Matrix(array);
	matrix.printMatrix();
	matrix.printMatrixForMathematica();

	double []vector = new double [dimension];
	for (int i = 0; i < dimension; i++)
	{
	    vector[i] = random.nextDouble();
	}

	Vector inputVector = new Vector(vector);

	int iterationNumber = 300;
	for (int i = 0; i < iterationNumber; i++)
	{
	    Vector temp = matrix.product(inputVector);
	    inputVector = temp.normalize();
	}

	System.out.println("Output vector : ");
	inputVector.printVector();

	Complex []eigenvalues = new Complex[dimension];
	eigenvalues = matrix.eigenvalues();
	System.out.println("Eigenvalues of the matrix are : ");
	for (int i = 0; i < dimension; i++)
	{
	    System.out.println(eigenvalues[i]);
	}

	double [][]randomArray = new double [dimension][dimension];
	for (int i = 0; i < dimension; i++)
	{
	    for (int j = 0; j < dimension; j++)
	    {
		randomArray[i][j] = random.nextDouble();
	    }
	}

	Matrix inputMatrix = new Matrix(randomArray);
	Matrix []matrixArray;
	matrixArray = (matrix.product(inputMatrix)).QRDecomposition();
	for (int i = 0; i < iterationNumber; i++)
	{
	    matrixArray = (matrix.product(matrixArray[0])).QRDecomposition();
	}

	Matrix Q = matrixArray[0];
	Matrix R = matrixArray[1];
	System.out.println("Q matrix : ");
	Q.printMatrix();
	System.out.println("R matrix : ");
	R.printMatrix();
	Matrix Result = ((Q.transpose()).product(matrix)).product(Q);
	System.out.println("Here is your resultant matrix: ");
	Result.printMatrix();
    }
}
