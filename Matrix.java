import java.util.Random;

public class Matrix
{
    private int dimension;
    private double [][]matrix;
    
    public Matrix(double [][]array)
    {
	if (array.length != array[0].length)
	{
	    throw new IllegalArgumentException("Only square matrix is accepted. ");
	}
	this.dimension = array.length;
	matrix = new double [dimension][dimension];
	for (int i = 0; i < dimension; i++)
	{
	    for (int j = 0; j < dimension; j++)
	    {
		matrix[i][j] = array[i][j];
	    }
	}
    }

    public void printMatrix()
    {
	for (int i = 0; i < dimension; i++)
	{
	    for (int j = 0; j < dimension; j++)
	    {
		System.out.print(matrix[i][j] + "\t");
	    }
	    System.out.println();
	}
    }

    public int getDimension()
    {
	return this.dimension;
    }

    public double [][] getMatrix()
    {
	return matrix;
    }

    public double detOfMatrix()
    {
	int count = 0;
	double [][]array;
	array = new double [dimension][dimension];
	for (int i = 0; i < dimension; i++)
	{
	    for (int j = 0; j < dimension; j++)
	    {
		array[i][j] = matrix[i][j];
	    }
	}

	int column = 0;
	while (column < dimension)
	{
	    int nonZero = column;
	    while (array[nonZero][column] == 0)
	    {
		nonZero++;
		if (nonZero == dimension)
		{
		    return 0;
		}
	    }

	    if (nonZero != column)
	    {
		count++;
		double temp;
		for (int j = 0; j < dimension; j++)
		{
		    temp = array[column][j];
		    array[column][j] = array[nonZero][j];
		    array[nonZero][j] = temp;
		}
	    }

	    double factor = 1;
	    for(int i = column + 1; i < dimension; i++)
	    {
		factor = -array[i][column]/array[column][column];
		for (int j = 0; j < dimension; j++)
		{
		    array[i][j] = array[i][j] + factor*array[column][j];
		}
	    }
	    column++;
	}

	double temp = 1;
	for (int i= 0; i < dimension; i++)
	{
	    temp = temp*array[i][i];
	}
	if (count % 2 == 0)
	    return temp;
	else
	    return -temp;
    }

    public boolean isSingular()
    {
	if (this.detOfMatrix() == 0)
	    return true;
	else 
	    return false;
    }

    public Matrix inverse()
    {
	double [][]argument;
	int dimensionOfArgument = 2*dimension;
	argument = new double [dimension][dimensionOfArgument];
	for (int i = 0; i < dimension; i++)
	{
	    for (int j = 0; j < dimension; j++)
	    {
		argument[i][j] = this.matrix[i][j];
	    }
	}

	for (int i = 0; i < dimension; i++)
	{
	    for (int j = dimension; j < dimensionOfArgument; j++)
	    {
		if (i == j - dimension)
		    argument[i][j] = 1;
		else
		    argument[i][j] = 0;
	    }
	}

	int column = 0;
	while (column < dimension)
	{
	    int nonZero = column;
	    while (argument[nonZero][column] == 0)
	    {
		nonZero++;
		if (nonZero == dimension)
		{
		    return null;
		}
	    }

	    if (nonZero != column)
	    {
		double temp;
		for (int j = 0; j < dimensionOfArgument; j++)
		{
		    temp = argument[column][j];
		    argument[column][j] = argument[nonZero][j];
		    argument[nonZero][j] = temp;
		}
	    }

	    double factor = 1;
	    for (int i = column + 1; i < dimension; i++)
	    {
		factor = -argument[i][column]/argument[column][column];
		for (int j = 0; j < dimensionOfArgument; j++)
		{
		    argument[i][j] = argument[i][j] + factor*argument[column][j];
		}
	    }
	    column++;
	}

	int row = dimension - 1;
	while (row > 0)
	{
	    for (int i = row - 1; i >= 0; i--)
	    {
		double factor = -argument[i][row]/argument[row][row];
		for (int j = 0; j < dimensionOfArgument; j++)
		{
		    argument[i][j] = argument[i][j] + argument[row][j]*factor;
		}
	    }
	    row--;
	}

	row = 0;
	while (row < dimension)
	{
	    double factor = (1/argument[row][row]);
	    for (int j = row; j < dimensionOfArgument; j++)
	    {
		argument[row][j] = argument[row][j]*factor;
	    }
	    row++;
	}

	double [][]temp;
	temp = new double [dimension][dimension];
	for (int i = 0; i < dimension; i++)
	{
	    for (int j = 0; j < dimension; j++)
	    {
		temp[i][j] = argument[i][j + dimension];
	    }
	}

	return new Matrix(temp);
    }

    public static void main(String []args)
    {
	int dimension = Integer.parseInt(args[0]);
	double [][]array = new double [dimension][dimension];
	Random random = new Random();
	for (int i = 0; i < dimension; i++)
	{
	    for (int j= 0; j < dimension; j++)
	    {
		array[i][j] = random.nextInt(dimension) + 0.3*random.nextInt(3*dimension);;
	    }
	}
	Matrix matrix = new Matrix(array);
	System.out.println("Below is your matrix : ");
	matrix.printMatrix();
	System.out.println("Det of your matrix is " + matrix.detOfMatrix());
	boolean b = true;
	b = matrix.isSingular();
	System.out.println("Matrix is singular? " + b);
	if (!b)
	{
	    System.out.println("The inverse of your original matrix is : ");
	    matrix.inverse().printMatrix();
	}
    }
}
