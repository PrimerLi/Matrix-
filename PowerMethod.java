import java.util.Random;

public class PowerMethod
{
    public static void main(String []args)
    {
	int dimension = 3;
	double [][]array = new double [dimension][dimension];
	for (int i = 0; i < dimension; i++)
	{
	    for (int j = 0; j < dimension; j++)
	    {
		array[i][j] = 0;
		if (i == 0 || j == 0)
		    array[i][j] = 1;
	    }
	}
	array[1][1] = 1;
	array[1][2] = -1;
	array[2][1] = -2;
	array[2][2] = 0;

	Matrix matrix = new Matrix(array);
	matrix.printMatrix();

	Random random = new Random();
	double []vector = new double [dimension];
	for (int i = 0; i < dimension; i++)
	{
	    vector[i] = random.nextDouble();
	}

	Vector inputVector = new Vector(vector);

	int iterationNumber = 100;
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
    }
}
