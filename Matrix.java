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

    public Matrix(int dimension)
    {
	this.dimension = dimension;
	for (int i = 0; i < dimension; i++)
	{
	    for (int j = 0; j < dimension; j++)
	    {
		matrix[i][j] = 0;
	    }
	}
    }

    public Matrix transpose()
    {
	double [][]temp = new double [dimension][dimension];
	for (int i = 0; i < dimension; i++)
	{
	    for (int j = 0; j < dimension; j++)
	    {
		temp[j][i] = matrix[i][j];
	    }
	}
	return new Matrix(temp);
    }

    public Matrix product(Matrix argument)
    {
	double [][]temp = new double [dimension][dimension];
	for (int i = 0; i < dimension; i++)
	{
	    for (int j = 0; j < dimension; j++)
	    {
		double sum = 0;
		for (int k = 0; k < dimension; k++)
		{
		    sum = sum + this.matrix[i][k]*argument.matrix[k][j];
		}
		temp[i][j] = sum;
	    }
	}
	return new Matrix(temp);
    }

    public Vector product(Vector argument)
    {
	double [] temp = new double [dimension];
	for (int i = 0; i < dimension; i++)
	{
	    double sum = 0;
	    for (int j = 0; j < dimension; j++)
	    {
		sum = sum + this.matrix[i][j]*argument.element(j);
	    }
	    temp[i] = sum;
	}
	return new Vector(temp);
    }

    public Matrix add(Matrix argument)
    {
	double [][]temp = new double [dimension][dimension];
	for (int i = 0; i < dimension; i++)
	{
	    for (int j = 0; j < dimension; j++)
	    {
		temp[i][j] = this.matrix[i][j] + argument.matrix[i][j];
	    }
	}
	return new Matrix(temp);
    }

    public Matrix scale(double factor)
    {
	double [][]temp = new double [dimension][dimension];
	for (int i = 0; i < dimension; i++)
	{
	    for (int j = 0; j < dimension; j++)
	    {
		temp[i][j] = factor*this.matrix[i][j];
	    }
	}
	return new Matrix(temp);
    }

    public Matrix subtract(Matrix argument)
    {
	return this.add(argument.scale(-1.0));
    }

    public void printMatrix()
    {
	for (int i = 0; i < dimension; i++)
	{
	    for (int j = 0; j < dimension; j++)
	    {
		System.out.format("%.3f", matrix[i][j]);
		System.out.print("\t");
	    }
	    System.out.println();
	}
    }

    public void printMatrixForMathematica()
    {
        System.out.print("{");
        for (int i = 0; i < dimension; i++)
        {
            System.out.print("{");
            for (int j = 0; j < dimension; j++)
            {
                if (j != dimension - 1)
                {
                    System.out.format("%.6f, ", matrix[i][j]);
                }
                else
                {
                    System.out.format("%.6f", matrix[i][j]);
                } 
            }
            if (i != dimension - 1)
		System.out.print("}, ");
            else
                System.out.print("}");
        }
        System.out.println("}");
    }

    public int getDimension()
    {
	return this.dimension;
    }

    public double [][] getMatrix()
    {
	return matrix;
    }

    public Matrix randomize()
    {
	Random random = new Random();
	double [][]randomArray = new double [dimension][dimension];
	for (int i = 0; i < dimension; i++)
	{
	    for (int j = 0; j < dimension; j++)
	    {
		randomArray[i][j] = random.nextDouble();
	    }
	}
	Matrix randomMatrix = new Matrix(randomArray);
	/*System.out.println("Matrix used to randomize the original matrix : ");
	randomMatrix.printMatrixForMathematica();*/
	return ((randomMatrix.inverse()).product(this)).product(randomMatrix);
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

    public Matrix[] QRDecomposition()
    {
	Vector []xi = new Vector[dimension];
	for (int col = 0; col < dimension; col++)
	{
	    double []temp = new double [dimension];
	    for (int row = 0; row < dimension; row++)
	    {
		temp[row] = matrix[row][col];
	    }
	    xi[col] = new Vector(temp);
	}
	
	Vector []e = new Vector[dimension];
	for (int n = 0; n < dimension; n++)
	{
	    double []nullVector = new double [dimension];
	    for (int i = 0; i < dimension; i++)
	    {
		nullVector[i] = 0;
	    }
	    Vector projection = new Vector(nullVector);
	    for (int i = 0; i <= n - 1; i++)
	    {
		projection = projection.add( e[i].scale(xi[n].innerProductWith(e[i])));
	    }
	    e[n] = xi[n].subtract(projection).normalize();
	}

	double [][]temp = new double [dimension][dimension];
	for (int i = 0; i < dimension; i++)
	{
	    for (int j = 0; j < dimension; j++)
	    {
		temp[j][i] = e[i].element(j);
	    }
	}
	Matrix Q = new Matrix(temp);
	double [][]RArray = new double [dimension][dimension];
	for (int i = 0; i < dimension; i++)
	{
	    for (int j = 0; j < i; j++)
	    {
		RArray[i][j] = 0;
	    }
	}
	for (int i = 0; i < dimension; i++)
	{
	    for (int j = i; j < dimension; j++)
	    {
		RArray[i][j] = e[i].innerProductWith(xi[j]);
	    }
	}
	Matrix R = new Matrix(RArray);
	/*Matrix R = this.inverse().product(Q);
	R = R.inverse();*/
	Matrix [] matrixArray = new Matrix[2];
	matrixArray[0] = Q;
	matrixArray[1] = R;
	return matrixArray;
    }

    private static boolean isDiagonal(Matrix argument)
    {
	int dimension = argument.dimension;
	double epsilon = 0.000001;
	for (int i = 0; i < dimension; i++)
	{
	    for (int j = 0; j < dimension; j++)
	    {
		if (i != j)
		{
		    if (Math.abs(argument.matrix[i][j]) > epsilon)
			return false;
		}
	    }
	}
	return true;
    }

    private static boolean isBlock(Matrix argument)
    {
	int dimension = argument.dimension;
	double epsilon = 0.0000001;
	for (int i = 0; i < dimension - 1; i++)
	{
	    if (Math.abs(argument.matrix[i + 1][i]) > epsilon)
		return true;
	}
	return false;
    }

    public Complex []eigenvalues()
    {
	return this.eigenvalues(600);
    }

    public double trace()
    {
	double sum = 0;
	for (int i = 0; i < dimension; i++)
	{
	    sum = sum + this.matrix[i][i];
	}
	return sum;
    }

    public double normOfMatrix()
    {
	return Math.sqrt(((this.transpose()).product(this)).trace());
    }

    private boolean isUpperTriangle()
    {
	if (dimension <= 0) return false;
	if (this.dimension == 1 || this.dimension == 2) return true;
	double epsilon = 0.0000001;
	if (this.dimension == 3)
	{
	    if (Math.abs(this.matrix[dimension - 1][0]) < epsilon)
	    {
		if (Math.abs(matrix[dimension - 1][1]) < epsilon || Math.abs(matrix[1][0]) < epsilon)
		{
		    return true;
		}
	    }
	    return false;
	}
	else
	{
	    for (int i = 1; i <= dimension - 1; i++)
	    {
		for (int j = 0; j <= i - 2; j++)
		{
		    if (Math.abs(matrix[i][j]) > epsilon)
		    {
			return false;
		    }
		}
	    }
	    return true;
	}
    }

    private Complex [] eigenvalues(int iterationNumber)
    {
	Matrix randomMatrix = this.randomize();
	//System.out.println("Randomized matrix : ");
	//randomMatrix.printMatrixForMathematica();
	boolean hasConverged = false;
	double lambda = 1;
	Matrix []matrixArray = randomMatrix.QRDecomposition();
	for (int i = 0; i < iterationNumber; i++)
	{
	    Matrix tempMatrix = matrixArray[1].product(matrixArray[0]);
	    matrixArray = tempMatrix.QRDecomposition();
	}

	Matrix result = matrixArray[1].product(matrixArray[0]);

	if (result.isUpperTriangle())
	{
	    hasConverged = true;
	}
	else
	{
	    hasConverged = false;
	}

	if (!hasConverged)
	{
	    //System.out.println("QR algorithm is not applicable to the original matrix. ");
	    double [][]lambdaArray = new double [dimension][dimension];
	    for (int i = 0; i < dimension; i++)
	    {
		for (int j = 0; j < dimension; j++)
		{
		    if (i == j)
			lambdaArray[i][j] = lambda;
		    else
			lambdaArray[i][j] = 0;
		}
	    }
	    Matrix lambdaMatrix = new Matrix(lambdaArray);
	    randomMatrix = randomMatrix.subtract(lambdaMatrix);
	    //System.out.println("New matrix : ");
	    //randomMatrix.printMatrix();
	    //randomMatrix.printMatrixForMathematica();
	    matrixArray = randomMatrix.QRDecomposition();
	    for (int i = 0; i < iterationNumber; i++)
	    {
		Matrix tempMatrix = matrixArray[1].product(matrixArray[0]);
		matrixArray = tempMatrix.QRDecomposition();
	    }
	    result = matrixArray[1].product(matrixArray[0]);
	    /*System.out.println("QR Result for new matrix : ");
	    result.printMatrix();
	    result.printMatrixForMathematica();*/
	}

	if (isBlock(result))
	{
	    Complex []eigenvector = new Complex[dimension];
	    int row = 0;
	    double epsilon = 0.0000001;
	    while (row < dimension - 1)
	    {
		if (Math.abs(result.matrix[row + 1][row]) < epsilon)
		{
		    eigenvector[row] = Complex.toComplex(result.matrix[row][row]);
		    row++;
		}
		else
		{
		    double a = result.matrix[row][row];
		    double b = result.matrix[row][row + 1];
		    double c = result.matrix[row + 1][row];
		    double d = result.matrix[row + 1][row + 1];
		    double trace = a + d;
		    double determinant = a*d - b*c;
		    double discriminant = trace*trace - 4*determinant;
		    if (discriminant < 0)
		    {
			eigenvector[row] = new Complex(0.5*trace, 0.5*Math.sqrt(Math.abs(discriminant)));
			eigenvector[row + 1] = eigenvector[row].conjugate();
		    }
		    else
		    {
			eigenvector[row] = new Complex(0.5*(trace + Math.sqrt(discriminant)), 0);
			eigenvector[row + 1] = new Complex(0.5*(trace - Math.sqrt(discriminant)), 0);
		    }
		    row = row + 2;
		}
	    }
	    if (row == dimension - 1)
	    {
		eigenvector[row] = Complex.toComplex(result.matrix[row][row]);
	    }
	    if (hasConverged)
		return eigenvector;
	    else
	    {
		for (int i = 0; i < dimension; i++)
		{
		    eigenvector[i] = eigenvector[i].add(Complex.toComplex(lambda));
		}
		return eigenvector;
	    }
	}
	else
	{
	    Complex []eigenvector = new Complex[dimension];
	    for (int i = 0; i < dimension; i++)
	    {
		eigenvector[i] = Complex.toComplex(result.matrix[i][i]);
	    }
	    if (hasConverged)
		return eigenvector;
	    else
	    {
		for (int i = 0; i < dimension; i++)
		{
		    eigenvector[i] = eigenvector[i].add(Complex.toComplex(lambda));
		}
		return eigenvector;
	    }
	}
    }

    public static void main(String []args)
    {
	if (args.length != 1)
	{
	    throw new IllegalArgumentException("Matrix dimension = args[0]");
	}
	int dimension = Integer.parseInt(args[0]);
	Random random = new Random();
	double [][]array = new double [dimension][dimension];
	for (int i = 0; i < dimension; i++)
	{
	    for (int j = 0; j < dimension; j++)
	    {
		if (i == j + 1)
		    array[i][j] = 1;
		/*else if (i == j)
		    array[i][j] = -1;*/
		else
		    array[i][j] = 0;
	    }
	}
	array[0][dimension - 1] = 1;


	Matrix matrix = new Matrix(array);
	matrix.printMatrix();
	matrix.printMatrixForMathematica();
	System.out.println("Testing QRDecomposition : ");
	Matrix [] arrayMatrix = matrix.QRDecomposition();
	System.out.println("Q matrix is ");
	arrayMatrix[0].printMatrix();
	System.out.println("R matrix is ");
	arrayMatrix[1].printMatrix();
	System.out.println("Product of Q and R is ");
	arrayMatrix[0].product(arrayMatrix[1]).printMatrix();

	System.out.println("Testing eigenvalues: ");
	Complex [] eigenvector = matrix.eigenvalues();
	for (int i = 0; i < eigenvector.length; i++)
	{
	    System.out.println(eigenvector[i]);
	}
    }
}
