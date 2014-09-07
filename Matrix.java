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

    public static void main(String []args)
    {
	int dimension = 3;
	double [][]array = new double [dimension][dimension];
	for (int i = 0; i < dimension; i++)
	{
	    for (int j= 0; j < dimension; j++)
	    {
		if (i == j)
		    array[i][j] = 1;
		else
		    array[i][j] = 0;
	    }
	}
	Matrix matrix = new Matrix(array);
	System.out.println("Below is your matrix : ");
	matrix.printMatrix();
	System.out.println("Det of your matrix is " + matrix.detOfMatrix());
	boolean b = true;
	b = matrix.isSingular();
	System.out.println("Matrix is singular? " + b);
    }
}
